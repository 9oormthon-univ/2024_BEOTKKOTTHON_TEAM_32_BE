package site.balpyo.ai.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import site.balpyo.ai.dto.AIGenerateRequest;
import site.balpyo.guest.entity.GuestEntity;
import site.balpyo.script.entity.ScriptEntity;

import java.time.LocalDateTime;

@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private GuestEntity guestEntity;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "gpt_info_id", referencedColumnName = "gptInfoId")
    private GPTInfoEntity gptInfoEntity;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "aiGenerateLogEntity", cascade = CascadeType.ALL)
    private ScriptEntity scriptEntity;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public AIGenerateLogEntity convertToEntity(AIGenerateRequest aiGenerateRequest, GPTInfoEntity gptInfoEntity, GuestEntity guestEntity){
        return AIGenerateLogEntity.builder()
                .secTime(aiGenerateRequest.getSecTime())
                .topic(aiGenerateRequest.getTopic())
                .keywords(aiGenerateRequest.getKeywords())
                .secPerLetter(0) // TODO :: 차후 0 값 변경
                .gptInfoEntity(gptInfoEntity)
                .guestEntity(guestEntity)
                .build();
    }

}
