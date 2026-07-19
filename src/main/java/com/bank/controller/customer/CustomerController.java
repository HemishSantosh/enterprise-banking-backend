package com.bank.controller.customer;

import com.bank.dto.customer.CustomerRequest;
import com.bank.dto.customer.CustomerResponse;
import com.bank.service.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // Get logged-in customer profile
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/profile")
    public CustomerResponse getProfile(Authentication authentication) {

        return customerService.getProfile(authentication.getName());
    }

    // Update logged-in customer profile
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/profile")
    public CustomerResponse updateProfile(
            Authentication authentication,
            @Valid @RequestBody CustomerRequest request) {

        return customerService.updateProfile(
                authentication.getName(),
                request);
    }
}