package com.bank.service.loan;

import com.bank.dto.loan.LoanRequest;
import com.bank.dto.loan.LoanResponse;

import java.util.List;

public interface LoanService {

    LoanResponse applyLoan(
            String customerEmail,
            LoanRequest request);

    List<LoanResponse> getMyLoans(
            String customerEmail);

    LoanResponse getLoanByNumber(
            String loanNumber);

    LoanResponse approveLoan(
            Long id);

    LoanResponse rejectLoan(
            Long id);
}