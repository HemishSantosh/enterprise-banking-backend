package com.bank.service.impl;

import com.bank.dto.AdminDashboardResponse;
import com.bank.repository.AccountRepository;
import com.bank.repository.CustomerRepository;
import com.bank.repository.TransactionRepository;
import com.bank.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.bank.dto.RecentTransactionResponse;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public AdminDashboardResponse getDashboard() {

        long totalCustomers = customerRepository.count();

        long totalAccounts = accountRepository.count();

        Double totalBalance = accountRepository.getTotalBalance();

        LocalDate today = LocalDate.now();

        LocalDateTime start = today.atStartOfDay();

        LocalDateTime end = today.atTime(LocalTime.MAX);

        long todayTransactions =
                transactionRepository.countByTransactionDateBetween(start, end);

        return new AdminDashboardResponse(
                totalCustomers,
                totalAccounts,
                todayTransactions,
                totalBalance
        );
    }
    @Override
    public List<RecentTransactionResponse> getRecentTransactions() {

        return transactionRepository
                .findTop5ByOrderByTransactionDateDesc()
                .stream()
                .map(transaction -> new RecentTransactionResponse(
                        transaction.getReferenceNumber(),
                        transaction.getAccount().getAccountNumber(),
                        transaction.getTransactionType(),
                        transaction.getAmount(),
                        transaction.getTransactionDate()
                ))
                .toList();
    }
}