package com.ashok.lang.dsa;

import java.util.Random;

/**
 * This class is to generate random Strings. Random class doesn't have
 * functions for random character, Strings, large numbers.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class RandomStrings extends Random {
    @SuppressWarnings("compatibility:-3752703102521334407")
    private static final long serialVersionUID = 7817998554436830797L;

    public RandomStrings() {
        super();
    }

    public RandomStrings(long seed) {
        super(seed);
    }

    public char nextChar() {
        return (char)(nextInt() & 255);
    }

    /**
     * Returns a random string of length n. It can consists all type of
     * characters without space characters.
     *
     * @param n Length of the Random String to be returned
     * @return Random String of length n
     */
    public String nextString(int n) {
        if (n < 1)
            throw new IllegalArgumentException("n must be positive: " + n);

        char[] ar = new char[n];
        for (int i = 0; i < n; i++) {
            char temp = nextChar();
            while (Character.isWhitespace(temp))
                temp = nextChar();
            ar[i] = temp;
        }
        return String.valueOf(ar);
    }

    public String nextBinaryString(int n) {
        if (n < 1)
            throw new IllegalArgumentException("n must be positive: " + n);

        boolean one = true;
        char[] ar = new char[n];
        for (int i = 0; i < n; i++) {
            if (one)
                ar[i] = '1';
            else
                ar[i] = '0';

            one = (nextInt() & 1) == 1;
        }

        return String.valueOf(ar);
    }

    /**
     * Returns a random string of length n consisting only Capital Letters.
     *
     * @param n Length of the random string to be returned
     * @return Random String of length n
     */
    public String nextStringABC(int n) {
        if (n < 1)
            throw new IllegalArgumentException("n must be positive: " + n);

        char[] ar = new char[n];
        for (int i = 0; i < n; i++)
            ar[i] = (char)(Math.abs(nextInt()) % 26 + 'A');

        return String.valueOf(ar);
    }

    /**
     * Returns a random string of length n consisting only english alphabets
     * in small cap.
     *
     * @param n
     * @return
     */

    public String nextStringabc(int n) {
        if (n < 1)
            throw new IllegalArgumentException("n must be positive: " + n);

        char[] ar = new char[n];
        for (int i = 0; i < n; i++)
            ar[i] = (char)(Math.abs(nextInt()) % 26 + 'a');

        return String.valueOf(ar);
    }

    /**
     * Returns random string of length consisting only english alphabets.
     * @param n
     * @return
     */

    public String nextStringAaBb(int n) {
        if (n < 1)
            throw new IllegalArgumentException("n must be positive: " + n);

        char[] ar = new char[n];
        for (int i = 0; i < n; i++) {
            int temp = nextInt() & 2047;
            if ((temp & 1) == 0)
                ar[i] = (char)(temp % 26 + 'a');
            else
                ar[i] = (char)(temp % 26 + 'A');
        }

        return String.valueOf(ar);
    }

    /**
     * Returns alphanumeric random string of length n.
     *
     * @param n
     * @return
     */

    public String nextStringAa1(int n) {
        if (n < 1)
            throw new IllegalArgumentException("n must be positive: " + n);

        char[] ar = new char[n];
        for (int i = 0; i < n; i++) {
            int temp = nextInt() & 2047;
            if (temp % 3 == 0)
                ar[i] = (char)(temp % 26 + 'a');
            else if (temp % 3 == 1)
                ar[i] = (char)(temp % 26 + 'A');
            else
                ar[i] = (char)(temp % 10 + '0');
        }

        return String.valueOf(ar);
    }

    /**
     * Returns non-negative number having n digits.
     *
     * @param n
     * @return
     */

    public String nextString123(int n) {
        if (n < 1)
            throw new IllegalArgumentException("n must be positive: " + n);

        StringBuilder sb = new StringBuilder(n);
        while (sb.length() < n)
            sb.append(Math.abs(nextLong()));

        return sb.substring(0, n);
    }
}
