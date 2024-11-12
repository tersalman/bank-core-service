package com.example.core_banking_service_jdk_23_maven.service;

import com.example.core_banking_service_jdk_23_maven.model.BankAccountEntity;
import com.example.core_banking_service_jdk_23_maven.model.UtilityAccountEntity;
import com.example.core_banking_service_jdk_23_maven.model.dto.BankAccount;
import com.example.core_banking_service_jdk_23_maven.model.dto.UtilityAccount;
import com.example.core_banking_service_jdk_23_maven.model.mapper.BankAccountMapper;
import com.example.core_banking_service_jdk_23_maven.model.mapper.UtilityAccountMapper;
import com.example.core_banking_service_jdk_23_maven.repository.BankAccountRepository;
import com.example.core_banking_service_jdk_23_maven.repository.UtilityAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private BankAccountMapper bankAccountMapper = new BankAccountMapper();
    private UtilityAccountMapper utilityAccountMapper = new UtilityAccountMapper();

    private final BankAccountRepository bankAccountRepository;
    private final UtilityAccountRepository utilityAccountRepository;

    public BankAccount readBankAccount(String accountNumber) {
        BankAccountEntity entity = bankAccountRepository.findByNumber(accountNumber).get();
        return bankAccountMapper.convertToDto(entity);
    }

    public UtilityAccount readUtilityAccount(String provider) {
        UtilityAccountEntity utilityAccountEntity = utilityAccountRepository.findByProviderName(provider).get();
        return utilityAccountMapper.convertToDto(utilityAccountEntity);
    }

    public UtilityAccount readUtilityAccount(Long id){
        return utilityAccountMapper.convertToDto(utilityAccountRepository.findById(id).get());
    }
}
