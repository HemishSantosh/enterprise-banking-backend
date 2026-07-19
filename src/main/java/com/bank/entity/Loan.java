package com.bank.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String loanNumber;

    @Column(nullable = false)
    private String loanType;

    @Column(nullable = false)
    private BigDecimal loanAmount;

    @Column(nullable = false)
    private Double interestRate;

    @Column(nullable = false)
    private Integer tenureMonths;

    @Column(nullable = false)
    private BigDecimal emiAmount;

    @Column(nullable = false)
    private String status;

    private LocalDateTime appliedAt;

    private LocalDateTime approvedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}