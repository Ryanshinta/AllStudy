package live.allstudy.service;

import java.util.List;

import live.allstudy.entity.User;

public interface UserDetailsService {
    User saveUser(User user);

    List<User> getAllUser();
    
    User updateUserById(User user, Long id);

    void deleteUserById(Long id);
    
}
