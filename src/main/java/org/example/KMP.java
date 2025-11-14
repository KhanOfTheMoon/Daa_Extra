package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * KMP string matching implementation.
 * Contains only algorithm logic, без main.
 */
public class KMP {

    /**
     * Builds the LPS (Longest Proper Prefix which is also Suffix) array for the given pattern
     * lps[i] = length of the longest proper prefix of pattern[0..i], which is also a suffix of pattern[0..i].
     * Time: O(m), m = pattern length.
     */
    public static int[] buildLps(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];

        int len = 0; // current longest prefix-suffix
        int i = 1;   // lps[0] = 0

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    // fallback to previous longest prefix-suffix
                    len = lps[len - 1];
                } else {
                    // no prefix-suffix for this position
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    /**
     * KMP search: finds all starting indices where pattern occurs in text.
     * @param text    full text
     * @param pattern pattern to search
     * @param lps     precomputed LPS array for pattern
     * @return list of starting indices (0-based)
     * Time: O(n), n = text length.
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
                // full pattern matched
                result.add(i - j);
                j = lps[j - 1]; // поиск следующего вхождения
            } else if (i < n && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) {
                    j = lps[j - 1]; // двигаем паттерн по LPS
                } else {
                    i++;            // ничего не совпало, идём дальше по тексту
                }
            }
        }

        return result;
    }
}
