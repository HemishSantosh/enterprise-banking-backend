package com.bank.dto.profile;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private String customerNumber;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private Boolean kycVerified;
}