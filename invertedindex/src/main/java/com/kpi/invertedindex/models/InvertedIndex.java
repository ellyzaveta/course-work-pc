package com.kpi.invertedindex.models;

import com.kpi.invertedindex.models.concurrenthashmap.CustomConcurrentHashMap;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class InvertedIndex {

    private final CustomConcurrentHashMap<String, List<String>> map = new CustomConcurrentHashMap<>(5000);

    public void add(String word, String filename) {
        map.computeIfAbsent(word, k -> new CopyOnWriteArrayList<>()).add(filename);
    }

    public List<String> get(String keyword) {
        return map.getOrDefault(keyword, Collections.emptyList());
    }

}

