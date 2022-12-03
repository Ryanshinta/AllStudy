package live.allstudy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "StudyRoom")
public class reportEntity {
    @Id
    private String id;

    private String userId;

    private Integer totalLogin;

    private Integer totalLike;

    private Integer totalFollowers;

    private Integer totalFollowing;

    private Integer totalPost;

    private Integer publicRoomJoin;

    private Integer privateRoomJoin;

    private Integer totalEvent;

}
