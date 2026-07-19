package com.bank.service.beneficiary;

import com.bank.dto.beneficiary.BeneficiaryRequest;
import com.bank.dto.beneficiary.BeneficiaryResponse;

import java.util.List;

public interface BeneficiaryService {

    BeneficiaryResponse addBeneficiary(
            String customerEmail,
            BeneficiaryRequest request);

    List<BeneficiaryResponse> getBeneficiaries(
            String customerEmail);

    BeneficiaryResponse updateBeneficiary(
            Long id,
            BeneficiaryRequest request,
            String customerEmail);

    void deleteBeneficiary(
            Long id,
            String customerEmail);

}