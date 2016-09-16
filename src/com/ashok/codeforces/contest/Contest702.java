package com.ashok.codeforces.contest;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Contest ID: 702
 * Description: Educational Codeforces Round 15
 * Link: http://codeforces.com/contests/702
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Contest702 {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solveD();
        in.close();
        out.close();
    }

    private static void solveD() throws IOException {
        long d = in.readLong();
        int k = in.readInt(), a = in.readInt(), b = in.readInt(), t = in
                .readInt();

        long minTime = Math.min(d * b, ((d - 1) / t) * t + d * k);
        long deno = t + 1L * k * (a - b);
        long repairCount = 1L * k * (b - a);

        if (deno <= 0)
            repairCount = (d - 1) / k;

        repairCount = Math.min((d - 1) / k, repairCount);
        repairCount = Math.max(0, repairCount);

        long carDistance = (repairCount + 1) * k * a;

        if (carDistance > d)
            repairCount--;

        while (repairCount >= 0) {
            long carTime = repairCount * t + (repairCount + 1) * k * a;
            long distance = d - (repairCount + 1) * k * a;

            long total = carTime + distance * b;

            if (total >= minTime)
                break;

            minTime = total;
            repairCount--;
        }

        out.println(minTime);
    }

    private static void solveC() throws IOException {
        int n = in.readInt(), m = in.readInt();
        int[] cities = in.readIntArray(n), towers = in.readIntArray(m);

        int maxDistance = 0, dD = 2000000000;
        int ts = 0, te = 0;
        for (int i = 0; i < n; i++) {
            while (ts < m && towers[ts] < cities[i])
                ts++;

            if (ts > 0 && (ts == m || towers[ts] > cities[i]))
                ts--;

            while (te < m && towers[te] < cities[i])
                te++;

            int left = ts < m ? cities[i] - towers[ts] : dD, right = te < m ?
                    towers[te] -
                            cities[i] : dD;

            maxDistance = Math.max(maxDistance, Math.min(left >= 0 ? left :
                    dD, right));
        }

        out.println(maxDistance);
    }

    private static void solveA() throws IOException {
        int len = 1;
        int n = in.readInt();
        int[] ar = in.readIntArray(n);

        if (n == 1) {
            out.println(1);
            return;
        }

        int current = 1;
        for (int i = 1; i < n; i++) {
            if (ar[i] > ar[i - 1])
                current++;
            else {
                len = Math.max(len, current);
                current = 1;
            }
        }

        out.println(len > current ? len : current);

    }

    private static void solveB() throws IOException {
        int n = in.readInt();
        int[] ar = in.readIntArray(n);
        Arrays.sort(ar);

        if (n == 1) {
            out.println(0);
            return;
        }

        if (ar[0] == ar[n - 1]) {
            if (powerOfTwo(ar[0])) {
                out.println(1L * n * (n - 1) / 2);
            } else {
                out.println(0);
            }

            return;
        }

        long res = 0;
        for (int i = 1; i < n; i++) {
            res += count(ar, higherPower(ar[i]) - ar[i], 0, i - 1);
        }

        out.println(res);
    }

    private static boolean powerOfTwo(int n) {
        return n == 0 || (n & (n - 1)) == 0;
    }

    private static int higherPower(int n) {
        if (powerOfTwo(n))
            return n << 1;

        int r = 1;
        while (r < n)
            r = r << 1;

        return r;
    }

    private static int count(int[] ar, int n, final int s, final int e) {
        int start = s, end = e;

        if (n < ar[start])
            return 0;

        int mid = (start + end) >>> 1;

        while (mid > start) {
            if (ar[mid] < n)
                start = mid;
            else
                end = mid;

            mid = (start + end) >>> 1;
        }

        int count = 0;
        while (start <= e && ar[start] <= n) {
            if (ar[start++] == n)
                count++;
        }

        return count;
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
