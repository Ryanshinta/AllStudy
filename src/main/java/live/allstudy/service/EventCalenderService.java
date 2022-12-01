package live.allstudy.service;

import live.allstudy.entity.EventCalenderEntity;
import live.allstudy.entity.PostEntity;
import live.allstudy.entity.UserEntity;
import live.allstudy.repository.EventCalenderRepository;
import live.allstudy.repository.UserRepository;
import live.allstudy.util.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Ryan
 */

@Service
public class EventCalenderService {
    @Autowired
    EventCalenderRepository eventCalenderRepository;

    @Autowired
    UserRepository userRepository;

    public ResponseObj getAllEventByUserId(String userId){
        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()){
            responseObj.setStatus("fail");
            responseObj.setMessage("User not found");
            return responseObj;
        }else {
            UserEntity EventUser = userEntity.get();
            Optional<List<EventCalenderEntity>> Events = eventCalenderRepository.findByUserId(EventUser.getId());
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            responseObj.setPayload(Events);

        }
        return responseObj;
    }


    public ResponseObj insertEvent(EventCalenderEntity inputEvent,String userId){
        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()){
            responseObj.setStatus("fail");
            responseObj.setMessage("User not found");
            return responseObj;
        }else {
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            inputEvent.setUserId(userId);
            responseObj.setPayload(eventCalenderRepository.save(inputEvent));
        }
        return responseObj;
    }

    public ResponseObj deleteEvent(String eventId){
        ResponseObj responseObj = new ResponseObj();
        Optional<EventCalenderEntity> eventEntity = eventCalenderRepository.findById(eventId);
        if (eventEntity.isEmpty()){
            responseObj.setStatus("fail");
            responseObj.setMessage("Event not found");
            return responseObj;
        }else {
            eventCalenderRepository.deleteById(eventId);
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            responseObj.setPayload(null);
        }
        return responseObj;
    }

    public ResponseObj updateEvent(EventCalenderEntity inputEvent){
        ResponseObj responseObj = new ResponseObj();
        Optional<EventCalenderEntity> eventEntity = eventCalenderRepository.findById(inputEvent.getId());
        if (eventEntity.isEmpty()){
            responseObj.setStatus("fail");
            responseObj.setMessage("Event not found");
            return responseObj;
        }else {
            EventCalenderEntity entity = eventEntity.get();
            if (!inputEvent.getTitle().isEmpty()){
                entity.setTitle(inputEvent.getTitle());
            }
            if (inputEvent.getStart() != null){
                entity.setStart(inputEvent.getStart());
            }

            if (inputEvent.getEnd() != null){
                entity.setEnd(inputEvent.getEnd());
            }

            responseObj.setStatus("success");
            responseObj.setMessage("success");
            responseObj.setPayload(eventCalenderRepository.save(entity));
        }
        return responseObj;
    }

    public ResponseObj getEventById(String eventId){
        ResponseObj responseObj = new ResponseObj();
        Optional<EventCalenderEntity> eventEntity = eventCalenderRepository.findById(eventId);
        if (eventEntity.isEmpty()){
            responseObj.setStatus("fail");
            responseObj.setMessage("Event not found");
            return responseObj;
        }else {
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            responseObj.setPayload(eventEntity);
        }
        return responseObj;
    }
}
