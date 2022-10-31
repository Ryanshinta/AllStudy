package live.allstudy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "UserConnection")
public class UserConnectionEntity {
    @Id
    private String id;

    List<String> following = new ArrayList<>();
    List<String> follower = new ArrayList<>();
}
