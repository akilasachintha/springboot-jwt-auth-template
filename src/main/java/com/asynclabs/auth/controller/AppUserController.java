package com.asynclabs.auth.controller;

import com.asynclabs.auth.dto.ApiResponse;
import com.asynclabs.auth.dto.ChangePasswordRequest;
import com.asynclabs.auth.dto.ChangePasswordResponse;
import com.asynclabs.auth.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @PatchMapping("/user/change-password")
    public ResponseEntity<ApiResponse<ChangePasswordResponse>> changePassword(
            @RequestBody ChangePasswordRequest changePasswordRequest,
            Principal connectedUser) {

        var result = appUserService.changePassword(changePasswordRequest, connectedUser);
        return ResponseEntity.ok(result);
    }
}
