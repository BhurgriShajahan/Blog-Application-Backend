package blog.app.mapper;

import blog.app.model.dto.CommentDto;
import blog.app.model.dto.UpdateCommentDto;
import blog.app.model.entities.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateCommentMapper {

    UpdateCommentDto entityToDto(Comment comment);
    CommentDto dtoToEntity(UpdateCommentDto commentDto);
}
