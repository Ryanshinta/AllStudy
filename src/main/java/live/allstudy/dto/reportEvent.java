package live.allstudy.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class reportEvent {
    Instant start;
    Instant end;
    List<Integer> events;
}
