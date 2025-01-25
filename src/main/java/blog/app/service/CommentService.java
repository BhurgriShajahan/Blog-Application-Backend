package blog.app.service;

import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.CommentDto;
import blog.app.model.dto.UpdateCommentDto;

public interface CommentService {

    CustomResponseEntity<CommentDto> createComment(CommentDto commentDto,Long postId);
    CustomResponseEntity<UpdateCommentDto> updateComment(UpdateCommentDto commentDto, Long commentId);
    CustomResponseEntity<?> deleteComment(Long commentId);
}
