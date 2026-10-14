package com.vaultpay.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record RegisterResponseDto(
        UUID customerId,
        String fullName,
        String email,
        UUID accountId,
        String agency,
        String accountNumber,
        BigDecimal initialBalance) {
}