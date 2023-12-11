package com.kpi.performancecomparison.controllers;


import com.kpi.performancecomparison.data.ChartCoordinates;
import com.kpi.performancecomparison.services.PerformanceComparisonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/performanceComparison")
@AllArgsConstructor
@Primary
public class PerformanceComparisonController {

    @Autowired
    private final PerformanceComparisonService performanceComparisonService;

    @GetMapping("/start")
    public void start() {
        performanceComparisonService.start();
    }

    @GetMapping("/get")
    public HashMap<Long, List<ChartCoordinates>> get() {
        return Optional.ofNullable(performanceComparisonService.getResult())
                .orElse(new HashMap<>());
    }
}
