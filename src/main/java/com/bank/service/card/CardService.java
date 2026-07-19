package com.bank.service.card;

import com.bank.dto.card.CardRequest;
import com.bank.dto.card.CardResponse;

import java.util.List;

public interface CardService {

    CardResponse requestCard(String customerEmail, CardRequest request);

    List<CardResponse> getMyCards(String customerEmail);

    CardResponse blockCard(String cardNumber);

    CardResponse unblockCard(String cardNumber);
}