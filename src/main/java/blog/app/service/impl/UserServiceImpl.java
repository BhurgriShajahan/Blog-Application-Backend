package blog.app.service.impl;

import blog.app.mapper.UserMapper;
import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.UserDto;
import blog.app.model.entities.User;
import blog.app.repository.UserRepository;
import blog.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public CustomResponseEntity<UserDto> saveUser(UserDto userDto) {
        try {
            if (userDto == null) {
                logger.warn("Attempted to save a null UserDto.");
                return new CustomResponseEntity<>(HttpStatus.BAD_REQUEST.value(), "UserDto cannot be null!", null);
            }

            boolean emailExists = userRepository.existsByEmail(userDto.getEmail());
            if (emailExists) {
                logger.warn("Email '{}' already exists in the database.", userDto.getEmail());
                return new CustomResponseEntity<>(HttpStatus.CONFLICT.value(), "Email is already registered.", null);
            }
            boolean poneExists = userRepository.existsByPhone(userDto.getPhone());
            if (poneExists) {
                logger.warn("Phone '{}' already exists in the database.", userDto.getEmail());
                return new CustomResponseEntity<>(HttpStatus.CONFLICT.value(), "Phone is already registered.", null);
            }

            User user = userMapper.dtoToEntity(userDto);
            user = userRepository.save(user);

            UserDto savedUserDto = userMapper.entityToDto(user);

            logger.info("User successfully created with ID: {}", user.getId());
            return new CustomResponseEntity<>(savedUserDto, "User created successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while creating user: {}", e.getMessage(), e);
            return new CustomResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while creating the user.", null);
        }
    }


    @Override
    public CustomResponseEntity<List<UserDto>> fetchAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            List<UserDto> userDtos = users.stream()
                    .map(userMapper::entityToDto)
                    .collect(Collectors.toList());

            logger.info("Fetched {} users.", userDtos.size());
            return new CustomResponseEntity<>(userDtos, "Fetched all users successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while fetching all users: {}", e.getMessage(), e);
            return new CustomResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while fetching users.", null);
        }
    }

    @Override
    public CustomResponseEntity<UserDto> fetchUserDetails(Long userId) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                logger.warn("User with ID {} not found.", userId);
                return new CustomResponseEntity<>(HttpStatus.NOT_FOUND.value(), "User not found.", null);
            }

            UserDto userDto = userMapper.entityToDto(userOptional.get());
            logger.info("Fetched user details for ID: {}", userId);
            return new CustomResponseEntity<>(userDto, "User details fetched successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while fetching user details for ID {}: {}", userId, e.getMessage(), e);
            return new CustomResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while fetching user details.", null);
        }
    }

    @Override
    public CustomResponseEntity<UserDto> updateUser(UserDto userDto, Long userId) {
        try {
            if (userDto == null) {
                logger.warn("Attempted to update a user with null UserDto.");
                return new CustomResponseEntity<>(HttpStatus.BAD_REQUEST.value(), "UserDto cannot be null!", null);
            }

            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                logger.warn("User with ID {} not found for update.", userId);
                return new CustomResponseEntity<>(HttpStatus.NOT_FOUND.value(), "User not found.", null);
            }

            User existingUser = userOptional.get();

            if (!existingUser.getEmail().equals(userDto.getEmail()) && userRepository.existsByEmail(userDto.getEmail())) {
                logger.warn("Email '{}' is already in use by another user.", userDto.getEmail());
                return new CustomResponseEntity<>(HttpStatus.CONFLICT.value(), "Email is already registered to another user.", null);
            }

            existingUser.setUsername(userDto.getUsername());
            existingUser.setEmail(userDto.getEmail());
            existingUser.setPhone(userDto.getPhone());
            existingUser.setAbout(userDto.getAbout());
            existingUser.setPassword(userDto.getPassword());

            existingUser = userRepository.save(existingUser);

            UserDto updatedUserDto = userMapper.entityToDto(existingUser);

            logger.info("User updated successfully with ID: {}", userId);
            return new CustomResponseEntity<>(updatedUserDto, "User updated successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while updating user with ID {}: {}", userId, e.getMessage(), e);
            return new CustomResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while updating the user.", null);
        }
    }


    @Override
    public CustomResponseEntity<?> deleteUser(Long userId) {
        try {
            Optional<User> findUser = userRepository.findById(userId);
            if (findUser.isEmpty()) {
                logger.warn("User with ID {} not found for deletion.", userId);
                return new CustomResponseEntity<>(HttpStatus.NOT_FOUND.value(), "User not found!", null);
            }

            userRepository.deleteById(userId);
            logger.info("User with ID {} deleted successfully.", userId);
            return new CustomResponseEntity<>(HttpStatus.OK.value(), "User deleted successfully.", null);
        } catch (Exception e) {
            logger.error("Error occurred while deleting user with ID {}: {}", userId, e.getMessage(), e);
            return new CustomResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while deleting the user.", null);
        }
    }
}
