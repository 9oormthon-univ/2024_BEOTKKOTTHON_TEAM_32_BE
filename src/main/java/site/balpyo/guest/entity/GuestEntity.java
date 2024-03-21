package site.balpyo.guest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import site.balpyo.script.entity.ScriptEntity;

import javax.persistence.*;
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
    private String uid;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "guestEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScriptEntity> scriptEntities;

}
