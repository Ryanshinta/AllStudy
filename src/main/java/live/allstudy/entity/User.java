package live.allstudy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "user")
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "default 1")
    private boolean status;

    private String avatar;

    private Date create_time;

    private Date update_time;
}