package site.v1.balpyo.ai.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author dongheonlee
 */
@Data
public class EstimateRequestDTO {

    // 소요시간을 계산할 입력 텍스트
    @NotBlank(message = "text는 비어 있을 수 없습니다.")
    private String text;

}
