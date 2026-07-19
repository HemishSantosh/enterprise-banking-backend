package com.bank.service.impl;

import com.bank.dto.AnalyticsSummaryResponse;
import com.bank.dto.CategoryExpenseResponse;
import com.bank.dto.MonthlyDataResponse;
import com.bank.entity.Account;
import com.bank.entity.Customer;
import com.bank.entity.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.CustomerRepository;
import com.bank.repository.TransactionRepository;
import com.bank.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.bank.dto.analytics.LatestTransactionResponse;
import java.util.Comparator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.bank.dto.MonthlyTrendResponse;
@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public AnalyticsSummaryResponse getSummary() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<Account> accounts = accountRepository.findByCustomer(customer);

        BigDecimal totalBalance = BigDecimal.ZERO;
        BigDecimal monthlyIncome = BigDecimal.ZERO;
        BigDecimal monthlyExpense = BigDecimal.ZERO;

        long totalTransactions = 0;

        LocalDate now = LocalDate.now();

        for (Account account : accounts) {

            totalBalance = totalBalance.add(account.getBalance());

            List<Transaction> transactions =
                    transactionRepository.findByAccountOrderByTransactionDateDesc(account);

            totalTransactions += transactions.size();

            for (Transaction transaction : transactions) {

                if (transaction.getTransactionDate().getMonthValue() != now.getMonthValue()
                        || transaction.getTransactionDate().getYear() != now.getYear()) {
                    continue;
                }

                String type = transaction.getTransactionType();

                if ("DEPOSIT".equalsIgnoreCase(type)
                        || "TRANSFER_CREDIT".equalsIgnoreCase(type)) {

                    monthlyIncome = monthlyIncome.add(transaction.getAmount());

                } else if ("WITHDRAW".equalsIgnoreCase(type)
                        || "TRANSFER_DEBIT".equalsIgnoreCase(type)) {

                    monthlyExpense = monthlyExpense.add(transaction.getAmount());
                }
            }
        }

        BigDecimal savings = monthlyIncome.subtract(monthlyExpense);

        return AnalyticsSummaryResponse.builder()
                .totalBalance(totalBalance.doubleValue())
                .monthlyIncome(monthlyIncome.doubleValue())
                .monthlyExpense(monthlyExpense.doubleValue())
                .savings(savings.doubleValue())
                .totalTransactions(totalTransactions)
                .build();
    }

    @Override
    public List<MonthlyDataResponse> getMonthlyIncomeExpense() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<Account> accounts = accountRepository.findByCustomer(customer);

        List<MonthlyDataResponse> response = new ArrayList<>();

        for (int i = 11; i >= 0; i--) {

            LocalDate month = LocalDate.now().minusMonths(i);

            BigDecimal income = BigDecimal.ZERO;
            BigDecimal expense = BigDecimal.ZERO;

            for (Account account : accounts) {

                List<Transaction> transactions =
                        transactionRepository.findByAccountOrderByTransactionDateDesc(account);

                for (Transaction transaction : transactions) {

                    if (transaction.getTransactionDate().getYear() != month.getYear()
                            || transaction.getTransactionDate().getMonthValue() != month.getMonthValue()) {
                        continue;
                    }

                    String type = transaction.getTransactionType();

                    if ("DEPOSIT".equalsIgnoreCase(type)
                            || "TRANSFER_CREDIT".equalsIgnoreCase(type)) {

                        income = income.add(transaction.getAmount());

                    } else if ("WITHDRAW".equalsIgnoreCase(type)
                            || "TRANSFER_DEBIT".equalsIgnoreCase(type)) {

                        expense = expense.add(transaction.getAmount());
                    }
                }
            }

            response.add(
                    MonthlyDataResponse.builder()
                            .month(month.getMonth().name().substring(0, 3))
                            .income(income.doubleValue())
                            .expense(expense.doubleValue())
                            .build()
            );
        }

        return response;
    }

    @Override
    public List<CategoryExpenseResponse> getCategoryExpenses() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<Account> accounts = accountRepository.findByCustomer(customer);

        BigDecimal withdraw = BigDecimal.ZERO;
        BigDecimal transferDebit = BigDecimal.ZERO;

        for (Account account : accounts) {

            List<Transaction> transactions =
                    transactionRepository.findByAccountOrderByTransactionDateDesc(account);

            for (Transaction transaction : transactions) {

                String type = transaction.getTransactionType();

                if ("WITHDRAW".equalsIgnoreCase(type)) {
                    withdraw = withdraw.add(transaction.getAmount());
                }

                if ("TRANSFER_DEBIT".equalsIgnoreCase(type)) {
                    transferDebit = transferDebit.add(transaction.getAmount());
                }
            }
        }

        List<CategoryExpenseResponse> list = new ArrayList<>();

        list.add(CategoryExpenseResponse.builder()
                .category("Withdraw")
                .amount(withdraw.doubleValue())
                .build());

        list.add(CategoryExpenseResponse.builder()
                .category("Transfer")
                .amount(transferDebit.doubleValue())
                .build());

        return list;
    }
    @Override
    public List<LatestTransactionResponse> getRecentTransactions() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<Account> accounts = accountRepository.findByCustomer(customer);

        List<LatestTransactionResponse> response = new ArrayList<>();

        for (Account account : accounts) {

            List<Transaction> transactions =
                    transactionRepository.findByAccountOrderByTransactionDateDesc(account);

            for (Transaction transaction : transactions) {

                response.add(
                        new LatestTransactionResponse(
                                transaction.getId(),
                                transaction.getTransactionType(),
                                transaction.getAmount().doubleValue(),
                                transaction.getTransactionDate()
                        )
                );
            }
        }

        response.sort(
                Comparator.comparing(
                        LatestTransactionResponse::getTransactionDate
                ).reversed()
        );

        return response.stream()
                .limit(5)
                .toList();
    }
    @Override
    public List<MonthlyTrendResponse> getMonthlyTrend() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<Account> accounts = accountRepository.findByCustomer(customer);

        List<MonthlyTrendResponse> result = new ArrayList<>();

        for (int i = 5; i >= 0; i--) {

            LocalDate monthDate = LocalDate.now().minusMonths(i);

            BigDecimal income = BigDecimal.ZERO;
            BigDecimal expense = BigDecimal.ZERO;

            for (Account account : accounts) {

                List<Transaction> transactions =
                        transactionRepository.findByAccountOrderByTransactionDateDesc(account);

                for (Transaction tx : transactions) {

                    if (tx.getTransactionDate().getYear() != monthDate.getYear()
                            || tx.getTransactionDate().getMonthValue() != monthDate.getMonthValue()) {
                        continue;
                    }

                    String type = tx.getTransactionType();

                    if ("DEPOSIT".equals(type)
                            || "TRANSFER_CREDIT".equals(type)) {

                        income = income.add(tx.getAmount());

                    } else if ("WITHDRAW".equals(type)
                            || "TRANSFER_DEBIT".equals(type)) {

                        expense = expense.add(tx.getAmount());
                    }
                }
            }

            result.add(
                    MonthlyTrendResponse.builder()
                            .month(monthDate.getMonth().name().substring(0, 3))
                            .income(income.doubleValue())
                            .expense(expense.doubleValue())
                            .build()
            );
        }

        return result;
    }
}