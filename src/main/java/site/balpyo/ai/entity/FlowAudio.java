package site.balpyo.ai.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "aiGenerateLogEntity")
public class FlowAudio {

    @Id
    @Column(name = "profileUrl")
    private String profileUrl;

    @OneToOne
    private AIGenerateLogEntity aiGenerateLogEntity;

    public void changeAudio(AIGenerateLogEntity aiGenerateLogEntity) {
        this.aiGenerateLogEntity = aiGenerateLogEntity;
    }

}

