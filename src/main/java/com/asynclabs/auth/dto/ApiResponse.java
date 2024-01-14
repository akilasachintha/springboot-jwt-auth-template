package com.asynclabs.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiResponse<T> {
    private T data;
    private boolean isSuccess;
    private String error;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, true, "");
    }

    public static <T> ApiResponse<T> error(String errorMessage) {
        return new ApiResponse<>(null, false, errorMessage);
    }
}

