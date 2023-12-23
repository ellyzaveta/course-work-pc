package com.kpi.invertedindex.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.lucene.analysis.standard.ClassicAnalyzer.STOP_WORDS_SET;

public class DataPreprocessor {

    public static final String SPLIT_SYMBOLS = "[!:;().,\\s]+";
    public static final String PUNCTUATION_SYMBOLS = "^" + SPLIT_SYMBOLS;

    public static List<String> getTokens(String input) {

        if (!isInputCorrect(input)) {
            throw new IllegalArgumentException("Incorrect input");
        }

        List<String> tokens = tokenize(input);
        tokens = normalize(tokens);
        tokens = removeStopWords(tokens);

        return tokens;
    }

    private static boolean isInputCorrect(String input) {
        return (input != null && !input.trim().isEmpty());
    }

    private static List<String> tokenize(String input) {
        String[] tokensArray = input.replaceAll(PUNCTUATION_SYMBOLS, "")
                .split(SPLIT_SYMBOLS);

        return new ArrayList<>(Arrays.asList(tokensArray));
    }

    private static List<String> normalize(List<String> tokens) {
        return tokens.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    private static List<String> removeStopWords(List<String> tokens) {
        return tokens.stream()
                .filter(token -> !STOP_WORDS_SET.contains(token))
                .collect(Collectors.toList());
    }

}

