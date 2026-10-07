package com.vaultpay.domain.repository;

import com.vaultpay.domain.model.LedgerEntry;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LedgerEntryRepository {
    LedgerEntry save(LedgerEntry ledgerEntry);
    Optional<LedgerEntry> findById(UUID id);
    List<LedgerEntry> findByAccountId(UUID accountId);
    List<LedgerEntry> findByTransactionId(UUID transactionId);
}