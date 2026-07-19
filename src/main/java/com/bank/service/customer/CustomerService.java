package com.bank.service.customer;

import com.bank.dto.customer.CustomerRequest;
import com.bank.dto.customer.CustomerResponse;

public interface CustomerService {

    CustomerResponse getProfile(String email);

    CustomerResponse updateProfile(String email,
                                   CustomerRequest request);

}