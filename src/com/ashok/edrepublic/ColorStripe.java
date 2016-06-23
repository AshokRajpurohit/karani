package com.ashok.edrepublic;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Paint Flag With Multiple Color Stripes
 */

public class ColorStripe {

    private static PrintWriter out;
    private static InputStream in;
    private int[][] strokes;
    private String stripe;
    private static int[] charIndex;

    static {
        charIndex = new int[256];
        for (int i = 'A'; i <= 'Z'; i++)
            charIndex[i] = i - 'A';
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        ColorStripe a = new ColorStripe();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        out.println(process(normalize(in.read())));
    }

    private int process(String s) {
        if (s.length() == 1)
            return 1;

        strokes = new int[s.length()][s.length()];
        stripe = s;

        for (int i = 0; i < s.length(); i++)
            for (int j = i + 1; j < s.length(); j++)
                strokes[i][j] = -1;

        for (int i = 0; i < s.length(); i++)
            strokes[i][i] = 1;

        return process(0, s.length() - 1);
    }

    /**
     * will update it later.
     * @param left
     * @param right
     * @return
     */
    private int process(int left, int right) {
        if (left < 0 || right >= stripe.length())
            return 0;

        if (strokes[left][right] != -1)
            return strokes[left][right];

        int count = countOfMax(stripe, left, right);

        int ans = right - left + 2 - count; // max possible value.
        System.out.println(left + "\t" + right + "\t" + ans + "\t" + count);

        if (stripe.charAt(left) == stripe.charAt(left + 1))
            ans = Math.min(ans, process(left + 1, right));
        else
            ans = Math.min(ans, process(left + 1, right) + 1);

        if (stripe.charAt(right) == stripe.charAt(right - 1))
            ans = Math.min(ans, process(left, right - 1));
        else
            ans = Math.min(ans, process(left, right - 1) + 1);

        if (right > left + 1) {
            if (stripe.charAt(left) == stripe.charAt(right) &&
                stripe.charAt(left + 1) == stripe.charAt(right - 1) &&
                stripe.charAt(left) == stripe.charAt(left + 1))
                ans = Math.min(ans, process(left + 1, right - 1));
            else if (stripe.charAt(left) == stripe.charAt(right))
                ans = Math.min(ans, process(left + 1, right - 1) + 1);
            else
                ans = Math.min(ans, process(left + 1, right - 1) + 2);
        }

        strokes[left][right] = ans;
        return ans;
    }

    /**
     * Returns the count of maximum time appearing color in the substring.
     * @param s
     * @param start
     * @param end
     * @return
     */
    private static int countOfMax(String s, int start, int end) {
        int[] ar = new int[26];
        for (int i = start; i <= end; i++)
            ar[charIndex[s.charAt(i)]]++;

        int max = 0;
        for (int i = 0; i < 26; i++)
            max = Math.max(max, ar[i]);

        return max;
    }

    /**
     * This function removes consecutive duplicate colors as the count doesn't
     * increase if consecutives duplicates are increased.
     * For example strokes for ABC is same as for AABBBCCC.
     * This function converts AABBCCC into ABC to reduce overhead calculations.
     * @param s
     * @return
     */
    private static String normalize(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append(s.charAt(0));
        for (int i = 1; i < s.length() - 1; i++) {
            if (s.charAt(i) != s.charAt(i - 1))
                sb.append(s.charAt(i));
        }
        if (s.charAt(s.length() - 1) != s.charAt(0))
            sb.append(s.charAt(s.length() - 1));

        return sb.toString();
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

        public String read() throws IOException {
            StringBuilder sb = new StringBuilder();
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                 '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                    buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }
    }
}
