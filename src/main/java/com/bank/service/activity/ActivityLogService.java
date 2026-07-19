package com.bank.service.activity;

import com.bank.dto.activity.ActivityLogResponse;

import java.util.List;

public interface ActivityLogService {

    List<ActivityLogResponse> getUserActivities();

    void saveActivity(String username,
                      String activity,
                      String status);

}