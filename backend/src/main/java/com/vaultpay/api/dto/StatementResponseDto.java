package com.vaultpay.api.dto;

import com.vaultpay.domain.enums.LedgerEntryType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class StatementResponseDto {

    private UUID accountId;
    private BigDecimal currentBalance;
    private List<LedgerEntryDto> entries;

    public StatementResponseDto() {}

    public StatementResponseDto(UUID accountId, BigDecimal currentBalance, List<LedgerEntryDto> entries) {
        this.accountId = accountId;
        this.currentBalance = currentBalance;
        this.entries = entries;
    }

    public UUID getAccountId() { return accountId; }
    public BigDecimal getCurrentBalance() { return currentBalance; }
    public List<LedgerEntryDto> getEntries() { return entries; }

    public static class LedgerEntryDto {
        private UUID id;
        private UUID transactionId;
        private LedgerEntryType entryType;
        private BigDecimal amount;
        private BigDecimal balanceAfter;
        private LocalDateTime timestamp;

        public LedgerEntryDto() {}

        public LedgerEntryDto(UUID id, UUID transactionId, LedgerEntryType entryType, BigDecimal amount, BigDecimal balanceAfter, LocalDateTime timestamp) {
            this.id = id;
            this.transactionId = transactionId;
            this.entryType = entryType;
            this.amount = amount;
            this.balanceAfter = balanceAfter;
            this.timestamp = timestamp;
        }

        public UUID getId() { return id; }
        public UUID getTransactionId() { return transactionId; }
        public LedgerEntryType getEntryType() { return entryType; }
        public BigDecimal getAmount() { return amount; }
        public BigDecimal getBalanceAfter() { return balanceAfter; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
}