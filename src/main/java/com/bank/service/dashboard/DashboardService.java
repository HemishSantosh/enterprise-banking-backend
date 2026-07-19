package com.bank.service.dashboard;
import com.bank.dto.dashboard.AnalyticsResponse;

import com.bank.dto.dashboard.DashboardResponse;

public interface DashboardService {

    DashboardResponse getDashboard(String email);

    AnalyticsResponse getAnalytics(String email);
}