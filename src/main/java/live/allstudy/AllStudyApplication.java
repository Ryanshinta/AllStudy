package live.allstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@EntityScan("live.allstudy.entity")
@SpringBootApplication
public class AllStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllStudyApplication.class, args);
    }

}
