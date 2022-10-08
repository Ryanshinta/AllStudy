package live.allstudy.controller;


import live.allstudy.dto.UserIDDTO;
import live.allstudy.entity.PostEntity;
import live.allstudy.service.PostService;
import live.allstudy.util.ResponseClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/insertpost")
    public ResponseEntity<ResponseClass> insertPost(@RequestBody PostEntity inputPost) {
        return new ResponseEntity<ResponseClass>(postService.insertPost(inputPost), HttpStatus.OK);
    }

    @PostMapping("/myposts")
    public ResponseEntity<ResponseClass> findPostByUserId(@RequestBody UserIDDTO inputUserId) {
        return new ResponseEntity<ResponseClass>(postService.findPostByUserId(inputUserId), HttpStatus.OK);
    }

}
