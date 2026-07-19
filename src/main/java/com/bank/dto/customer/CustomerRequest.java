package com.bank.dto.customer;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerRequest {

    private String firstName;
    private String lastName;
    private String phone;
    private String gender;
    private LocalDate dateOfBirth;
    private String aadhaarNumber;
    private String panNumber;
    private String address;
    private String city;
    private String state;
    private String pincode;

}