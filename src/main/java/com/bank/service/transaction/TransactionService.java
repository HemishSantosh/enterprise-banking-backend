package com.bank.service.transaction;

import com.bank.dto.transaction.DepositRequest;
import com.bank.dto.transaction.TransactionResponse;
import com.bank.dto.transaction.TransferRequest;
import com.bank.dto.transaction.WithdrawRequest;

import java.util.List;

public interface TransactionService {

    TransactionResponse deposit(DepositRequest request);

    TransactionResponse withdraw(WithdrawRequest request);

    TransactionResponse transfer(TransferRequest request);

    List<TransactionResponse> getTransactionHistory(String accountNumber);
    TransactionResponse getTransactionByReference(String referenceNumber);
}