package site.balpyo.common.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;
import site.balpyo.common.dto.UploadResponseDTO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class S3Util {
    private final S3Client s3Client;

    public void deleteFileFromS3(String objectPath) {
        AmazonS3 s3 = s3Client.getAmazonS3();
        try {
            s3.deleteObject(s3Client.getBucket(), objectPath);
            log.info("Delete Object successfully");
        } catch (SdkClientException e) {
            e.printStackTrace();
            log.info("Error deleteFileFromS3");
        }
    }

}
