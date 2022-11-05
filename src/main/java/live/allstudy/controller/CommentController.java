package live.allstudy.controller;

import live.allstudy.dto.CommentPostRequestEntity;
import live.allstudy.dto.UserIDDTO;
import live.allstudy.entity.CommentEntity;
import live.allstudy.service.CommentService;
import live.allstudy.util.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/insertComment")
    public ResponseEntity<ResponseObj> insertComment(@RequestBody CommentPostRequestEntity postedComment) {
        CommentEntity inputComment = postedComment.getCommentEntity();
        UserIDDTO inputPostId = postedComment.getPostId();
        return new ResponseEntity<ResponseObj>(commentService.insertComment(inputComment, inputPostId.getId()), HttpStatus.OK);
    }

    @PostMapping("/getComments")
    public ResponseEntity<ResponseObj> getComments(@RequestBody UserIDDTO inputPostId) {
        return new ResponseEntity<ResponseObj>(commentService.getComments(inputPostId.getId()), HttpStatus.OK);
    }
}
