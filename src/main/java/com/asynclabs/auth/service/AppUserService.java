package com.asynclabs.auth.service;

import com.asynclabs.auth.dto.ApiResponse;
import com.asynclabs.auth.dto.ChangePasswordRequest;
import com.asynclabs.auth.dto.ChangePasswordResponse;
import com.asynclabs.auth.entity.AppUser;
import com.asynclabs.auth.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;

    public ApiResponse<ChangePasswordResponse> changePassword(ChangePasswordRequest changePasswordRequest, Principal connectedUser) {
        var user = (AppUser) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            return ApiResponse.error("Old password is incorrect");
        }

        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            return ApiResponse.error("New password and confirm new password must be the same");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        appUserRepository.save(user);

        return ApiResponse.success(ChangePasswordResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build());
    }
}
