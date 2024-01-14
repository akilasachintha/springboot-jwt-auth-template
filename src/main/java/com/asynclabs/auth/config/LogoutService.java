package com.asynclabs.auth.config;

import com.asynclabs.auth.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenService tokenService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwt;

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        jwt = authorizationHeader.substring(7);
        var storedToken = tokenService.getToken(jwt);
        if (storedToken != null) {
            tokenService.removeToken(jwt);
            SecurityContextHolder.clearContext();
        }
    }
}
