package com.vaultpay.domain.service;

import com.vaultpay.domain.model.Account;
import com.vaultpay.domain.model.LedgerEntry;
import com.vaultpay.domain.repository.AccountRepository;
import com.vaultpay.domain.repository.LedgerEntryRepository;

import java.util.List;
import java.util.UUID;

public class AccountService {

    private final AccountRepository accountRepository;
    private final LedgerEntryRepository ledgerEntryRepository;

    public AccountService(AccountRepository accountRepository, LedgerEntryRepository ledgerEntryRepository) {
        this.accountRepository = accountRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
    }

    public Account getAccountById(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));
    }

    public List<LedgerEntry> getStatement(UUID accountId) {
        // Valida se a conta existe antes de buscar o extrato
        getAccountById(accountId);
        return ledgerEntryRepository.findByAccountId(accountId);
    }
}