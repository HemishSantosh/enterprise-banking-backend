package com.bank.dto.account;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequest {

    private String accountType;

    private BigDecimal initialDeposit;
}