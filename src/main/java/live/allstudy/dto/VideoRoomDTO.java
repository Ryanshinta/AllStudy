package live.allstudy.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoRoomDTO {
    String sessionId;
    String roomName;
    String roomDesc;
}
