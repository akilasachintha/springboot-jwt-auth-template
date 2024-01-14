package com.asynclabs.auth.controller;

import com.asynclabs.auth.dto.*;
import com.asynclabs.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final LogoutHandler logoutHandler;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @RequestBody RegisterRequest registrationRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registrationRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(
            @RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @GetMapping("/logout")
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Unauthorized"));
        }

        logoutHandler.logout(request, response, authentication);
        return ResponseEntity.ok(ApiResponse.success("Logout successful!"));
    }

    @GetMapping("current-user")
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }
}
