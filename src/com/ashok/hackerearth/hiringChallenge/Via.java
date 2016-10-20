package com.ashok.hackerearth.hiringChallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * Problem Name: Via.com Java Hiring Challenge
 * Link: https://www.hackerearth.com/viacom-java-hiring-challenge/problems
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Via {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        Second.solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        while (true) {
            out.println(in.readInt());
            out.flush();
        }
    }

    /**
     * Problem: Micro and his Son
     */
    final static class First {
        static int[] palindromCount = new int[2400];

        static {
            palindromCount[0] = 1;

            for (int i = 1; i < palindromCount.length; i++) {
                if (isPalindrom(i))
                    palindromCount[i] = palindromCount[i - 1] + 1;
                else
                    palindromCount[i] = palindromCount[i - 1];
            }
        }

        private static boolean isPalindrom(int n) {
            int minutes = n % 100;
            if (minutes > 59)
                return false;

            int hours = n / 100;

            if (hours / 10 == minutes % 10) {
                return hours % 10 == minutes / 10;
            }

            return false;
        }

        static void solve() throws IOException {
            int t = in.readInt();
            StringBuilder sb = new StringBuilder(t << 2);

            while (t > 0) {
                t--;

                sb.append(counts(in.readInt(), in.readInt())).append('\n');
            }

            out.print(sb);
        }

        private static int counts(int start, int end) {
            return counts(end) - counts(start - 1);
        }

        private static int counts(int time) {
            if (time < 0)
                return 0;

            return palindromCount[time];
        }


    }

    /**
     * Problem: Albums
     */
    final static class Second {
        private static int[] factors = new int[10001];
        private static boolean[] primes = new boolean[10001];
        private static LinkedList<Pair>[] factorList = new LinkedList[10001];
        private static int[] ar;
        private static final int bruteForceLimit = 6;
        private static Pair[][] factorGroups = new Pair[10001][];

        static {
            for (int i = 2; i <= 100; i++) {
                if (factors[i] != 0)
                    continue;

                for (int j = i; j < factors.length; j += i)
                    factors[j] = i;

                primes[i] = true;
            }

            for (int i = 101; i < factors.length; i++)
                if (factors[i] == 0) {
                    factors[i] = i;
                    primes[i] = true;
                }
        }

        static void solve() throws IOException {
            int n = in.readInt();
            ar = in.readIntArray(n);
            int q = in.readInt();
            StringBuilder sb = new StringBuilder(q << 2);
            process();

            while (q > 0) {
                q--;

                sb.append(query(in.readInt() - 1, in.readInt() - 1, in.readInt()));
                sb.append('\n');
            }

            out.print(sb);
        }

        private static int query(int L, int R, int k) {
            if (k == 1)
                return R + 1 - L;

            if (R + 1 - L <= bruteForceLimit) {
                int count = 0;

                for (int i = L; i <= R; i++)
                    if (ar[i] % k == 0)
                        count++;

                return count;
            }

            int factor = factors[k];

            Pair[] pairs = factorGroups[factor];
            if (pairs == null)
                return 0;

            int count = 0;
            for (int i = getIndex(pairs, L); pairs[i].index <= R; i++)
                if (k % pairs[i].value == 0)
                    count++;

            return count;
        }

        private static int getIndex(Pair[] pairs, int arrayIndex) {
            if (arrayIndex == pairs[0].index)
                return 0;

            int start = 0, end = pairs.length - 1;
            int mid = (start + end) >>> 1;

            while (mid != start) {
                if (pairs[mid].index == arrayIndex)
                    return mid;

                if (pairs[mid].index > arrayIndex)
                    end = mid;
                else
                    start = mid;

                mid = (start + end) >>> 1;
            }

            return pairs[mid].index == arrayIndex ? mid : end;
        }

        private static void process() {
            Pair[] pairs = new Pair[ar.length];

            for (int i = 0; i < ar.length; i++)
                pairs[i] = new Pair(i, ar[i]);

            for (int i = 0; i < ar.length; i++) {
                int v = ar[i];

                while (v != 1) {
                    int factor = factors[v];
                    ensure(factor);
                    factorList[factor].addLast(pairs[i]);
                    v /= factor;
                }
            }

            for (int i = 2; i < 10001; i++) {
                if (factorList[i] != null) {
                    Pair[] pairs1 = new Pair[factorList[i].size()];
                    int index = 0;

                    for (Pair pair : factorList[i])
                        pairs1[index++] = pair;

                }
            }
        }

        private static void ensure(int index) {
            if (factorList[index] == null)
                factorList[index] = new LinkedList<>();
        }
    }

    final static class Pair {
        int index, value;

        Pair(int i, int v) {
            index = i;
            value = v;
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
