package com.example.core_banking_service_jdk_23_maven.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@Entity
@Table(name = "banking_core_transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private String referenceNumber;
    private String transactionId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="account_id", referencedColumnName ="id")
    private BankAccountEntity account;
}
