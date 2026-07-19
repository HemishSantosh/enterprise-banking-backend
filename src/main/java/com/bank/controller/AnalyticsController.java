package com.bank.controller;

import com.bank.dto.AnalyticsSummaryResponse;
import com.bank.dto.CategoryExpenseResponse;
import com.bank.dto.MonthlyDataResponse;
import com.bank.dto.MonthlyTrendResponse;
import com.bank.dto.analytics.LatestTransactionResponse;
import com.bank.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/summary")
    public AnalyticsSummaryResponse getSummary() {
        return analyticsService.getSummary();
    }

    @GetMapping("/monthly-income-expense")
    public List<MonthlyDataResponse> getMonthlyIncomeExpense() {
        return analyticsService.getMonthlyIncomeExpense();
    }

    @GetMapping("/category-expenses")
    public List<CategoryExpenseResponse> getCategoryExpenses() {
        return analyticsService.getCategoryExpenses();
    }

    @GetMapping("/recent-transactions")
    public ResponseEntity<List<LatestTransactionResponse>> getRecentTransactions() {
        return ResponseEntity.ok(
                analyticsService.getRecentTransactions()
        );
    }

    @GetMapping("/monthly-trend")
    public ResponseEntity<List<MonthlyTrendResponse>> getMonthlyTrend() {
        return ResponseEntity.ok(
                analyticsService.getMonthlyTrend()
        );
    }
}