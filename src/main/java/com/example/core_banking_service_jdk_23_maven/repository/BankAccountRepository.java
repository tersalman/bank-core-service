package com.example.core_banking_service_jdk_23_maven.repository;

import com.example.core_banking_service_jdk_23_maven.model.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {
    Optional<BankAccountEntity> findByNumber(String accountNumber);
}
