package com.bank.service.impl.beneficiary;

import com.bank.dto.beneficiary.BeneficiaryRequest;
import com.bank.dto.beneficiary.BeneficiaryResponse;
import com.bank.entity.Beneficiary;
import com.bank.entity.Customer;
import com.bank.repository.BeneficiaryRepository;
import com.bank.repository.CustomerRepository;
import com.bank.service.beneficiary.BeneficiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BeneficiaryServiceImpl implements BeneficiaryService {

    private final BeneficiaryRepository beneficiaryRepository;
    private final CustomerRepository customerRepository;

    @Override
    public BeneficiaryResponse addBeneficiary(
            String customerEmail,
            BeneficiaryRequest request) {

        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        if (beneficiaryRepository.existsByAccountNumber(request.getAccountNumber())) {
            throw new RuntimeException("Beneficiary already exists.");
        }

        Beneficiary beneficiary = Beneficiary.builder()
                .beneficiaryName(request.getBeneficiaryName())
                .accountNumber(request.getAccountNumber())
                .ifscCode(request.getIfscCode())
                .bankName(request.getBankName())
                .nickname(request.getNickname())
                .verified(false)
                .createdAt(LocalDateTime.now())
                .customer(customer)
                .build();

        beneficiaryRepository.save(beneficiary);

        return BeneficiaryResponse.builder()
                .id(beneficiary.getId())
                .beneficiaryName(beneficiary.getBeneficiaryName())
                .accountNumber(beneficiary.getAccountNumber())
                .ifscCode(beneficiary.getIfscCode())
                .bankName(beneficiary.getBankName())
                .nickname(beneficiary.getNickname())
                .verified(beneficiary.getVerified())
                .message("Beneficiary Added Successfully")
                .build();
    }

    @Override
    public List<BeneficiaryResponse> getBeneficiaries(String customerEmail) {

        System.out.println("====================================");
        System.out.println("Logged in email: " + customerEmail);

        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        System.out.println("Customer ID: " + customer.getId());

        List<Beneficiary> beneficiaries =
                beneficiaryRepository.findByCustomer(customer);

        System.out.println("Beneficiary Count: " + beneficiaries.size());

        beneficiaries.forEach(b ->
                System.out.println(
                        "Beneficiary ID: " + b.getId() +
                                ", Name: " + b.getBeneficiaryName() +
                                ", Customer ID: " + b.getCustomer().getId()));

        System.out.println("====================================");

        return beneficiaries.stream()
                .map(beneficiary -> BeneficiaryResponse.builder()
                        .id(beneficiary.getId())
                        .beneficiaryName(beneficiary.getBeneficiaryName())
                        .accountNumber(beneficiary.getAccountNumber())
                        .ifscCode(beneficiary.getIfscCode())
                        .bankName(beneficiary.getBankName())
                        .nickname(beneficiary.getNickname())
                        .verified(beneficiary.getVerified())
                        .message("Success")
                        .build())
                .toList();
    }
    @Override
    public BeneficiaryResponse updateBeneficiary(
            Long id,
            BeneficiaryRequest request,
            String customerEmail) {

        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Beneficiary beneficiary = beneficiaryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Beneficiary not found"));

        if (!beneficiary.getCustomer().getId().equals(customer.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        beneficiary.setBeneficiaryName(request.getBeneficiaryName());
        beneficiary.setIfscCode(request.getIfscCode());
        beneficiary.setBankName(request.getBankName());
        beneficiary.setNickname(request.getNickname());

        beneficiaryRepository.save(beneficiary);

        return BeneficiaryResponse.builder()
                .id(beneficiary.getId())
                .beneficiaryName(beneficiary.getBeneficiaryName())
                .accountNumber(beneficiary.getAccountNumber())
                .ifscCode(beneficiary.getIfscCode())
                .bankName(beneficiary.getBankName())
                .nickname(beneficiary.getNickname())
                .verified(beneficiary.getVerified())
                .message("Beneficiary Updated Successfully")
                .build();
    }

    @Override
    public void deleteBeneficiary(
            Long id,
            String customerEmail) {

        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Beneficiary beneficiary = beneficiaryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Beneficiary not found"));

        if (!beneficiary.getCustomer().getId().equals(customer.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        beneficiaryRepository.delete(beneficiary);
    }
}