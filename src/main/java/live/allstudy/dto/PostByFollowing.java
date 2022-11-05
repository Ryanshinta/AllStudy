package live.allstudy.dto;

import live.allstudy.entity.PostEntity;
import live.allstudy.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostByFollowing {
    private UserEntity user;
    private PostEntity post;
}
