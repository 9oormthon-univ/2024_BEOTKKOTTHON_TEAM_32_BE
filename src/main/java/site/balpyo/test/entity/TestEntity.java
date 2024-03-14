package site.balpyo.test.entity;

import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private Long test_id;

    private String test_content;

    @CreationTimestamp
    private LocalDateTime created_at;

    @Builder
    public TestEntity(Long test_id, String test_content, LocalDateTime created_at) {
        this.test_id = test_id;
        this.test_content = test_content;
        this.created_at = created_at;
    }
}