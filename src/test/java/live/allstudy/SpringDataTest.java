//package live.allstudy;
//
//import live.allstudy.entity.EventCalenderEntity;
//import live.allstudy.entity.PostEntity;
//import live.allstudy.repository.EventCalenderRepository;
//import live.allstudy.repository.PostRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.Instant;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//
//@SpringBootTest
//public class SpringDataTest {
//
//    @Autowired
//    EventCalenderRepository eventCalenderRepository;
//
//    @Test
//    void contextLoads() {
//
////        Instant start = Instant.now().minus(3, ChronoUnit.DAYS);
////        Instant end = Instant.now().plus(4, ChronoUnit.DAYS);
////        System.out.println(start);
////        System.out.println(end);
////        Optional<List<EventCalenderEntity>> result = eventCalenderRepository.findByStartBetween(start, end);
////
////        List<EventCalenderEntity> events = result.get();
////        List<EventCalenderEntity> userEvents = new ArrayList<>();
//        List<Integer> result = new ArrayList<>(7);
//        Instant start ;
//        Instant end ;
//        for (int i = 7; i > 0 ; i--) {
//            start = Instant.now().minus(i,ChronoUnit.DAYS);
//            end = Instant.now().minus(i-1,ChronoUnit.DAYS);
//            Optional<List<EventCalenderEntity>> byStart = eventCalenderRepository.findByStartBetween(start,end);
//            if (byStart.isEmpty()){
//                result.add(0);
//            }else {
//                List<EventCalenderEntity> allEvents = byStart.get();
//                Integer currDayEvent = 0;
//                for (EventCalenderEntity entity : allEvents){
//                    if (entity.getUserId().equals("638ada809bb5f214aaebe64b")){
//                        currDayEvent++;
//                    }
//                }
//                result.add(currDayEvent);
//            }
//        }
//
//        System.out.println(result.toString());
//
//    }
//}
