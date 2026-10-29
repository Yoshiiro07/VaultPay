package com.vaultpay.domain.model;

import com.vaultpay.domain.enums.CustomerStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity 
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID customer_id;
    
    private String fullName;
    private String cpfCnpj;
    private String email;
    private String passwordHash;
    private CustomerStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Customer(UUID customer_id, String fullName, String cpfCnpj, String email, 
                    String passwordHash, CustomerStatus status, 
                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.customer_id = customer_id != null ? customer_id : UUID.randomUUID();
        this.fullName = Objects.requireNonNull(fullName, "Nome completo é obrigatório");
        this.cpfCnpj = Objects.requireNonNull(cpfCnpj, "CPF/CNPJ é obrigatório");
        this.email = Objects.requireNonNull(email, "E-mail é obrigatório");
        this.passwordHash = Objects.requireNonNull(passwordHash, "Senha é obrigatória");
        this.status = status != null ? status : CustomerStatus.ACTIVE;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
    }

    //Getters
    public UUID getcustomer_id() { return customer_id; }
    public String getFullName() { return fullName; }
    public String getCpfCnpj() { return cpfCnpj; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public CustomerStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    
}
