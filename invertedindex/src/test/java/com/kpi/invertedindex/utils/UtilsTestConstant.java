package com.kpi.invertedindex.utils;

import java.util.Arrays;
import java.util.List;

public class UtilsTestConstant {

    public static final String WORD_1 = "word1";
    public static final String WORD_2 = "word2";
    public static final String WORD_3 = "word3";
    public static final String WORD_4 = "word3";
    public static final String COMMA = ",";
    public static final String DOT = ".";
    public static final String SPACE = " ";

    public static final String TEST_STRING;
    public static final List<String> EXPECTED_RESULT = Arrays.asList(WORD_1, WORD_2, WORD_3, WORD_4);

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(COMMA);
        stringBuilder.append(WORD_1);
        stringBuilder.append(COMMA);
        stringBuilder.append(SPACE);
        stringBuilder.append(WORD_2);
        stringBuilder.append(COMMA);
        stringBuilder.append(DOT);
        stringBuilder.append(WORD_3);
        stringBuilder.append(DOT);
        stringBuilder.append(WORD_4);

        TEST_STRING = stringBuilder.toString();
    }
}
