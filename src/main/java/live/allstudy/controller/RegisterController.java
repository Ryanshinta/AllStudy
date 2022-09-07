package live.allstudy.controller;


import live.allstudy.dto.registerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import live.allstudy.entity.User;
import live.allstudy.repository.UserRepository;

@Controller
public class RegisterController {
    

    @Autowired
    UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody registerDto registerUser){
        
        if(userRepository.existsByEmail(registerUser.getEmail())){
            return new ResponseEntity<>("Email is already taken!",HttpStatus.BAD_REQUEST);
        }
        // if(userRepository.existsByEmail(user.getName())){
        //     return new ResponseEntity<>("Email is already taken!",HttpStatus.BAD_REQUEST);
        // }

        User user = new User();
        user.setName(registerUser.getName());
        user.setEmail(registerUser.getEmail());
        user.setPassword(registerUser.getPassword());

        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }  
}
