package com.vaultpay.api.controller;

import com.vaultpay.api.dto.StatementResponseDto;
import com.vaultpay.domain.model.Account;
import com.vaultpay.domain.model.LedgerEntry;
import com.vaultpay.domain.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}/statement")
    public ResponseEntity<StatementResponseDto> getStatement(@PathVariable("id") UUID accountId) {
        Account account = accountService.getAccountById(accountId);
        List<LedgerEntry> entries = accountService.getStatement(accountId);

        List<StatementResponseDto.LedgerEntryDto> entryDtos = entries.stream()
                .map(entry -> new StatementResponseDto.LedgerEntryDto(
                        entry.getId(),
                        entry.getTransactionId(),
                        entry.getEntryType(),
                        entry.getAmount(),
                        entry.getBalanceAfter(),
                        entry.getTimestamp()
                ))
                .collect(Collectors.toList());

        StatementResponseDto response = new StatementResponseDto(
                account.getId(),
                account.getBalance(),
                entryDtos
        );

        return ResponseEntity.ok(response);
    }
}