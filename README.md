# KMP String Matching in Java

## Introduction

For this task I picked the Knuth–Morris–Pratt (KMP) algorithm from the list 
The goal was to find all occurrences of a pattern inside a text string, and see how the algorithm behaves on short, medium and long inputs

## Implementation

Everything is in one class KMP (in my case org.example.KMP).

Main parts:

- `buildLps(String pattern)` – creates the LPS array (longest proper prefix which is also suffix).  
  This array tells us how far we can shift the pattern when we get a mismatch.
- `kmpSearch(String text, String pattern, int[] lps)` – actually runs KMP and returns all starting
  indices of matches.
- `main(String[] args)` – just calls these methods for three test strings (short, medium, long) and
  prints text, pattern, LPS array, match indices and timings.

## How KMP works

Instead of checking the pattern from scratch after every mismatch (like the naïve algorithm),
KMP remembers information about the pattern itself in the LPS array.

When some prefix of the pattern already matched and we hit a mismatch, we don’t go back in the text.
We move the pattern using the LPS value and continue from there.  
Because of this we scan the text only once.

## Testing in different strings

I tested the implementation on three input sizes:

| String | N   | LPS build time | KMP search time |
|--------|-----|----------------|-----------------|
| Short  | 6   | 0.0035 ms      | 0.0176 ms       |
| Medium | 11  | 0.0014 ms      | 0.0036 ms       |
| Long   | 280 | 0.0009 ms      | 0.0252 ms       |

The numbers are small in all cases.  
The first (short) string looks a bit noisy in terms of time, which is normal on very small inputs
any tiny overhead (JVM warm-up, OS scheduling, etc.) can dominate
For medium and long strings the time grows roughly linearly with N, which is what we expect

## Complexity

Let n be the length of the text and m the length of the pattern.

- Building the LPS array takes O(m) time
- The search itself goes through the text once in O(n) time
- Overall time complexity: O(n + m)

The LPS array uses O(m) extra memory; apart from that only a few variables are needed,
so the auxiliary space is also **O(m)** (not counting the list of match positions)

## Conclusion

I implemented KMP from scratch, tested it on three different string lengths and printed LPS,
matches and timings. The results match the theory: KMP handles overlapping matches correctly and
its running time grows linearly with the size of the input, which makes it much more efficient
than the naïve O(n·m) matching for longer texts
