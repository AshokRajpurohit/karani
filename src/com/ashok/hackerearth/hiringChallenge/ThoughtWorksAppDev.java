package com.ashok.hackerearth.hiringChallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Hiring Challenge: ThoughtWorks Application Developer Hiring Challenge
 * Date: 25-06-2016
 * https://www.hackerearth.com/thoughtworks-application-developer-hiring-challenge/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class ThoughtWorksAppDev {
    public static void main(String[] args) throws IOException {
//        while (true)
        GcdArray.solve();
    }

    static final class GcdArray {
        private static final int[][] map = new int[101][101];

        static {
            for (int i = 1; i <= 100; i++) {
                map[i][1] = 1;
                map[1][i] = 1;
            }

            for (int i = 2; i <= 100; i++)
                map[i][i] = i;

            for (int i = 2; i <= 100; i++)
                for (int j = i + 1; j <= 100; j++) {
                    map[i][j] = gcd(j, i);
                    map[j][i] = map[i][j];
                }
        }

        public static void solve() throws IOException {
            InputReader in = new InputReader();
            PrintWriter out = new PrintWriter(System.out);

            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            int[] gcdMap = new int[101], temp = new int[101];

            for (int i = 0; i < n; i++) {
                copy(gcdMap, temp);
                temp[ar[i]] = (temp[ar[i]] << 1) + 1;

                for (int j = 1; j <= 100; j++) {
                    if (gcdMap[j] != 0 && j != ar[i]) {
                        int g = map[j][ar[i]];
                        temp[g] += gcdMap[j];
                    }
                }

                copy(temp, gcdMap);
            }

            out.println(gcdMap[1]);
            out.close();
        }

        private static void copyAndClean(int[] from, int[] to) {
            copy(from, to);
            clean(from);
        }

        private static void clean(int[] ar) {
            for (int i = 0; i < ar.length; i++)
                ar[i] = 0;
        }

        private static void copy(int[] from, int[] to) {
            for (int i = 0; i < to.length; i++) {
                to[i] = from[i];
            }
        }

        private static int gcd(int a, int b) {
            if (b == 0)
                return a;

            return gcd(b, a % b);
        }
    }

    static final class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset;
        protected int bufferSize;

        public InputReader() {
            in = System.in;
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
