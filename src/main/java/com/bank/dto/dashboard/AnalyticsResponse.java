package com.bank.dto.dashboard;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class AnalyticsResponse {

    private List<String> months;

    private List<BigDecimal> deposits;

    private List<BigDecimal> withdrawals;

    private List<BigDecimal> transfers;
}