package live.allstudy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import live.allstudy.repository.UserRepository;

@SpringBootTest
public class SpringDataTest {
    
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSpringData() {
        
    }
}
