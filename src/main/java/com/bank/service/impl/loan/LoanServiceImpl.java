package com.bank.service.impl.loan;

import com.bank.dto.loan.LoanRequest;
import com.bank.dto.loan.LoanResponse;
import com.bank.entity.Customer;
import com.bank.entity.Loan;
import com.bank.repository.CustomerRepository;
import com.bank.repository.LoanRepository;
import com.bank.service.loan.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.bank.service.audit.AuditLogService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final AuditLogService auditLogService;

    @Override
    public LoanResponse applyLoan(
            String customerEmail,
            LoanRequest request) {

        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        double interestRate = getInterestRate(request.getLoanType());

        BigDecimal emi = calculateEMI(
                request.getLoanAmount(),
                interestRate,
                request.getTenureMonths());

        Loan loan = Loan.builder()
                .loanNumber(generateLoanNumber())
                .loanType(request.getLoanType())
                .loanAmount(request.getLoanAmount())
                .interestRate(interestRate)
                .tenureMonths(request.getTenureMonths())
                .emiAmount(emi)
                .status("PENDING")
                .appliedAt(LocalDateTime.now())
                .customer(customer)
                .build();
        loanRepository.save(loan);
        auditLogService.log(
                customerEmail,
                "APPLY_LOAN",
                "SUCCESS",
                "Loan application submitted. Loan Number: " + loan.getLoanNumber()
        );

        return LoanResponse.builder()
                .loanNumber(loan.getLoanNumber())
                .loanType(loan.getLoanType())
                .loanAmount(loan.getLoanAmount())
                .interestRate(loan.getInterestRate())
                .tenureMonths(loan.getTenureMonths())
                .emiAmount(loan.getEmiAmount())
                .status(loan.getStatus())
                .message("Loan Application Submitted Successfully")
                .build();
    }
    @Override
    public List<LoanResponse> getMyLoans(String customerEmail) {

        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        return loanRepository.findByCustomer(customer)
                .stream()
                .map(loan -> LoanResponse.builder()
                        .loanNumber(loan.getLoanNumber())
                        .loanType(loan.getLoanType())
                        .loanAmount(loan.getLoanAmount())
                        .interestRate(loan.getInterestRate())
                        .tenureMonths(loan.getTenureMonths())
                        .emiAmount(loan.getEmiAmount())
                        .status(loan.getStatus())
                        .message("Success")
                        .build())
                .toList();
    }

    @Override
    public LoanResponse getLoanByNumber(String loanNumber) {

        Loan loan = loanRepository.findByLoanNumber(loanNumber)
                .orElseThrow(() ->
                        new RuntimeException("Loan not found"));

        return LoanResponse.builder()
                .loanNumber(loan.getLoanNumber())
                .loanType(loan.getLoanType())
                .loanAmount(loan.getLoanAmount())
                .interestRate(loan.getInterestRate())
                .tenureMonths(loan.getTenureMonths())
                .emiAmount(loan.getEmiAmount())
                .status(loan.getStatus())
                .message("Success")
                .build();
    }

    @Override
    public LoanResponse approveLoan(Long id) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Loan not found"));

        loan.setStatus("APPROVED");
        loan.setApprovedAt(LocalDateTime.now());

        loanRepository.save(loan);

        auditLogService.log(
                "ADMIN",
                "APPROVE_LOAN",
                "SUCCESS",
                "Loan " + loan.getLoanNumber() + " approved."
        );

        return LoanResponse.builder()
                .loanNumber(loan.getLoanNumber())
                .loanType(loan.getLoanType())
                .loanAmount(loan.getLoanAmount())
                .interestRate(loan.getInterestRate())
                .tenureMonths(loan.getTenureMonths())
                .emiAmount(loan.getEmiAmount())
                .status(loan.getStatus())
                .message("Loan Approved Successfully")
                .build();
    }
    @Override
    public LoanResponse rejectLoan(Long id) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Loan not found"));

        loan.setStatus("REJECTED");

        loanRepository.save(loan);

        auditLogService.log(
                "ADMIN",
                "REJECT_LOAN",
                "SUCCESS",
                "Loan " + loan.getLoanNumber() + " rejected."
        );

        return LoanResponse.builder()
                .loanNumber(loan.getLoanNumber())
                .loanType(loan.getLoanType())
                .loanAmount(loan.getLoanAmount())
                .interestRate(loan.getInterestRate())
                .tenureMonths(loan.getTenureMonths())
                .emiAmount(loan.getEmiAmount())
                .status(loan.getStatus())
                .message("Loan Rejected Successfully")
                .build();
    }

    private String generateLoanNumber() {
        return "LOAN-" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 10)
                .toUpperCase();
    }

    private double getInterestRate(String loanType) {

        return switch (loanType.toUpperCase()) {
            case "HOME" -> 8.5;
            case "CAR" -> 9.0;
            case "EDUCATION" -> 7.5;
            case "PERSONAL" -> 12.5;
            case "BUSINESS" -> 11.0;
            default -> 10.0;
        };
    }

    private BigDecimal calculateEMI(
            BigDecimal principal,
            double annualRate,
            int months) {

        double monthlyRate = annualRate / (12 * 100);

        double emi = (principal.doubleValue() * monthlyRate *
                Math.pow(1 + monthlyRate, months))
                / (Math.pow(1 + monthlyRate, months) - 1);

        return BigDecimal.valueOf(emi)
                .setScale(2, RoundingMode.HALF_UP);
    }
}