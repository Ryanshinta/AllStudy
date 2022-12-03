package live.allstudy.controller;

import live.allstudy.dto.UserEmailDTO;
import live.allstudy.dto.UserIDDTO;
import live.allstudy.dto.UserSignInDTO;
import live.allstudy.entity.AuthorizedEntity;
import live.allstudy.entity.PartnerEntity;
import live.allstudy.entity.UserEntity;
import live.allstudy.service.UserService;
import live.allstudy.util.JWTUtil;
import live.allstudy.util.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import live.allstudy.repository.UserRepository;

import java.security.Principal;
import java.util.Optional;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/users")
    public ResponseEntity<ResponseObj> findAllUsers() {
        return new ResponseEntity<ResponseObj>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/users/profile")
    public ResponseEntity<ResponseObj> findByEmail(@RequestBody UserEmailDTO inputEmail) {
        return new ResponseEntity<ResponseObj>(userService.findByEmail(inputEmail.getEmail()), HttpStatus.OK);
    }

    @PostMapping("/users/profilePhoto")
    public ResponseEntity<ResponseObj> findByUserId(@RequestBody UserIDDTO userIDDTO) {
        return new ResponseEntity<ResponseObj>(userService.findByUserId(userIDDTO.getId()), HttpStatus.OK);
    }


    @PostMapping("/users/save")
    public ResponseEntity<ResponseObj> saveUser(@RequestBody UserEntity inputUser) {
        return new ResponseEntity<ResponseObj>(userService.saveUser(inputUser), HttpStatus.OK);
    }

    @PutMapping("/users/update")
    public ResponseEntity<ResponseObj> update(@RequestBody UserEntity inputUser) {
        return new ResponseEntity<ResponseObj>(userService.update(inputUser), HttpStatus.OK);
    }

    @PostMapping("/users/signin")
    public ResponseEntity<ResponseObj> userSignIn(@RequestBody UserSignInDTO inputUser) {
        try {
            System.out.println(inputUser.getEmail());
            System.out.println(inputUser.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(inputUser.getEmail(), inputUser.getPassword()));

            String token = jwtUtil.generateToken(inputUser.getEmail());
            Optional<UserEntity> optUser = userRepo.findByEmail(inputUser.getEmail());
            UserEntity user = optUser.get();
            user.setPassword("");
            return new ResponseEntity<ResponseObj>(new ResponseObj("success", "authenticated", new AuthorizedEntity(user, token)), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<ResponseObj>(new ResponseObj("fail", "unauthenticated", null), HttpStatus.OK);
        }
    }

    @GetMapping("/getdata")
    public ResponseEntity<String> testAfterLogin(Principal p) {
        return ResponseEntity.ok("Welcome. You are: " + p.getName());
    }


//    @PostMapping("/users/follow")
//    public ResponseEntity<ResponseObj> followUser(@RequestBody PartnerEntity partnerEmail) {
//        return new ResponseEntity<ResponseObj>(userService.followUser(partnerEmail), HttpStatus.OK);
//    }

    @PostMapping("/users/unfollow")
    public ResponseEntity<ResponseObj> unfollowUser(@RequestBody PartnerEntity partnerEmail) {
        return new ResponseEntity<ResponseObj>(userService.unfollowUser(partnerEmail), HttpStatus.OK);
    }

    @PostMapping("/users/getFollowing")
    public ResponseEntity<ResponseObj> findFollowing(@RequestBody UserEmailDTO inputId) {
        return new ResponseEntity<ResponseObj>(userService.findFollowing(inputId.getEmail()), HttpStatus.OK);
    }

    @PostMapping("/users/getFollower")
    public ResponseEntity<ResponseObj> findFollower(@RequestBody UserEmailDTO inputId) {
        return new ResponseEntity<ResponseObj>(userService.findFollower(inputId.getEmail()), HttpStatus.OK);
    }
}