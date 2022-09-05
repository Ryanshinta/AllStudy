package live.allstudy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import live.allstudy.entity.User;
import live.allstudy.repository.UserRepository;
import live.allstudy.service.UserDetailsService;

@SpringBootTest
public class SpringDataTest {
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    public void testSpringData() {
        User user = new User();
        user.setCreate_time(create_time);
        userDetailsService.saveUser(user)
    }
}
