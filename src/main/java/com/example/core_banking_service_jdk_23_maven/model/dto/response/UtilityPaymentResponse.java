package com.example.core_banking_service_jdk_23_maven.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UtilityPaymentResponse {
    private String message;
    private String transactionId;
}
