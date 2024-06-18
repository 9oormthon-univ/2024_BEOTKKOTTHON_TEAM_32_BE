package site.balpyo.ai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


/**
 * @author dongheonlee
 */
@Data
public class EstimateRequestDTO {

    // 소요시간을 계산할 입력 텍스트
    @NotBlank(message = "text는 비어 있을 수 없습니다.")
    private String text;

}
