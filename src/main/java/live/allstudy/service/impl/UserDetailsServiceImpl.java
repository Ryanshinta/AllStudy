package live.allstudy.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import live.allstudy.entity.User;
import live.allstudy.repository.UserRepository;
import live.allstudy.service.UserDetailsService;

public class UserDetailsServiceImpl implements UserDetailsService{


    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User updateUserById(User user, Long id) {
        User userDB = userRepository.findById(id).get();
        
        //todo One by one save updated data into class
        return userRepository.save(user);
    
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
        
    }
    
}
