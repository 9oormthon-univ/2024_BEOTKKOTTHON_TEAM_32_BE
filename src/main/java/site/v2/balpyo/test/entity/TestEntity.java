package site.v2.balpyo.test.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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