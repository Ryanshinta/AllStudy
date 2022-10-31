package live.allstudy.service;


import live.allstudy.entity.UserConnectionEntity;
import live.allstudy.entity.UserEntity;
import live.allstudy.repository.UserConnectionRepository;
import live.allstudy.repository.UserRepository;
import live.allstudy.util.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserConnectionRepository userConnRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> optUser = userRepo.findByEmail(email);
        User springUser = null;

        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException("Cannot find user with email: " + email);
        } else {
            UserEntity foundUser = optUser.get();
            String role = foundUser.getRole();
            Set<GrantedAuthority> ga = new HashSet<>();
            ga.add(new SimpleGrantedAuthority(role));
            springUser = new User(foundUser.getEmail(), foundUser.getPassword(), ga);
            return springUser;
        }
    }

    public ResponseObj findAll() {
        ResponseObj responseObj = new ResponseObj();
        responseObj.setPayload(userRepo.findAll());
        responseObj.setStatus("success");
        responseObj.setMessage("success");
        return responseObj;
    }

    public ResponseObj findById(String id) {
        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> optUser = userRepo.findById(id);
        if (optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("user id: " + id + " not existed");
            responseObj.setPayload(null);
        } else {
            responseObj.setPayload(optUser.get());
            responseObj.setStatus("success");
            responseObj.setMessage("success");
        }
        return responseObj;
    }

    public ResponseObj saveUser(UserEntity inputUser) {
        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> optUser = userRepo.findByEmail(inputUser.getEmail());
        if (optUser.isPresent()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("Email address " + inputUser.getEmail() + " existed");
            responseObj.setPayload(null);
            return responseObj;
        } else {
            inputUser.setPassword(bCryptEncoder.encode(inputUser.getPassword()));
            UserEntity user = userRepo.save(inputUser);
            UserConnectionEntity userConnectionEntity = new UserConnectionEntity();
            userConnectionEntity.setId(inputUser.getId());

            userConnRepo.save(userConnectionEntity);
            responseObj.setPayload(user);
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            return responseObj;
        }
    }

    public ResponseObj getALlFollowing(String id){
        ResponseObj responseObj = new ResponseObj();
        //Optional<UserEntity> optUser = userRepo.findById(id);
        Optional<UserConnectionEntity> optUserConn = userConnRepo.findById(id);
        if (optUserConn.get().getFollowing().isEmpty()){
            responseObj.setStatus("fail");
            responseObj.setMessage("user id: " + id + " not existed");
            responseObj.setPayload(null);
        }else {
            List<String> followerIds = optUserConn.get().getFollower();
            List<UserEntity> followerAccounts = new ArrayList<>();

            if (followerIds.size() > 0) {
                for (String followerId : followerIds) {
                    Optional<UserEntity> optFollowerUser = userRepo.findById(followerId);
                    if (optFollowerUser.isPresent()) {
                        UserEntity followerUser = optFollowerUser.get();
                        followerUser.setPassword("");
                        followerAccounts.add(followerUser);
                    }
                }
                responseObj.setStatus("success");
                responseObj.setMessage("success");
                responseObj.setPayload(followerAccounts);
            } else {
                responseObj.setStatus("fail");
                responseObj.setMessage("User id " + id + " does not have any follower");
                responseObj.setPayload(null);
            }
        }
        return responseObj;
    }

}
