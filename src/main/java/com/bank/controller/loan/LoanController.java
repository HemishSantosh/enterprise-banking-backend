package com.bank.controller.loan;

import com.bank.dto.loan.LoanRequest;
import com.bank.dto.loan.LoanResponse;
import com.bank.service.loan.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    // Customer applies for loan
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/apply")
    public LoanResponse applyLoan(
            Authentication authentication,
            @Valid @RequestBody LoanRequest request) {

        return loanService.applyLoan(
                authentication.getName(),
                request);
    }

    // Customer views own loans
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my-loans")
    public List<LoanResponse> getMyLoans(
            Authentication authentication) {

        return loanService.getMyLoans(
                authentication.getName());
    }

    // Customer views loan by loan number
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{loanNumber}")
    public LoanResponse getLoan(
            @PathVariable String loanNumber) {

        return loanService.getLoanByNumber(loanNumber);
    }

    // Admin approves loan
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/{id}/approve")
    public LoanResponse approveLoan(
            @PathVariable Long id) {

        return loanService.approveLoan(id);
    }

    // Admin rejects loan
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/{id}/reject")
    public LoanResponse rejectLoan(
            @PathVariable Long id) {

        return loanService.rejectLoan(id);
    }
}