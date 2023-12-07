package com.kpi.invertedindex.utils;

import org.junit.jupiter.api.Test;

import static com.kpi.invertedindex.utils.UtilsTestConstant.EXPECTED_RESULT;
import static com.kpi.invertedindex.utils.UtilsTestConstant.TEST_STRING;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class DataPreprocessorTest {

    @Test
    public void testGetTokensOfLine() {

        List<String> actualResult = DataPreprocessor.getTokens(TEST_STRING);
        assertEquals(EXPECTED_RESULT, actualResult);
    }
}

