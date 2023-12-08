package com.kpi.invertedindex.controllers;

import com.kpi.invertedindex.services.InvertedIndexService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InvertedIndexControllerTest {

    private InvertedIndexService invertedIndexService;
    private InvertedIndexController invertedIndexController;

    @BeforeEach
    void initUseCase() {
        invertedIndexService = mock(InvertedIndexService.class);
        invertedIndexController = new InvertedIndexController(invertedIndexService);
    }

    @Test
    public void testIndexThrowsExceptionWhenIndexIsFull() {

        when(invertedIndexService.isIndexed()).thenReturn(true);

        assertThrows(RuntimeException.class, () -> invertedIndexController.index(anyInt()));
    }

    @Test
    public void testGetThrowsExceptionWhenIndexNotBuilt() {

        when(invertedIndexService.isIndexed()).thenReturn(false);

        assertThrows(RuntimeException.class, () -> invertedIndexController.get(anyString()));
    }

    @Test
    public void testIndexNormalExecution() {
        when(invertedIndexService.isIndexed()).thenReturn(false);
        assertDoesNotThrow(() -> invertedIndexController.index(5));
    }

    @Test
    public void testGetNormalExecution() {

        List<String> expectedResults = Arrays.asList("result1", "result2");

        when(invertedIndexService.isIndexed()).thenReturn(true);
        when(invertedIndexService.get(anyString())).thenReturn(expectedResults);

        List<String> actualResults = invertedIndexController.get("keyword");

        assertEquals(expectedResults, actualResults);
    }

    @Test
    public void testGetProgressNormalExecution() {

        double expectedProgress = 0.75;
        when(invertedIndexService.getProgress()).thenReturn(expectedProgress);

        double actualProgress = invertedIndexController.getProgress();

        assertEquals(expectedProgress, actualProgress, 0.001);
    }

}