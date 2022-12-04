package live.allstudy.controller;


import live.allstudy.dto.UserIDDTO;
import live.allstudy.entity.EventCalenderEntity;
import live.allstudy.entity.UserEntity;
import live.allstudy.entity.reportEntity;
import live.allstudy.repository.EventCalenderRepository;
import live.allstudy.repository.ReportRepository;
import live.allstudy.repository.UserRepository;
import live.allstudy.service.ReportService;
import live.allstudy.util.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Ryan
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ReportController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    ReportService reportService;

    @Autowired
    EventCalenderRepository eventCalenderRepository;

    private boolean isUserExist(String userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return false;
        }
        return true;
    }

    @PostMapping("/getReport")
    public ResponseEntity<ResponseObj> getReport(@RequestBody UserIDDTO userIDDTO){
        ResponseObj responseObj = new ResponseObj();

        if (isUserExist(userIDDTO.getId())){
            reportService.updateTotalLike(userIDDTO.getId());
            reportService.updateTotalFollowing(userIDDTO.getId());
            reportService.updateTotalFollower(userIDDTO.getId());
            reportService.updateTotalPost(userIDDTO.getId());
            reportService.updateTotalEvent(userIDDTO.getId());

            Optional<reportEntity> userReport = reportRepository.findByUserId(userIDDTO.getId());
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            responseObj.setPayload(userReport.get());
            return new ResponseEntity<>(responseObj,HttpStatus.OK);
        }else {
            responseObj.setStatus("fail");
            responseObj.setMessage("Cannot find user by id "+userIDDTO.getId());
            responseObj.setPayload(null);

            return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/getEventReport")
    public ResponseEntity<ResponseObj> getEvent(@RequestBody UserIDDTO userIDDTO){
        ResponseObj responseObj = new ResponseObj();
        if (isUserExist(userIDDTO.getId())){
            responseObj.setStatus("fail");
            responseObj.setMessage("fail");
            responseObj.setPayload(null);
        }

        List<Integer> result = new ArrayList<>(7);
        Instant start ;
        Instant end ;
        for (int i = 7; i > 0 ; i--) {
            start = Instant.now().minus(i,ChronoUnit.DAYS);
            end = Instant.now().minus(i-1,ChronoUnit.DAYS);
            Optional<List<EventCalenderEntity>> byStart = eventCalenderRepository.findByStartBetween(start,end);
            if (byStart.isEmpty()){
                result.add(0);
            }else {
                List<EventCalenderEntity> allEvents = byStart.get();
                Integer currDayEvent = 0;
                for (EventCalenderEntity entity : allEvents){
                    if (entity.getUserId().equals(userIDDTO.getId())){
                        currDayEvent++;
                    }
                }
                result.add(currDayEvent);
            }
        }
        responseObj.setStatus("success");
        responseObj.setMessage("success");
        responseObj.setPayload(result);
        return new ResponseEntity<>(responseObj,HttpStatus.OK);
    }


}
