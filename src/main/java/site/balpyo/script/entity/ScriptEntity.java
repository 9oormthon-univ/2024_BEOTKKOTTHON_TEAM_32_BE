package site.balpyo.script.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import site.balpyo.ai.entity.AIGenerateLogEntity;
import site.balpyo.guest.entity.GuestEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "script")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScriptEntity {

    @Id
    @GeneratedValue
    private Long script_id;

    @Lob
    private String script;

    private String title;

    private Integer secTime;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ai_log_id")
    private AIGenerateLogEntity aiGenerateLogEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "uid")
    private GuestEntity guestEntity;


}

