package com.bank.service.impl.customer;

import com.bank.dto.customer.CustomerRequest;
import com.bank.dto.customer.CustomerResponse;
import com.bank.entity.Customer;
import com.bank.repository.CustomerRepository;
import com.bank.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse getProfile(String email) {

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        return CustomerResponse.builder()
                .id(customer.getId())
                .customerNumber(customer.getCustomerNumber())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .kycVerified(customer.getKycVerified())
                .build();
    }

    @Override
    public CustomerResponse updateProfile(
            String email,
            CustomerRequest request) {

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setPhone(request.getPhone());
        customer.setGender(request.getGender());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setAadhaarNumber(request.getAadhaarNumber());
        customer.setPanNumber(request.getPanNumber());
        customer.setAddress(request.getAddress());
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setPincode(request.getPincode());

        customerRepository.save(customer);

        return CustomerResponse.builder()
                .id(customer.getId())
                .customerNumber(customer.getCustomerNumber())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .kycVerified(customer.getKycVerified())
                .build();
    }
}