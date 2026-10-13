package com.vaultpay.domain.service.security;

import java.util.UUID;

public interface TokenService {
    String generateToken(UUID userId, String email);

    boolean isTokenValid(String token);

    String getSubject(String token);
}
