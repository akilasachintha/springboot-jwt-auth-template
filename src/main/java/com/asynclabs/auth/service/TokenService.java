package com.asynclabs.auth.service;

import com.asynclabs.auth.entity.AppUser;
import com.asynclabs.auth.entity.Token;
import com.asynclabs.auth.entity.TokenType;
import com.asynclabs.auth.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public void saveToken(String jwtToken, AppUser user) {
        var token = Token.builder()
                .token(jwtToken)
                .appUser(user)
                .tokenType(TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();

        tokenRepository.save(token);
    }

    public void removeToken(String jwtToken) {
        var token = tokenRepository.findByToken(jwtToken);
        if (token != null) {
            tokenRepository.delete(token);
        }
    }

    public boolean isValidToken(String jwtToken) {
        var token = tokenRepository.findByToken(jwtToken);
        return token != null && !token.isExpired() && !token.isRevoked();
    }

    public Token getToken(String jwtToken) {
        return tokenRepository.findByToken(jwtToken);
    }
}
