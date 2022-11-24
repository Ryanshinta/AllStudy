package live.allstudy.dto;


import live.allstudy.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPostRequestEntity {
    private CommentEntity commentEntity;
    private UserEmailDTO postId;
}
