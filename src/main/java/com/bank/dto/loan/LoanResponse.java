package com.bank.dto.loan;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LoanResponse {

    private String loanNumber;

    private String loanType;

    private BigDecimal loanAmount;

    private Double interestRate;

    private Integer tenureMonths;

    private BigDecimal emiAmount;

    private String status;

    private String message;
}