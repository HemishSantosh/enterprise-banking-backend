package com.bank.service.impl.card;

import com.bank.dto.card.CardRequest;
import com.bank.dto.card.CardResponse;
import com.bank.entity.Card;
import com.bank.entity.Customer;
import com.bank.repository.CardRepository;
import com.bank.repository.CustomerRepository;
import com.bank.service.card.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;

    @Override
    public CardResponse requestCard(String customerEmail, CardRequest request) {

        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Card card = Card.builder()
                .cardNumber(generateCardNumber())
                .cardType(request.getCardType())
                .cardHolderName(customer.getFirstName() + " " + customer.getLastName())
                .expiryDate(LocalDate.now().plusYears(5))
                .cvv(generateCVV())
                .status("ACTIVE")
                .customer(customer)
                .build();

        cardRepository.save(card);

        return map(card, "Card Created Successfully");
    }

    @Override
    public List<CardResponse> getMyCards(String customerEmail) {

        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return cardRepository.findByCustomer(customer)
                .stream()
                .map(card -> map(card, "Success"))
                .toList();
    }

    @Override
    public CardResponse blockCard(String cardNumber) {

        Card card = cardRepository.findByCardNumber(cardNumber);

        if (card == null) {
            throw new RuntimeException("Card not found");
        }

        card.setStatus("BLOCKED");
        cardRepository.save(card);

        return map(card, "Card Blocked Successfully");
    }

    @Override
    public CardResponse unblockCard(String cardNumber) {

        Card card = cardRepository.findByCardNumber(cardNumber);

        if (card == null) {
            throw new RuntimeException("Card not found");
        }

        card.setStatus("ACTIVE");
        cardRepository.save(card);

        return map(card, "Card Activated Successfully");
    }

    private CardResponse map(Card card, String message) {
        return CardResponse.builder()
                .cardNumber(card.getCardNumber())
                .cardType(card.getCardType())
                .cardHolderName(card.getCardHolderName())
                .expiryDate(card.getExpiryDate())
                .status(card.getStatus())
                .message(message)
                .build();
    }

    private String generateCardNumber() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 16);
    }

    private String generateCVV() {
        return String.valueOf((int) (100 + Math.random() * 900));
    }
}