package com.ashok.friends.tridip;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

public class MoEngage {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
//        Walls.solve();
//        RapidTyping.solve();
        BeautifulStrings.solve();
        in.close();
        out.close();
    }

    private static void reverse(int[] ar) {
        int n = ar.length;
        for (int i = 0, j = n - 1; i < j; i++, j--) {
            int temp = ar[i];
            ar[i] = ar[j];
            ar[j] = temp;
        }
    }

    final static class Walls {
        private static void solve() throws IOException {
            int t = in.readInt();
            while (t > 0) {
                t--;
                int n = in.readInt();
                int[] ar = in.readIntArray(n);
                out.println(process(ar));
            }
        }

        private static long process(int[] ar) {
            int n = ar.length;
            if (n < 3)
                return 0;

            long max = 1L * (n - 3) * Math.min(ar[n - 1], ar[0]);
            for (int i = 0; i < ar.length; i++) {
                long limit = (max - 1) / ar[i];
                for (int j = n - 1; j > i + limit; j--) {
                    if (ar[j] >= ar[i]) {
                        max = Math.max(max, 1L * (j - i - 1) * ar[i]);
                        break;
                    }
                }
            }

            reverse(ar);
            for (int i = 0; i < ar.length; i++) {
                long limit = (max - 1) / ar[i];
                for (int j = n - 1; j > i + limit; j--) {
                    if (ar[j] >= ar[i]) {
                        max = Math.max(max, 1L * (j - i - 1) * ar[i]);
                        break;
                    }
                }
            }

            return max;
        }
    }

    final static class RapidTyping {
        final Point[] charToPoints = new Point[256];

        RapidTyping(String[] keys) {
            Arrays.fill(charToPoints, INVALID_POINT);
            int n = keys.length, m = keys[0].length();
            int x = 0;
            for (String row : keys) {
                int y = 0;
                char[] rowChars = row.toCharArray();
                for (char ch : rowChars)
                    charToPoints[ch] = new Point(x, y++);

                x++;
            }
        }

        private static void solve() throws IOException {
            int n = in.readInt(), m = in.readInt();
            String[] ar = in.readStringArray(n, m);
            RapidTyping typing = new RapidTyping(ar);
            out.println(typing.getTime(in.read().toCharArray()));
        }

        private int getTime(char[] chars) {
            if (!allExist(chars))
                return -1;

            int len = chars.length, distance = 0;
            Point cur = new Point(0, 0);
            for (char ch : chars) {
                Point point = charToPoints[ch];
                distance += distance(cur, point);
                cur = point;
            }

            return distance;
        }

        private boolean allExist(char[] chars) {
            for (char ch : chars)
                if (charToPoints[ch] == INVALID_POINT)
                    return false;

            return true;
        }

        private static int distance(Point a, Point b) {
            return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
        }
    }

    final static class BeautifulStrings {
        private static final int MOD = 1000000007;

        private static void solve() throws IOException {
            int t = in.readInt();
            while (t > 0) {
                t--;
                out.println(process(in.readInt()));
            }
        }

        private static long process(int n) {
            int[] ar = new int[]{n + 4, n + 1, n + 2, n + 3};
            for (int i = 4; i > 1; i--) {
                for (int j = 0; j < 4; j++) {
                    if (ar[j] % i == 0) {
                        ar[j] /= i;
                        break;
                    }
                }
            }

            long res = 1L;
            for (int e : ar)
                res = res * e % MOD;

            return res;
        }
    }

    private static final Point INVALID_POINT = new Point(-1, -1);

    final static class Point {
        final int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
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
    }
}