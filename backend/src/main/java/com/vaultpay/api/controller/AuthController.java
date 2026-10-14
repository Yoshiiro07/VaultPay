package com.vaultpay.api.controller;

import com.vaultpay.api.dto.LoginRequestDto;
import com.vaultpay.api.dto.LoginResponseDto;
import com.vaultpay.api.dto.RegisterRequestDto;
import com.vaultpay.api.dto.RegisterResponseDto;
import com.vaultpay.domain.model.Customer;
import com.vaultpay.domain.repository.CustomerRepository;
import com.vaultpay.domain.service.security.PasswordEncoderPort;
import com.vaultpay.domain.service.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vaultpay.domain.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final CustomerRepository customerRepository;
    private final TokenService tokenService;
    private final PasswordEncoderPort passwordEncoder;
    private final AuthService authService;

    public AuthController(CustomerRepository customerRepository, TokenService tokenService,
            PasswordEncoderPort passwordEncoder, AuthService authService) {
        this.customerRepository = customerRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        Customer customer = customerRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas."));

        if (!passwordEncoder.matches(request.password(), customer.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenService.generateToken(customer.getId(), customer.getEmail());
        return ResponseEntity.ok(LoginResponseDto.bearer(token, 86400));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
        RegisterResponseDto response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}