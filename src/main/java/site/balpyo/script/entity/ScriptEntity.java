package site.balpyo.script.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import site.balpyo.ai.entity.AIGenerateLogEntity;
import site.balpyo.ai.entity.GPTInfoEntity;
import site.balpyo.user.entity.GuestEntity;

import javax.persistence.*;
import java.rmi.server.UID;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "script")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScriptEntity {

    @Id
    @GeneratedValue
    private Long script_id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "scriptEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private AIGenerateLogEntity aiGenerateLogEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id")
    private GuestEntity guestEntity;


}

