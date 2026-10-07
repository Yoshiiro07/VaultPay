package com.vaultpay.infrastructure.persistence.repository;

import com.vaultpay.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataCustomerRepository extends JpaRepository<CustomerEntity, UUID> {
    Optional<CustomerEntity> findByCpfCnpj(String cpfCnpj);
    Optional<CustomerEntity> findByEmail(String email);
    boolean existsByCpfCnpj(String cpfCnpj);
    boolean existsByEmail(String email);
}