package com.bank.repository;

import com.bank.entity.Account;
import com.bank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountOrderByTransactionDateDesc(Account account);

    Optional<Transaction> findByReferenceNumber(String referenceNumber);
    List<Transaction> findTop5ByOrderByTransactionDateDesc();
    long countByTransactionDateBetween(
            LocalDateTime start,
            LocalDateTime end
    );
}