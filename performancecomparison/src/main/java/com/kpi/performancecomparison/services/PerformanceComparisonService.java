package com.kpi.performancecomparison.services;

import com.kpi.performancecomparison.data.ChartCoordinates;
import com.kpi.performancecomparison.utils.PerformanceComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class PerformanceComparisonService {

    private CompletableFuture<HashMap<Long, List<ChartCoordinates>>> processingThread;

    @Autowired
    private PerformanceComparator performanceComparator;

    public PerformanceComparisonService(PerformanceComparator performanceComparator) {
        this.performanceComparator = performanceComparator;
    }

    public synchronized void start() {
        processingThread = CompletableFuture.supplyAsync(() -> {
            try {
                return performanceComparator.getResult();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public synchronized HashMap<Long, List<ChartCoordinates>> getResult() {
        if(isReady()) {
            try {
                return processingThread.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private boolean isReady() {
        return processingThread != null && processingThread.isDone();
    }

}

