package com.bank.repository;

import com.bank.entity.Account;
import com.bank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByCustomer(Customer customer);

    boolean existsByAccountNumber(String accountNumber);

    @Query("SELECT COALESCE(SUM(a.balance), 0) FROM Account a")
    Double getTotalBalance();
}