package com.bank.controller.beneficiary;

import com.bank.dto.beneficiary.BeneficiaryRequest;
import com.bank.dto.beneficiary.BeneficiaryResponse;
import com.bank.service.beneficiary.BeneficiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beneficiaries")
@RequiredArgsConstructor
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;

    // Add Beneficiary
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public BeneficiaryResponse addBeneficiary(
            Authentication authentication,
            @Valid @RequestBody BeneficiaryRequest request) {

        return beneficiaryService.addBeneficiary(
                authentication.getName(),
                request);
    }

    // View All Beneficiaries
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public List<BeneficiaryResponse> getBeneficiaries(
            Authentication authentication) {

        return beneficiaryService.getBeneficiaries(
                authentication.getName());
    }

    // Update Beneficiary
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/{id}")
    public BeneficiaryResponse updateBeneficiary(
            @PathVariable Long id,
            Authentication authentication,
            @Valid @RequestBody BeneficiaryRequest request) {

        return beneficiaryService.updateBeneficiary(
                id,
                request,
                authentication.getName());
    }

    // Delete Beneficiary
    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/{id}")
    public String deleteBeneficiary(
            @PathVariable Long id,
            Authentication authentication) {

        beneficiaryService.deleteBeneficiary(
                id,
                authentication.getName());

        return "Beneficiary Deleted Successfully";
    }
}