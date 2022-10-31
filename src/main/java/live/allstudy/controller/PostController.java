package live.allstudy.controller;


import live.allstudy.dto.UserIDDTO;
import live.allstudy.entity.PostEntity;
import live.allstudy.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/insertpost")
    public org.springframework.http.ResponseEntity insertPost(@RequestBody PostEntity inputPost) {
        return new org.springframework.http.ResponseEntity(postService.insertPost(inputPost), HttpStatus.OK);
    }

    @GetMapping("/myposts")
    public org.springframework.http.ResponseEntity findPostByUserId(@RequestBody UserIDDTO inputUserId) {
        return new org.springframework.http.ResponseEntity(postService.findPostByUserId(inputUserId), HttpStatus.OK);
    }

}
