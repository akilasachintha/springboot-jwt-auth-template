package com.asynclabs.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private boolean isExpired;

    private boolean isRevoked;

    @ManyToOne
    @JoinColumn(name = "app_user_id", foreignKey = @ForeignKey(name = "fk_token_app_user_id"))
    private AppUser appUser;
}
