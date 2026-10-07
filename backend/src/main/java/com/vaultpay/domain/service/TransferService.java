package com.vaultpay.domain.service;

import com.vaultpay.domain.enums.LedgerEntryType;
import com.vaultpay.domain.enums.TransactionType;
import com.vaultpay.domain.model.Account;
import com.vaultpay.domain.model.LedgerEntry;
import com.vaultpay.domain.model.Transaction;
import com.vaultpay.domain.repository.AccountRepository;
import com.vaultpay.domain.repository.LedgerEntryRepository;
import com.vaultpay.domain.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class TransferService {
    
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final LedgerEntryRepository ledgerEntryRepository;

    public TransferService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            LedgerEntryRepository ledgerEntryRepository
    ) {
        this.accountRepository = Objects.requireNonNull(accountRepository, "AccountRepository não pode ser nulo");
        this.transactionRepository = Objects.requireNonNull(transactionRepository, "TransactionRepository não pode ser nulo");
        this.ledgerEntryRepository = Objects.requireNonNull(ledgerEntryRepository, "LedgerEntryRepository não pode ser nulo");
    }

    public Transaction execute(UUID sourceAccountId, UUID targetAccountId, BigDecimal amount, TransactionType transactionType) {
        if(sourceAccountId.equals(targetAccountId)) {
            throw new IllegalArgumentException("As contas de destino e origem devem ser diferentes.");
        }

        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0 ) {
            throw new IllegalArgumentException("o valor deve ser maior que zero.");
        }

        Account sourceAccount = accountRepository.findById(sourceAccountId)
        .orElseThrow(() -> new IllegalArgumentException("Conta de origem não encontrada."));

        Account targetAccount = accountRepository.findById(targetAccountId)
        .orElseThrow(() -> new IllegalArgumentException("Conta de destino não encontrada."));

        // Validação de saldo + limite
        BigDecimal availableBalance = sourceAccount.getBalance().add(sourceAccount.getCreditLimit());
        if (availableBalance.compareTo(amount) < 0) {
            Transaction failedTransaction = new Transaction(
                    null, sourceAccountId, targetAccountId, amount,
                    transactionType, null, "Saldo insuficiente", null
            );
            failedTransaction.markAsFailed("Saldo insuficiente");
            return transactionRepository.save(failedTransaction);
        }

        // Atualização dos saldos
        Account updatedSourceAccount = new Account(
                sourceAccount.getId(),
                sourceAccount.getAccountNumber(),
                sourceAccount.getAgency(),
                sourceAccount.getAccountType(),
                sourceAccount.getBalance().subtract(amount),
                sourceAccount.getCreditLimit(),
                sourceAccount.getStatus(),
                sourceAccount.getCustomerId(),
                sourceAccount.getCreatedAt(),
                null
        );

        Account updatedTargetAccount = new Account(
                targetAccount.getId(),
                targetAccount.getAccountNumber(),
                targetAccount.getAgency(),
                targetAccount.getAccountType(),
                targetAccount.getBalance().add(amount),
                targetAccount.getCreditLimit(),
                targetAccount.getStatus(),
                targetAccount.getCustomerId(),
                targetAccount.getCreatedAt(),
                null
        );

       // Persistência das contas
        accountRepository.save(updatedSourceAccount);
        accountRepository.save(updatedTargetAccount);

        // Registra a Transação concluída
        Transaction transaction = new Transaction(
                null, sourceAccountId, targetAccountId, amount,
                transactionType, null, null, null
        );
        transaction.markAsCompleted();
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Registra os lançamentos no Livro Razão (Ledger)
        LedgerEntry debitEntry = new LedgerEntry(
                null, savedTransaction.getId(), sourceAccountId,
                LedgerEntryType.DEBIT, amount, updatedSourceAccount.getBalance(), null
        );
        ledgerEntryRepository.save(debitEntry);

        LedgerEntry creditEntry = new LedgerEntry(
                null, savedTransaction.getId(), targetAccountId,
                LedgerEntryType.CREDIT, amount, updatedTargetAccount.getBalance(), null
        );
        ledgerEntryRepository.save(creditEntry);

        return savedTransaction;
    }
}