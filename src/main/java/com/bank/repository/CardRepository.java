package com.bank.repository;

import com.bank.entity.Card;
import com.bank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByCustomer(Customer customer);

    Card findByCardNumber(String cardNumber);
}