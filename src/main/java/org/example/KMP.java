import java.util.ArrayList;
import java.util.List;

public class KMP {
    public static int[] buildLps(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];

        int len = 0;
        int i = 1;

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            }
            else {
                if (len != 0) {
                    len = lps[len - 1];
                }
                else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    public static List<Integer> kmpSearch(String text, String pattern) {
        List<Integer> result = new ArrayList<>();

        if (pattern.isEmpty() || text.isEmpty() || pattern.length() > text.length()) {
            return result;
        }

        int n = text.length();
        int m = pattern.length();

        int[] lps = buildLps(pattern);

        int i = 0;
        int j = 0;

        while (i < n) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }

            if (j == m) {
                result.add(i - j);
                j = lps[j - 1];
            }
            else if (i < n && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) {
                    j = lps[j - 1];
                }
                else {
                    i++;
                }
            }
        }

        return result;
    }
    public static void main(String[] args) {
        // Test 1: Short string
        String text1 = "ababa";
        String pattern1 = "aba";
        System.out.println("Test 1 (short):");
        System.out.println("Text:    " + text1);
        System.out.println("Pattern: " + pattern1);
        System.out.println("Matches at indices: " + kmpSearch(text1, pattern1));
        System.out.println();

        // Test 2: Medium-length string
        String text2 = "the quick brown fox jumps over the lazy dog";
        String pattern2 = "the";
        System.out.println("Test 2 (medium):");
        System.out.println("Text:    " + text2);
        System.out.println("Pattern: " + pattern2);
        System.out.println("Matches at indices: " + kmpSearch(text2, pattern2));
        System.out.println();

        // Test 3: Longer string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5000; i++) {
            sb.append("abcxabcdabxabcdabcdabcy");
        }
        String text3 = sb.toString();
        String pattern3 = "abcdabcy";
        System.out.println("Test 3 (long):");
        System.out.println("Text length:    " + text3.length());
        System.out.println("Pattern:        " + pattern3);
        System.out.println("Number of matches: " + kmpSearch(text3, pattern3).size());
    }
}

