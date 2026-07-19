package com.bank.controller.activity;

import com.bank.dto.activity.ActivityLogResponse;
import com.bank.service.activity.ActivityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public List<ActivityLogResponse> getActivities() {
        return activityLogService.getUserActivities();
    }

}