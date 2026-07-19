package com.bank.dto.loan;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanRequest {

    @NotBlank(message = "Loan type is required")
    private String loanType;

    @NotNull(message = "Loan amount is required")
    @DecimalMin(value = "10000.00", message = "Minimum loan amount is ₹10,000")
    private BigDecimal loanAmount;

    @NotNull(message = "Loan tenure is required")
    @Min(value = 6, message = "Minimum tenure is 6 months")
    @Max(value = 360, message = "Maximum tenure is 360 months")
    private Integer tenureMonths;

}