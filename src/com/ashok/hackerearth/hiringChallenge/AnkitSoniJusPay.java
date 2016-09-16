package com.ashok.hackerearth.hiringChallenge;

import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Problem Name: JusPay
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AnkitSoniJusPay {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        test(in.readInt(), in.readInt());

        in.close();
        out.close();
    }

    private static void test(int testCount, int max) throws IOException {
        Random random = new Random();
        while (testCount > 0) {
            testCount--;

            int[] ar = Generators.generateRandomIntegerArray(max, max + 1);

            for (int i = 0; i < ar.length; i++)
                ar[i] -= 1;

            int a = random.nextInt(ar.length), b = random.nextInt(ar.length);
            List<Integer> list = new LinkedList<>();
            for (int e : ar)
                list.add(e);

            long time = System.currentTimeMillis();
            int x = Fourth.solve(ar, a, b, ar.length);
            System.out.println("ashok " + x);
            System.out.println(System.currentTimeMillis() - time);

            time = System.currentTimeMillis();
            int y = Ankit.solve(list, a, b, ar.length);
            System.out.println("ankit " + y);
            System.out.println(System.currentTimeMillis() - time);
        }
    }

    private void solve() throws IOException {
        while (true) {
            out.println(in.readLine());
            out.flush();
        }
    }

    final static class Second {
        private static void solve() throws IOException {
            int n = in.readInt();
            int[] cells = in.readIntArray(n);

            if (n == 1) {
                out.println(-1);
                return;
            }

            boolean[] check = new boolean[n];
            int index = 0, lenth = -1;

            while (index < n) {
                while (index < n && cells[index] != -1)
                    index++;

                if (index == n)
                    break;

                check[index] = true;
                int e = cells[index];
                while (e != -1 && !check[e]) {
                    check[e] = true;
                    e = cells[e];
                }

                if (e == -1)
                    continue;

                int len = 1;
                int eb = e;
                e = cells[e];
                while (e != eb) {
                    e = cells[e];
                    len++;
                }

                lenth = Math.max(len, lenth);
            }

            out.println(lenth);
        }
    }

    final static class Fourth {
        private static int solve(int[] cells, int a, int b, int n) throws
                IOException {
            if (cells[a] == -1 && cells[b] == -1) {
                out.println(-1);
                return -1;
            }

            int[] ara = new int[n], arb = new int[n];
            for (int i = 0; i < n; i++)
                ara[i] = -1;

            Arrays.fill(ara, -1);
            Arrays.fill(arb, -1);
            ara[a] = 0;
            arb[b] = 0;

            int e = cells[a];
            boolean[] check = new boolean[n];
            check[a] = true;
            int d = 1;
            while (e != -1 && !check[e]) {
                ara[e] = d++;
                check[e] = true;
                e = cells[e];
            }

            d = 1;
            check = new boolean[n];
            check[b] = true;
            e = cells[b];

            while (e != -1 && !check[e]) {
                arb[e] = d++;
                check[e] = true;
                e = cells[e];
            }

            int min = n + 1, index = 0;
            for (int i = 0; i < n; i++) {
                if (ara[i] == -1 || arb[i] == -1)
                    continue;

                if (min > ara[i] + arb[i]) {
                    index = i;
                    min = ara[i] + arb[i];
                }
            }

            return index;
        }
    }

    final static class Third {
        private static void solve() throws IOException {
            int n = in.readInt();
            int[] cells = in.readIntArray(n);

            for (int i = 0; i < n; i++)
                cells[i] = cells[i] == -1 ? cells[i] : cells[i] - 1;

            int[] entryCount = new int[n];
            for (int e : cells)
                if (e != -1)
                    entryCount[e]++;

            int max = 0, index = 0;
            for (int i = 0; i < n; i++) {
                if (entryCount[i] > max) {
                    max = entryCount[i];
                    index = i;
                }
            }

            out.println(max);

        }
    }

    final static class Ankit {
        static int process(int[] ar, int a, int b) {
            List<Integer> list = new LinkedList<>();
            for (int e : ar)
                list.add(e);

            return solve(list, a, b, list.size());
        }

        static int solve(List<Integer> list, int q1, int q2, int N) {
            if (q1 == q2) {
                return q1;
            }

            List<Integer> x = new ArrayList<Integer>();
            List<Integer> y = new ArrayList<Integer>();
            x.add(q1);
            y.add(q2);
            boolean f1 = false, f2 = false;
            for (int i = q1, j = q2; i < N; ) {
                if (!f1 && list.get(i) != -1 && q1 != list.get(i) && i != list
                        .get(i) && !x.contains(list.get(i))) {
                    x.add(list.get(i));
                    i = list.get(i);
                } else {
                    f1 = true;
                }
                if (!f2 && list.get(j) != -1 && q2 != list.get(j) && j != list
                        .get(j) && !y.contains(list.get(j))) {
                    y.add(list.get(j));
                    j = list.get(j);
                } else {
                    f2 = true;
                }
                for (int k = 0; k < x.size(); k++) {
                    if (y.contains(x.get(k)))
                        return x.get(k);
                }
                if (f1 && f2) {
                    return -1;
                }
            }
            return -1;
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
    }
}
