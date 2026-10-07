package com.vaultpay.api.controller;

import com.vaultpay.api.dto.TransactionResponseDto;
import com.vaultpay.api.dto.TransferRequestDto;
import com.vaultpay.domain.model.Transaction;
import com.vaultpay.domain.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransferService transferService;

    public TransactionController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDto> transfer(@RequestBody TransferRequestDto request) {
        Transaction transaction = transferService.execute(
                request.getSourceAccountId(),
                request.getTargetAccountId(),
                request.getAmount(),
                request.getTransactionType()
        );

        TransactionResponseDto response = new TransactionResponseDto(
                transaction.getId(),
                transaction.getSourceAccountId(),
                transaction.getTargetAccountId(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getStatus(),
                transaction.getFailureReason(),
                transaction.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }
}