package blog.app.controller;

import blog.app.endpoints.AuthControllerEndpoints;
import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.request.LoginRequest;
import blog.app.model.dto.request.SignupRequest;
import blog.app.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController implements AuthControllerEndpoints {

    private final AuthService authService;

    @Override
    public CustomResponseEntity<?> signup(SignupRequest signupRequest) {
        return authService.signup(signupRequest);
    }

    @Override
    public CustomResponseEntity<?> login(LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
