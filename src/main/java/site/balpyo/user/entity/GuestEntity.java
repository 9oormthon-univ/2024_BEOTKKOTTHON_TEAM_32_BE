package site.balpyo.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import site.balpyo.ai.entity.GPTInfoEntity;
import site.balpyo.script.entity.ScriptEntity;

import javax.persistence.*;
import java.rmi.server.UID;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "guest")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestEntity {

    @Id
    @GeneratedValue
    private String uid;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "guestEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScriptEntity> scriptEntities;

}
