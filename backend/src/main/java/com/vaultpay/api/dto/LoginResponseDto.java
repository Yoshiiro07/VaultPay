package com.vaultpay.api.dto;

public record LoginResponseDto(
        String accessToken,
        String tokenType,
        long expiresInSeconds) {
    public static LoginResponseDto bearer(String token, long expiresInSeconds) {
        return new LoginResponseDto(token, "Bearer", expiresInSeconds);
    }
}