package site.balpyo.ai.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import site.balpyo.ai.dto.AIGenerateRequest;
import site.balpyo.script.entity.ScriptEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ai_generate_log")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AIGenerateLogEntity {

    @Id
    @GeneratedValue
    private Long aiLogId;

    private Integer secTime;

    private String topic;

    private String keywords;

    private double secPerLetter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gpt_info_id", referencedColumnName = "gptInfoId")
    private GPTInfoEntity gptInfoEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "script_id")
    private ScriptEntity scriptEntity;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public AIGenerateLogEntity convertToEntity(AIGenerateRequest aiGenerateRequest, GPTInfoEntity gptInfoEntity){
        return AIGenerateLogEntity.builder()
                .secTime(aiGenerateRequest.getSecTime())
                .topic(aiGenerateRequest.getTopic())
                .keywords(aiGenerateRequest.getKeywords())
                .secPerLetter(0) // TODO :: 차후 0 값 변경
                .gptInfoEntity(gptInfoEntity)
                .build();
    }

}
