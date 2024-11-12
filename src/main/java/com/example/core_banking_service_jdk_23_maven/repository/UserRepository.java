package com.example.core_banking_service_jdk_23_maven.repository;

import com.example.core_banking_service_jdk_23_maven.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByIdentificationNumber(String identificationNumber);
}
