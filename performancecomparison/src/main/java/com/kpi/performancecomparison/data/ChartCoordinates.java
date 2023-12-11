package com.kpi.performancecomparison.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartCoordinates {
    int threadsNum;
    long executionTime;
}
