package com.bank.controller.card;

import com.bank.dto.card.CardRequest;
import com.bank.dto.card.CardResponse;
import com.bank.service.card.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    // Customer requests a new card
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/request")
    public CardResponse requestCard(
            Authentication authentication,
            @RequestBody CardRequest request) {

        return cardService.requestCard(
                authentication.getName(),
                request);
    }

    // Customer views all their cards
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my-cards")
    public List<CardResponse> getMyCards(
            Authentication authentication) {

        return cardService.getMyCards(authentication.getName());
    }

    // Customer blocks a card
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/{cardNumber}/block")
    public CardResponse blockCard(
            @PathVariable String cardNumber) {

        return cardService.blockCard(cardNumber);
    }

    // Customer unblocks a card
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/{cardNumber}/unblock")
    public CardResponse unblockCard(
            @PathVariable String cardNumber) {

        return cardService.unblockCard(cardNumber);
    }
}