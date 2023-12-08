package com.kpi.invertedindex.services;

import com.kpi.invertedindex.models.InvertedIndex;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvertedIndexServiceTest {

    private final InvertedIndex invertedIndex = new InvertedIndex();
    private final InvertedIndexService invertedIndexService = new InvertedIndexService("/Users/lizakhmyz/Desktop/data for unit testing", invertedIndex);

    @Test
    public void testIndexAndGet() {
        invertedIndexService.index(2);

        while(!invertedIndexService.isIndexed()) {
            continue;
        }

        List<String> result = invertedIndexService.get("test");

        assertEquals(result.size(), 2);
    }

}