package com.ashok.projecteuler.problems;

import com.ashok.Params;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.LinkedList;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class P059 {
    public static void solve() throws IOException {
        Output sysout = new Output();
        long time = System.currentTimeMillis();
        InputReader input = new InputReader(Params.Path.property + "projecteuler\\problems\\P059.in");
        Output output = new Output(Params.Path.property + "projecteuler\\problems\\P059.out");
        LinkedList<Integer> list = new LinkedList<>();

        while (input.hasNext()) {
            list.add(input.readInt());
        }

        int[] key = new int[3];

        for (int i = 'a'; i <= 'z'; i++) {
            key[0] = i;
            for (int j = 'a'; j <= 'z'; j++) {
                key[1] = j;
                for (int k = 'a'; k <= 'z'; k++) {
                    key[2] = k;
                    String message = decrypt(list, key);
                    if (isSimpleText(message)) {
                        output.println(asciiSum(message) + ": " + message);
                    }
                }
            }
        }

        output.close();
        sysout.println("total time taken: " + (System.currentTimeMillis() - time));
        sysout.close();
    }

    private static boolean isSimpleText(String s) {
        for (int i = 0; i < s.length(); i++)
            if (!isCharSimpleText(s.charAt(i)))
                return false;

        return true;
    }

    private static boolean checkSyntax(String s) {
        for (int i = 0; i < s.length() - 1; i++)
            if (s.charAt(i) == '.' && s.charAt(i + 1) != ' ')
                return false;

        return true;
    }

    private static boolean isCharSimpleText(char ch) {
        return ch >= ' ' && ch <= 127;
    }

    private static String decrypt(int[] ar, int[] key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, j = 0; i < ar.length; i++, j++) {
            if (j == key.length)
                j = 0;

            sb.append((char) (ar[i] ^ key[j]));
        }

        return sb.toString();
    }

    private static String decrypt(LinkedList<Integer> list, int[] key) {
        StringBuilder sb = new StringBuilder();
        int j = 0;

        for (int e : list) {
            if (j == key.length)
                j = 0;

            sb.append((char) (e ^ key[j++]));
        }

        return sb.toString();
    }

    private static int asciiSum(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++)
            sum += s.charAt(i);

        return sum;
    }
}
