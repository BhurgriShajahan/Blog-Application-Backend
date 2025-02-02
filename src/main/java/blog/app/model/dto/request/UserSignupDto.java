package blog.app.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSignupDto {

    private String username;
    private String password;
    private String email;
    private String phone;

}
