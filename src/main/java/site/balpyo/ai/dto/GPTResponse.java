package site.balpyo.ai.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GPTResponse {

    @JsonProperty("id")
    private String gptInfoId;

    @JsonProperty("object")
    private String gptObject;

    @JsonProperty("model")
    private String gptModel;

    @JsonProperty("choices")
    private List<?> gptGeneratedScript;

    @JsonProperty("created")
    private Integer gptCreatedAt;

    @JsonProperty("usage")
    private Usage usage;

    @Getter
    public static class Usage {
        @JsonProperty("prompt_tokens")
        private Integer promptToken;

        @JsonProperty("completion_tokens")
        private Integer completionToken;

        @JsonProperty("total_tokens")
        private Integer totalToken;

    }


}
