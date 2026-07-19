package com.bank.repository;

import com.bank.entity.Beneficiary;
import com.bank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

    List<Beneficiary> findByCustomer(Customer customer);

    boolean existsByAccountNumber(String accountNumber);

}