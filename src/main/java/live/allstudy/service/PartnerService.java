package live.allstudy.service;

import live.allstudy.dto.UserEmailDTO;
import live.allstudy.entity.PartnerEntity;
import live.allstudy.entity.PartnerRequestEntity;
import live.allstudy.entity.UserEntity;
import live.allstudy.repository.RequestRepository;
import live.allstudy.repository.UserRepository;
import live.allstudy.util.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author JIAHAO_CHAI
 */

@Service
public class PartnerService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RequestRepository requestRepo;

    public ResponseObj findAll() {
        ResponseObj responseObj = new ResponseObj();
        responseObj.setPayload(requestRepo.findAll());
        responseObj.setStatus("success");
        responseObj.setMessage("success");
        return responseObj;
    }

    public ResponseObj findAllPending(PartnerRequestEntity receiver) {
        ResponseObj responseObj = new ResponseObj();
        responseObj.setPayload(requestRepo.findAllByStatusAndReceiveEmail("pending", receiver.getReceiveEmail()));
        responseObj.setStatus("success");
        responseObj.setMessage("success");
        return responseObj;
    }

    public ResponseObj deleteRequest(PartnerRequestEntity requestId) {
        ResponseObj responseObj = new ResponseObj();
        Optional<PartnerRequestEntity> optRequest = requestRepo.findById(requestId.getId());
        PartnerRequestEntity currentRequest = optRequest.get();
        currentRequest.setStatus("failed");
        requestRepo.save(currentRequest);
        responseObj.setStatus("success");
        responseObj.setMessage("successful delete request");
        return responseObj;
    }

    public ResponseObj newRequest(PartnerRequestEntity requestInfo) {
        ResponseObj responseObj = new ResponseObj();
        Optional<PartnerRequestEntity> request = requestRepo.findByReceiveEmailAndRequestEmail(requestInfo.getReceiveEmail(), requestInfo.getRequestEmail());
        Optional<UserEntity> receiver = userRepo.findByEmail(requestInfo.getReceiveEmail());
        Optional<UserEntity> requestor = userRepo.findByEmail(requestInfo.getRequestEmail());
        if (request.isEmpty()) {
            if (receiver.isEmpty() || requestor.isEmpty()) {
                responseObj.setStatus("fail");
                responseObj.setMessage("one of the email not exist");
                responseObj.setPayload(null);
                System.out.println("email invalid");
                return responseObj;
            } else {
                responseObj.setStatus("success");
                responseObj.setMessage("success add new request");
                String timestamp = Timestamp.from(Instant.now()).toString();
                requestInfo.setDateTime(timestamp);
                requestInfo.setUsername(requestor.get().getLastName() + " " + requestor.get().getFirstName());
                responseObj.setPayload(requestRepo.save(requestInfo));
                System.out.println(requestInfo);
                return responseObj;
            }
        } else {
            System.out.println("exist request");
            responseObj.setStatus("fail");
            responseObj.setMessage("request already exist");
            responseObj.setPayload(null);
            return responseObj;
        }
    }

    public ResponseObj approvedRequest(PartnerRequestEntity requestId) {
        ResponseObj responseObj = new ResponseObj();
        Optional<PartnerRequestEntity> optRequest = requestRepo.findById(requestId.getId());
        PartnerRequestEntity currentRequest = optRequest.get();
        Optional<UserEntity> receiverEmail = userRepo.findByEmail(currentRequest.getReceiveEmail());
        Optional<UserEntity> requestorEmail = userRepo.findByEmail(currentRequest.getRequestEmail());
        UserEntity receiver = receiverEmail.get();
        UserEntity requestor = requestorEmail.get();

        // add to follower list
        List<String> receiverList = receiver.getFollower();
        if (receiverList == null) {
            receiverList = new ArrayList<>();
        }
//            followerList.forEach((n) -> System.out.println(n));
        if (!receiverList.contains(requestorEmail.get().getEmail())) {
            receiverList.add(requestor.getEmail());
            receiver.setFollower(receiverList);
        } else {
            System.out.println(requestorEmail.get().getEmail() + " already exist");
        }

        // add to following list
        List<String> requestorList = requestor.getFollowing();
        if (requestorList == null) {
            requestorList = new ArrayList<>();
        }
//            followingList.forEach((n) -> System.out.println(n));
        if (!requestorList.contains(receiverEmail.get().getEmail())) {
            requestorList.add(receiver.getEmail());
            requestor.setFollowing(requestorList);
        } else {
            System.out.println(receiverEmail.get().getEmail() + " already exist");
        }
        userRepo.save(requestor);
        userRepo.save(receiver);

        responseObj.setStatus("success");
        responseObj.setMessage(
                "User Email " + requestor.getEmail() + " successfully followed user email " + receiver.getEmail());
        responseObj.setPayload(new UserEmailDTO(requestId.getRequestEmail()));
        System.out.println(" ");

        requestRepo.deleteById(currentRequest.getId());

        return responseObj;
    }
}
