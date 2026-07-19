package com.bank.dto.transaction;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {

    private String referenceNumber;

    private String transactionType;

    private String fromAccount;

    private String toAccount;

    private BigDecimal amount;

    private BigDecimal balanceAfterTransaction;

    private LocalDateTime transactionDate;

    private String remarks;

    private String message;

}