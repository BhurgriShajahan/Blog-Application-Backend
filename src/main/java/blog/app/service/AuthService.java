package blog.app.service;

import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.request.LoginRequest;
import blog.app.model.dto.request.SignupRequest;

public interface AuthService {
    CustomResponseEntity<?> signup(SignupRequest request);
    CustomResponseEntity<?> login(LoginRequest request);}

