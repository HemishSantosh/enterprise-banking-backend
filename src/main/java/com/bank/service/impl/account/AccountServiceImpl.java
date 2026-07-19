package com.bank.service.impl.account;

import com.bank.dto.account.AccountRequest;
import com.bank.dto.account.AccountResponse;
import com.bank.entity.Account;
import com.bank.entity.Customer;
import com.bank.repository.AccountRepository;
import com.bank.repository.CustomerRepository;
import com.bank.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    public AccountResponse createAccount(String email,
                                         AccountRequest request) {
        System.out.println("========== CREATE ACCOUNT ==========");
        System.out.println("Email: " + email);
        System.out.println("Account Type: " + request.getAccountType());
        System.out.println("Initial Deposit: " + request.getInitialDeposit());
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        if (request.getInitialDeposit() == null
                || request.getInitialDeposit().compareTo(BigDecimal.ZERO) < 0) {

            throw new RuntimeException("Invalid Initial Deposit");
        }

        String accountNumber =
                String.valueOf(100000000000L + accountRepository.count() + 1);

        Account account = Account.builder()
                .accountNumber(accountNumber)
                .accountType(request.getAccountType().toUpperCase())
                .balance(request.getInitialDeposit())
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .customer(customer)
                .build();

        Account savedAccount = accountRepository.save(account);

        return AccountResponse.builder()
                .id(savedAccount.getId())
                .accountNumber(savedAccount.getAccountNumber())
                .accountType(savedAccount.getAccountType())
                .balance(savedAccount.getBalance())
                .status(savedAccount.getStatus())
                .customerName(
                        customer.getFirstName() + " " + customer.getLastName())
                .build();
    }

    @Override
    public List<AccountResponse> getCustomerAccounts(String email) {

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        return accountRepository.findByCustomer(customer)
                .stream()
                .map(account -> AccountResponse.builder()
                        .id(account.getId())
                        .accountNumber(account.getAccountNumber())
                        .accountType(account.getAccountType())
                        .balance(account.getBalance())
                        .status(account.getStatus())
                        .customerName(customer.getFirstName() + " " + customer.getLastName())
                        .build())
                .toList();
    }

    @Override
    public AccountResponse getAccountByNumber(String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .status(account.getStatus())
                .customerName(
                        account.getCustomer().getFirstName() + " "
                                + account.getCustomer().getLastName())
                .build();
    }
}