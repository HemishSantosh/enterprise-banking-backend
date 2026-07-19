package com.bank.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String cardNumber;

    private String cardType;      // DEBIT or CREDIT

    private String cardHolderName;

    private LocalDate expiryDate;

    private String cvv;

    private String status;        // ACTIVE, BLOCKED

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}