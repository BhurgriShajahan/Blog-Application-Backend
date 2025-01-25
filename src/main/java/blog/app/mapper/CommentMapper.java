package blog.app.mapper;

import blog.app.model.dto.CommentDto;
import blog.app.model.entities.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment dtoToEntity(CommentDto commentDto);
    CommentDto entityToDto(Comment comment);

    // Custom mapping for postId
    default CommentDto entityToDtoWithPostId(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentDto commentDto = entityToDto(comment);
        if (comment.getPost() != null) {
            commentDto.setPostId(comment.getPost().getId());
        }
        return commentDto;
    }
}
