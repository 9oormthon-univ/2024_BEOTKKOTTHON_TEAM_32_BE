package site.balpyo.script.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScriptResponse {

        private String script;
        private String gptId;
        private String uid;
        private String title;
        private Integer secTime;
}
