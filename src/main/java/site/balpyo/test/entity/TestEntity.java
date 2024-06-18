package site.balpyo.test.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;


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