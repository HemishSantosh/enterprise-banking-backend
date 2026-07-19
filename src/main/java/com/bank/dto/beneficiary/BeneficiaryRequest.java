package com.bank.dto.beneficiary;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BeneficiaryRequest {

    @NotBlank(message = "Beneficiary name is required")
    @Size(min = 3, max = 100,
            message = "Beneficiary name must be between 3 and 100 characters")
    private String beneficiaryName;

    @NotBlank(message = "Account number is required")
    @Pattern(
            regexp = "^\\d{10,18}$",
            message = "Account number must contain 10 to 18 digits"
    )
    private String accountNumber;

    @NotBlank(message = "IFSC code is required")
    @Pattern(
            regexp = "^[A-Z]{4}0[A-Z0-9]{6}$",
            message = "Invalid IFSC code"
    )
    private String ifscCode;

    @NotBlank(message = "Bank name is required")
    @Size(max = 100,
            message = "Bank name cannot exceed 100 characters")
    private String bankName;

    @Size(max = 50,
            message = "Nickname cannot exceed 50 characters")
    private String nickname;

}