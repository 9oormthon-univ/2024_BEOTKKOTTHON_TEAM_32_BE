package site.balpyo.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;




@Data
public class AIGenerateRequest {

    @NotBlank(message = "Topic은 비어 있을 수 없습니다.")
    private String topic;

    @NotBlank(message = "Keywords는 비어 있을 수 없습니다.")
    private String keywords;

    @NotNull(message = "SecTime은 null일 수 없습니다.")
    private Integer secTime;

    private boolean test;

    @NotBlank(message = "balpyoSecretKey는 비어 있을 수 없습니다.")
    private String balpyoAPIKey;

}
