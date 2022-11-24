package live.allstudy.controller;

import live.allstudy.dto.UserSignInDTO;
import live.allstudy.entity.PartnerEntity;
import live.allstudy.entity.PartnerRequestEntity;
import live.allstudy.entity.PostEntity;
import live.allstudy.entity.UserEntity;
import live.allstudy.service.PartnerService;
import live.allstudy.service.UserService;
import live.allstudy.util.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author JIAHAO_CHAI
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PartnerController {
    @Autowired
    private PartnerService partnerService;

    @Autowired
    private UserService userService;

    @PostMapping("/partner")
    public ResponseEntity<ResponseObj> findAllRequest(){
        return new ResponseEntity<ResponseObj>(partnerService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/partner/request")
    public ResponseEntity<ResponseObj> newRequest(@RequestBody PartnerRequestEntity requestInfo){
        return new ResponseEntity<ResponseObj>(partnerService.newRequest(requestInfo), HttpStatus.OK);
    }

    @PostMapping("/partner/deleteRequest")
    public ResponseEntity<ResponseObj> deleteRequest(@RequestBody PartnerRequestEntity requestId){
        return new ResponseEntity<ResponseObj>(partnerService.deleteRequest(requestId), HttpStatus.OK);
    }

    @PostMapping("/partner/suggestPartner")
    public ResponseEntity<ResponseObj> suggestPartner(@RequestBody UserSignInDTO loginEmail){
        return new ResponseEntity<ResponseObj>(userService.findAllNotFollowed(loginEmail.getEmail()),HttpStatus.OK);
    }

    @PostMapping("/partner/pendingRequest")
    public ResponseEntity<ResponseObj> findPendingRequest(@RequestBody PartnerRequestEntity receiveEmail){
        return new ResponseEntity<ResponseObj>(partnerService.findAllPending(receiveEmail), HttpStatus.OK);
    }

    @PostMapping("/partner/approvedRequest")
    public ResponseEntity<ResponseObj> approvedRequest(@RequestBody PartnerRequestEntity requestId){
        return new ResponseEntity<ResponseObj>(partnerService.approvedRequest(requestId), HttpStatus.OK);
    }
}
