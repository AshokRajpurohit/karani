package com.ashok.friends.aman;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;

/**
 * Problem Name: Multiple Problems
 * Link: Private
 * Description: Cisco Programming Puzzles - Set 4 | HackerRank | Aman Kashyap
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class HackerRankCisco {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        HackerRankCisco a = new HackerRankCisco();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            out.println(in.read());
            out.flush();
        }
    }

    final static class FibonacciNumbers {
        private static int sumFibonacci(int n) {
            if (n <= 8)
                return 2;

            int a = 1, b = 2, c = 0;
            int sum = 0;

            while (b < n) {
                if ((b & 1) == 0)
                    sum += b;

                if (b >= n - a) // when b is going to overflow, break the loop.
                    break;

                c = a + b;
                a = b;
                b = c;
            }

            return sum;
        }
    }

    final static class CollatzSequence {
        private static int[] seq;

        private static int longestSequence(int n) {
            if (n <= 3)
                return n;

            seq = new int[n + 1];
            seq[1] = 1;

            for (int i = 2; i <= n; i++)
                process(i);

            int max = 1;
            for (int i = 1; i <= n; i++)
                if (seq[max] < seq[i])
                    max = i;

            return max;
        }

        private static int process(int n) {
            if (n < seq.length && seq[n] > 0)
                return seq[n];

            int len = 0;

            if ((n & 1) == 0)
                len = process(n / 2) + 1;
            else
                len = process(n * 3 + 1) + 1;

            if (n < seq.length)
                seq[n] = len;

            return len;
        }
    }

    final static class RearrangeWords {
        private final static String NA = "no answer";

        private static String[] nextAnagram(String[] words) {
            String[] anagrams = new String[words.length];

            for (int i = 0; i < words.length; i++)
                anagrams[i] = rearrangeWord(words[i]);

            return anagrams;
        }

        private static String rearrangeWord(String word) {
            char[] ar = word.toCharArray();

            if (impossible(ar))
                return NA;

            char max = ar[ar.length - 1];

            for (int i = ar.length - 2; i >= 0; i--) {
                if (ar[i] < ar[i + 1]) {
                    int index = getMin(ar, ar[i], i + 1);
                    swap(ar, i, index);

                    Arrays.sort(ar, i + 1, ar.length);
                    return String.valueOf(ar);
                }
            }

            return NA;
        }

        private static int getMin(char[] ar, char ch, int start) {
            char min = ar[start];
            int index = start;

            for (int i = start; i < ar.length; i++)
                if (ar[i] > ch && ar[i] < min) {
                    min = ar[i];
                    index = i;
                }

            return index;
        }

        private static void swap(char[] ar, int i, int j) {
            char t = ar[i];
            ar[i] = ar[j];
            ar[j] = t;
        }

        private static boolean impossible(char[] ar) {
            char ref = ar[0];

            for (char ch : ar) {
                if (ref < ch)
                    return false;

                ref = ch;
            }

            return true;
        }
    }

    final static class MergeStrings {

        static String mergeStringFunc(String a, String b) {
            StringBuilder sb = new StringBuilder(a.length() + b.length());
            int index = 0;

            while (index < Math.max(a.length(), b.length())) {
                append(sb, a, index);
                append(sb, b, index);

                index++;
            }

            return sb.toString();
        }

        private static void append(StringBuilder sb, String s, int index) {
            if (index >= s.length())
                return;

            sb.append(s.charAt(index));
        }
    }
}
