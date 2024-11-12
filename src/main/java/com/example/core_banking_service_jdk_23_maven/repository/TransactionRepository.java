package com.example.core_banking_service_jdk_23_maven.repository;

import com.example.core_banking_service_jdk_23_maven.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
