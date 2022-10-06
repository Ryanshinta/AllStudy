package live.allstudy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String userId;

    private String originalUserId;

    private String content;

    private String image;

    private Instant createdAt;

    @OneToMany(mappedBy = "post")
    List<LikeEntity> like;

    @OneToMany(mappedBy = "post")
    List<CommentEntity> comment;
}
