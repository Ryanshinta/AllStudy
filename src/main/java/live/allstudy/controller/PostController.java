package live.allstudy.controller;


import live.allstudy.dto.UserEmailDTO;
import live.allstudy.entity.PostEntity;
import live.allstudy.service.PostService;
import live.allstudy.util.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/insertPost")
    public ResponseEntity<ResponseObj> insertPost(@RequestBody PostEntity inputPost) {
        return new ResponseEntity<ResponseObj>(postService.insertPost(inputPost), HttpStatus.OK);
    }

    @GetMapping("/myPosts")
    public ResponseEntity<ResponseObj> findPostByUserId(@RequestBody UserEmailDTO inputUserId) {
        return new ResponseEntity<ResponseObj>(postService.findPostByUserId(inputUserId), HttpStatus.OK);
    }

    @PostMapping("/followingPosts")
    public ResponseEntity<ResponseObj> findPostByFollowing(@RequestBody UserEmailDTO inputUserId) {
        return new ResponseEntity<ResponseObj>(postService.findPostByFollowing(inputUserId), HttpStatus.OK);
    }

    @PostMapping("/likePost")
    public ResponseEntity<ResponseObj> lovePost(@RequestBody UserEmailDTO postId, @RequestBody UserEmailDTO userID) {
        return new ResponseEntity<ResponseObj>(postService.updatePostByLike(postId,userID), HttpStatus.OK);
    }

    @PostMapping("/sharePost")
    public ResponseEntity<ResponseObj> sharePost(@RequestBody UserEmailDTO postId, @RequestBody UserEmailDTO userID) {
        return new ResponseEntity<ResponseObj>(postService.updatePostByShare(postId,userID), HttpStatus.OK);
    }
}
