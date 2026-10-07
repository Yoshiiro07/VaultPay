package com.vaultpay.infrastructure.persistence.adapter;

import com.vaultpay.domain.model.LedgerEntry;
import com.vaultpay.domain.repository.LedgerEntryRepository;
import com.vaultpay.infrastructure.persistence.entity.LedgerEntryEntity;
import com.vaultpay.infrastructure.persistence.repository.SpringDataLedgerEntryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class LedgerEntryRepositoryAdapter implements LedgerEntryRepository {

    private final SpringDataLedgerEntryRepository springDataRepository;

    public LedgerEntryRepositoryAdapter(SpringDataLedgerEntryRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public LedgerEntry save(LedgerEntry ledgerEntry) {
        LedgerEntryEntity entity = toEntity(ledgerEntry);
        LedgerEntryEntity saved = springDataRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<LedgerEntry> findById(UUID id) {
        return springDataRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<LedgerEntry> findByAccountId(UUID accountId) {
        return springDataRepository.findByAccountId(accountId)
                .stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<LedgerEntry> findByTransactionId(UUID transactionId) {
        return springDataRepository.findByTransactionId(transactionId)
                .stream().map(this::toDomain).collect(Collectors.toList());
    }

    private LedgerEntryEntity toEntity(LedgerEntry domain) {
        return new LedgerEntryEntity(
                domain.getId(),
                domain.getTransactionId(),
                domain.getAccountId(),
                domain.getEntryType(),
                domain.getAmount(),
                domain.getBalanceAfter(),
                domain.getTimestamp()
        );
    }

    private LedgerEntry toDomain(LedgerEntryEntity entity) {
        return new LedgerEntry(
                entity.getId(),
                entity.getTransactionId(),
                entity.getAccountId(),
                entity.getEntryType(),
                entity.getAmount(),
                entity.getBalanceAfter(),
                entity.getTimestamp()
        );
    }
}