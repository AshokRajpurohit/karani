/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.qr18;

import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Trouble Sort
 * Link:
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class TroubleSort {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
//        play();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        for (int i = 1; i <= t; i++) {
            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            int res = process(ar);
            out.println("Case #" + i + ": " + (res == -1 ? "OK" : res));
        }
    }

    private static int process(int[] ar) {
        troubleSort(ar);
        for (int i = 0; i < ar.length - 1; i++) {
            if (ar[i] > ar[i + 1])
                return i;
        }

        return -1;
    }

    private static void play() throws IOException {
        Output output = new Output();
        while (true) {
            int n = in.readInt();
            int[] ar = Generators.generateRandomIntegerArray(n, in.readInt(), in.readInt());
            int[] copy = ar.clone();
            output.print(ar);
            bruteForceSort(ar);
            output.print(ar);
            troubleSort(copy);
            output.print(copy);
            output.flush();
        }
    }

    private static void bruteForceSort(int[] ar) {
        boolean sorted = false;
        int len = ar.length;
        while (!sorted) {
            sorted = true;
            for (int i = 0, j = 2; j < len; i++, j++)
                if (ar[i] > ar[j]) {
                    sorted = false;
                    swap(ar, i, j);
                }
        }
    }

    private static void troubleSort(int[] ar) {
        int len = ar.length;
        int[] oddIndexNumbers = new int[len >>> 1], evenIndexNumbers = new int[(len + 1) >>> 1];
        for (int i = 0, j = 0; i < len; i += 2, j++)
            evenIndexNumbers[j] = ar[i];

        for (int i = 1, j = 0; i < len; i += 2, j++)
            oddIndexNumbers[j] = ar[i];

        Arrays.sort(oddIndexNumbers);
        Arrays.sort(evenIndexNumbers);
        for (int i = 0, j = 0; i < len; i += 2, j++)
            ar[i] = evenIndexNumbers[j];

        for (int i = 1, j = 0; i < len; i += 2, j++)
            ar[i] = oddIndexNumbers[j];
    }

    private static void swap(int[] ar, int a, int b) {
        int temp = ar[a];
        ar[a] = ar[b];
        ar[b] = temp;
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}