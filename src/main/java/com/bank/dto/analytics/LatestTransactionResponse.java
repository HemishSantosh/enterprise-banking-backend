package com.bank.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LatestTransactionResponse {

    private Long id;
    private String type;
    private Double amount;
    private LocalDateTime transactionDate;
}
