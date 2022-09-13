package live.allstudy.controller;

import live.allstudy.dto.authDto;
import live.allstudy.service.LoginService;
import live.allstudy.service.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import live.allstudy.entity.User;
import live.allstudy.repository.UserRepository;

@Controller
public class authController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    private LoginService loginService;


    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody authDto registerUser){

        if(userRepository.existsByEmail(registerUser.getEmail())){
            return new ResponseEntity<>("Email is already taken!",HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setName(registerUser.getName());
        user.setEmail(registerUser.getEmail());
        user.setPassword(registerUser.getPassword());

        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/auth/login")
    public ResponseEntity loginUser(@RequestBody authDto loginUser){

        return loginService.login(loginUser);
    }

//    @GetMapping(")
}