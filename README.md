# KMP String Matching in Java

## Introduction

For this task I picked the Knuth–Morris–Pratt (KMP) algorithm from the list
The goal was to find all occurrences of a pattern inside a text string and see how the algorithm behaves on short, medium and long inputs

## Implementation

The algorithm itself is in one class KMP (org.example.KMP), and tests are in KMPTest as JUnit test

Main parts:

- `buildLps(String pattern)` – creates the LPS array (longest proper prefix which is also suffix)
  This array tells how far we can shift the pattern when we get a mismatch
- `kmpSearch(String text, String pattern, int[] lps)` – runs KMP and returns all starting
  indices of matches in the text
- `KMPTest` – has three tests (short, medium, long) which call these methods and print  
  text, pattern, LPS array, match indices and timings

## How KMP works

Instead of checking the pattern from scratch after every mismatch (like the naïve algorithm),
KMP uses the LPS array to remember information about the pattern

When some prefix of the pattern already matched and we hit a mismatch, we don’t go back in the text
We move the pattern using the LPS value and continue from there
Because of this the text is scanned only once

## Testing in different strings

I tested the implementation on three input sizes:

| String | N   | LPS build time | KMP search time |
|--------|-----|----------------|-----------------|
| Short  | 6   | 0.0017 ms      | 0.0035 ms       |
| Medium | 11  | 0.0021 ms      | 0.0050 ms       |
| Long   | 280 | 0.0024 ms      | 0.0451 ms       |

The times are very small in all cases
For such short inputs the numbers jump a bit from run to run (JVM warm-up, OS noise, etc.),  
but overall the search time increases roughly linearly with N, which matches the expected behaviour of KMP

## Complexity

Let n be the length of the text and m the length of the pattern

- Building the LPS array takes O(m) time
- The search itself goes through the text once in O(n) time
- Overall time complexity: O(n + m)

The LPS array uses **O(m)** extra memory; apart from that only a few variables are needed,  
so the auxiliary space is also O(m) (not counting the list of match positions)

## Conclusion

I implemented KMP from scratch, wrote Maven tests for three different string lengths and printed the LPS array, match positions and timings.  
The results match the theory: KMP finds overlapping matches correctly and its running time grows linearly with the input size, which makes it much more efficient than the naïve O(n·m) string matching for longer texts.
