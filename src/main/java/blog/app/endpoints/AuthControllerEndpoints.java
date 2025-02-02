package blog.app.endpoints;

import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.request.LoginRequest;
import blog.app.model.dto.request.SignupRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/auth")
public interface AuthControllerEndpoints {

    // Url http://localhost:8090/v1/auth/signup
    @PostMapping("/signup")
    CustomResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest);

    //Url http://localhost:8090/v1/auth/login
    @PostMapping("/login")
    CustomResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest);

}
