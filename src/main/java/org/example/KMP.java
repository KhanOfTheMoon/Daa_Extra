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
}
