package com.vaultpay.infrastructure.config;

import com.vaultpay.domain.repository.AccountRepository;
import com.vaultpay.domain.repository.LedgerEntryRepository;
import com.vaultpay.domain.repository.TransactionRepository;
import com.vaultpay.domain.service.TransferService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainServiceConfig {

    @Bean
    public TransferService transferService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            LedgerEntryRepository ledgerEntryRepository
    ) {
        return new TransferService(accountRepository, transactionRepository, ledgerEntryRepository);
    }
}