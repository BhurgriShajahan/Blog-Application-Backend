package blog.app.controller;

import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.CommentDto;
import blog.app.model.dto.UpdateCommentDto;
import blog.app.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/comments/")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping
    public CustomResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto,@RequestParam(required = true) Long postId) {
        return commentService.createComment(commentDto,postId);
    }


    @PutMapping("update/{commentId}")
    public CustomResponseEntity<UpdateCommentDto> updateComment(@Valid @RequestBody UpdateCommentDto updateCommentDto, @PathVariable Long commentId) {
        return commentService.updateComment(updateCommentDto, commentId);
    }


    @DeleteMapping("delete/{commentId}")
    public CustomResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }
}
