// package live.allstudy.service.impl;
//
// import java.util.List;
// import java.util.Optional;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
//
// import live.allstudy.entity.User;
// import live.allstudy.repository.UserRepository;
// import live.allstudy.service.UserDetailsService;
//
// @Service
// public class UserDetailsServiceImpl implements UserDetailsService{
//
//     @Autowired
//     UserRepository userRepository;
//
//     @Override
//     public User getUserByEmail(String email) {
//         if (!userRepository.existsByEmail(email)){
//             throw new RuntimeException("User not found");
//         }
//
//         User user = userRepository.findByEmail(email).get();
//         return user;
//     }
// }
