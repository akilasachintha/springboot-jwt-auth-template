package com.asynclabs.auth.repository;

import com.asynclabs.auth.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, String> {
    List<Token> findAllValidTokensByAppUserId(String user_id);

    Token findByToken(String token);
}
