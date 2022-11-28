package live.allstudy.service;


import live.allstudy.dto.UserIDDTO;
import live.allstudy.entity.UserEntity;
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

            // user follows himself so he could get his posts in newsfeed as well
            UserEntity user = userRepo.save(inputUser);
            List<String> listFollowing = user.getFollowing();
            if (listFollowing == null) {
                listFollowing = new ArrayList<>();
            }
            listFollowing.add(user.getId());
            user.setFollowing(listFollowing);
            this.updateWithoutPassword(user);
            responseObj.setPayload(user);
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            return responseObj;
        }
    }


    public boolean updateWithoutPassword(UserEntity inputUser) {
        Optional<UserEntity> optUser = userRepo.findById(inputUser.getId());
        if (optUser.isEmpty()) {
            return false;
        } else {
            UserEntity currentUser = optUser.get();
            if (inputUser.getPassword().equals(currentUser.getPassword())) {
                userRepo.save(inputUser);
                return true;
            } else {
                return false;
            }
        }
    }

    public ResponseObj findFollowing(String id) {
        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> optUser = userRepo.findById(id);
        if (optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("user id: " + id + " not existed");
            responseObj.setPayload(null);
            return responseObj;
        } else {
            List<String> followingIds = optUser.get().getFollowing();
            List<UserEntity> followingAccounts = new ArrayList<>();
            if (followingIds.size() > 0) {
                for (String followingId : followingIds) {
                    Optional<UserEntity> optFollowingUser = userRepo.findById(followingId);
                    if (optFollowingUser.isPresent()) {
                        UserEntity followingUser = optFollowingUser.get();
                        followingUser.setPassword("");
                        followingAccounts.add(followingUser);
                    }
                }
                responseObj.setStatus("success");
                responseObj.setMessage("success");
                responseObj.setPayload(followingAccounts);
            } else {
                responseObj.setStatus("fail");
                responseObj.setMessage("User id " + id + " does not follow anyone");
                responseObj.setPayload(null);
            }
            return responseObj;
        }
    }

    public ResponseObj findFollower(String id) {
        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> optUser = userRepo.findById(id);
        if (optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("user id: " + id + " not existed");
            responseObj.setPayload(null);
        } else {
            List<String> followerIds = optUser.get().getFollower();
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

    public ResponseObj update(UserEntity inputUser) {
        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> optUser = userRepo.findById(inputUser.getId());
        if (optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("user id: " + inputUser.getId() + " not existed");
            responseObj.setPayload(null);
        } else {
            UserEntity currentUser = optUser.get();
            if (bCryptEncoder.matches(inputUser.getPassword(), currentUser.getPassword())) {
                inputUser.setPassword(bCryptEncoder.encode(inputUser.getPassword()));
                responseObj.setPayload(userRepo.save(inputUser));
                responseObj.setStatus("success");
                responseObj.setMessage("success");
            } else {
                responseObj.setStatus("fail");
                responseObj.setMessage("current password is not correct");
                responseObj.setPayload(null);
            }
        }
        return responseObj;
    }

    public ResponseObj followUser(UserIDDTO user1, UserIDDTO user2) {
        // id1 - followed user, id2 - follower

        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> optFollowedUser = userRepo.findById(user1.getId());
        Optional<UserEntity> optFollower = userRepo.findById(user2.getId());
        if (optFollowedUser.isEmpty() || optFollower.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("invalid user id");
            responseObj.setPayload(null);
        } else {
            UserEntity followedUser = optFollowedUser.get();
            UserEntity follower = optFollower.get();

            // add to follower list
            List<String> followerList = followedUser.getFollower();
            if (followerList == null) {
                followerList = new ArrayList<>();
            }
            followerList.add(follower.getId());
            followedUser.setFollower(followerList);

            // add to following list
            List<String> followingList = follower.getFollowing();
            if (followingList == null) {
                followingList = new ArrayList<>();
            }
            followingList.add(followedUser.getId());
            follower.setFollowing(followingList);

            userRepo.save(followedUser);
            userRepo.save(follower);

            responseObj.setStatus("success");
            responseObj.setMessage(
                    "User id " + follower.getId() + " successfully followed user id " + followedUser.getId());
            responseObj.setPayload(new UserIDDTO(user1.getId()));
        }
        return responseObj;
    }

    public ResponseObj unfollowUser(UserIDDTO user1, UserIDDTO user2) {
        // id1 - followed user, id2 - follower

        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> optFollowedUser = userRepo.findById(user1.getId());
        Optional<UserEntity> optFollower = userRepo.findById(user2.getId());
        if (optFollowedUser.isEmpty() || optFollower.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("invalid user id");
            responseObj.setPayload(null);
            return responseObj;
        } else {
            UserEntity followedUser = optFollowedUser.get();
            UserEntity follower = optFollower.get();

            // add to follower list
            List<String> followerList = followedUser.getFollower();
            if (followerList == null) {
                followerList = new ArrayList<>();
            }
            followerList.remove(follower.getId());
            followedUser.setFollower(followerList);

            // add to following list
            List<String> followingList = follower.getFollowing();
            if (followingList == null) {
                followingList = new ArrayList<>();
            }
            followingList.remove(followedUser.getId());
            follower.setFollowing(followingList);

            userRepo.save(followedUser);
            userRepo.save(follower);

            responseObj.setStatus("success");
            responseObj.setMessage(
                    "User id " + follower.getId() + " successfully unfollowed user id " + followedUser.getId());
            responseObj.setPayload(new UserIDDTO(user1.getId()));
            return responseObj;
        }
    }

}
