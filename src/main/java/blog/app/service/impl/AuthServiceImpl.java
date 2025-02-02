package blog.app.service.impl;

import blog.app.config.JwtUtil;
import blog.app.enums.RoleType;
import blog.app.exceptions.CustomException;
import blog.app.mapper.UserSignupMapper;
import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.EmailDto;
import blog.app.model.dto.request.LoginRequest;
import blog.app.model.dto.request.SignupRequest;
import blog.app.model.dto.request.UserSignupDto;
import blog.app.model.entities.Role;
import blog.app.model.entities.User;
import blog.app.repository.RoleRepository;
import blog.app.repository.UserRepository;
import blog.app.service.AuthService;
import blog.app.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private UserSignupMapper userSignupMapper;
    private EmailService emailService;


    @Override
    public CustomResponseEntity<?> signup(SignupRequest request) {
        try {
            if (userRepository.existsByUsername(request.getUsername())) {
                return new CustomResponseEntity<>(1000, "Username is already registered.");
            } else if (userRepository.existsByEmail(request.getEmail())) {
                return new CustomResponseEntity<>(1001, "Email is already in registered.");
            } else if (userRepository.existsByPhone(request.getPhone())) {
                return new CustomResponseEntity<>(1002, "Phone is already in registered.");
            }
            UserSignupDto userSignupDto = new UserSignupDto();
            userSignupDto.setUsername(request.getUsername());
            userSignupDto.setEmail(request.getEmail());
            userSignupDto.setPhone(request.getPhone());
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            userSignupDto.setPassword(encodedPassword);

            User user = userSignupMapper.dtoToEntity(userSignupDto);

            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByRoleType(RoleType.ROLE_USER)
                    .orElseThrow(() -> new CustomException("Role not found."));
            roles.add(userRole);
            user.setRoles(roles);

            userRepository.save(user);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("username", user.getUsername());
            responseData.put("email", user.getEmail());
            responseData.put("roles", roles.stream().map(role -> role.getRoleType().name()).collect(Collectors.toSet()));


            EmailDto emailDto = new EmailDto();
            emailDto.setTo(userSignupDto.getEmail());
            emailDto.setSubject("Signup");
            emailDto.setMessage("Signup up successfully 😍 your Otp is: 3295");
            emailService.sendEmail(emailDto);

            return new CustomResponseEntity<>(responseData, "User registered successfully.");

        } catch (CustomException e) {
            return new CustomResponseEntity<>(1000, e.getMessage(), 400);
        } catch (Exception e) {
            logger.error("Unexpected error during signup", e);
            return new CustomResponseEntity<>(1000, "An unexpected error occurred.", 500);
        }
    }

    @Override
    public CustomResponseEntity<?> login(LoginRequest request) {
        try {

            User user = userRepository.findByUsernameOrEmail(request.getUsernameOrEmail(),request.getUsernameOrEmail());

            if (user == null) {
                logger.error("User not found for username or email: " + request.getUsernameOrEmail());
                return new CustomResponseEntity<>(1001, "Invalid username or password.");
            }
            // Verify password
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                logger.error("Password mismatch for user: " + request.getUsernameOrEmail());
                return new CustomResponseEntity<>(1001, "Invalid username/email or password.");
            }

            List<String> rolesList = user.getRoles().stream()
                    .map(role -> role.getRoleType().name())
                    .collect(Collectors.toList());

            Set<GrantedAuthority> authorities = rolesList.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());

            String token = jwtUtil.generateToken(user.getUsername(), authorities);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("username", user.getUsername());
            responseData.put("token", token);
            responseData.put("roles", rolesList);

            return new CustomResponseEntity<>(responseData, "Login successfully.");
        } catch (Exception exception) {
            logger.error("Error occurred during login", exception);
            return CustomResponseEntity.errorResponse(exception);
        }
    }
}
