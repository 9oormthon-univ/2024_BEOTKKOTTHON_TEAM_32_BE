package site.balpyo.ai.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import site.balpyo.ai.dto.GPTResponse;

import javax.annotation.processing.Completion;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "gpt_info_entity")
@Builder
@AllArgsConstructor
public class GPTInfoEntity {

    @Id
    private String gptInfoId;

    private String gptObject;

    private String gptModel;

    private Integer gptCreatedAt;

    private Integer promptToken;

    @Lob
    private String gptGeneratedScript;

    private Integer completionToken;

    private Integer totalToken;

    @OneToOne(mappedBy = "gptInfoEntity")
    private AIGenerateLogEntity aiGenerateLogEntity;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public GPTInfoEntity ResponseBodyToGPTInfoEntity(Object resultBody){

        ObjectMapper mapper = new ObjectMapper();
        GPTResponse response = mapper.convertValue(resultBody, GPTResponse.class);


        return GPTInfoEntity.builder()
                .gptInfoId(response.getGptInfoId())
                .gptObject(response.getGptObject())
                .gptModel(response.getGptModel())
                .gptCreatedAt(response.getGptCreatedAt())
                .promptToken(response.getUsage().getPromptToken())
                .gptGeneratedScript(response.getGptGeneratedScript().get(0).getMessage().getContent().toString())
                .completionToken(response.getUsage().getCompletionToken())
                .totalToken(response.getUsage().getTotalToken())
                .build();
    }
}
