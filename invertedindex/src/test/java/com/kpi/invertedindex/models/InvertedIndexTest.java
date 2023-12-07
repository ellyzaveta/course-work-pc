package com.kpi.invertedindex.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.kpi.invertedindex.models.ModelTestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class InvertedIndexTest {

    private InvertedIndex index;

    @BeforeEach
    public void setup() {
        index = new InvertedIndex();

        index.add(WORD_1, FILE_1);
        index.add(WORD_2, FILE_2);
        index.add(WORD_1, FILE_3);
    }

    @Test
    public void testGetExistingWord() {

        List<String> files = index.get(WORD_1);

        assertNotNull(files);
        assertTrue(files.contains(FILE_1));
        assertTrue(files.contains(FILE_3));
        assertEquals(2, files.size());
    }

    @Test
    public void testGetNonExistingWord() {
        List<String> files = index.get(NON_EXISTING_WORD);

        assertNotNull(files);
        assertTrue(files.isEmpty());
    }
}