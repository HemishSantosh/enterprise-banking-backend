package com.bank.dto.transaction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {

    @NotBlank(message = "From account number is required")
    private String fromAccount;

    @NotBlank(message = "To account number is required")
    private String toAccount;

    @NotNull(message = "Transfer amount is required")
    @DecimalMin(value = "0.01", message = "Transfer amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Remarks are required")
    @Size(max = 200, message = "Remarks cannot exceed 200 characters")
    private String remarks;

}