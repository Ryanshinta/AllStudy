package live.allstudy.service;

import live.allstudy.dto.PostByFollowing;
import live.allstudy.dto.UserEmailDTO;
import live.allstudy.dto.UserIDDTO;
import live.allstudy.dto.postIdDTO;
import live.allstudy.entity.PostEntity;
import live.allstudy.entity.UserEntity;
import live.allstudy.repository.PostRepository;
import live.allstudy.repository.UserRepository;
import live.allstudy.util.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class PostService {
    @Autowired
    private PostRepository postRepo;
    @Autowired
    private UserRepository userRepo;


    public ResponseObj deletePostById(String id){
        System.out.println(id);
        ResponseObj responseObj = new ResponseObj();
        Optional<PostEntity> post = postRepo.findById(id);
        if (post.isEmpty()){
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find post id: " + id);
            responseObj.setPayload(null);
            return responseObj;
        }else {
            postRepo.deleteById(id);
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            responseObj.setPayload(null);
        }
        return responseObj;
    }

    public ResponseObj getAllPost(){
        ResponseObj responseObj = new ResponseObj();
        Optional<List<PostEntity>> allPostOpt = postRepo.findAllByOrderByCreatedAtDesc();
        List<PostEntity> allPosts = allPostOpt.get();
        responseObj.setStatus("success");
        responseObj.setMessage("success");
        responseObj.setPayload(allPosts);
        return responseObj;
    }

    public ResponseObj insertPost(PostEntity inputPost) {
        ResponseObj responseObj = new ResponseObj();
        inputPost.setCreatedAt(Instant.now());
        responseObj.setStatus("success");
        responseObj.setMessage("success");
        responseObj.setPayload(postRepo.save(inputPost));
        return responseObj;
    }

    public ResponseObj findFollowingPostById(UserIDDTO inputUserId) {
        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> optUser = userRepo.findById(inputUserId.getId());
        if (optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find any post from user id: " + inputUserId.getId());
            responseObj.setPayload(null);
            return responseObj;
        } else {
            UserEntity user = optUser.get();
            if (user.getFollowing() != null) {
                // if user followed someone, get their emails
                List<String> followingEmails = new ArrayList<>(user.getFollowing());

                // based on these emails, get their equivalent posts
                List<PostEntity> listPosts = new ArrayList<>();
                for (String followingEmail : followingEmails) {
                    // get following user info based on Id
                    UserEntity followingUser = new UserEntity();
                    Optional<UserEntity> optFollowingUser = userRepo.findByEmail(followingEmail);
                    if (optFollowingUser.isPresent()) {
                        followingUser = optFollowingUser.get();
                    }


                    // get equivalent posts
                    Optional<List<PostEntity>> followingPostsOpt = postRepo.findByUserIdOrderByCreatedAtDesc(followingUser.getId());

                    if (followingPostsOpt.isPresent()) {
                        // if followed account has any post, collect them
                        List<PostEntity> followingPosts = followingPostsOpt.get();
                        listPosts.addAll(followingPosts);
                    }
                }
                responseObj.setStatus("success");
                responseObj.setMessage("success");
                responseObj.setPayload(listPosts);
                return responseObj;
            } else {
                responseObj.setStatus("fail");
                responseObj.setMessage("user id: " + inputUserId.getId() + " has empty following list");
                responseObj.setPayload(null);
                return responseObj;
            }
        }
    }


    public ResponseObj findPostByFollowing(UserIDDTO inputUserId) {
        ResponseObj responseObj = new ResponseObj();
        Optional<UserEntity> optUser = userRepo.findById(inputUserId.getId());
        if (optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find any post from user id: " + inputUserId.getId());
            responseObj.setPayload(null);
            return responseObj;
        } else {
            UserEntity user = optUser.get();
            if (user.getFollowing() != null) {
                // if user followed someone, get their ids
                List<String> followingEmails = new ArrayList<>();
                for (String email : user.getFollowing()) {
                    followingEmails.add(email);
                }
                // based on these ids, get their equivalent posts
                List<PostByFollowing> listPosts = new ArrayList<>();
                for (String followingEmail : followingEmails) {
                    // get following user info based on Id
                    UserEntity followingUser = new UserEntity();
                    Optional<UserEntity> optFollowingUser = userRepo.findByEmail(followingEmail);
                    if (optFollowingUser.isPresent()) {
                        followingUser = optFollowingUser.get();
                    }

                    followingUser.setPassword("");

                    // get equivalent posts
                    Optional<List<PostEntity>> followingPostsOpt = postRepo.findByUserId(followingUser.getId());

                    if (followingPostsOpt.isPresent()) {
                        // if followed account has any post, collect them
                        List<PostEntity> followingPosts = followingPostsOpt.get();
                        if (followingPosts != null) {
                            for (PostEntity item : followingPosts) {
                                listPosts.add(new PostByFollowing(followingUser, item));
                            }
                        }
                    }
                }
                Collections.sort(listPosts, (o1, o2) -> o2.getPost().getCreatedAt().compareTo(o1.getPost().getCreatedAt()));
                responseObj.setStatus("success");
                responseObj.setMessage("success");
                responseObj.setPayload(listPosts);
                return responseObj;
            } else {
                responseObj.setStatus("fail");
                responseObj.setMessage("user id: " + inputUserId.getId() + " has empty following list");
                responseObj.setPayload(null);
                return responseObj;
            }
        }
    }


    public ResponseObj findPostByUserId(UserIDDTO inputUserId) {
        ResponseObj responseObj = new ResponseObj();
        Optional<List<PostEntity>> userPostsOpt = postRepo.findByUserIdOrderByCreatedAtDesc(inputUserId.getId());
        if (userPostsOpt.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find any post from user id: " + inputUserId.getId());
            responseObj.setPayload(null);
            return responseObj;
        } else {
            List<PostEntity> userPosts = userPostsOpt.get();
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            responseObj.setPayload(userPosts);
            return responseObj;
        }
    }


    public ResponseObj updatePostByComment(PostEntity inputPost) {
        ResponseObj responseObj = new ResponseObj();
        Optional<PostEntity> optPost = postRepo.findById(inputPost.getId());
        if (optPost.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find post id: " + inputPost.getId());
            responseObj.setPayload(null);
            return responseObj;
        } else {
            // inputPost.setCreatedAt(Instant.now());
            postRepo.save(inputPost);
            responseObj.setStatus("success");
            responseObj.setMessage("post is updated successfully");
            responseObj.setPayload(inputPost);
            return responseObj;
        }
    }

    public ResponseObj updatePostByLike(String postId, String userID) {
        // id 1 - post Id, id 2 - user who liked post
        ResponseObj responseObj = new ResponseObj();
        Optional<PostEntity> optPost = postRepo.findById(postId);
        if (optPost.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find post id: " + postId);
            responseObj.setPayload(null);
            return responseObj;
        } else {
            PostEntity targetPost = optPost.get();
            List<String> loveList = targetPost.getLike();
            if (loveList == null) {
                loveList = new ArrayList<>();
            }
            // love and unlove a post
            if (!loveList.contains(userID)) {
                loveList.add(userID);
            } else {
                loveList.remove(userID);
            }
            targetPost.setLike(loveList);
            postRepo.save(targetPost);
            responseObj.setStatus("success");
            responseObj.setMessage("update love to the target post id: " + targetPost.getId());
            responseObj.setPayload(targetPost);
            return responseObj;
        }
    }

    public ResponseObj updatePostByShare(UserIDDTO postId,UserIDDTO userID) {
        ResponseObj responseObj = new ResponseObj();
        Optional<PostEntity> optPost = postRepo.findById(postId.getId());
        if (optPost.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find post id: " + postId.getId());
            responseObj.setPayload(null);
            return responseObj;
        } else {
            PostEntity targetPost = optPost.get();
            List<String> shareList = targetPost.getShare();
            if (shareList == null) {
                shareList = new ArrayList<>();
            }
            // save id of user who shared the post then update post
            shareList.add(userID.getId());
            targetPost.setShare(shareList);
            postRepo.save(targetPost);
            // update post list of user who shared the post
            targetPost.setUserId(userID.getId());
            targetPost.setId(null);
            targetPost.setContent("Shared a post: " + targetPost.getContent());
            targetPost.setLike(new ArrayList<>());
            targetPost.setShare(new ArrayList<>());
            targetPost.setComment(new ArrayList<>());
            postRepo.save(targetPost);

            responseObj.setStatus("success");
            responseObj.setMessage("add a share to the target post id: " + targetPost.getId());
            responseObj.setPayload(targetPost);
            return responseObj;
        }
    }
}