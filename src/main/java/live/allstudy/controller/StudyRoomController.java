package live.allstudy.controller;



import io.openvidu.java.client.*;
import live.allstudy.dto.VideoRoomDTO;
import live.allstudy.entity.StudyRoomEntity;
import live.allstudy.repository.StudyRoomRepository;

import live.allstudy.util.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.openvidu.java.client.MediaMode.ROUTED;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class StudyRoomController {



    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;

    private OpenVidu openVidu;

    @Autowired
    private StudyRoomRepository roomRepository;

    @PostConstruct
    public void init() {
        this.openVidu = new OpenVidu(OPENVIDU_URL,OPENVIDU_SECRET);
    }

    @PostMapping("/session")
    public ResponseEntity<String> initializeSession (@RequestBody VideoRoomDTO roomInfo)
        throws OpenViduJavaClientException, OpenViduHttpException {
        Map<String, String> params = new HashMap<>(1);
        params.put("customSessionId",roomInfo.getSessionId());
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openVidu.createSession(properties);
        StudyRoomEntity studyRoom = new StudyRoomEntity();

        studyRoom.setSessionID(session.getSessionId());
        studyRoom.setRoomName(roomInfo.getRoomName());
        studyRoom.setCreatedAt(Instant.now());
        studyRoom.setRoomDesc(roomInfo.getRoomDesc());
        roomRepository.save(studyRoom);

        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }

    @PostMapping("/sessions/{sessionId}/connections")
    public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
                                                   @RequestBody(required = false) Map<String, Object> params)
    throws OpenViduHttpException,OpenViduJavaClientException{
        Session session = openVidu.getActiveSession(sessionId);
        if (session == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
        Connection connection = session.createConnection(properties);
        return new ResponseEntity<>(connection.getToken(),HttpStatus.OK);
    }

    @GetMapping("/getAllStudyRoom")
    public ResponseEntity<ResponseObj> getAllRoomSession() throws OpenViduJavaClientException, OpenViduHttpException {
        List<StudyRoomEntity> rooms = roomRepository.findAll();
        if (rooms.isEmpty()){
            return new ResponseEntity<>(null,HttpStatus.OK);
        }
        ResponseObj responseObj = new ResponseObj();

        responseObj.setMessage("success");
        responseObj.setStatus("success");
        responseObj.setPayload(rooms);

        return new ResponseEntity<>(responseObj,HttpStatus.OK);
    }
}
