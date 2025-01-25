package blog.app.mapper;

import blog.app.model.dto.UpdatePostDto;
import blog.app.model.entities.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdatePostMapper {

    UpdatePostDto entityToDto(Post post);
    Post dtoToEntity(UpdatePostDto updatePostDto);

}
