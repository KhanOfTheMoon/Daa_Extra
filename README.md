# KMP String Matching in Java

This project implements the **Knuth–Morris–Pratt (KMP)** string matching algorithm in Java.  
The program finds all occurrences of a pattern in a text, builds the LPS array, and prints timings for preprocessing and search.

## Implementation

- Class: `KMP`
- `buildLps(String pattern)`: builds the LPS (Longest Proper Prefix which is also Suffix) array used to skip redundant comparisons.
- `kmpSearch(String text, String pattern, int[] lps)`: performs the KMP search and returns all starting indices of matches.
- `main(String[] args)`: runs three fixed tests (short, medium, long strings) and prints:
  - text and pattern,
  - LPS array,
  - list of match indices,
  - LPS build time and search time (in ms).

## Testing Results

Three test cases:

1. **SHORT**  
   - Text: `banana`  
   - Pattern: `ana`  
   - LPS: `[0, 0, 1]`  
   - Matches: `[1, 3]`

2. **MEDIUM**  
   - Text: `abracadabra`  
   - Pattern: `abra`  
   - LPS: `[0, 0, 0, 1]`  
   - Matches: `[0, 7]`

3. **LONG**  
   - Text: 12×`"abcxabcdabxabcdabcdabcy"` + `"test"` (length 280)  
   - Pattern: `abcdabcy`  
   - LPS: `[0, 0, 0, 0, 1, 2, 3, 0]`  
   - Matches: `[15, 38, 61, 84, 107, 130, 153, 176, 199, 222, 245, 268]`

For all cases, measured times for LPS construction and search are very small and grow roughly linearly with text length.

## Complexity Analysis

Let `n` be the text length and `m` the pattern length.

- **Time complexity**
  - LPS construction: `O(m)`
  - Search: `O(n)`
  - Overall: `O(n + m)` (best/average/worst case).

- **Space complexity**
  - LPS array: `O(m)`
  - Extra variables: `O(1)`  
  - Excluding the output list of matches, total auxiliary space: `O(m)`.

## Conclusion

The KMP implementation correctly finds all occurrences of the pattern, including overlapping ones, and handles short, medium and long inputs.  
The experimental timings confirm the theoretical behaviour: preprocessing and search scale linearly with the sizes of the pattern and text, so KMP is more efficient than the naïve `O(n·m)` approach for larger inputs.
