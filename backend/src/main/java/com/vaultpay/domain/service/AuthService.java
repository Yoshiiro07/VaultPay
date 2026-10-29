package com.vaultpay.domain.service;

import com.vaultpay.api.dto.RegisterRequestDto;
import com.vaultpay.api.dto.RegisterResponseDto;
import com.vaultpay.domain.enums.AccountStatus;
import com.vaultpay.domain.enums.AccountType;
import com.vaultpay.domain.enums.CustomerStatus;
import com.vaultpay.domain.model.Account;
import com.vaultpay.domain.model.Customer;
import com.vaultpay.domain.service.security.PasswordEncoderPort;
import com.vaultpay.domain.repository.AccountRepository;
import com.vaultpay.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class AuthService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoderPort passwordEncoder;

    public AuthService(CustomerRepository customerRepository,
            AccountRepository accountRepository,
            PasswordEncoderPort passwordEncoder) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegisterResponseDto register(RegisterRequestDto request) {
        if (customerRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado no sistema.");
        }

        String hashedPassword = passwordEncoder.encode(request.password());

        Customer customer = new Customer(
                UUID.randomUUID(),
                request.fullName(),
                request.cpfCnpj(),
                request.email(),
                hashedPassword,
                CustomerStatus.ACTIVE,
                LocalDateTime.now(),
                null);
        Customer savedCustomer = customerRepository.save(customer);

        String accountNumber = (10000 + new Random().nextInt(90000)) + "-" + new Random().nextInt(10);
        String agency = "0001";

        Account account = new Account(
                UUID.randomUUID(),
                accountNumber,
                agency,
                AccountType.CHECKING,
                BigDecimal.ZERO,
                BigDecimal.valueOf(500.00), // Limite de crédito
                AccountStatus.ACTIVE,
                savedCustomer.getcustomer_id(),
                LocalDateTime.now(),
                null);
        Account savedAccount = accountRepository.save(account);

        return new RegisterResponseDto(
                savedCustomer.getcustomer_id(),
                savedCustomer.getFullName(),
                savedCustomer.getEmail(),
                savedAccount.getId(),
                savedAccount.getAgency(),
                savedAccount.getAccountNumber(),
                savedAccount.getBalance());
    }
}