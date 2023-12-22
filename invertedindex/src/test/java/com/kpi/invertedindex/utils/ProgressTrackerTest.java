package com.kpi.invertedindex.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgressTrackerTest {

    private ProgressTracker progressTracker;
    private final long totalProgress = 1000;

    @BeforeEach
    void setUp() {
        progressTracker = new ProgressTracker(totalProgress);
    }

    @Test
    void testInitialization() {
        assertEquals(0, progressTracker.getProgress());
    }

    @Test
    void testUpdateProgress() {
        progressTracker.updateProgress(100);
        assertEquals(10.0, progressTracker.getProgress());
    }

    @Test
    void testGetProgress() {
        progressTracker.updateProgress(500);
        assertEquals(50.0, progressTracker.getProgress());
    }

    @Test
    void testThreadSafety() throws InterruptedException {
        Thread thread1 = new Thread(() -> progressTracker.updateProgress(500));
        Thread thread2 = new Thread(() -> progressTracker.updateProgress(500));

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        assertEquals(100.0, progressTracker.getProgress());
    }

}