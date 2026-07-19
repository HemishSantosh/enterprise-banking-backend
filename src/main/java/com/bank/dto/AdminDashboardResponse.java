package com.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardResponse {

    private long totalCustomers;

    private long totalAccounts;

    private long todayTransactions;

    private Double totalBalance;

}