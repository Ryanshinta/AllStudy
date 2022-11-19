package live.allstudy.controller;



import io.openvidu.java.client.*;
import live.allstudy.util.ResponseObj;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class StudyRoomController {

    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;

    private OpenVidu openVidu;

    @PostConstruct
    public void init() {
        this.openVidu = new OpenVidu(OPENVIDU_URL,OPENVIDU_SECRET);
    }

    @PostMapping("/session")
    public ResponseEntity<ResponseObj> initializeSession (@RequestBody(required = false)Map<String,Object> params)
        throws OpenViduJavaClientException, OpenViduHttpException {
        ResponseObj responseObj = new ResponseObj();
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openVidu.createSession(properties);
        return new ResponseEntity<>(null);


    }
}
