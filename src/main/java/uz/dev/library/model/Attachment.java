package uz.dev.library.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Created by: asrorbek
 * DateTime: 5/1/25 21:42
 **/

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String path;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "content_type")
    private String contentType;

    private Long size;

    public Attachment(String path, String originalName, String contentType, Long size) {
        this.path = path;
        this.originalName = originalName;
        this.contentType = contentType;
        this.size = size;
    }

}
