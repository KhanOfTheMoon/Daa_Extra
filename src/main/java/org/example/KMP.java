package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KMP {

    /**
     * Build LPS array for the given pattern
     * Time complexity: O(m), m = pattern length
     */

    public static int[] buildLps(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];

        int len = 0;  // current longest prefix-suffix
        int i = 1;    // lps[0] is always 0

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    // fall back to previous longest prefix-suffix
                    len = lps[len - 1];
                } else {
                    // no prefix-suffix for this i
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    /**
     * KMP search that uses a precomputed LPS array
     * Time complexity: O(n), n = text length
     */
    public static List<Integer> kmpSearch(String text, String pattern, int[] lps) {
        List<Integer> result = new ArrayList<>();

        if (pattern.isEmpty() || text.isEmpty() || pattern.length() > text.length()) {
            return result;
        }

        int n = text.length();
        int m = pattern.length();

        int i = 0; // index in text
        int j = 0; // index in pattern

        while (i < n) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }

            if (j == m) {
                // full match ends at i-1, starts at i-j
                result.add(i - j);
                j = lps[j - 1]; // try to find next match
            } else if (i < n && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) {
                    j = lps[j - 1]; // shift pattern using LPS
                } else {
                    i++;            // no prefix matched yet -> move in text
                }
            }
        }

        return result;
    }

    // Helper to measure one test and print it nicely
    private static void runTest(String label, String text, String pattern) {
        int n = text.length();

        System.out.println(label + " (n=" + n + ")");

        // Measure LPS build time
        long startLps = System.nanoTime();
        int[] lps = buildLps(pattern);
        long endLps = System.nanoTime();
        double lpsMs = (endLps - startLps) / 1_000_000.0;

        // Measure KMP search time
        long startSearch = System.nanoTime();
        List<Integer> matches = kmpSearch(text, pattern, lps);
        long endSearch = System.nanoTime();
        double searchMs = (endSearch - startSearch) / 1_000_000.0;

        System.out.printf("LPS build time: %.4f ms%n", lpsMs);
        System.out.printf("KMP search time: %.4f ms%n", searchMs);
        System.out.println();

        System.out.println("Text:    \"" + text + "\"");
        System.out.println("Pattern: \"" + pattern + "\"");
        System.out.println();

        System.out.println("LPS Array: " + Arrays.toString(lps));
        System.out.println("Matches:  " + matches);
        System.out.println();
    }

    public static void main(String[] args) {
        // short example
        String shortText = "banana";      // n = 6
        String shortPattern = "ana";
        runTest("SHORT", shortText, shortPattern);

        // medium example
        String mediumText = "abracadabra";   // n = 11
        String mediumPattern = "abra";
        runTest("MEDIUM", mediumText, mediumPattern);

        // long example (n = 280)
        String base = "abcxabcdabxabcdabcdabcy"; // length = 23
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) { // 12 * 23 = 276
            sb.append(base);
        }
        sb.append("test"); // +4 = 280
        String longText = sb.toString();
        String longPattern = "abcdabcy";

        runTest("LONG", longText, longPattern);
    }
}
