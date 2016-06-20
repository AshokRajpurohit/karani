package com.ashok.codechef.marathon.year15.SEP15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Random;

/**
 * problem: Filling the maze
 * https://www.codechef.com/SEPT15/problems/THEGAME
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class THEGAME {

    private static PrintWriter out;
    private static InputStream in;
    private static double limit = 0.000001;

    private static String[] genMaze(int r, int c) {
        String[] ar = new String[r];
        char[] car = { 'o', '#' };
        Random random = new Random();
        for (int i = 0; i < r; i++) {
            StringBuilder sb = new StringBuilder(c);
            for (int j = 0; j < c; j++)
                sb.append(car[random.nextInt() & 1]);
            ar[i] = sb.toString();
        }

        return ar;
    }

    public static void main(String[] args) throws IOException, Exception {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        THEGAME a = new THEGAME();
        a.solve();
        out.close();
    }

    public void solve() throws IOException, Exception {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder();

        while (t > 0) {
            t--;
            int r = in.readInt();
            int c = in.readInt();
            String[] ar = new String[r];
            for (int i = 0; i < r; i++)
                ar[i] = in.read(c);

            sb.append(process(ar)).append('\n');
        }

        out.print(sb);

        while (true) {
            String[] ar = genMaze(in.readInt(), in.readInt());
            System.out.println(process(ar));
        }

    }

    private static double process(String[] ar) throws Exception {
        int[][] field = new int[ar.length][ar[0].length()];
        int count = 0;
        for (int i = 0; i < ar.length; i++)
            for (int j = 0; j < ar[0].length(); j++) {
                if (ar[i].charAt(j) != '#' && field[i][j] == 0) {
                    count++;
                    fill(ar, field, count, i, j);
                }
            }

        int[] counts = new int[count + 1];
        for (int i = 0; i < ar.length; i++)
            for (int j = 0; j < ar[0].length(); j++)
                if (ar[i].charAt(j) != '#')
                    counts[field[i][j]]++;

        return process(counts);
    }

    private static double process(int[] counts) throws Exception {
        //        if (counts.length > 11)
        //            throw new Exception("Not going to do it");

        if (counts[1] == 0)
            return 0.0;

        boolean[] check = new boolean[counts.length];
        for (int i = 0; i < check.length; i++)
            check[i] = true;

        int total = 0;
        for (int i = 1; i < counts.length; i++)
            total += counts[i];

        double res = counts[1] * 1.0 / total;

        for (int i = 2; i < counts.length; i++) {
            check[i] = false;
            res +=
getClicks(counts, check, 1, total - counts[i], counts[i] * 1.0 / total) *
 counts[i] / total;
            check[i] = true;
        }

        return res;
    }

    private static double getClicks(int[] counts, boolean[] check, int clicks,
                                    int total, double p) {
        if (p * (clicks + 1) <= limit)
            return clicks + 1.0;

        if (counts[1] == total)
            return clicks + 1.0;

        double res = counts[1] * 1.0 * (clicks + 1) / total;

        for (int i = 2; i < counts.length; i++)
            if (check[i]) {
                check[i] = false;
                res +=
getClicks(counts, check, clicks + 1, total - counts[i], p * counts[i] /
          total) * counts[i] / total;
                check[i] = true;
            }

        return res;
    }

    private static void fill(String[] ar, int[][] field, int count, int row,
                             int col) {
        if (row >= ar.length || row < 0 || col >= ar[0].length() || col < 0 ||
            ar[row].charAt(col) == '#' || field[row][col] != 0)
            return;

        field[row][col] = count;
        fill(ar, field, count, row + 1, col);
        fill(ar, field, count, row - 1, col);
        fill(ar, field, count, row, col + 1);
        fill(ar, field, count, row, col - 1);
    }

    private static double goEasy(int[] counts) {
        int res = 0, total = 0;
        for (int i = 0; i < counts.length; i++)
            total += counts[i];

        double p = counts[1] * 1.0 / total;
        double q = 1 - p;
        double temp = p;
        for (int i = 2; i < counts.length; i++) {
            temp *= q;
            res += temp * i;
        }
        return res + p;
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

        public int readInt() throws IOException {
            int number = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            if (bufferSize == -1)
                throw new IOException("No new bytes");
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                number = (number << 3) + (number << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            return number * s;
        }

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
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
            for (int i = 0; offset < bufferSize && i < n; ++offset) {
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
