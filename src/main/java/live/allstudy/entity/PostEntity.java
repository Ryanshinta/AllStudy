package live.allstudy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private String id;

    private String userId;

    private String originalUserId;

    private String content;

    private String image;

    private Instant createdAt;

    List<String> like = new ArrayList<>();

    List<String> share = new ArrayList<>();

    List<CommentEntity> comment = new ArrayList<>();
}
