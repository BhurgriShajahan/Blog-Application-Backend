package blog.app.mapper;

import blog.app.model.dto.PostDetailsDto;
import blog.app.model.entities.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostDetailsMapper {

    PostDetailsDto entityToDto(Post post);

    Post dtoToEntity(PostDetailsDto postDto);

}
