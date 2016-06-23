package com.ashok.lang.problems;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name: Write Numbers in Word format
 * Link: Standard Problem
 *
 * Example: 123 is One Hundred Twenty Three.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class NumberInWords {
    private static Output out;
    private static InputReader in;
    private final static String[] firstTwenty =
    { "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight",
      "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen",
      "Sixteen", "Seventeen", "Eighteen", "Nineteen" }, TENS =
    { "Zero", "Ten", "Twenty", "Thirty", "Fourty", "Fifty", "Sixty", "Seventy",
      "Eighty", "Ninety" };

    private final static String[] numbers =
    { "Trillion", "Billion", "Million", "Thousand", "Hundred" };

    private final static long[] bases =
    { Long.valueOf("1000000000000"), 1000000000, 1000000, 1000, 100 };

    private NumberInWords() {
        // nothing.
    }

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        NumberInWords a = new NumberInWords();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        while (true) {
            out.println(numberInWords(in.readLong()));
            out.flush();
        }
    }

    public static String numberInWords(long n) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bases.length; i++) {
            long count = n / bases[i];
            n %= bases[i];

            if (count > 0) {
                sb.append(numberInWords(count)).append(' ').append(numbers[i]).append(' ');
            }
        }

        if (n == 0)
            return sb.toString();

        if (n < 20)
            sb.append(firstTwenty[(int)n]);
        else {
            long count = n / 10;
            sb.append(TENS[(int)count]).append(' ');

            n %= 10;

            if (n > 0)
                sb.append(firstTwenty[(int)n]);
        }

        return sb.toString();
    }
}
