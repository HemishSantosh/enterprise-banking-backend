package com.bank.dto.profile;

import lombok.Data;

@Data
public class UpdateProfileRequest {

    private String phone;
    private String address;
    private String city;
    private String state;
    private String pincode;
}