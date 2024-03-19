package site.balpyo.ai.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GPTResponse {

    @JsonProperty("id")
    private String gptInfoId;

    @JsonProperty("object")
    private String gptObject;

    @JsonProperty("model")
    private String gptModel;

    @JsonProperty("choices")
    private List<Choice> gptGeneratedScript;

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

    @Getter
    public static class Choice {
        @JsonProperty("index")
        private Integer index;

        @JsonProperty("message")
        private Message message;

        @JsonProperty("logprobs")
        private Object logprobs;

        @JsonProperty("finish_reason")
        private String finishReason;

        @Getter
        public static class Message {
            @JsonProperty("role")
            private String role;

            @JsonProperty("content")
            private String content;
        }
    }
}
