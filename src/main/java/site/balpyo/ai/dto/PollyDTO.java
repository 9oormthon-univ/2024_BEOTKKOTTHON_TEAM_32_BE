package site.balpyo.ai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


/**
 * @author dongheonlee
 */
@Data
public class PollyDTO {

    // 음성으로 변환할 텍스트
    @NotBlank(message = "text는 비어 있을 수 없습니다.")
    private String text;

    // 빠르기 조절 [-2, -1, 0, 1, 2]
    @NotBlank(message = "speed는 비어 있을 수 없습니다.")
    private int speed;

    @NotBlank(message = "balpyoSecretKey는 비어 있을 수 없습니다.")
    private String balpyoAPIKey;
}
