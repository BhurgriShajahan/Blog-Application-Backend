package blog.app.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class UserDto {

    private Long id;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    private String username;

    @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "Phone number must be between 10 and 15 digits"
    )
    private String phone;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=]).*$",
            message = "Password must include at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    private String password;

    @Size(max = 500, message = "About section cannot exceed 500 characters")
    private String about;

    //Getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Username cannot be blank") @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Username cannot be blank") @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters") String username) {
        this.username = username;
    }

    public @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "Phone number must be between 10 and 15 digits"
    ) String getPhone() {
        return phone;
    }

    public void setPhone(@Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "Phone number must be between 10 and 15 digits"
    ) String phone) {
        this.phone = phone;
    }

    public @NotBlank(message = "Email cannot be blank") @Email(message = "Invalid email format") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email cannot be blank") @Email(message = "Invalid email format") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password cannot be blank") @Size(min = 8, message = "Password must be at least 8 characters long") @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=]).*$",
            message = "Password must include at least one uppercase letter, one lowercase letter, one number, and one special character"
    ) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password cannot be blank") @Size(min = 8, message = "Password must be at least 8 characters long") @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=]).*$",
            message = "Password must include at least one uppercase letter, one lowercase letter, one number, and one special character"
    ) String password) {
        this.password = password;
    }

    public @Size(max = 500, message = "About section cannot exceed 500 characters") String getAbout() {
        return about;
    }

    public void setAbout(@Size(max = 500, message = "About section cannot exceed 500 characters") String about) {
        this.about = about;
    }
}
