/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.qr21;

import com.ashok.lang.utils.ArrayUtils;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class QualificationRound21 {
    private static final PrintWriter out = new PrintWriter(System.out);
    private static final InputReader in = new InputReader();
    private static final String CASE = "Case #";

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        medianSort();
//        int t = in.readInt();
//        for (int i = 1; i <= t; i++) {
//        }
    }

    private static void medianSort() throws IOException {
        int t = in.readInt(), n = in.readInt(), q = in.readInt();
        TestInteractiveRunner runner = new TestInteractiveRunner(q, null);
        for (int i = 0; i < t; i++) {
            int[] ar = IntStream.range(1, n + 1).toArray();
            ArrayUtils.randomizeArray(ar);
            runner = new TestInteractiveRunner(runner.queries, ar);
            int[] br = mergeSort(runner, 1, n);
            List<Integer> nums = Arrays.stream(br).mapToObj(v -> v).collect(Collectors.toList());

            boolean res = runner.submit(nums.stream().mapToInt(v -> v).toArray());
            out.println(nums + " | result: " + res);
            StringBuilder sb = new StringBuilder();
            for (int e : ar) sb.append(e).append(' ');
            out.println("original array: " + sb);
            out.flush();

            if (!res) {
                br = mergeSort(runner, 1, n);
            }
        }
    }

    private static int[] mergeSort(InteractiveRunner runner, int start, int end) {
        int n = end + 1 - start;
        ArrayList<Integer> nums = new ArrayList<>(n);
        if (n <= 100) {
            int m = runner.query(start, start + 1, start + 2);
            nums.add(m == start ? start + 1 : start);
            nums.add(m);
            nums.add(m == start + 2 ? start + 1 : start + 2);

            for (int x = start + 3; x <= end; x++) {
                int l = 0, r = nums.size() - 1;
                while (true) {
                    int left = nums.get(l), right = nums.get(r);
                    m = runner.query(left, x, right);
                    if (x == m) {
                        if (l == r - 1) {
                            nums.add(r, x);
                        } else {
                            l++;
                            r--;
                            if (r == l) r++;
                            continue;
                        }
                    } else if (m == left) {
                        nums.add(l, x);
                    } else nums.add(r + 1, x);
                    break;
                }
            }

            return nums.stream().mapToInt(v -> v).toArray();
        }

        int mid = (start + end) >>> 1;
        int[] left = mergeSort(runner, start, mid), right = mergeSort(runner, mid + 1, end);
        int m = 0;
        int li = 0, ri = 0;
        while(li < left.length - 1 && ri < right.length) {
            m = runner.query(left[li], left[li + 1], right[ri]);
            if (m == left[li]) {
                nums.add(right[ri++]);
            } else if (m == right[ri]) {
                nums.add(left[li++]);
            } else {
                nums.add(left[li++]);
                nums.add(left[li++]);
            }
        }

        if (li == left.length) {
            while(ri < right.length) nums.add(right[ri++]);
        } else if (ri == right.length) {
            while(li < left.length) nums.add(left[li++]);
        } else {
            while(li < left.length && ri < right.length - 1) {
                m = runner.query(left[li], right[ri], right[ri + 1]);
                if (m == right[ri]) {
                    nums.add(left[li++]);
                } else if (m == left[li]) {
                    nums.add(right[ri++]);
                } else {
                    nums.add(right[ri++]);
                    nums.add(right[ri++]);
                }
            }

            if (li == left.length && ri < right.length) nums.add(right[ri++]);
            else if (ri == right.length && li < left.length) nums.add(left[li++]);
            else if (li < left.length && ri < right.length) {
                int last = nums.get(nums.size() - 1);
                m = runner.query(last, left[li], right[ri]);

                if (m == left[li]) {
                    nums.add(left[li]);
                    nums.add(right[ri]);
                } else if (m == right[ri]) {
                    nums.add(right[ri]);
                    nums.add(left[li]);
                } else {
                    throw new RuntimeException("something terrible happened");
                }
            }
        }

        return nums.stream().mapToInt(v -> v).toArray();
    }

    final static class SystemInteractiveRunner implements InteractiveRunner {

        @Override
        public int query(int x, int y, int z) {
            out.println(x + " " + y + " " + z);
            out.flush();
            try {
                int m = in.readInt();
                return m;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return -1;
        }

        @Override
        public boolean submit(int[] ans) {
            StringBuilder sb = new StringBuilder();
            for (int e : ans) sb.append(e).append(' ');
            out.println(sb);
            out.flush();
            try {
                return 1 == in.readInt();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }
    }

    final static class TestInteractiveRunner implements InteractiveRunner {
        int queries = 0;
        final int[] ar;

        TestInteractiveRunner(final int maxQueries, final int[] ar) {
            queries = maxQueries;
            this.ar = ar;
        }

        @Override
        public int query(int x, int y, int z) {
            if (queries == 0) throw new RuntimeException("limit exceeded");
            queries--;
            int a = ar[x - 1], b = ar[y - 1], c = ar[z - 1];
            int min = Math.min(a, Math.min(b, c)), max = Math.max(a, Math.max(b, c));
            int med = a + b + c - min - max;
            return med == a ? x : (med == b ? y : z);
        }

        @Override
        public boolean submit(int[] ans) {
            if (ans.length != ar.length) return false;
            for (int i = 0; i < ans.length; i++) ans[i]--;

            for (int i = 1; i < ans.length - 1; i++) {
                int x = ans[i - 1], y = ans[i], z = ans[i + 1];
                if (ar[x] > ar[y] != ar[y] > ar[z]) return false;
            }

            return true;
        }
    }

    private interface InteractiveRunner {
        int query(int x, int y, int z);

        boolean submit(int[] ans);
    }

    private static String moonUmbrellas() throws IOException {
        final char moon = 'C', umb = 'J';
        int x = in.readInt(), y = in.readInt();
        char[] mural = in.read().toCharArray();
        if (mural.length < 2) return "0";
        int maxC = 0, maxJ = 0;
        boolean lastC = mural[0] != umb, lastJ = mural[0] != moon;
        for (int i = 1; i < mural.length; i++) {
            char ch = mural[i];
            if (ch == moon) {
                int tempC = lastC ? maxC : Integer.MAX_VALUE;
                if (lastJ) tempC = Math.min(maxJ + y, tempC);

                maxJ = 0;
                maxC = tempC;
                lastJ = false;
                lastC = true;
            } else if (ch == umb) {
                int tempJ = lastJ ? maxJ : Integer.MAX_VALUE;
                if (lastC) tempJ = Math.min(tempJ, maxC + x);

                maxC = 0;
                maxJ = tempJ;
                lastJ = true;
                lastC = false;
            } else {
                int tempJ = lastC ? maxC + x : Integer.MAX_VALUE;
                if (lastJ) tempJ = Math.min(tempJ, maxJ);

                int tempC = lastJ ? maxJ + y : Integer.MAX_VALUE;
                if (lastC) tempC = Math.min(tempC, maxC);

                lastC = true;
                lastJ = true;
                maxC = tempC;
                maxJ = tempJ;
            }
        }

        if (lastC && lastJ) return Math.min(maxC, maxJ) + "";
        return (lastC ? maxC : maxJ) + "";
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }

        public int[][] readIntTable(int m, int n) throws IOException {
            int[][] res = new int[m][];
            for (int i = 0; i < m; i++)
                res[i] = readIntArray(n);

            return res;
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
