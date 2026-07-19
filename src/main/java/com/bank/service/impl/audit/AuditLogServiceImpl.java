package com.bank.service.impl.audit;

import com.bank.entity.AuditLog;
import com.bank.repository.AuditLogRepository;
import com.bank.service.audit.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public void log(
            String username,
            String action,
            String status,
            String description) {

        AuditLog auditLog = AuditLog.builder()
                .username(username)
                .action(action)
                .status(status)
                .description(description)
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(auditLog);
    }
}