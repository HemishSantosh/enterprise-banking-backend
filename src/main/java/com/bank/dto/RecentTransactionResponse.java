package com.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RecentTransactionResponse {

    private String referenceNumber;

    private String accountNumber;

    private String transactionType;

    private BigDecimal amount;

    private LocalDateTime transactionDate;
}