package com.kpi.invertedindex.controllers;

import com.kpi.invertedindex.services.InvertedIndexService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class InvertedIndexController {

    private InvertedIndexService invertedIndexService;

    public void index(int threadsNum) {

        boolean isIndexed = invertedIndexService.isIndexed();

        if (isIndexed) {
            throw new RuntimeException("Index is full");
        }

        invertedIndexService.index(threadsNum);
    }

    public List<String> get(String keyword) {

        boolean isIndexed = invertedIndexService.isIndexed();

        if (!isIndexed) {
            throw new RuntimeException("Index hasn't been built yet");
        }

        return invertedIndexService.get(keyword);
    }

    public double getProgress() {
        return invertedIndexService.getProgress();
    }
}
