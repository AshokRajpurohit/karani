package com.ashok.leetcode.practice;


import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name: Word Pattern
 * Link: https://leetcode.com/problems/word-pattern/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class WordPattern {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        WordPattern a = new WordPattern();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        while (true)
            System.out.println(wordPattern(in.read(), in.readLine()));
    }

    private boolean wordPattern(String pattern, String s) {
        int spaceCount = 0;
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == ' ')
                spaceCount++;

        if (spaceCount != pattern.length() - 1)
            return false;

        int[] ar = new int[pattern.length()];
        int i = 0;
        for (int j = 0, k = 0; k < s.length(); ) {
            while (k < s.length() && s.charAt(k) != ' ')
                k++;

            ar[i] = getHashCode(s, j, k);
            i++;
            j = k + 1;
            k++;
        }

        //        out.print(ar);
        //        out.flush();

        for (i = 1; i < pattern.length(); i++)
            for (int j = 0; j < i; j++) {
                if ((pattern.charAt(i) == pattern.charAt(j)) &&
                    (ar[i] != ar[j]))
                    return false;

                if ((pattern.charAt(i) != pattern.charAt(j)) &&
                    (ar[i] == ar[j]))
                    return false;
            }

        return true;
    }

    private static int getHashCode(String s, int start, int end) {
        long res = 0;
        for (int i = start; i < end; i++)
            res = ((res * 31) + s.charAt(i) - 'a') % 1000000007;

        return (int)res;
    }
}
