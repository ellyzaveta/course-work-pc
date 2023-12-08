package com.kpi.invertedindex.services;

import com.kpi.invertedindex.models.InvertedIndex;
import com.kpi.invertedindex.utils.FileHandler;
import com.kpi.invertedindex.utils.ProgressTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.kpi.invertedindex.utils.DataPreprocessor.getTokens;
import static com.kpi.invertedindex.utils.FileHandler.getStringPath;

@Service
public class InvertedIndexService {

    @Autowired
    private final InvertedIndex invertedIndex;

    private final ProgressTracker progressTracker;

    private final List<Path> pathsToIndex;

    public InvertedIndexService(@Value("${directory.path}") String path, InvertedIndex invertedIndex) {

        this.invertedIndex = invertedIndex;

        try {
            this.pathsToIndex = FileHandler.getAllTxtFilesPaths(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        progressTracker = new ProgressTracker(FileHandler.getFileListSize(pathsToIndex));
    }

    public List<String> get(String keyword) {
        return invertedIndex.get(keyword);
    }

    public void index(int threadsNumber) {

        ExecutorService executorService = Executors.newFixedThreadPool(threadsNumber);

        int filesPerThread = (int) Math.ceil((double) pathsToIndex.size() / threadsNumber);

        IntStream.range(0, threadsNumber).forEach(
                i -> executorService.submit(()
                        -> indexBatchOfFiles(i, filesPerThread)));

        executorService.shutdown();

        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void indexBatchOfFiles(int batchIndex, int filesPerThread) {
        int start = batchIndex * filesPerThread;
        int end = Math.min(start + filesPerThread, pathsToIndex.size());

        for (int i = start; i < end; i++) {
            processFileSafely(pathsToIndex.get(i));
        }
    }

    private void processFileSafely(Path path) {
        try {
            processFile(path);
            updateProgress(path);
        } catch (IOException e) {
            System.err.println("Error processing file: " + path + " - " + e.getMessage());
        }
    }

    private void processFile(Path path) throws IOException {
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(line -> indexLine(line, path));
        }
    }

    private void indexLine(String line, Path path) {

        List<String> tokens = getTokens(line);
        String fullFilePath = getStringPath(path);

        tokens.forEach(word ->
                invertedIndex.add(word, fullFilePath));
    }

    private void updateProgress(Path path) {
        progressTracker.updateProgress(FileHandler.getFileSize(path));
    }

    public boolean isIndexed() {
        return progressTracker.isFinished();
    }

}

