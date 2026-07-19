package com.bank.controller;

import com.bank.dto.AdminDashboardResponse;
import com.bank.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.bank.dto.RecentTransactionResponse;
import java.util.List;
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminDashboardResponse getDashboard() {
        return adminService.getDashboard();
    }
    @GetMapping("/recent-transactions")
    @PreAuthorize("hasRole('ADMIN')")
    public List<RecentTransactionResponse> getRecentTransactions() {
        return adminService.getRecentTransactions();
    }
}