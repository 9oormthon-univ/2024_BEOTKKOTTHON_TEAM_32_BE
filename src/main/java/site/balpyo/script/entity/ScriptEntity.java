package site.balpyo.script.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import site.balpyo.ai.entity.AIGenerateLogEntity;
import site.balpyo.guest.entity.GuestEntity;


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
    @Column(columnDefinition = "LONGTEXT")
    private String script;

    private String title;

    private Integer secTime;

    private String voiceFilePath;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ai_log_id")
    private AIGenerateLogEntity aiGenerateLogEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private GuestEntity guestEntity;

    @Column(nullable = false)
    private Boolean isGenerating; // 작업 진행 중 여부를 나타내는 필드


}

