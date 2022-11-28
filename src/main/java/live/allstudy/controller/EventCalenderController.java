package live.allstudy.controller;


import live.allstudy.dto.UserIDDTO;
import live.allstudy.dto.insertEventDTO;
import live.allstudy.dto.updateEventDTO;
import live.allstudy.entity.EventCalenderEntity;
import live.allstudy.service.EventCalenderService;
import live.allstudy.service.UserService;
import live.allstudy.util.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

/**
 * @author Ryan
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class EventCalenderController {

    @Autowired
    EventCalenderService eventCalenderService;
    
    UserService userService;

    @PostMapping("/insertEvent")
    public ResponseEntity<ResponseObj> insertEvent(@RequestBody insertEventDTO insertEventDTO){
        EventCalenderEntity eventCalenderEntity = new EventCalenderEntity();

        eventCalenderEntity.setTitle(insertEventDTO.getTitle());
        eventCalenderEntity.setStart(insertEventDTO.getStart());
        eventCalenderEntity.setEnd(insertEventDTO.getEnd());
        eventCalenderEntity.setCreatedAt(Instant.now());
        eventCalenderEntity.setBackgroundColor(insertEventDTO.getBackgroundColor());
        eventCalenderEntity.setTextColor(insertEventDTO.getTextColor());



        return new ResponseEntity<ResponseObj>(eventCalenderService.insertEvent(eventCalenderEntity,insertEventDTO.getUserId()), HttpStatus.OK);
    }

    @PostMapping("/getAllEventByUserId")
    public ResponseEntity<ResponseObj> getAllEventByUserId(@RequestBody UserIDDTO userIDDTO){
        return new ResponseEntity<ResponseObj>(eventCalenderService.getAllEventByUserId(userIDDTO.getId()), HttpStatus.OK);
    }

    @PostMapping("/deleteEvent")
    public ResponseEntity<ResponseObj> deleteEvent(@RequestBody UserIDDTO eventID){
        return new ResponseEntity<ResponseObj>(eventCalenderService.deleteEvent(eventID.getId()), HttpStatus.OK);
    }

    @PostMapping("/updateEvent")
    public ResponseEntity<ResponseObj> updateEvent(@RequestBody updateEventDTO updateEventDTO){

        EventCalenderEntity eventCalenderEntity = new EventCalenderEntity();

        eventCalenderEntity.setId(updateEventDTO.getId());
        eventCalenderEntity.setStart(updateEventDTO.getStart());
        eventCalenderEntity.setEnd(updateEventDTO.getEnd());
        eventCalenderEntity.setTitle(updateEventDTO.getTitle());

        return new ResponseEntity<ResponseObj>(eventCalenderService.updateEvent(eventCalenderEntity), HttpStatus.OK);
    }

    @PostMapping("/getEventById")
    public ResponseEntity<ResponseObj> getEventById(@RequestBody UserIDDTO eventID){
        return new ResponseEntity<ResponseObj>(eventCalenderService.getEventById(eventID.getId()), HttpStatus.OK);
    }

}
