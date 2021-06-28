/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.qr21;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Comparator;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Round1A {
    private static final PrintWriter out = new PrintWriter(System.out);
    private static final InputReader in = new InputReader();
    private static final String CASE = "Case #";

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        for (int i = 1; i <= t; i++) {
            print(i, appendSort());
        }
    }

    private static String primeTime() throws IOException {
        int m = in.readInt();
        int[] primes = new int[m];
        long[] pcounts = new long[m];

        long sum = 0;
        for (int i = 0; i < m; i++) {
            primes[i] = in.readInt();
            pcounts[i] = in.readLong();
            sum += primes[i] * pcounts[i];
        }

        long res = calculate(primes, pcounts);

        return null;
    }

    private static long calculate(int[] primes, long[] counts) {
        return -1;
    }

    private static String appendSort() throws IOException {
        int n = in.readInt();
        int[] ar = in.readIntArray(n);
        int count = 0;
        Comparator<String> comparator = (a, b) -> {
            if (a.length() != b.length()) return a.length() - b.length();
            for (int i = 0; i < a.length(); i++) {
                if (a.charAt(i) != b.charAt(i)) {
                    return a.charAt(i) - b.charAt(i);
                }
            }
            return 0;
        };

        String ref = String.valueOf(ar[0]);
        for (int i = 1; i < n; i++) {
            String num = String.valueOf(ar[i]);
            int cmp = comparator.compare(num, ref);
            if (cmp == 0) {
                count++;
                ref = num + "0";
            } else if (cmp > 0) ref = num;
            else if (num.length() == ref.length()) {
                ref = num + "0";
                count++;
            } else {
                count +=  ref.length() - num.length();
                cmp = num.compareTo(ref.substring(0, num.length()));
                StringBuilder sb = new StringBuilder(num);
                if (cmp < 0) {
                    count++;
                    while(sb.length() <= ref.length()) sb.append('0');
                } else if (cmp > 0){
                    while(sb.length() < ref.length()) sb.append('0');
                } else {
                    String s1 = ref.substring(num.length());
                    boolean all9 = true;
                    for (char ch: s1.toCharArray()) all9 = all9 && (ch == '9');
                    if (all9) {
                        count++;
                        while(sb.length() <= ref.length()) sb.append('0');
                    } else {
                        String s2 = new BigInteger(s1).add(BigInteger.ONE).toString();
                        StringBuilder sb1 = new StringBuilder();
                        while(sb1.length() + s2.length() < s1.length()) sb1.append('0');
                        sb1.append(s2);
                        sb.append(sb1);
                    }
                }
                ref = sb.toString();
            }

        }
        return String.valueOf(count);
    }

    private static void print(int testNo, String result) {
        out.println(String.join(" ", CASE + testNo + ":", result));
    }

    private static class InputReader {
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;
        InputStream in;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

        public boolean hasNext() throws IOException {
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            return bufferSize != -1;
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

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }

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
