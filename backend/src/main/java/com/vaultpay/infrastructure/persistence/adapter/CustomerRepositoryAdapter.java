package com.vaultpay.infrastructure.persistence.adapter;

import com.vaultpay.domain.model.Customer;
import com.vaultpay.domain.repository.CustomerRepository;
import com.vaultpay.infrastructure.persistence.entity.CustomerEntity;
import com.vaultpay.infrastructure.persistence.repository.SpringDataCustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final SpringDataCustomerRepository springDataRepository;

    public CustomerRepositoryAdapter(SpringDataCustomerRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = toEntity(customer);
        CustomerEntity saved = springDataRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return springDataRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Customer> findByCpfCnpj(String cpfCnpj) {
        return springDataRepository.findByCpfCnpj(cpfCnpj).map(this::toDomain);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return springDataRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public boolean existsByCpfCnpj(String cpfCnpj) {
        return springDataRepository.existsByCpfCnpj(cpfCnpj);
    }

    @Override
    public boolean existsByEmail(String email) {
        return springDataRepository.existsByEmail(email);
    }

    private CustomerEntity toEntity(Customer domain) {
        return new CustomerEntity(
                domain.getId(),
                domain.getFullName(),
                domain.getCpfCnpj(),
                domain.getEmail(),
                domain.getPasswordHash(),
                domain.getStatus(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }

    private Customer toDomain(CustomerEntity entity) {
        return new Customer(
                entity.getId(),
                entity.getFullName(),
                entity.getCpfCnpj(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}