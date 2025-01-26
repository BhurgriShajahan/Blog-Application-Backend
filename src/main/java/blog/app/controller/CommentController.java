package blog.app.controller;

import blog.app.endpoints.CommentControllerEndpoints;
import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.CommentDto;
import blog.app.model.dto.UpdateCommentDto;
import blog.app.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController implements CommentControllerEndpoints {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    public CustomResponseEntity<CommentDto> createComment(CommentDto commentDto,Long postId) {
        return commentService.createComment(commentDto,postId);
    }

    public CustomResponseEntity<UpdateCommentDto> updateComment(UpdateCommentDto updateCommentDto,Long commentId) {
        return commentService.updateComment(updateCommentDto, commentId);
    }

    public CustomResponseEntity<?> deleteComment(Long commentId) {
        return commentService.deleteComment(commentId);
    }
}
