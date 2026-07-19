package com.bank.controller.account;

import com.bank.dto.account.AccountRequest;
import com.bank.dto.account.AccountResponse;
import com.bank.service.account.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // Create a new account
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/create")
    public AccountResponse createAccount(
            Authentication authentication,
            @Valid @RequestBody AccountRequest request) {

        return accountService.createAccount(
                authentication.getName(),
                request);
    }

    // Get all accounts of logged-in customer
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my-accounts")
    public List<AccountResponse> getMyAccounts(
            Authentication authentication) {

        return accountService.getCustomerAccounts(
                authentication.getName());
    }

    // Get account details by account number
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{accountNumber}")
    public AccountResponse getAccountByNumber(
            @PathVariable String accountNumber) {

        return accountService.getAccountByNumber(accountNumber);
    }
}