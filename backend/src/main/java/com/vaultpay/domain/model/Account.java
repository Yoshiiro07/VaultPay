package com.vaultpay.domain.model;

import com.vaultpay.domain.enums.AccountStatus;
import com.vaultpay.domain.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Account {
    private UUID id;
    private String accountNumber;
    private String agency;
    private AccountType accountType;
    private BigDecimal balance;
    private BigDecimal creditLimit;
    private AccountStatus status;
    private UUID customerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Account(UUID id, String accountNumber, String agency, AccountType accountType, 
                   BigDecimal balance, BigDecimal creditLimit, AccountStatus status, 
                   UUID customerId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id != null ? id : UUID.randomUUID();
        this.accountNumber = Objects.requireNonNull(accountNumber, "Número da conta é obrigatório");
        this.agency = Objects.requireNonNull(agency, "Agência é obrigatória");
        this.accountType = accountType != null ? accountType : AccountType.CHECKING;
        this.balance = balance != null ? balance : BigDecimal.ZERO;
        this.creditLimit = creditLimit != null ? creditLimit : BigDecimal.ZERO;
        this.status = status != null ? status : AccountStatus.ACTIVE;
        this.customerId = Objects.requireNonNull(customerId, "ID do correntista é obrigatório");
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
    }

     // Depósito
     public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do depósito precisa ser maior que zero.");
        }
        if (this.status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Conta inativa ou bloqueada, verifique na sua agência.");
        }

        this.balance = this.balance.add(amount);
        this.updatedAt = LocalDateTime.now();
        this.balance = this.balance.add(amount);
        this.updatedAt = LocalDateTime.now();
     } 
     
     // Saque
        public void withdraw(BigDecimal amount) {
            if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("O valor do saque deve ser maior que zero");
            }
            if(this.status != AccountStatus.ACTIVE) {
                throw new IllegalStateException("Não é possível sacar de uma conta inativa, verifique na sua agência.");
            }
    
            BigDecimal availableBalance = this.balance.add(this.creditLimit);
            if(availableBalance.compareTo(amount) < 0) {
                throw new IllegalStateException("Saldo insuficiente para realizar a transação");
            }
            this.balance = this.balance.subtract(amount);
            this.updatedAt = LocalDateTime.now();
        }

     // Getters
    public UUID getId() { return id; }
    public String getAccountNumber() { return accountNumber; }
    public String getAgency() { return agency; }
    public AccountType getAccountType() { return accountType; }
    public BigDecimal getBalance() { return balance; }
    public BigDecimal getCreditLimit() { return creditLimit; }
    public AccountStatus getStatus() { return status; }
    public UUID getCustomerId() { return customerId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}