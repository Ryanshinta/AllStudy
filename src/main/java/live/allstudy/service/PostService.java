package live.allstudy.service;

import live.allstudy.dto.UserIDDTO;
import live.allstudy.entity.PostEntity;
import live.allstudy.repository.PostRepository;
import live.allstudy.repository.UserRepository;
import live.allstudy.util.ResponseClass;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Service
public class PostService {

    Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepo;
    @Autowired
    private UserRepository userRepo;


    public ResponseClass insertPost(PostEntity inputPost) {
        ResponseClass responseObj = new ResponseClass();
        inputPost.setCreatedAt(Instant.now());
        responseObj.setStatus("success");
        responseObj.setMessage("success");
        responseObj.setPayload(postRepo.save(inputPost));
        return responseObj;
    }

    public ResponseClass findPostByUserId(UserIDDTO inputUserId) {
        ResponseClass responseObj = new ResponseClass();
        Optional<List<PostEntity>> userPostsOpt = postRepo.findByUserIdOrderByCreatedAtDesc(inputUserId.getId());
        if (userPostsOpt.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find any post from user id: " + inputUserId.getId());
            responseObj.setPayload(null);
        } else {

            List<PostEntity> userPosts = userPostsOpt.get();
            logger.debug(userPosts.toString());
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            responseObj.setPayload(userPosts);
        }
        return responseObj;
    }
}
