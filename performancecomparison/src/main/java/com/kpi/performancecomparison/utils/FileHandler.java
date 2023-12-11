package com.kpi.performancecomparison.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler {

    public static List<Path> getAllTxtFilesPaths(String directoryPath) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .collect(Collectors.toList());
        }
    }

    public static long getFileSize(Path path) {
        try {
            return Files.size(path);
        } catch (IOException e) {
            System.err.println("Unable to get size of file: " + path);
            return 0;
        }
    }

    public static long getFileListSize(List<Path> filePathsToIndex) {
        return filePathsToIndex.stream().mapToLong(FileHandler::getFileSize).sum();
    }

    public static long getFilesSize(String path) throws IOException {
        List<Path> directoryPaths = getAllTxtFilesPaths(path);
        return getFileListSize(directoryPaths);
    }
}
