package com.asynclabs.auth.service;

import com.asynclabs.auth.config.JwtService;
import com.asynclabs.auth.dto.*;
import com.asynclabs.auth.entity.AppUser;
import com.asynclabs.auth.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public ApiResponse<RegisterResponse> register(RegisterRequest registrationRequest) {
        try {
            var users = appUserRepository.findAll();
            for (var user : users) {
                if (user.getEmail().equals(registrationRequest.getEmail())) {
                    return ApiResponse.error("Email already exists");
                }
            }

            var user = AppUser.builder()
                    .email(registrationRequest.getEmail())
                    .password(passwordEncoder.encode(registrationRequest.getPassword()))
                    .firstName(registrationRequest.getFirstName())
                    .lastName(registrationRequest.getLastName())
                    .role(registrationRequest.getRole())
                    .build();

            appUserRepository.save(user);

            var result = RegisterResponse.builder()
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .role(user.getRole())
                    .build();

            return ApiResponse.success(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ApiResponse<AuthenticationResponse> login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));

        var user = appUserRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            return ApiResponse.error("User not found");
        }

        var jwtToken = jwtService.generateToken(user);

        var result = AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .build();

        tokenService.saveToken(jwtToken, user);

        return ApiResponse.success(result);
    }

    public ApiResponse<UserResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            var user = appUserRepository.findByEmail(authentication.getName());
            if (user == null) {
                return ApiResponse.error("User not found");
            }

            var result = UserResponse.builder()
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .role(user.getRole())
                    .build();

            return ApiResponse.success(result);
        }

        return ApiResponse.error("User not found");
    }
}
