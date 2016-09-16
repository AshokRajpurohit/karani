package com.ashok.lang.algorithms;

import com.ashok.lang.math.Power;

public class Strings {
    private static char[] upperToLowerCase = new char[256], lowerToUpperCase =
        new char[256];
    private static int mod = 1000000007;

    static {
        for (int i = 0; i < 256; i++) {
            upperToLowerCase[i] = (char)i;
            lowerToUpperCase[i] = (char)i;
        }

        for (int i = 'a', j = 'A'; i <= 'z'; i++, j++) {
            upperToLowerCase[i] = (char)i;
            upperToLowerCase[j] = (char)i;
            lowerToUpperCase[i] = (char)j;
            lowerToUpperCase[j] = (char)j;
        }
    }

    public static char upperTolower(char c) {
        return upperToLowerCase[c];
    }

    private Strings() {
        super();
    }

    /**
     * Converts a db column name to variable type string.
     * e.g. db_table_column should be dbTableColumn
     *
     * @param s
     * @return
     */
    public static String dbColumnToVariable(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '_') {
                i++;
                sb.append(lowerToUpperCase[s.charAt(i)]);
            } else
                sb.append(upperToLowerCase[s.charAt(i)]);
        }

        sb.append('(').append('"');
        for (int i = 0; i < s.length(); i++)
            sb.append(upperToLowerCase[s.charAt(i)]);

        sb.append('"').append(')').append(',');

        return sb.toString();
    }

    /*public static String longestCommonSubstring(String a, String b) {
        char[] ar = a.toCharArray(), br = b.toCharArray();
        int index = 0, length = 1;

        for (int i = 0; i < a.length();) {
            for (int j = 0; j < b.length(); j++) {
                if (ar[i] != br[j])
                    continue;
            }
        }
    }*/

    /**
     * returns the length of Longest Common Subsequence of two strings a and b.
     * @param a
     * @param b
     * @return
     */
    public static int LongestCommonSubsequence(String a, String b) {
        int[][] ar = new int[a.length()][b.length()];
        boolean[][] var = new boolean[a.length()][b.length()];

        var[0][0] = true;
        if (a.charAt(0) == b.charAt(0))
            ar[0][0] = 1;

        return LCS(ar, var, a, b, a.length() - 1, b.length() - 1);
    }

    public static int stringCountRabinKarp(String s, String search) {
        if (search.length() > s.length())
            return 0;

        long hash = hashCode(search);
        long pow = Power.pow(31, search.length() - 1, mod);
        long code = 0;
        int count = 0;

        for (int i = 0; i < search.length(); i++)
            code = (code * 31 + s.charAt(i)) % mod;

        if (code == hash)
            count++;

        for (int i = search.length(); i < s.length(); i++) {
            code -= (pow * s.charAt(i - search.length())) % mod;
            code += mod;
            code = (code * 31 + s.charAt(i)) % mod;
            if (code == hash)
                count++;
        }
        return count;
    }

    /**
     * This method returns the number of times search appears in s while
     * occurances can be intersecting.
     * This method is based on KMP algorithm for string matching.
     * if the search string and s string both are of same length then it checks
     * for equality and returns 1 or 0 accordingly.
     *
     * @param s
     * @param search
     * @return number of times search appears in String s
     */
    public static int stringCountIntersect(String s, String search) {
        if (s.length() < search.length())
            return 0;

        if (s.length() == search.length()) {
            for (int i = 0; i < s.length(); i++)
                if (s.charAt(i) != search.charAt(i))
                    return 0;
            return 1;
        }

        int count = 0;
        int[] failTable = failFunctionKMP(search);
        int i = 0, j = 0;

        while (i < s.length()) {
            if (s.charAt(i) == search.charAt(j)) {
                if (j == search.length() - 1) {
                    count++;
                    i++;
                    j = failTable[j];
                } else {
                    i++;
                    j++;
                }
            } else {
                if (j > 0) {
                    j = failTable[j - 1];
                } else
                    i++;
            }
        }

        return count;
    }

    /**
     * This method returns number of times String search appears in String s,
     * while occurances are non intersecting.
     *
     * @param s
     * @param search
     * @return
     */
    public static int stringCountNonIntersect(String s, String search) {
        if (s.length() < search.length())
            return 0;

        if (s.length() == search.length()) {
            for (int i = 0; i < s.length(); i++)
                if (s.charAt(i) != search.charAt(i))
                    return 0;
            return 1;
        }

        int count = 0;
        int[] failTable = failFunctionKMP(search);
        int i = 0, j = 0;

        while (i < s.length()) {
            if (s.charAt(i) == search.charAt(j)) {
                if (j == search.length() - 1) {
                    count++;
                    i++;
                    j = 0;
                } else {
                    i++;
                    j++;
                }
            } else {
                if (j > 0) {
                    j = failTable[j - 1];
                } else
                    i++;
            }
        }

        return count;
    }

    /**
     * returns fail function (Partial Matching Table) used in KMP string search
     * algorithm.
     *
     * @param s String for which the fail function to be generated.
     * @return
     */
    private static int[] failFunctionKMP(String s) {
        int[] ar = new int[s.length()];
        int i = 1, j = 0;

        while (i < s.length()) {
            if (s.charAt(i) == s.charAt(j)) {
                ar[i] = j + 1;
                i++;
                j++;
            } else if (j > 0)
                j = ar[j - 1];
            else {
                ar[i] = 0;
                i++;
            }
        }
        return ar;
    }

    /**
     * Returns a hash code for this string. The hash code for a
     * <code>String</code> object is computed as
     * <blockquote><pre>
     * s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
     * </pre></blockquote>
     * using <code>int</code> arithmetic, where <code>s[i]</code> is the
     * <i>i</i>th character of the string, <code>n</code> is the length of
     * the string, and <code>^</code> indicates exponentiation.
     * (The hash value of the empty string is zero.)
     *
     * @param s
     * @return
     */
    private static long hashCode(String s) {
        long hash = 0;
        for (int i = 0; i < s.length(); i++)
            hash = (hash * 31 + s.charAt(i)) % mod;

        return hash;
    }

    /**
     * recursively calculates the length of largest common subsequence.
     * var is to check whether the length for this index ai and bi is already
     * calculated or not.
     * ar is to store the precalculated length upto ai and bi index.
     *
     * @param ar to store and reuse the calculated length.
     * @param var
     * @param a
     * @param b
     * @param ai index in first string
     * @param bi index in second string
     * @return
     */
    private static int LCS(int[][] ar, boolean[][] var, String a, String b,
                           int ai, int bi) {
        if (ai < 0 || bi < 0)
            return 0;

        if (var[ai][bi])
            return ar[ai][bi];

        var[ai][bi] = true;
        ar[ai][bi] = LCS(ar, var, a, b, ai - 1, bi - 1);
        if (a.charAt(ai) == b.charAt(bi))
            ar[ai][bi]++;

        ar[ai][bi] = Math.max(ar[ai][bi], LCS(ar, var, a, b, ai - 1, bi));
        ar[ai][bi] = Math.max(ar[ai][bi], LCS(ar, var, a, b, ai, bi - 1));
        return ar[ai][bi];
    }

    /**
     * This is implementation Manacher's Algorithm for largest Palindromic
     * substring in a string. For the algorithm description please follow
     * the link:
     * http://articles.leetcode.com/2011/11/longest-palindromic-substring-part-ii.html
     * if the string length is less than 30 then normal method is better.
     *
     * @param s
     * @return
     */
    public static String LargestPalindromeManacher(String s) {
        if (s.length() <= 1)
            return s;

        String t = preprocess(s);
        int centre = 0, right = 0;
        int[] len = new int[t.length()];

        for (int i = 0; i < t.length() - 1; i++) {
            int i_mirror = (centre << 1) - i;
            len[i] = right > i ? Math.min(right - i, len[i_mirror]) : 0;

            // Attempt to expand palindrome centered at i
            while (i > len[i] &&
                   t.charAt(i + 1 + len[i]) == t.charAt(i - 1 - len[i]))
                len[i]++;

            // If palindrome centered at i expand past right boundry,
            // adjust center based on expanded palindrome.
            if (i + len[i] > right) {
                centre = i;
                right = i + len[i];
            }
        }

        int maxLen = 0, centreIndex = 0;
        for (int i = 1; i < t.length() - 1; i++) {
            if (len[i] > maxLen) {
                maxLen = len[i];
                centreIndex = i;
            }
        }

        int startIndex = (centreIndex - 1 - maxLen) / 2;
        return s.substring(startIndex, startIndex + maxLen);
    }

    /**
     * convert s into t
     * for example, if s = "abba", then t = "$#a#b#b#a#@"
     * the # are interleaved to avoid even/odd-length palindromes uniformly
     * $ and @ are prepended and appended to each end to avoid bounds checking
     */
    private static String preprocess(String s) {
        StringBuilder sb = new StringBuilder((s.length() << 1) + 3);
        sb.append("^#");
        for (int i = 0; i < s.length(); i++)
            sb.append(s.charAt(i)).append('#');
        sb.append('$');
        return sb.toString();
    }

    /**
     * The time complexity for this method is O(n^2) with constant space.
     * palindrome is spread around the centre as much possible and then
     * centre is moved to next position. This method is better than Manacher's
     * algorithm for smaller strings. In worst case when all the characters
     * are same then with string length 17 it's performance is equal to
     * {@code LargestPalindromeManacher}.
     *
     * @param s
     * @return returns the largest palindromic substring in string s
     */
    public static String LargestPalindromeN2(String s) {
        if (s.length() == 1)
            return s;
        int centre = 0, maxLen = 1, len = 0, startIndex = 0, endIndex = 0;

        // let's check first odd length palindromes.
        for (int i = 1; i < s.length() - maxLen / 2; i++) {
            len = 1;
            while (i > len / 2 && i + 1 + len / 2 < s.length() &&
                   s.charAt(i + 1 + len / 2) == s.charAt(i - 1 - len / 2))
                len += 2;

            if (maxLen < len) {
                maxLen = len;
                centre = i;
            }
        }

        startIndex = centre - maxLen / 2;
        endIndex = centre + maxLen / 2;

        // let's check now for even length palindromes.
        for (int i = 0; i < s.length() - maxLen / 2; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                len = 2;
                while (i > (len - 1) / 2 && i + 1 + len / 2 < s.length() &&
                       s.charAt(i + 1 + len / 2) == s.charAt(i - len / 2))
                    len += 2;

                if (maxLen < len) {
                    maxLen = len;
                    centre = i;
                }
            }
        }

        if (maxLen > endIndex + 1 - startIndex) {
            startIndex = centre - maxLen / 2 + 1;
            endIndex = centre + maxLen / 2;
        }

        return s.substring(startIndex, endIndex + 1);
    }
}
