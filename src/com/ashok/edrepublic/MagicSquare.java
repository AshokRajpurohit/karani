package com.ashok.edrepublic;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * EA Evaluation: Magic Squares
 *
 * Given a set of numbers, find how many ways there
 * are to build the Magic Square
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class MagicSquare {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] matrix;
    private static Integer[] numbers, freq;
    private static int sum, count;
    private static int n;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        MagicSquare a = new MagicSquare();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();

        out.println(solve(in.readIntArray(9)));
    }

    private static int solve(int[] ar) {
        int total = 0;
        for (int i = 0; i < 9; i++)
            total += ar[i];

        if (total % 3 != 0)
            return 0;

        n = 3;
        sum = total / n;

        populate(ar);

        return process();
    }

    private static int process() {
        count = 0;
        for (int i = 0; i < numbers.length; i++) {
            matrix[0] = numbers[i];
            freq[i]--;
            permute(1);
            freq[i]++;
        }

        return count;
    }

    private static void permute(int index) {
        if (index == 9) {
            if (check())
                count++;

            return;
        }

        if (index == 3) {
            if (!checkFirstRow())
                return;
        }

        if (index == 6) {
            if (!checkSecondRow()) {
                return;
            }
        }

        for (int i = 0; i < numbers.length; i++) {
            if (freq[i] > 0) {
                matrix[index] = numbers[i];
                freq[i]--;
                permute(index + 1);
                freq[i]++;
            }
        }
    }

    private static void populate(int[] ar) {
        Arrays.sort(ar);
        LinkedList<Integer> uniks = new LinkedList<Integer>(), fr =
            new LinkedList<Integer>();

        uniks.add(ar[0]);
        int temp = ar[0];
        for (int i = 0; i < 9; ) {
            int count = 0;
            while (i < 9 && ar[i] == temp) {
                i++;
                count++;
            }

            fr.add(count);
            if (i < 9) {
                temp = ar[i];
                uniks.add(temp);
            }
        }

        numbers = new Integer[uniks.size()];
        freq = new Integer[uniks.size()];

        matrix = new int[9];
        numbers = uniks.toArray(numbers);
        freq = fr.toArray(freq);
    }

    private static boolean checkFirstRow() {
        return matrix[0] + matrix[1] + matrix[2] == sum;
    }

    private static boolean checkSecondRow() {
        return matrix[3] + matrix[4] + matrix[5] == sum;
    }

    private static boolean checkRow(int index) {
        if (index % n != 0)
            return true;

        long local = 0;
        index -= n;
        for (int i = 0; i < n; i++, index++)
            local += matrix[index];

        return local == sum;
    }

    private static boolean check() {

        if (matrix[0] + matrix[1] + matrix[2] != sum)
            return false;

        if (matrix[3] + matrix[4] + matrix[5] != sum)
            return false;

        if (matrix[6] + matrix[7] + matrix[8] != sum)
            return false;

        if (matrix[0] + matrix[3] + matrix[6] != sum)
            return false;

        if (matrix[1] + matrix[4] + matrix[7] != sum)
            return false;

        if (matrix[2] + matrix[5] + matrix[8] != sum)
            return false;

        /*
        if (matrix[0] + matrix[4] + matrix[8] != sum)
            return false;

        if (matrix[2] + matrix[4] + matrix[6] != sum)
            return false;
        */

        //        print(matrix);
        return true;
    }

    private static void print(int[] ar) {
        StringBuilder sb = new StringBuilder(30);
        for (int e : ar)
            sb.append(e).append(", ");

        out.println(sb);
    }

    private static void print(Integer[] ar) {
        StringBuilder sb = new StringBuilder(30);
        for (Integer e : ar)
            sb.append(e).append(", ");

        out.println(sb);
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
            for (;
                 offset < bufferSize && buffer[offset] > 0x2f && buffer[offset] !=
                 ','; ++offset) {
                number = (number << 3) + (number << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            return number * s;
        }

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
