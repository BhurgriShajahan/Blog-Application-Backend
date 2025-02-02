package blog.app.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequest {

    @NotBlank(message = "Username is required!")
    @Size(min = 5, max = 20, message = "Username should be between 5 and 20 characters.")
    private String username;

    @NotBlank(message = "Email is required!")
    @Email(message = "Please provide a valid email address.")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 6, max = 20, message = "Password should be between 6 and 20 characters.")
    private String password;

    @NotBlank(message = "Phone number is required!")
    @Pattern(regexp = "^[+][0-9]{10,15}$", message = "Phone number must start with '+' and be between 10 and 15 digits.")
    private String phone;

}
