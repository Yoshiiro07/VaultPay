package com.vaultpay.domain.repository;

import com.vaultpay.domain.model.Customer;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(UUID id);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    boolean existsByCpfCnpj(String cpfCnpj);
    boolean existsByEmail(String email);
}
