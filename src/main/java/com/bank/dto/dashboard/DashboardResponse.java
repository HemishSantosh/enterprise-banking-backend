package com.bank.dto.dashboard;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DashboardResponse {

    private BigDecimal totalBalance;

    private int totalAccounts;

    private int totalTransactions;

    private int activeLoans;

    private String customerName;
}