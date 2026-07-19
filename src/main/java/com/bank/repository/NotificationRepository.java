package com.bank.repository;

import com.bank.entity.Notification;
import com.bank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserOrderByCreatedAtDesc(User user);

    long countByUserAndReadFalse(User user);

    List<Notification> findByUserAndReadFalse(User user);
}