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
        if(!isIndexed() && !isInProgress()) {
            taskProcessingThread = CompletableFuture.supplyAsync(() -> {
                invertedIndexController.index(threadsNumber);
                return null;
            });
        }
    }

    public List<String> get(String keyword) {
        return invertedIndexController.get(keyword);
    }

    public double getProgress() {
        return invertedIndexController.getProgress();
    }

    public boolean isInProgress() {
        return taskProcessingThread != null && !taskProcessingThread.isDone();
    }

    public boolean isIndexed() {
        return taskProcessingThread != null && taskProcessingThread.isDone();
    }
}
