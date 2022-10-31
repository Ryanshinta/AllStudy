package live.allstudy.controller;

import live.allstudy.dto.UserIDDTO;
import live.allstudy.dto.UserSignInDTO;
import live.allstudy.entity.AuthorizedEntity;
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
    public ResponseEntity findAllUsers() {
        return new ResponseEntity(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/users/profile")
    public ResponseEntity findById(@RequestBody UserIDDTO inputId) {
        return new ResponseEntity(userService.findById(inputId.getId()), HttpStatus.OK);
    }


    @PostMapping("/users/save")
    public ResponseEntity saveUser(@RequestBody UserEntity inputUser) {
        return new ResponseEntity(userService.saveUser(inputUser), HttpStatus.OK);
    }

    @PostMapping("/users/signin")
    public ResponseEntity userSignIn(@RequestBody UserSignInDTO inputUser) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(inputUser.getEmail(), inputUser.getPassword()));
            String token = jwtUtil.generateToken(inputUser.getEmail());

            Optional<UserEntity> optUser = userRepo.findByEmail(inputUser.getEmail());
            UserEntity user = optUser.get();
            user.setPassword("");
            return new ResponseEntity(new ResponseObj("success", "authenticated", new AuthorizedEntity(user, token)), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(new ResponseObj("fail", "unauthenticated", null), HttpStatus.OK);
        }
    }


    @GetMapping("/getdata")
    public ResponseEntity testAfterLogin(Principal p) {
        return ResponseEntity.ok("Welcome. You are: " + p.getName());
    }
}