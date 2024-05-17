package site.v1.balpyo.script.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ScriptResponse {

        private Long scriptId;
        private String script;
        private String gptId;
        private String uid;
        private String title;
        private Integer secTime;
        private String voiceFilePath;
        private String status;

}
