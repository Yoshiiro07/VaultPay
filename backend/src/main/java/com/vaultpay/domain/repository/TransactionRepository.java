package com.vaultpay.domain.repository;

import com.vaultpay.domain.model.Transaction;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    Optional<Transaction> findById(UUID id);
    List<Transaction> findBySourceAccountId(UUID sourceAccountId);
    List<Transaction> findByTargetAccountId(UUID targetAccountId);
}