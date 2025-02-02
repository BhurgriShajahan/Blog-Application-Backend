package blog.app.mapper;

import blog.app.model.dto.request.UserSignupDto;
import blog.app.model.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSignupMapper {

    UserSignupDto entityToDto(User user);

    User dtoToEntity(UserSignupDto userSignupDto);
}
