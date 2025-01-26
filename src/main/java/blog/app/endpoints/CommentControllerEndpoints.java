package blog.app.endpoints;

import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.CommentDto;
import blog.app.model.dto.UpdateCommentDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/comments/")
public interface CommentControllerEndpoints {

    //http://localhost:8090/v1/comments/?postId=1
    //Create
    @PostMapping
    CustomResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto, @RequestParam(required = true) Long postId);

    //http://localhost:8090/v1/comments/update/1
    //Update Comment
    @PutMapping("update/{commentId}")
    CustomResponseEntity<UpdateCommentDto> updateComment(@Valid @RequestBody UpdateCommentDto updateCommentDto, @PathVariable Long commentId);

    //http://localhost:8090/v1/comments/delete/2
    //Delete comment
    @DeleteMapping("delete/{commentId}")
    CustomResponseEntity<?> deleteComment(@PathVariable Long commentId);
}
