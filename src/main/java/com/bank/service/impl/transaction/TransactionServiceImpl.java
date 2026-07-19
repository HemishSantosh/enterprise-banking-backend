package com.bank.service.impl.transaction;
import com.bank.service.activity.ActivityLogService;
import com.bank.dto.transaction.DepositRequest;
import com.bank.dto.transaction.TransactionResponse;
import com.bank.dto.transaction.TransferRequest;
import com.bank.dto.transaction.WithdrawRequest;
import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.service.transaction.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ActivityLogService activityLogService;
    @Override
    public TransactionResponse deposit(DepositRequest request) {

        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        if (request.getAmount() == null
                || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException("Invalid deposit amount");
        }

        account.setBalance(account.getBalance().add(request.getAmount()));

        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .referenceNumber(generateReference())
                .transactionType("DEPOSIT")
                .amount(request.getAmount())
                .balanceAfterTransaction(account.getBalance())
                .transactionDate(LocalDateTime.now())
                .remarks(request.getRemarks())
                .account(account)
                .build();

        transactionRepository.save(transaction);
        activityLogService.saveActivity(
                account.getCustomer().getEmail(),
                "Deposited ₹" + request.getAmount(),
                "SUCCESS"
        );
        return TransactionResponse.builder()
                .referenceNumber(transaction.getReferenceNumber())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .balanceAfterTransaction(transaction.getBalanceAfterTransaction())
                .transactionDate(transaction.getTransactionDate())
                .remarks(transaction.getRemarks())
                .build();
    }

    @Override
    public TransactionResponse withdraw(WithdrawRequest request) {

        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        if (request.getAmount() == null
                || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException("Invalid withdrawal amount");
        }

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient Balance");
        }

        account.setBalance(account.getBalance().subtract(request.getAmount()));

        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .referenceNumber(generateReference())
                .transactionType("WITHDRAW")
                .amount(request.getAmount())
                .balanceAfterTransaction(account.getBalance())
                .transactionDate(LocalDateTime.now())
                .remarks(request.getRemarks())
                .account(account)
                .build();

        transactionRepository.save(transaction);
        activityLogService.saveActivity(
                account.getCustomer().getEmail(),
                "Withdrawn ₹" + request.getAmount(),
                "SUCCESS"
        );
        return TransactionResponse.builder()
                .referenceNumber(transaction.getReferenceNumber())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .balanceAfterTransaction(transaction.getBalanceAfterTransaction())
                .transactionDate(transaction.getTransactionDate())
                .remarks(transaction.getRemarks())
                .build();
    }

    @Override
    public TransactionResponse transfer(TransferRequest request){
        Account fromAccount = accountRepository.findByAccountNumber(request.getFromAccount())
                .orElseThrow(() ->
                        new RuntimeException("Sender account not found"));

        Account toAccount = accountRepository.findByAccountNumber(request.getToAccount())
                .orElseThrow(() ->
                        new RuntimeException("Receiver account not found"));

        if (request.getAmount() == null ||
                request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid transfer amount");
        }

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient Balance");
        }

        // Debit sender account
        fromAccount.setBalance(
                fromAccount.getBalance().subtract(request.getAmount()));

        // Credit receiver account
        toAccount.setBalance(
                toAccount.getBalance().add(request.getAmount()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Debit transaction
        Transaction debitTransaction = Transaction.builder()
                .referenceNumber(generateReference())
                .transactionType("TRANSFER_DEBIT")
                .amount(request.getAmount())
                .balanceAfterTransaction(fromAccount.getBalance())
                .transactionDate(LocalDateTime.now())
                .remarks(request.getRemarks())
                .account(fromAccount)
                .build();

        // Credit transaction
        Transaction creditTransaction = Transaction.builder()
                .referenceNumber(generateReference())
                .transactionType("TRANSFER_CREDIT")
                .amount(request.getAmount())
                .balanceAfterTransaction(toAccount.getBalance())
                .transactionDate(LocalDateTime.now())
                .remarks(request.getRemarks())
                .account(toAccount)
                .build();

        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);
        activityLogService.saveActivity(
                fromAccount.getCustomer().getEmail(),
                "Transferred ₹" + request.getAmount() +
                        " to " + toAccount.getAccountNumber(),
                "SUCCESS"
        );
        return TransactionResponse.builder()
                .referenceNumber(debitTransaction.getReferenceNumber())
                .transactionType("TRANSFER")
                .fromAccount(fromAccount.getAccountNumber())
                .toAccount(toAccount.getAccountNumber())
                .amount(request.getAmount())
                .balanceAfterTransaction(fromAccount.getBalance())
                .transactionDate(LocalDateTime.now())
                .remarks(request.getRemarks())
                .message("Transfer Successful")
                .build();
    }

    @Override
    public List<TransactionResponse> getTransactionHistory(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        List<Transaction> transactions =
                transactionRepository.findByAccountOrderByTransactionDateDesc(account);

        return transactions.stream()
                .map(transaction -> TransactionResponse.builder()
                        .referenceNumber(transaction.getReferenceNumber())
                        .transactionType(transaction.getTransactionType())
                        .amount(transaction.getAmount())
                        .balanceAfterTransaction(transaction.getBalanceAfterTransaction())
                        .transactionDate(transaction.getTransactionDate())
                        .remarks(transaction.getRemarks())
                        .message("Success")
                        .build())
                .toList();
    }
    @Override
    public TransactionResponse getTransactionByReference(String referenceNumber) {

        Transaction transaction = transactionRepository
                .findByReferenceNumber(referenceNumber)
                .orElseThrow(() ->
                        new RuntimeException("Transaction not found"));

        return TransactionResponse.builder()
                .referenceNumber(transaction.getReferenceNumber())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .balanceAfterTransaction(transaction.getBalanceAfterTransaction())
                .transactionDate(transaction.getTransactionDate())
                .remarks(transaction.getRemarks())
                .message("Success")
                .build();
    }
    private String generateReference() {
        return "TXN-" + UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }
}