package com.bank.dto.card;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CardResponse {

    private String cardNumber;
    private String cardType;
    private String cardHolderName;
    private LocalDate expiryDate;
    private String status;
    private String message;
}