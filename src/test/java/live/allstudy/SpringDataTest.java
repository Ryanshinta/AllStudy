package live.allstudy;

import live.allstudy.entity.PostEntity;
import live.allstudy.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;


@SpringBootTest
public class SpringDataTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void contextLoads() {
        try {
            Optional<List<PostEntity>> byUserId = postRepository.findAllByOrderByIdAsc();
            List<PostEntity> postEntities = byUserId.get();
            System.out.println(postEntities.toString());
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}
