package blog.app.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthResponse {
    private Long id;
    private String username;
    private String email;
}
