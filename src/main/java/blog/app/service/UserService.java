package blog.app.service;

import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.UserDto;

import java.util.List;

public interface UserService {

    CustomResponseEntity<UserDto> saveUser(UserDto userDto);

    CustomResponseEntity<List<UserDto>> fetchAllUsers();

    CustomResponseEntity<UserDto> fetchUserDetails(Long userId);

    CustomResponseEntity<UserDto> updateUser(UserDto userDto,Long userId);

    CustomResponseEntity<?> deleteUser(Long userId);


}
