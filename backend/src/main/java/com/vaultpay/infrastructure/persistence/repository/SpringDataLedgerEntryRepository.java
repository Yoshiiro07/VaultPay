package com.vaultpay.infrastructure.persistence.repository;

import com.vaultpay.infrastructure.persistence.entity.LedgerEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SpringDataLedgerEntryRepository extends JpaRepository<LedgerEntryEntity, UUID> {
    List<LedgerEntryEntity> findByAccountId(UUID accountId);
    List<LedgerEntryEntity> findByTransactionId(UUID transactionId);
}