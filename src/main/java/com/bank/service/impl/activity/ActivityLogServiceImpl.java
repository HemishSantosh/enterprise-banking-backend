package com.bank.service.impl.activity;

import com.bank.dto.activity.ActivityLogResponse;
import com.bank.entity.ActivityLog;
import com.bank.entity.User;
import com.bank.repository.ActivityLogRepository;
import com.bank.repository.UserRepository;
import com.bank.service.activity.ActivityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository activityLogRepository;
    private final UserRepository userRepository;

    @Override
    public List<ActivityLogResponse> getUserActivities() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByEmail(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return activityLogRepository
                .findByUserOrderByActivityTimeDesc(user)
                .stream()
                .map(activity -> ActivityLogResponse.builder()
                        .id(activity.getId())
                        .activity(activity.getActivity())
                        .status(activity.getStatus())
                        .activityTime(activity.getActivityTime())
                        .build())
                .toList();
    }

    @Override
    public void saveActivity(String username,
                             String activity,
                             String status) {

        System.out.println("========== SAVE ACTIVITY ==========");
        System.out.println("Username = " + username);

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("User = " + user.getEmail());

        ActivityLog log = ActivityLog.builder()
                .activity(activity)
                .status(status)
                .activityTime(LocalDateTime.now())
                .user(user)
                .build();

        activityLogRepository.save(log);

        System.out.println("Activity Saved");
    }
}