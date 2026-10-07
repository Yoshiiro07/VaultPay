package com.vaultpay.infrastructure.persistence.adapter;

import com.vaultpay.domain.model.Account;
import com.vaultpay.domain.repository.AccountRepository;
import com.vaultpay.infrastructure.persistence.entity.AccountEntity;
import com.vaultpay.infrastructure.persistence.repository.SpringDataAccountRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public class AccountRepositoryAdapter implements AccountRepository {

    private final SpringDataAccountRepository springDataAccountRepository;

    public AccountRepositoryAdapter(SpringDataAccountRepository springDataAccountRepository) {
        this.springDataAccountRepository = springDataAccountRepository;
    }

    @Override
    public Account save(Account account) {
        AccountEntity entity = toEntity(account);
        AccountEntity saved = springDataAccountRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return springDataAccountRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return springDataAccountRepository.findByAccountNumber(accountNumber).map(this::toDomain);
    }

    private AccountEntity toEntity(Account domain) {
        return new AccountEntity(
                domain.getId(),
                domain.getAccountNumber(),
                domain.getAgency(),
                domain.getAccountType(),
                domain.getBalance(),
                domain.getCreditLimit(),
                domain.getStatus(),
                domain.getCustomerId(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }

    private Account toDomain(AccountEntity entity) {
        return new Account(
                entity.getId(),
                entity.getAccountNumber(),
                entity.getAgency(),
                entity.getAccountType(),
                entity.getBalance(),
                entity.getCreditLimit(),
                entity.getStatus(),
                entity.getCustomerId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}