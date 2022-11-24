package live.allstudy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

/**
 * @author JIAHAO_CHAI
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Request")
public class PartnerRequestEntity {

    @Id
    private String id;

    private String requestEmail;

    private String receiveEmail;

    private String username;

    private String status;

    private String dateTime;
}
