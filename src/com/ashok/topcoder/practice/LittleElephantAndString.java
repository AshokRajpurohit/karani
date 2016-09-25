package com.ashok.topcoder.practice;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class LittleElephantAndString {
    public int getNumber(String a, String b) {
        if (impossible(a, b))
            return -1;

        return a.length();
    }

    private static boolean impossible(String a, String b) {
        if (a.length() != b.length())
            return false;

        int[] map = new int[256];

        for (int i = 0; i < a.length(); i++) {
            map[a.charAt(i)]++;
            map[b.charAt(i)]--;
        }

        for (int i = 'A'; i <= 'Z'; i++)
            if (map[i] != 0)
                return false;

        return true;
    }
}
