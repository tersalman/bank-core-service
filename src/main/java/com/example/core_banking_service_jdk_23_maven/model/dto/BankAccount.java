package com.example.core_banking_service_jdk_23_maven.model.dto;

import com.example.core_banking_service_jdk_23_maven.model.AccountStatus;
import com.example.core_banking_service_jdk_23_maven.model.AccountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccount {
    private Long id;
    private String number;
    private AccountType type;
    private AccountStatus status;
    private BigDecimal availableBalance;
    private BigDecimal actualBalance;
    private User user;
}
