package live.allstudy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "EventCalender")
public class EventCalenderEntity {
    @Id
    private String id;

    private String userId;

    private String title;

    private Instant start;

    private Instant end;

    private String backgroundColor;

    private String textColor;

    private Instant createdAt;
}
