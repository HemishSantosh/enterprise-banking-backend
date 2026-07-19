package com.bank.service.account;

import com.bank.dto.account.AccountRequest;
import com.bank.dto.account.AccountResponse;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(String email, AccountRequest request);

    List<AccountResponse> getCustomerAccounts(String email);

    AccountResponse getAccountByNumber(String accountNumber);

}