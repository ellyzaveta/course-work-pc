package com.kpi.server.threads;

import com.kpi.invertedindex.controllers.InvertedIndexController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ProcessingThread {

    @Autowired
    InvertedIndexController invertedIndexController;

    private CompletableFuture<Void> taskProcessingThread;

    public synchronized void index(int threadsNumber) {
        taskProcessingThread = CompletableFuture.supplyAsync(() -> {
            invertedIndexController.index(threadsNumber);
            return null;
        });
    }

    public synchronized List<String> get(String keyword) {
        if(taskProcessingThread.isDone()) {
            return invertedIndexController.get(keyword);
        }
        return null;
    }

    public synchronized boolean isInProgress() {
        return taskProcessingThread != null && !taskProcessingThread.isDone();
    }

    public synchronized boolean isIndexed() {
        return taskProcessingThread != null && taskProcessingThread.isDone();
    }

    public synchronized double getProgress() {
        return invertedIndexController.getProgress();
    }
}
