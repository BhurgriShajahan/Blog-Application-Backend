package blog.app.mapper;

import blog.app.model.dto.PostDto;
import blog.app.model.entities.Category;
import blog.app.model.entities.Post;
import blog.app.model.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mappings({
            @Mapping(source = "category.id", target = "categoryId"),
//            @Mapping(source = "user.id", target = "userId")
    })
    PostDto entityToDto(Post post);

    @Mappings({
            @Mapping(target = "category.id", source = "categoryId"),
//            @Mapping(target = "user.id", source = "userId")
    })
    Post dtoToEntity(PostDto postDto);
}
