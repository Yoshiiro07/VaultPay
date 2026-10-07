package com.vaultpay.infrastructure.persistence.repository;

import com.vaultpay.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SpringDataTransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findBySourceAccountId(UUID sourceAccountId);
    List<TransactionEntity> findByTargetAccountId(UUID targetAccountId);
}