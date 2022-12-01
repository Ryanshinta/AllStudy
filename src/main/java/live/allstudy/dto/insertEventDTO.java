package live.allstudy.dto;


import live.allstudy.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class insertEventDTO {
    private String userId;

    private String title;

    private Instant start;

    private String backgroundColor;

    private String textColor;

    private Instant end;
}
