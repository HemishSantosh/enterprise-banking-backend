package com.bank.dto.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {

    private Long id;

    private String customerNumber;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private Boolean kycVerified;

}