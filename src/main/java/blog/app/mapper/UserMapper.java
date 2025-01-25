package blog.app.mapper;

import blog.app.model.dto.UserDto;
import blog.app.model.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto entityToDto(User user);
    User dtoToEntity(UserDto userDto);

}
