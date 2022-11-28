package live.allstudy;

import live.allstudy.entity.PostEntity;
import live.allstudy.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class SpringDataTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void contextLoads() {
//        String date = "Sat Nov 26 2022 21:31:45 GMT+0800 (Malaysia Time)";
//        DateTimeFormatter f =
//        System.out.println(date.);

    }
}
