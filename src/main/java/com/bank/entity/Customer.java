package com.bank.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerNumber;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String phone;

    private LocalDate dateOfBirth;

    private String gender;

    private String aadhaarNumber;

    private String panNumber;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private Boolean kycVerified;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}