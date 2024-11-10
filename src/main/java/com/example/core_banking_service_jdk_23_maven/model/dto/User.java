package com.example.core_banking_service_jdk_23_maven.model.dto;

import com.example.core_banking_service_jdk_23_maven.model.BankAccountEntity;
import lombok.Data;

import java.util.List;

@Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String identificationNumber;
    private List<BankAccount> bankAccounts;

}
