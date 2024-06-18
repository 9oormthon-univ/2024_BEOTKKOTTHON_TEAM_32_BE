package site.balpyo.voice.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "voice")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voice_id;

    @Column
    private String file_path;

    @Column
    private Integer play_time;

    @Column
    private String speech_mark;

}
