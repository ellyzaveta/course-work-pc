package com.kpi.invertedindex.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InvertedIndexServiceTest {

    @Autowired
    private InvertedIndexService invertedIndexService;

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