package live.allstudy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "StudyRoom")
public class StudyRoomEntity {
    @Id
    private String sessionID;

    private String userID;

    private String userName;

    private String roomName;

    private String roomDesc;

    private String password;

    List<String> userInRoom = new ArrayList<>();

    private Instant createdAt;
}
