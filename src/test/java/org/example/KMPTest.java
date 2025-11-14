package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KMPTest {

    private void runAndPrint(String label, String text, String pattern) {
        int n = text.length();
        System.out.println(label + " (n=" + n + ")");

        long t0 = System.nanoTime();
        int[] lps = KMP.buildLps(pattern);
        long t1 = System.nanoTime();
        double lpsMs = (t1 - t0) / 1_000_000.0;

        long t2 = System.nanoTime();
        List<Integer> matches = KMP.kmpSearch(text, pattern, lps);
        long t3 = System.nanoTime();
        double searchMs = (t3 - t2) / 1_000_000.0;

        System.out.printf("LPS build time: %.4f ms%n", lpsMs);
        System.out.printf("KMP search time: %.4f ms%n%n", searchMs);

        System.out.println("Text:    \"" + text + "\"");
        System.out.println("Pattern: \"" + pattern + "\"");
        System.out.print("LPS Array: [");
        for (int i = 0; i < lps.length; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(lps[i]);
        }
        System.out.println("]");
        System.out.println("Matches:  " + matches);
        System.out.println();
    }

    @Test
    public void shortStringTest() {
        String text = "banana";
        String pattern = "ana";

        int[] lps = KMP.buildLps(pattern);
        List<Integer> matches = KMP.kmpSearch(text, pattern, lps);

        // Проверяем корректность
        assertEquals(List.of(1, 3), matches);

        // Печать для отчёта
        runAndPrint("SHORT", text, pattern);
    }

    @Test
    public void mediumStringTest() {
        String text = "abracadabra";
        String pattern = "abra";

        int[] lps = KMP.buildLps(pattern);
        List<Integer> matches = KMP.kmpSearch(text, pattern, lps);

        assertEquals(List.of(0, 7), matches);

        runAndPrint("MEDIUM", text, pattern);
    }

    @Test
    public void longStringTest() {
        String base = "abcxabcdabxabcdabcdabcy";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            sb.append(base);
        }
        sb.append("test");
        String text = sb.toString(); // n = 280
        String pattern = "abcdabcy";

        int[] lps = KMP.buildLps(pattern);
        List<Integer> matches = KMP.kmpSearch(text, pattern, lps);

        assertEquals(
                List.of(15, 38, 61, 84, 107, 130, 153, 176, 199, 222, 245, 268),
                matches
        );

        runAndPrint("LONG", text, pattern);
    }
}
