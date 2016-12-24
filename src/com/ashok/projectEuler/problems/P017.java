package com.ashok.projectEuler.problems;

import com.ashok.lang.problems.NumberInWords;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class P017 {
    public static void solve(int start, int end) {
        int count = 0;
        for (int i = start; i <= end; i++)
            count += getCharCount(i);

        System.out.println(count);
    }

    private static int getCharCount(int num) {
        String numString = NumberInWords.numberInWords(num);
        int count = 0;

        if (num % 100 != 0 && num > 100 && num < 1000) // extra 'and' in number.
            count = 3;

        for (int i = 0; i < numString.length(); i++)
            if (numString.charAt(i) != ' ')
                count++;

        return count;
    }
}
