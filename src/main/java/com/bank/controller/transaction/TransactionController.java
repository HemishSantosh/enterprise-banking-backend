package com.bank.controller.transaction;

import com.bank.dto.transaction.DepositRequest;
import com.bank.dto.transaction.TransactionResponse;
import com.bank.dto.transaction.TransferRequest;
import com.bank.dto.transaction.WithdrawRequest;
import com.bank.service.transaction.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // Deposit Money
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/deposit")
    public TransactionResponse deposit(
            @Valid @RequestBody DepositRequest request) {

        return transactionService.deposit(request);
    }

    // Withdraw Money
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/withdraw")
    public TransactionResponse withdraw(
            @Valid @RequestBody WithdrawRequest request) {

        return transactionService.withdraw(request);
    }

    // Transfer Money
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/transfer")
    public TransactionResponse transfer(
            @Valid @RequestBody TransferRequest request) {

        return transactionService.transfer(request);
    }

    // Transaction History
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/history/{accountNumber}")
    public List<TransactionResponse> getTransactionHistory(
            @PathVariable String accountNumber) {

        return transactionService.getTransactionHistory(accountNumber);
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{referenceNumber}")
    public TransactionResponse getTransactionByReference(
            @PathVariable String referenceNumber) {

        return transactionService.getTransactionByReference(referenceNumber);
    }
}