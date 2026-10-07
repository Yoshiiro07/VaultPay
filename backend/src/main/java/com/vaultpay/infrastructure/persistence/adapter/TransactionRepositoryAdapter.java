package com.vaultpay.infrastructure.persistence.adapter;

import com.vaultpay.domain.model.Transaction;
import com.vaultpay.domain.repository.TransactionRepository;
import com.vaultpay.infrastructure.persistence.entity.TransactionEntity;
import com.vaultpay.infrastructure.persistence.repository.SpringDataTransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class TransactionRepositoryAdapter implements TransactionRepository {

    private final SpringDataTransactionRepository springDataRepository;

    public TransactionRepositoryAdapter(SpringDataTransactionRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = toEntity(transaction);
        TransactionEntity saved = springDataRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        return springDataRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Transaction> findBySourceAccountId(UUID sourceAccountId) {
        return springDataRepository.findBySourceAccountId(sourceAccountId)
                .stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByTargetAccountId(UUID targetAccountId) {
        return springDataRepository.findByTargetAccountId(targetAccountId)
                .stream().map(this::toDomain).collect(Collectors.toList());
    }

    private TransactionEntity toEntity(Transaction domain) {
        return new TransactionEntity(
                domain.getId(),
                domain.getSourceAccountId(),
                domain.getTargetAccountId(),
                domain.getAmount(),
                domain.getTransactionType(),
                domain.getStatus(),
                domain.getFailureReason(),
                domain.getCreatedAt()
        );
    }

    private Transaction toDomain(TransactionEntity entity) {
        return new Transaction(
                entity.getId(),
                entity.getSourceAccountId(),
                entity.getTargetAccountId(),
                entity.getAmount(),
                entity.getTransactionType(),
                entity.getStatus(),
                entity.getFailureReason(),
                entity.getCreatedAt()
        );
    }
}