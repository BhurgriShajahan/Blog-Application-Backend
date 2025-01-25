package blog.app.controller;

import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.UserDto;
import blog.app.service.UserService;
//import jakarta.validation.Valid;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Save new user
    @PostMapping("create")
    public CustomResponseEntity<UserDto>saveUser(@Valid @RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    // Fetch all users
    @GetMapping("all")
    public CustomResponseEntity<List<UserDto>> fetchAllUsers() {
         return userService.fetchAllUsers();
    }

    // Fetch user details by ID
    @GetMapping("fetch/{id}")
    public CustomResponseEntity<UserDto> fetchUserDetails(@PathVariable("id") Long userId) {
         return userService.fetchUserDetails(userId);
    }

    // Update user
    @PutMapping("update/{id}")
    public CustomResponseEntity<UserDto> updateUser(
           @Valid @RequestBody UserDto userDto,
            @PathVariable("id") Long userId) {
        return userService.updateUser(userDto, userId);
    }

    // Delete user by ID
    @DeleteMapping("/delete/{id}")
    public CustomResponseEntity<?> deleteUser(@PathVariable("id") Long userId) {
        return userService.deleteUser(userId);
    }

}
