package live.allstudy.service;

import live.allstudy.entity.CommentEntity;
import live.allstudy.entity.PostEntity;
import live.allstudy.repository.CommentRepository;
import live.allstudy.repository.PostRepository;
import live.allstudy.util.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private PostService postService;


    public ResponseObj insertComment(CommentEntity inputComment, String inputPostId) {
        ResponseObj responseObj = new ResponseObj();
        Optional<PostEntity> optPost = postRepo.findById(inputPostId);
        if (optPost.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find target post id: " + inputPostId);
            responseObj.setPayload(null);
            return responseObj;
        } else {
            inputComment.setCreatedAt(Instant.now());
            commentRepo.save(inputComment);
            PostEntity targetPost = optPost.get();
            List<CommentEntity> commentList = targetPost.getComment();
            if (commentList == null) {
                commentList = new ArrayList<>();
            }
            commentList.add(inputComment);
            targetPost.setComment(commentList);
            postService.updatePostByComment(targetPost);
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            responseObj.setPayload(inputComment);
            return responseObj;
        }
    }
}
