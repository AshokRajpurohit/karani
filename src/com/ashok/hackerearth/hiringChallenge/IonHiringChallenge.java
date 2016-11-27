package com.ashok.hackerearth.hiringChallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Problem Name: ION Core Java Hiring Challenge
 * Link: https://www.hackerearth.com/ion-core-java-hiring-challenge/problems
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class IonHiringChallenge {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        First.solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        while (true) {
            out.println(in.readInt());
            out.flush();
        }
    }

    final static class First {
        public static void solve() throws IOException {
            int n = in.readInt();
            Integer[] numbers = new Integer[n];
            for (int i = 0; i < n; i++)
                numbers[i] = in.readInt();

            Arrays.sort(numbers, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return Integer.compare(Math.abs(o1), Math.abs(o2));
                }
            });

            StringBuilder sb = new StringBuilder(n * 10);
            for (int e : numbers)
                sb.append(e).append(' ');

            out.println(sb);
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
    }
}
