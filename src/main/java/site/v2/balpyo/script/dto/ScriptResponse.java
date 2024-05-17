package site.v2.balpyo.script.dto;

import lombok.*;

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
