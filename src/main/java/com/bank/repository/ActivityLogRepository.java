package com.bank.repository;

import com.bank.entity.ActivityLog;
import com.bank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    List<ActivityLog> findByUserOrderByActivityTimeDesc(User user);

}