package com.kpi.invertedindex.models.concurrenthashmap;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class CustomConcurrentHashMapTest {

    @Test
    public void testConcurrentHashMapOperations() throws InterruptedException {

        String key = "key";
        String documentPath = "documentPath";

        int capacity = 50000;
        CustomConcurrentHashMap<String, List<String>> map = new CustomConcurrentHashMap<>(capacity);

        int numberOfThreads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        int iterationsNum = 1000;

        Runnable task = () -> {
            for (int i = 0; i < iterationsNum; i++) {
                map.computeIfAbsent(key, k -> new CopyOnWriteArrayList<>()).add(documentPath);
                map.get(key);
            }
        };

        for (int i = 0; i < numberOfThreads; i++) {
            executor.submit(task);
        }

        executor.shutdown();

        boolean finished = executor.awaitTermination(1, TimeUnit.MINUTES);

        if (!finished) {
            fail("Tasks did not finish within the expected time limit.");
        }

        int expectedSize = 10000;
        assertEquals(expectedSize, map.get(key).size());
    }
}