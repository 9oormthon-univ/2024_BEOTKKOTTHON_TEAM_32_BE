package site.balpyo.ai.dto.upload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadFileDTO {

    private MultipartFile file;
}
