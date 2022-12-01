package live.allstudy.service;


import com.mongodb.internal.inject.OptionalProvider;
import live.allstudy.dto.UserEmailDTO;
import live.allstudy.entity.PartnerEntity;
import live.allstudy.entity.PartnerRequestEntity;
import live.allstudy.entity.UserEntity;
import live.allstudy.repository.RequestRepository;
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
    private RequestRepository requestRepo;

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

    public ResponseObj findAllNotFollowed(String email) {
        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> optUser = userRepo.findByEmail(email);
        List<PartnerRequestEntity> optRequest = requestRepo.findAllByRequestEmail(email);

        //get login user
        List<String> receiveEmail = new ArrayList<>();
        for (int i = 0; i < optRequest.size(); i++){
            receiveEmail.add(0, optRequest.get(i).getReceiveEmail());
        }

        UserEntity currentUser = optUser.get();
        //get following users by login user
        List<String> following = currentUser.getFollowing();
        following.addAll(receiveEmail);
        //get all users
        List<UserEntity> followedUsers = new ArrayList<>();
        following.forEach((n) -> followedUsers.add(0, userRepo.findAllByEmail(n)));
        List<UserEntity> allUser = userRepo.findAll();
        //join all users and following users
        followedUsers.removeAll(Collections.singleton(null));
        allUser.removeAll(followedUsers);
        allUser.removeIf(s -> s.getEmail().contains(email));
        //perform remove duplicates users
        //return users not following by login users
        responseObj.setPayload(allUser);
        responseObj.setStatus("success");
        responseObj.setMessage("success");
        return responseObj;
    }

    public ResponseObj findByEmail(String email) {
        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> optUser = userRepo.findByEmail(email);
        if (optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("user email: " + email + " not existed");
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
            user.setUsername(user.getLastName() + " " + user.getFirstName());
//            List<String> listFollowing = user.getFollowing();
//            if (listFollowing == null) {
//                listFollowing = new ArrayList<>();
//            }
//            listFollowing.add(user.getId());
//            user.setFollowing(listFollowing);
            this.updateWithoutPassword(user);
            responseObj.setPayload(user);
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            return responseObj;
        }
    }


    public ResponseObj update(UserEntity inputUser) {
        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> optUser = userRepo.findByEmail(inputUser.getEmail());
        if (optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("user email: " + inputUser.getEmail() + " not existed");
            responseObj.setPayload(null);
            return responseObj;
        } else {
            UserEntity currentUser = optUser.get();
            if (bCryptEncoder.matches(inputUser.getPassword(), currentUser.getPassword())) {
                userRepo.deleteById(currentUser.getId());
                inputUser.setUsername(inputUser.getLastName() + " " + inputUser.getFirstName());
                inputUser.setPassword(bCryptEncoder.encode(inputUser.getPassword()));
                responseObj.setPayload(userRepo.save(inputUser));
                responseObj.setStatus("success");
                responseObj.setMessage("success");
                return responseObj;
            } else {
                responseObj.setStatus("fail");
                responseObj.setMessage("current password is not correct");
                responseObj.setPayload(null);
                return responseObj;
            }
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

    public ResponseObj findFollowing(String email) {
        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> optUser = userRepo.findByEmail(email);
        if (optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("user email: " + email + " not existed");
            responseObj.setPayload(null);
            return responseObj;
        } else {
            List<String> followingEmails = optUser.get().getFollowing();
            List<UserEntity> followingAccounts = new ArrayList<>();
            if (followingEmails.size() > 0) {
                for (String followingEmail : followingEmails) {
                    Optional<UserEntity> optFollowingUser = userRepo.findByEmail(followingEmail);
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
                responseObj.setMessage("User email " + email + " does not follow anyone");
                responseObj.setPayload(null);
            }
            return responseObj;
        }
    }

    public ResponseObj findFollower(String email) {
        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> optUser = userRepo.findByEmail(email);
        if (optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("user email: " + email + " not existed");
            responseObj.setPayload(null);
        } else {
            List<String> followerEmails = optUser.get().getFollower();
            List<UserEntity> followerAccounts = new ArrayList<>();
            if (followerEmails.size() > 0) {
                for (String followerEmail : followerEmails) {
                    Optional<UserEntity> optFollowerUser = userRepo.findByEmail(followerEmail);
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
                responseObj.setMessage("User email " + email + " does not have any follower");
                responseObj.setPayload(null);
            }
        }
        return responseObj;
    }

    public ResponseObj sendFollowRequest(PartnerEntity partnerEmail) {
        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> optFollowedUser = userRepo.findByEmail(partnerEmail.getEmail1());
        Optional<UserEntity> optFollower = userRepo.findByEmail(partnerEmail.getEmail2());
        if (optFollower.isEmpty() || optFollowedUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("user email not exist");
            responseObj.setPayload(null);
        } else {
            UserEntity followedUse = optFollowedUser.get();
            UserEntity follower = optFollower.get();
        }
        return responseObj;
    }

    public ResponseObj followUser(PartnerEntity partnerEmail) {
        // id1 - followed user, id2 - follower

        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> optFollowedUser = userRepo.findByEmail(partnerEmail.getEmail1());
        Optional<UserEntity> optFollower = userRepo.findByEmail(partnerEmail.getEmail2());
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
//            followerList.forEach((n) -> System.out.println(n));
            if (!followerList.contains(optFollower.get().getEmail())) {
                followerList.add(follower.getEmail());
                followedUser.setFollower(followerList);
            } else {
                System.out.println(optFollower.get().getEmail() + " already exist");
            }

            // add to following list
            List<String> followingList = follower.getFollowing();
            if (followingList == null) {
                followingList = new ArrayList<>();
            }
//            followingList.forEach((n) -> System.out.println(n));
            if (!followingList.contains(optFollowedUser.get().getEmail())) {
                followingList.add(followedUser.getEmail());
                follower.setFollowing(followingList);
            } else {
                System.out.println(optFollowedUser.get().getEmail() + " already exist");
            }
            userRepo.save(followedUser);
            userRepo.save(follower);

            responseObj.setStatus("success");
            responseObj.setMessage(
                    "User Email " + follower.getEmail() + " successfully followed user email " + followedUser.getEmail());
            responseObj.setPayload(new UserEmailDTO(partnerEmail.getEmail1()));
            System.out.println(" ");
            return responseObj;
        }
    }

    public ResponseObj unfollowUser(PartnerEntity partnerEmail) {
        // id1 - followed user, id2 - follower

        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> optFollowedUser = userRepo.findByEmail(partnerEmail.getEmail1());
        Optional<UserEntity> optFollower = userRepo.findByEmail(partnerEmail.getEmail2());
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
            followerList.remove(follower.getEmail());
            followedUser.setFollower(followerList);

            // add to following list
            List<String> followingList = follower.getFollowing();
            if (followingList == null) {
                followingList = new ArrayList<>();
            }
            followingList.remove(followedUser.getEmail());
            follower.setFollowing(followingList);

            userRepo.save(followedUser);
            userRepo.save(follower);

            responseObj.setStatus("success");
            responseObj.setMessage(
                    "User email " + follower.getEmail() + " successfully unfollowed user email " + followedUser.getEmail());
            responseObj.setPayload(new UserEmailDTO(partnerEmail.getEmail1()));
            return responseObj;
        }
    }

}
