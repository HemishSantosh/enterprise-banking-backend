package com.bank.service.audit;

public interface AuditLogService {

    void log(
            String username,
            String action,
            String status,
            String description
    );

}