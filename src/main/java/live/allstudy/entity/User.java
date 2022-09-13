package live.allstudy.entity;

import lombok.*;

import javax.persistence.*;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Nullable;

import java.util.Date;

@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String email;

    private String name;

    private String password;

    private String token;

    private boolean status;

    private String avatar;

    @Column(updatable = false)
    @CreatedDate
    private Date create_time;

    @UpdateTimestamp
    private Date update_time;
}