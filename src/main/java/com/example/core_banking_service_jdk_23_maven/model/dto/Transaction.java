package com.example.core_banking_service_jdk_23_maven.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transaction {
    private Long id;
    private BigDecimal amount;
    private BankAccount bankAccount;
    private String referenceNumber;

}
