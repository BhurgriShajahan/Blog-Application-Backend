package blog.app.controller;

import blog.app.endpoints.CommentControllerEndpoints;
import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.CommentDto;
import blog.app.model.dto.UpdateCommentDto;
import blog.app.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController implements CommentControllerEndpoints {

    private final CommentService commentService;

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
