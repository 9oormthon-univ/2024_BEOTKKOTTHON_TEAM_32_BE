package site.v2.balpyo.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class AIGenerateResponse {
    private Object resultScript;
    private String gptId;
}
