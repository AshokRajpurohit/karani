/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.vasantha;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Vasantha
 * Link: Invite Only
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Ezetap {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        ConcatenateCharacters.solve();
        DivisibilityCheck.solve();
        in.close();
        out.close();
    }

    final static class ConcatenateCharacters {
        private static void solve() throws IOException {
            int n = in.readInt();
            StringBuilder sb = new StringBuilder();
            String[] ar = in.readStringArray(n);
            for (String e : ar)
                sb.append(e);

            out.println(sb);
        }
    }

    final static class DivisibilityCheck {
        private static void solve() throws IOException {
            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            int res = process(n, ar);
            out.println(res);
            out.flush();
        }

        private static int process(int n, int[] ar) {
            Arrays.sort(ar);
            int[] uniqueNumbers = toArray(getUniqueNumbers(ar));
            int[] duplicates = toArray(getDuplicates(ar));
            int count = n - uniqueNumbers.length;

            return count + processUniques(uniqueNumbers, duplicates);
        }

        private static int processUniques(int[] ar, int[] duplicates) {
            int len = ar.length;
            int max = ar[len - 1];
            boolean[] map = new boolean[max + 1];
            int limit = 1 + (int) (Math.sqrt(max));
            for (int e : duplicates)
                map[e] = true;

            for (int base = 0; base < len; base++) {
                int e = ar[base];
                int f = e << 1;
                while (f <= max) {
                    map[f] = true;
                    f += e;
                }
            }

            return count(map, true);
        }

        private static int count(boolean[] ar, boolean value) {
            int count = 0;
            for (boolean v : ar)
                if (v == value) count++;

            return count;
        }

        private static List<Integer> getDuplicates(int[] ar) {
            List<Integer> list = new LinkedList<>();
            int prev = ar[0] - 1;
            boolean first = true;
            for (int e : ar) {
                if (e == prev) {
                    if (first) list.add(e);
                    first = false;
                } else {
                    first = true;
                }

                prev = e;
            }

            return list;
        }

        private static List<Integer> getUniqueNumbers(int[] ar) {
            List<Integer> list = new LinkedList<>();
            int prev = ar[0] - 1;
            for (int e : ar) {
                if (e != prev)
                    list.add(e);

                prev = e;
            }

            return list;
        }

        public static int[] toArray(Iterable<Integer> list) {
            int size = 0;
            for (Integer e : list)
                size++;

            int[] ar = new int[size];
            int index = 0;

            for (Integer e : list)
                ar[index++] = e;

            return ar;
        }

        private static int bruteForce(int[] ar) {
            int n = ar.length, count = 0;
            for (int i = 0; i < n; i++)
                for (int j = i + 1; j < n; j++)
                    if (ar[j] % ar[i] == 0)
                        count++;

            return count;
        }

        private static int countDuplicates(int[] ar) {
            int prev = ar[0] - 1, count = 0;
            boolean first = true;
            for (int e : ar) {
                if (e == prev) {
                    if (first) count++;
                    first = false;
                } else {
                    first = true;
                }

                prev = e;
            }

            return count;
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

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = readLong();

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

        public String[] readStringArray(int size, int len) throws IOException {
            String[] res = new String[size];
            for (int i = 0; i < size; i++)
                res[i] = read(len);

            return res;
        }

        public String[] readStringArray(int len) throws IOException {
            String[] ar = new String[len];
            for (int i = 0; i < len; i++)
                ar[i] = read();

            return ar;
        }

    }
}