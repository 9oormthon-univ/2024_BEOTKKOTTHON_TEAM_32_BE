package site.balpyo.script.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScriptRequest {
    private String script;
    private String gptId;
    private String uid;
    private String title;
    private Integer secTime;
}
