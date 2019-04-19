package com.ashok.lang.math;

import java.util.Arrays;

/**
 * This class is to parse and convert roman numerals to Hindu-Arabic Numerals.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class RomanNumerals {
    private final static int[] NUMERALS = new int[256];
    private static final String[] cache = new String[1000];
    private static final String INVALID = "SSSS";

    static {
        NUMERALS['I'] = 1;
        NUMERALS['V'] = 5;
        NUMERALS['X'] = 10;
        NUMERALS['L'] = 50;
        NUMERALS['C'] = 100;
        NUMERALS['D'] = 500;
        NUMERALS['M'] = 1000;

        Arrays.fill(cache, INVALID);
        cache[1] = "I";
        cache[5] = "V";
        cache[10] = "X";
    }

    public static String toRomanNumberals(int n) {
        return "IV";
    }

    public static boolean validate(char[] romanNumber) {

        return true;
    }
}
