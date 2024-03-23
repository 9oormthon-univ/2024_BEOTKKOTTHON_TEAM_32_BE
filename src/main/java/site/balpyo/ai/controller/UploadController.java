package site.balpyo.ai.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.balpyo.ai.dto.upload.UploadFileDTO;
import site.balpyo.ai.dto.upload.UploadResultDTO;
import site.balpyo.common.s3.S3Client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Log4j2
public class UploadController {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final S3Client s3Client;

    //post 방식으로 파일 등록
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadResultDTO upload(UploadFileDTO uploadFileDTO) throws IOException {

        AmazonS3 s3 = s3Client.getAmazonS3();

        MultipartFile multipartFile = uploadFileDTO.getFile();

        String profileURL = null;

        if (multipartFile != null) {
            String originalName = multipartFile.getOriginalFilename();
            log.info(originalName);

            String uuid = UUID.randomUUID().toString();

            // "_" 제거
            originalName = originalName.replaceAll("_", "");

            String originalFileName = uuid + "_" + originalName;

            File originalFile = new File(originalFileName);

            try {
                originalFile = convertMultipartFileToFile(uploadFileDTO.getFile(), originalFileName);

                String objectPath = "/" + originalFile;

                String baseUploadURL = "https://balpyo-bucket.s3.ap-northeast-2.amazonaws.com/audio";
                profileURL = baseUploadURL + objectPath;
                log.info(profileURL);

                s3.putObject(bucketName, objectPath, originalFile);
                setAcl(s3, objectPath);


            } finally {
                assert originalFile != null;
                originalFile.delete();
            }

            return UploadResultDTO.builder()
                    .profileUrl(profileURL)
                    .build();

        } else {
            return null;
        }
    }

    public File convertMultipartFileToFile(MultipartFile multipartFile, String fileName) {
        File convertedFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return convertedFile;
    }

    public void setAcl(AmazonS3 s3, String objectPath) {
        AccessControlList objectAcl = s3.getObjectAcl(bucketName, objectPath);
        objectAcl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        s3.setObjectAcl(bucketName, objectPath, objectAcl);
    }
}
