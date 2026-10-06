package com.vaultpay.domain.model;

import com.vaultpay.domain.enums.CustomerStatus;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Customer {

    private UUID id;
    private String fullName;
    private String cpfCnpj;
    private String email;
    private String passwordHash;
    private CustomerStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Customer(UUID id, String fullName, String cpfCnpj, String email, 
                    String passwordHash, CustomerStatus status, 
                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id != null ? id : UUID.randomUUID();
        this.fullName = Objects.requireNonNull(fullName, "Nome completo é obrigatório");
        this.cpfCnpj = Objects.requireNonNull(cpfCnpj, "CPF/CNPJ é obrigatório");
        this.email = Objects.requireNonNull(email, "E-mail é obrigatório");
        this.passwordHash = Objects.requireNonNull(passwordHash, "Senha é obrigatória");
        this.status = status != null ? status : CustomerStatus.ACTIVE;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
    }

    //Getters
    public UUID getId() { return id; }
    public String getFullName() { return fullName; }
    public String getCpfCnpj() { return cpfCnpj; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public CustomerStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    
}
