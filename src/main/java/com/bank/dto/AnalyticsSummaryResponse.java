package com.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsSummaryResponse {

    private double totalBalance;

    private double monthlyIncome;

    private double monthlyExpense;

    private double savings;

    private long totalTransactions;

}