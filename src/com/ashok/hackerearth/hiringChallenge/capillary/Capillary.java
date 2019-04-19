/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.hiringChallenge.capillary;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Capillary Java Hiring Challenge
 * Link: https://www.hackerearth.com/challenge/hiring/capillary-java-hiring-challenge/problems
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Capillary {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
//        MaximumSum.solve();
        UploadServer.solve();
        in.close();
        out.close();
    }


    private static long getUniqueElementSum(long[] ar) {
        Arrays.sort(ar);
        long sum = ar[0];
        for (int i = 1; i < ar.length; i++) {
            if (ar[i] != ar[i - 1]) sum += ar[i];
        }

        return sum;
    }

    final static class MaximumSum {
        private static void solve() throws IOException {
            int n = in.readInt();
            out.println(process(in.readIntArray(n)));
        }

        private static long process(int[] ar) {
            int len = ar.length;
            if (len == 1) return ar[0];
            CustomArray array = new CustomArray(len * len);
            for (int i = 0; i < len; i++) {
                long sum = 0, max = ar[i];
                for (int j = i; j < len; j++) {
                    if (sum < 0) sum = 0;
                    sum += ar[j];
                    max = Math.max(sum, max);
                    array.add(max);
                }
            }

            return getUniqueElementSum(array.array);
        }
    }

    final static class UploadServer {
        private static final String MUSIC = "M\n", VIDEO = "V\n", NONE = "N\n", IGNORE = "";

        private static void solve() throws IOException {
            int n = in.readInt();
            StringBuilder sb = new StringBuilder(n << 1);
            while (n > 0) {
                n--;
                String[] params = in.readLine().split("\\s+");
                sb.append(findRequestType(params));
            }

            out.print(sb);
        }

        private static String findRequestType(String[] params) {
            switch (params.length) {
                case 2:
                    return validateName(params[0]) && isNumber(params[1]) ? MUSIC : NONE;
                case 3:
                    return validateName(params[0]) && isNumber(params[1]) && isNumber(params[2]) ? VIDEO : NONE;
                default:
                    return NONE;
            }
        }

        private static boolean validateName(String name) {

            boolean res = name.matches("^[a-zA-z0-9]+$") && name.matches(".*[a-zA-Z]+.*");
            for (int i = 0; i < name.length(); i++) {
                char ch = name.charAt(i);
                if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) return true;
            }
            return false;
        }

        private static boolean isNumber(String num) {
            if (num.charAt(0) == '0') return false;
            boolean res = num.matches("^[0-9]+$");
            return res;

            /*for (int i = 1; i < num.length(); i++) {
                char ch = num.charAt(i);
                if (ch < '0' || ch > '9') return false;
            }
            return true;*/
        }
    }

    final static class CustomArray {
        final long[] array;
        final int capacity;
        private int index;

        CustomArray(int capacity) {
            array = new long[capacity];
            this.capacity = capacity;
        }

        void add(long v) {
            array[index++] = v;
        }

        public boolean isEmpty() {
            return index == 0;
        }

        public void reset() {
            index = 0;
        }
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

        public String readLine() throws IOException {
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
                if (buffer[offset] == '\n' || buffer[offset] == '\r')
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