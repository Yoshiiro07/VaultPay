package com.vaultpay.domain.model;

import com.vaultpay.domain.enums.TransactionStatus;
import com.vaultpay.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private UUID sourceAccountId;
    private UUID targetAccountId;
    private BigDecimal amount;
    private TransactionType transactionType;
    private TransactionStatus status;
    private String failureReason;
    private LocalDateTime createdAt;

    public Transaction(UUID id, UUID sourceAccountId, UUID targetAccountId, BigDecimal amount, 
                       TransactionType transactionType, TransactionStatus status, 
                       String failureReason, LocalDateTime createdAt) {
        this.id = id != null ? id : UUID.randomUUID();
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.status = status != null ? status : TransactionStatus.PENDING;
        this.failureReason = failureReason;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    //Methods update transaction status
    public void markAsCompleted() {
        this.status = TransactionStatus.COMPLETED;
    }

    public void markAsFailed(String reason) {
        this.status = TransactionStatus.FAILED;
        this.failureReason = reason;
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getSourceAccountId() { return sourceAccountId; }
    public UUID getTargetAccountId() { return targetAccountId; }
    public BigDecimal getAmount() { return amount; }
    public TransactionType getTransactionType() { return transactionType; }
    public TransactionStatus getStatus() { return status; }
    public String getFailureReason() { return failureReason; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
