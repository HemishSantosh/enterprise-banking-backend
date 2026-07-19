package com.bank.controller.dashboard;
import com.bank.dto.dashboard.AnalyticsResponse;
import com.bank.dto.dashboard.DashboardResponse;
import com.bank.service.dashboard.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public DashboardResponse getDashboard(
            Authentication authentication) {

        return dashboardService.getDashboard(
                authentication.getName());
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/analytics")
    public AnalyticsResponse getAnalytics(
            Authentication authentication) {

        return dashboardService.getAnalytics(
                authentication.getName());
    }
}