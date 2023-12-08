package com.kpi.client.utils;

import java.util.List;
import java.util.Scanner;

public class Console {

    public static int printThreadsNumberRequest() {
        System.out.println("\n\uD83D\uDD22 ️Index hasn't been built yet. Enter number of threads to build inverted index: ");

        Scanner scanner = new Scanner(System.in);

        return scanner.nextInt();
    }

    public static void showProgressBar(double progress) {

        final int barWidth = 70;

        progress = Math.max(0, Math.min(100, progress));

        int pos = (int) (barWidth * progress / 100.0);

        StringBuilder progressBar = new StringBuilder(barWidth + 10);

        progressBar.append("[");

        for (int i = 0; i < barWidth; i++) {
            if (i < pos) {
                progressBar.append("―");
            } else if (i == pos) {
                progressBar.append("⟶");
            } else {
                progressBar.append(" ");
            }
        }

        progressBar.append("] ");
        progressBar.append((int) progress).append(" %");

        progressBar.append("\r");

        System.out.print(progressBar);

        System.out.flush();

    }

    public static void showFinalProgressBar() {

        final int barWidth = 70;

        double progress = 100;

        int pos = (int) (barWidth * progress / 100.0);

        StringBuilder progressBar = new StringBuilder(barWidth + 10);

        progressBar.append("[");

        for (int i = 0; i < barWidth; i++) {
            if (i < pos) {
                progressBar.append("―");
            } else if (i == pos) {
                progressBar.append("⟶");
            } else {
                progressBar.append(" ");
            }
        }

        progressBar.append("] ");
        progressBar.append((int) progress).append(" %");

        progressBar.append("\n");

        System.out.print(progressBar);

    }

    public static void printSpace() {
        System.out.println();
    }

    public static void printResult(String keyword, List<String> result) {
        if(result != null && !result.isEmpty()) {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("\n\uD83D\uDFE2 Set of documents, associated with " + keyword + " keyword:\n");

            result.forEach(element -> System.out.println("\uD83D\uDCC2 " + element));
        } else {
            System.out.println("\n\uD83D\uDD34 No documents, associated with " + keyword + " keyword.\n");
        }
    }

    public static String printKeywordRequest() {
        System.out.println("\nEnter keyword: ");

        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void printGreeting() {
        System.out.println("\n\uD83D\uDD0E SYSTEM FOR GETTING SET OF DOCUMENTS, ASSOCIATED WITH KEYWORD");
    }

    public static String printOptions() {

        System.out.println("\n\uD83D\uDD11 Enter a keyword to get a list of related documents (e - exit): ");

        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void printWaitMessage() {
        System.out.println("\n\t\t\uD83D\uDD57 When the server is ready, it will request you a keyword.");
    }
}
