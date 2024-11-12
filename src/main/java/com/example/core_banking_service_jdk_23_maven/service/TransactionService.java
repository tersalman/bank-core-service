package com.example.core_banking_service_jdk_23_maven.service;

import com.example.core_banking_service_jdk_23_maven.model.BankAccountEntity;
import com.example.core_banking_service_jdk_23_maven.model.TransactionEntity;
import com.example.core_banking_service_jdk_23_maven.model.TransactionType;
import com.example.core_banking_service_jdk_23_maven.model.dto.BankAccount;
import com.example.core_banking_service_jdk_23_maven.model.dto.UtilityAccount;
import com.example.core_banking_service_jdk_23_maven.model.dto.request.FundTransferRequest;
import com.example.core_banking_service_jdk_23_maven.model.dto.request.UtilityPaymentRequest;
import com.example.core_banking_service_jdk_23_maven.model.dto.response.FundTransferResponse;
import com.example.core_banking_service_jdk_23_maven.model.dto.response.UtilityPaymentResponse;
import com.example.core_banking_service_jdk_23_maven.repository.BankAccountRepository;
import com.example.core_banking_service_jdk_23_maven.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
    private final AccountService accountService;
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;

    public FundTransferResponse fundTransfer(FundTransferRequest fundTransferRequest) {

        BankAccount fromBankAccount = accountService.readBankAccount(fundTransferRequest.getFromAccount());
        BankAccount toBankAccount = accountService.readBankAccount(fundTransferRequest.getToAccount());

        //validating account balances
        validateBalance(fromBankAccount, fundTransferRequest.getAmount());

        String transactionId = internalFundTransfer(fromBankAccount, toBankAccount, fundTransferRequest.getAmount());
        return FundTransferResponse.builder().message("Transaction successfully completed").transactionId(transactionId).build();
    }

    public UtilityPaymentResponse utilPayment(UtilityPaymentRequest utilityPaymentRequest) {

        String transactionId = UUID.randomUUID().toString();

        BankAccount fromBankAccount = accountService.readBankAccount(utilityPaymentRequest.getAccount());

        //validating account balances
        validateBalance(fromBankAccount, utilityPaymentRequest.getAmount());

        UtilityAccount utilityAccount = accountService.readUtilityAccount(utilityPaymentRequest.getProviderId());

        BankAccountEntity fromAccount = bankAccountRepository.findByNumber(fromBankAccount.getNumber()).get();

        //we can call third party API to process UTIL payment from payment provider from here.

        fromAccount.setActualBalance(fromAccount.getActualBalance().subtract(utilityPaymentRequest.getAmount()));
        fromAccount.setAvailableBalance(fromAccount.getActualBalance().subtract(utilityPaymentRequest.getAmount()));

        transactionRepository.save(TransactionEntity.builder().transactionType(TransactionType.UTILITY_PAYMENT)
                .account(fromAccount)
                .transactionId(transactionId)
                .referenceNumber(utilityPaymentRequest.getReferenceNumber())
                .amount(utilityPaymentRequest.getAmount().negate()).build());

        return UtilityPaymentResponse.builder().message("Utility payment successfully completed")
                .transactionId(transactionId).build();

    }

    private void validateBalance(BankAccount bankAccount, BigDecimal amount) {
        if (bankAccount.getActualBalance().compareTo(BigDecimal.ZERO) < 0 || bankAccount.getActualBalance().compareTo(amount) < 0) {
            throw new RuntimeException();
        }
    }

    public String internalFundTransfer(BankAccount fromBankAccount, BankAccount toBankAccount, BigDecimal amount) {

        String transactionId = UUID.randomUUID().toString();

        BankAccountEntity fromBankAccountEntity = bankAccountRepository.findByNumber(fromBankAccount.getNumber()).get();
        BankAccountEntity toBankAccountEntity = bankAccountRepository.findByNumber(toBankAccount.getNumber()).get();
//  negate the amount to make it a debit and save changes in repository.
        changeBalance(fromBankAccountEntity,amount);
//        fromBankAccountEntity.setActualBalance(fromBankAccountEntity.getActualBalance().subtract(amount));
//        fromBankAccountEntity.setAvailableBalance(fromBankAccountEntity.getActualBalance().subtract(amount));
//        bankAccountRepository.save(fromBankAccountEntity);

        transactionRepository.save(TransactionEntity.builder().transactionType(TransactionType.FUND_TRANSFER)
                .referenceNumber(toBankAccountEntity.getNumber())
                .transactionId(transactionId)
                .account(fromBankAccountEntity).amount(amount.negate()).build());
//  plus the amount to make it a credit and save changes in repository.
        changeBalance(toBankAccountEntity, amount);
//        toBankAccountEntity.setActualBalance(toBankAccountEntity.getActualBalance().add(amount));
//        toBankAccountEntity.setAvailableBalance(toBankAccountEntity.getActualBalance().add(amount));
//        bankAccountRepository.save(toBankAccountEntity);

        transactionRepository.save(TransactionEntity.builder().transactionType(TransactionType.FUND_TRANSFER)
                .referenceNumber(toBankAccountEntity.getNumber())
                .transactionId(transactionId)
                .account(toBankAccountEntity).amount(amount).build());

        return transactionId;
    }

    private void changeBalance(BankAccountEntity bankAccountEntity, BigDecimal amount) {
        bankAccountEntity.setActualBalance(bankAccountEntity.getActualBalance().add(amount));
        bankAccountEntity.setAvailableBalance(bankAccountEntity.getActualBalance().add(amount));
        bankAccountRepository.save(bankAccountEntity);
    }

}
