package site.v2.balpyo.common.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

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
