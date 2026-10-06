package com.vaultpay.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.vaultpay.domain.enums.LedgerEntryType;

public class LedgerEntry {
    private UUID id;
    private UUID transactionId;
    private UUID accountId;
    private LedgerEntryType entryType;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    private LocalDateTime timestamp;

    public LedgerEntry(UUID id, UUID transactionId, UUID accountId, LedgerEntryType entryType, 
                       BigDecimal amount, BigDecimal balanceAfter, LocalDateTime timestamp) {
        this.id = id != null ? id : UUID.randomUUID();
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.entryType = entryType;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getTransactionId() { return transactionId; }
    public UUID getAccountId() { return accountId; }
    public LedgerEntryType getEntryType() { return entryType; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getBalanceAfter() { return balanceAfter; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
