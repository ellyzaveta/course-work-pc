package com.kpi.performancecomparison.utils;

import com.kpi.invertedindex.controllers.InvertedIndexController;
import com.kpi.invertedindex.models.InvertedIndex;
import com.kpi.invertedindex.services.InvertedIndexService;
import com.kpi.performancecomparison.data.ChartCoordinates;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@AllArgsConstructor
public class PerformanceComparator {

    @Value("#{'${performance.testdata.paths}'.split(',\\s*')}")
    private final List<String> paths;

    private HashMap<Long, List<ChartCoordinates>> resultMap;

    public HashMap<Long, List<ChartCoordinates>> getResult() throws IOException {
        resultMap = new HashMap<>();
        processPaths();

        return resultMap;
    }

    private void processPaths() {

        for (String path : paths) {
            long fileSize = 0;

            try {
                fileSize = FileHandler.getFilesSize(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            index(path, fileSize);

        }
    }

    private void index(String path, long fileSize) {
        Timer timer = new Timer();

        for (int i = 0; i < 8; i++) {
            timer.start();

            int threadsNumber = (int) Math.pow(2, i);

            InvertedIndexService invertedIndexService = new InvertedIndexService(path, new InvertedIndex());
            new InvertedIndexController(invertedIndexService).index(threadsNumber);

            resultMap.computeIfAbsent(fileSize, k -> new ArrayList<>()).add(new ChartCoordinates(threadsNumber, timer.stop()));
        }
    }

}

