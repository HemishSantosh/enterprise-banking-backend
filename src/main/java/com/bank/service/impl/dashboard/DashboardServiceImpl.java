package com.bank.service.impl.dashboard;

import com.bank.dto.dashboard.AnalyticsResponse;
import com.bank.dto.dashboard.DashboardResponse;
import com.bank.entity.Account;
import com.bank.entity.Customer;
import com.bank.entity.Loan;
import com.bank.repository.AccountRepository;
import com.bank.repository.CustomerRepository;
import com.bank.repository.LoanRepository;
import com.bank.repository.TransactionRepository;
import com.bank.service.dashboard.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final LoanRepository loanRepository;

    @Override
    public DashboardResponse getDashboard(String email) {

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        List<Account> accounts =
                accountRepository.findByCustomer(customer);

        BigDecimal totalBalance = accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalAccounts = accounts.size();

        int totalTransactions = accounts.stream()
                .mapToInt(account ->
                        transactionRepository
                                .findByAccountOrderByTransactionDateDesc(account)
                                .size())
                .sum();

        List<Loan> loans =
                loanRepository.findByCustomer(customer);

        int activeLoans = (int) loans.stream()
                .filter(loan ->
                        "APPROVED".equalsIgnoreCase(loan.getStatus()))
                .count();

        String customerName =
                customer.getFirstName() + " " + customer.getLastName();

        return DashboardResponse.builder()
                .customerName(customerName)
                .totalBalance(totalBalance)
                .totalAccounts(totalAccounts)
                .totalTransactions(totalTransactions)
                .activeLoans(activeLoans)
                .build();
    }

    @Override
    public AnalyticsResponse getAnalytics(String email) {

        return AnalyticsResponse.builder()

                .months(Arrays.asList(
                        "Jan",
                        "Feb",
                        "Mar",
                        "Apr",
                        "May",
                        "Jun"
                ))

                .deposits(Arrays.asList(
                        new BigDecimal("15000"),
                        new BigDecimal("18000"),
                        new BigDecimal("22000"),
                        new BigDecimal("27000"),
                        new BigDecimal("25000"),
                        new BigDecimal("30000")
                ))

                .withdrawals(Arrays.asList(
                        new BigDecimal("9000"),
                        new BigDecimal("10000"),
                        new BigDecimal("11000"),
                        new BigDecimal("12000"),
                        new BigDecimal("13000"),
                        new BigDecimal("15000")
                ))

                .transfers(Arrays.asList(
                        new BigDecimal("2000"),
                        new BigDecimal("3000"),
                        new BigDecimal("4000"),
                        new BigDecimal("5000"),
                        new BigDecimal("3500"),
                        new BigDecimal("4500")
                ))

                .build();
    }
}