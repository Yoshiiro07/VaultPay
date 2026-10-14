package com.vaultpay.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionDebitedEvent(
        UUID transactionId,
        UUID sourceAccountId,
        UUID targetAccountId,
        BigDecimal amount,
        String transactionType,
        LocalDateTime timestamp) {
}
