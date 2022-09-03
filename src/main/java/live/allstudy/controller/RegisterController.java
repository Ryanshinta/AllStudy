package live.allstudy.controller;

import java.net.http.HttpRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import live.allstudy.domain.ResponseResult;
import live.allstudy.entity.User;
import live.allstudy.repository.UserRepository;

@Controller
public class RegisterController {
    

    public ResponseResult<?> register(@RequestBody User user){
        

        
        User regUser = new User();
        regUser.setAvatar(user.getAvatar()); 
        //
        UserRepository.save(regUser);
        return null;

    }  
}
