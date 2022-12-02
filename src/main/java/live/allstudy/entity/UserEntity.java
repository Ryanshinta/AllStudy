package live.allstudy.entity;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "User")
public class UserEntity {
    @Id
    private String id;

    private String username;

    private String firstName;

    private String lastName;

    private String phone;

    private String gender;

    private String email;

    private String password;

    private String profile;

    private String role;

    public void setUsername(String username) {
        this.username = lastName + " " + firstName;
    }

    List<String> following = new ArrayList<>();

    List<String> follower = new ArrayList<>();

    private String resetPasswordToken;
}
