package live.allstudy.controller;


import live.allstudy.dto.UserEmailDTO;
import live.allstudy.dto.UserIDDTO;
import live.allstudy.dto.likeIdDTO;
import live.allstudy.dto.postIdDTO;
import live.allstudy.entity.PostEntity;
import live.allstudy.service.PostService;
import live.allstudy.service.ReportService;
import live.allstudy.util.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    private ReportService reportService;


    @GetMapping("/getAllPost")
    public ResponseEntity<ResponseObj> getAllPost(){
        return new ResponseEntity<ResponseObj>(postService.getAllPost(),HttpStatus.OK);
    }


    @PostMapping("/deletePostById")
    public ResponseEntity<ResponseObj> deletePost(@RequestBody UserIDDTO id){
        return new ResponseEntity<ResponseObj>(postService.deletePostById(id.getId()),HttpStatus.OK);
    }

    @PostMapping("/insertPost")
    public ResponseEntity<ResponseObj> insertPost(@RequestBody PostEntity inputPost) {
        return new ResponseEntity<ResponseObj>(postService.insertPost(inputPost), HttpStatus.OK);
    }

    @PostMapping("/followingPostsById")
    public ResponseEntity<ResponseObj> findPostByUserId(@RequestBody UserIDDTO inputUserId) {
         return new ResponseEntity<ResponseObj>(postService.findFollowingPostById(inputUserId),HttpStatus.OK);
    }

    @PostMapping("/myPost")
    public ResponseEntity<ResponseObj> findMyPost(@RequestBody UserIDDTO inputUserId) {
        return new ResponseEntity<ResponseObj>(postService.findPostByUserId(inputUserId), HttpStatus.OK);
    }
    @PostMapping("/followingPosts")
    public ResponseEntity<ResponseObj> findPostByFollowing(@RequestBody UserIDDTO inputUserId) {
        return new ResponseEntity<ResponseObj>(postService.findPostByFollowing(inputUserId), HttpStatus.OK);
    }

    @PostMapping("/likePost")
    public ResponseEntity<ResponseObj> lovePost(@RequestBody likeIdDTO likeIdDTO) {

        return new ResponseEntity<ResponseObj>(postService.updatePostByLike(likeIdDTO.getPostID(),likeIdDTO.getUserID()), HttpStatus.OK);
    }

    @PostMapping("/sharePost")
    public ResponseEntity<ResponseObj> sharePost(@RequestBody UserIDDTO postId, @RequestBody UserIDDTO userID) {
        return new ResponseEntity<ResponseObj>(postService.updatePostByShare(postId,userID), HttpStatus.OK);
    }
}
