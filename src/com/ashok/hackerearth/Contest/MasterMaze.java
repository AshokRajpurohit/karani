package com.ashok.hackerearth.Contest;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: MasterMaze Problems
 * Link: https://www.hackerearth.com/hpemastermaze/problems/
 * Date: 15/07/2016
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MasterMaze {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int mod = 1000000007;

    public static void main(String[] args) throws IOException {
        MasterMaze a = new MasterMaze();
        a.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        SchedulingProblem.solve();
        out.flush();
    }

    final static class SchedulingProblem {
        static void solve() throws IOException {
            int n = in.readInt();
            Pair[] pairs = new Pair[n];

            for (int i = 0; i < n; i++)
                pairs[i] = new Pair(in.readInt(), in.readInt());

            Arrays.sort(pairs);
            long res = 0;
            int time = 0;

            for (Pair pair : pairs) {
                time += pair.t;
                res += pair.p * time;
                pair.t = time;
            }

            out.println(res);
        }

        static long valueOfPair(Pair pair) {
            return 1L * pair.t * pair.p;
        }
    }

    private static void swap(Object[] objects, int i, int j) {
        Object temp = objects[i];
        objects[i] = objects[j];
        objects[j] = temp;
    }

    final static class Pair implements Comparable<Pair> {
        int p, t;

        Pair(int p, int t) {
            this.p = p;
            this.t = t;
        }

        @Override
        public int compareTo(Pair pair) {
            return pair.p * this.t - this.p * pair.t;
        }
    }

    /**
     * Improper Input Validation
     */
    final static class InputValidation {
        static void solve() throws IOException {
            int t = in.readInt();
            StringBuilder sb = new StringBuilder(t << 5);
            String pass = "Transaction Successful ", fail = "Transaction Failed";

            while (t > 0) {
                t--;

                int balance = extractNumber(in.read()), price = extractNumber(in
                        .read()), number = extractNumber(in.read());

                long save = balance - 1L * price * number;
                if (save >= 0)
                    sb.append(pass).append(save).append('\n');
                else
                    sb.append(fail).append('\n');
            }

            out.print(sb);
        }

        static int extractNumber(String s) {
            int n = 0;

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) >= '0' && s.charAt(i) <= '9')
                    n = (n << 3) + (n << 1) + s.charAt(i) - '0';
            }

            return n;
        }
    }

    /**
     * Problem: Encryption Problem
     */
    final static class Encryption {
        static void solve() throws IOException {
            String s = in.read();
            StringBuilder sb = new StringBuilder(s.length());
            int[] map = new int[256];

            for (int i = 0; i < s.length(); i++)
                map[s.charAt(i)]++;

            for (int i = 0; i < s.length(); ) {
                char ch = s.charAt(i++);
                if (map[ch] == 0)
                    continue;

                sb.append(ch).append(map[ch]);
                map[ch] = 0;
            }

            out.print(sb);
        }
    }

    final static class EncodeDecode {
        static long[] fact = new long[1000000];

        static {
            fact[0] = 1;

            for (int i = 1; i < fact.length; i++)
                fact[i] = fact[i - 1] * i % mod;
        }

        static void solve() throws IOException {
            int t = in.readInt();
            StringBuilder sb = new StringBuilder(t << 3);
            while (t > 0) {
                t--;

                sb.append(process(in.readInt(), in.readInt())).append('\n');
            }

            out.print(sb);
        }

        static long process(int x, int y) {
            if (x == 0 || y == 0)
                return 0;

            if (x == 1 && y == 1)
                return 1;

            if (x == 1 || y == 1)
                return 2;

            return ncr(x + y - 2, y - 1) << 1;
        }

        static long ncr(int n, int r) {
            return (fact[n] * inverse(fact[r]) % mod) * inverse(fact[n - r]) %
                    mod;
        }

        static long inverse(long a) {
            a = a % mod;
            if (a < 0)
                a += mod;

            if (a == 1 || a == 0)
                return a;

            long b = mod - 2, r = Long.highestOneBit(b), res = a;

            while (r > 1) {
                r = r >> 1;
                res = (res * res) % mod;
                if ((b & r) != 0) {
                    res = (res * a) % mod;
                }
            }

            return res;
        }
    }

    /**
     * Problem: Count Bits
     */
    final static class CountBits {
        private static long[] bitValue = new long[64];

        static {
            long r = 1;
            bitValue[0] = r;

            for (int i = 1; i < 64; i++) {
                r = r << 1;
                bitValue[i] = r;
            }
        }

        static void solve() throws IOException {
            int t = in.readInt();
            StringBuilder sb = new StringBuilder(t << 3);

            while (t > 0) {
                t--;
                sb.append(countBits(in.readLong())).append('\n');
            }

            out.print(sb);
        }

        static long countBits(long n) {
            long res = 0;

            char[] buf = Long.toBinaryString(n).toCharArray();
            boolean odd = true;

            for (int i = 0, j = buf.length - 1; i < buf.length; i++, j--) {
                if (buf[i] == '1') {
                    if (odd) {
                        res += Math.max(0, (bitValue[j] >>> 1) - 1);
                    } else {
                        res += (bitValue[j] >>> 1) + 1;
                    }

                    odd = !odd;
                }
            }

            return res;
        }
    }

    /**
     * Problem: Xenny and Packet Routing
     */
    final static class Xenny {
        static int[] pow2 = new int[8];

        static {
            pow2[0] = 1;

            for (int i = 1; i < 8; i++)
                pow2[i] = pow2[i - 1] << 1;
        }

        static void solve() throws IOException {
            int n = in.readInt(), m = in.readInt();
            BinaryNode root = new BinaryNode();

            for (int i = 0; i < m; i++)
                addNode(root, getRouterBits(in.read()));

            StringBuilder sb = new StringBuilder(n << 2);
            for (int i = 0; i < n; i++)
                sb.append(matchingRouterLength(root, getPacketBits(in.read())))
                        .append('\n');

            out.print(sb);
        }

        static int matchingRouterLength(BinaryNode node, char[] bits) {
            int index = 0;

            while (index < bits.length && node != null) {
                if (bits[index] == BinaryNode.ZERO) {
                    node = node.zero;
                } else {
                    node = node.one;
                }

                index++;
            }

            return node == null ? index - 1 : index;
        }

        static void addNode(BinaryNode node, char[] bits) {
            for (int i = 0; i < bits.length; i++) {
                if (bits[i] == BinaryNode.ZERO) {
                    if (node.zero == null) {
                        node.zero = new BinaryNode();
                    }

                    node = node.zero;
                } else {
                    if (node.one == null)
                        node.one = new BinaryNode();

                    node = node.one;
                }
            }
        }

        static char[] getPacketBits(String address) {
            List<Integer> networkAddress = new LinkedList<>();
            int index = 0;

            while (index < address.length()) {
                int num = 0;
                while (index < address.length() && address.charAt(index) != '.') {
                    num = (num << 3) + (num << 1) + address.charAt(index) - '0';
                    index++;
                }

                networkAddress.add(num);
                index++;
            }

            StringBuilder networkBits = new StringBuilder(networkAddress.size
                    () << 3);

            for (Integer e : networkAddress)
                networkBits.append(toBinary(e));

            return networkBits.toString().toCharArray();
        }

        static char[] getRouterBits(String address) {
            List<Integer> networkAddress = new LinkedList<>();
            int index = 0;
            while (true) {
                int num = 0;
                while (address.charAt(index) != '.' && address.charAt(index) != '/') {
                    num = (num << 3) + (num << 1) + address.charAt(index) - '0';
                    index++;
                }

                networkAddress.add(num);

                if (address.charAt(index) == '/') {
                    index++;
                    break;
                }

                index++;
            }

            int significantBits = 0;
            while (index < address.length()) {
                significantBits = (significantBits << 3) + (significantBits
                        << 1) + address.charAt(index++) - '0';
            }

            StringBuilder networkBits = new StringBuilder(networkAddress.size
                    () << 3);

            for (Integer e : networkAddress)
                networkBits.append(toBinary(e));

            return networkBits.substring(0, significantBits).toCharArray();
        }

        private static char[] toBinary(int n) {
            char[] ar = new char[8];

            for (int i = 7; i >= 0; i--) {
                ar[7 - i] = n >= pow2[i] ? '1' : '0';
                n %= pow2[i];
            }

            return ar;
        }
    }

    final static class BinaryNode {
        final static char ZERO = '0';
        BinaryNode zero, one;
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

        public boolean hasNext() throws IOException {
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            return bufferSize != -1;
        }

        public boolean isNewLine() {
            return buffer[offset] == '\n' || buffer[offset] == '\r';
        }

        public char nextChar() throws IOException {
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            return (char) buffer[offset++];
        }

        public char nextValidChar() throws IOException {
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

            return (char) buffer[offset++];

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

        public int[] readIntArray(int n, int d) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt() + d;

            return ar;
        }

        public int[][] readIntTable(int m, int n) throws IOException {
            int[][] res = new int[m][];
            for (int i = 0; i < m; i++)
                res[i] = readIntArray(n);

            return res;
        }

        public int[][] readIntTable(int m, int n, int d) throws IOException {
            int[][] res = new int[m][];
            for (int i = 0; i < m; i++)
                res[i] = readIntArray(n, d);

            return res;
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

        public long[] readLongArray(int n, long d) throws IOException {
            long[] ar = new long[n];
            for (int i = 0; i < n; i++)
                ar[i] = readLong() + d;

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

        public boolean isSpaceCharacter(char ch) {
            return ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n';
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

        public String readLines(int lines) throws IOException {
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

            for (; offset < bufferSize && lines > 0; ++offset) {
                if (buffer[offset] == '\n' || buffer[offset] == '\r')
                    lines--;

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

        public String[] readStringArray(int size) throws IOException {
            String[] res = new String[size];
            for (int i = 0; i < size; i++)
                res[i] = read();

            return res;
        }

        public String[] readStringArray(int size, int len) throws IOException {
            String[] res = new String[size];
            for (int i = 0; i < size; i++)
                res[i] = read(len);

            return res;
        }

        public double readDouble() throws IOException {
            return Double.parseDouble(read());
        }

        public double[] readDoubleArray(int n) throws IOException {
            double[] ar = new double[n];
            for (int i = 0; i < n; i++)
                ar[i] = readDouble();

            return ar;
        }
    }
}
