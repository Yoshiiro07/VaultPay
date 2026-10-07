package com.vaultpay.domain.repository;

import com.vaultpay.domain.model.Customer;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(UUID id);
    Optional<Customer> findByCpfCnpj(String cpfCnpj);
    Optional<Customer> findByEmail(String email);
    boolean existsByCpfCnpj(String cpfCnpj);
    boolean existsByEmail(String email);
}
