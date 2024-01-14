package com.asynclabs.auth.dto;

import com.asynclabs.auth.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String email;

    private String firstName;

    private String lastName;

    private Role role;
}
