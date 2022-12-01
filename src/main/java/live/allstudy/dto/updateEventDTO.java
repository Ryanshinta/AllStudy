package live.allstudy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class updateEventDTO {
    private String id;

    private String title;

    private Instant start;

    private Instant end;
}
