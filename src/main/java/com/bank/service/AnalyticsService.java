package com.bank.service;

import com.bank.dto.AnalyticsSummaryResponse;
import com.bank.dto.CategoryExpenseResponse;
import com.bank.dto.MonthlyDataResponse;
import com.bank.dto.MonthlyTrendResponse;
import com.bank.dto.analytics.LatestTransactionResponse;

import java.util.List;

public interface AnalyticsService {

    AnalyticsSummaryResponse getSummary();

    List<MonthlyDataResponse> getMonthlyIncomeExpense();

    List<CategoryExpenseResponse> getCategoryExpenses();

    List<LatestTransactionResponse> getRecentTransactions();

    List<MonthlyTrendResponse> getMonthlyTrend();
    
}