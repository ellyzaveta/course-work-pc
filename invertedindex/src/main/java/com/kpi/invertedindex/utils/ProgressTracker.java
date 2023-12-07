package com.kpi.invertedindex.utils;

public class ProgressTracker {

    private long currentProgress;
    private final long totalProgress;
    private static final int MAX_PROGRESS_PERCENT = 100;

    public ProgressTracker(long totalProgress) {
        this.currentProgress = 0L;
        this.totalProgress = totalProgress;
    }

    public synchronized void updateProgress(long fileSize) {
        currentProgress += fileSize;
    }

    public synchronized double getProgress() {
        return (double) currentProgress / totalProgress * MAX_PROGRESS_PERCENT;
    }

    public synchronized boolean isFinished() {
        return getProgress() == MAX_PROGRESS_PERCENT;
    }
}
