package com.bank.dto.beneficiary;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BeneficiaryResponse {

    private Long id;

    private String beneficiaryName;

    private String accountNumber;

    private String ifscCode;

    private String bankName;

    private String nickname;

    private Boolean verified;

    private String message;
}