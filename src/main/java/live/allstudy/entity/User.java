package live.allstudy.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "user")
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


}