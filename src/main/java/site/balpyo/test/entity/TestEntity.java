package site.balpyo.test.entity;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@Entity
@Table(name = "test")
public class TestEntity {

    @Id
    @GeneratedValue
    private Long testId;

    private String testContent;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public TestEntity(Long testId, String testContent, LocalDateTime createdAt) {
        this.testId = testId;
        this.testContent = testContent;
        this.createdAt = createdAt;
    }
}