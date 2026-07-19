package com.bank.service;
import com.bank.dto.RecentTransactionResponse;
import java.util.List;
import com.bank.dto.AdminDashboardResponse;

public interface AdminService {

    AdminDashboardResponse getDashboard();
    List<RecentTransactionResponse> getRecentTransactions();
}