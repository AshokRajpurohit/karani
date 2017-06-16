package com.ashok.friends.harsh;

import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;


public class AccelHack {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        MonkAndATM.test();
        StudiousAmitk.solve();
    }

    final static class StudiousAmitk {
        static LinkedList<Integer>[] list;
        static int nodes;

        private static void solve() throws IOException {
            int t = in.readInt();

            while (t > 0) {
                t--;

                int n = in.readInt(), m = in.readInt();
                list = getAdjacencyList(n);
                nodes = n;

                for (int i = 0; i < m; i++) {
                    list[in.readInt() - 1].add(in.readInt() - 1);
                }

                out.println(isCyclic() ? 0 : 1);
            }
        }

        private static boolean isCyclic() {
            boolean[] processing = new boolean[nodes], processed =
                    new boolean[nodes];
            for (int i = 0; i < nodes; i++) {
                if (!processing[i] && !processed[i]) {
                    processing[i] = true;
                    if (cyclic(i, processing, processed))
                        return true;

                    processed[i] = true;
                }
            }

            return false;
        }

        private static boolean cyclic(int n, boolean[] brown, boolean[] black) {
            if (list[n] == null)
                return false;

            for (Object o : list[n]) {
                if (brown[(Integer) o] && !black[(Integer) o])
                    return true;

                brown[(Integer) o] = true;
                if (cyclic((Integer) o, brown, black))
                    return true;

                black[(Integer) o] = true;
            }
            return false;
        }

        private static LinkedList<Integer>[] getAdjacencyList(int n) {
            LinkedList<Integer>[] list = new LinkedList[n];

            for (int i = 0; i < n; i++)
                list[i] = new LinkedList<>();

            return list;
        }
    }

    final static class MonkAndATM {
        private static int limit = 10000000;
        private static int[] factorCounts;
        private static int[] maxValues;

        static void initialize() {
            factorCounts = new int[limit + 1];
            maxValues = new int[limit + 1];

            Arrays.fill(factorCounts, 2);
            Arrays.fill(maxValues, 2);
            factorCounts[1] = 1;
            maxValues[1] = 1;

            int half = limit >>> 1;
            int root = (int) Math.sqrt(limit + 1);
            for (int i = 2; i <= root; i++) {
                int value = getValue(factorCounts[i]);
                maxValues[i] = Math.max(maxValues[i], value);
                value = maxValues[i];
                for (int j = i * i + i; j <= limit; j += i) {
                    factorCounts[j] += 2;
                    maxValues[j] = Math.max(maxValues[j], value);
                    maxValues[j] = Math.max(maxValues[j / i], maxValues[j]);
                }

                if (i * i <= limit) {
                    int j = i * i;
                    factorCounts[j]++;
                    maxValues[j] = Math.max(maxValues[j], value);
                }
            }

            for (int i = 4; i <= limit; i++)
                maxValues[i] = Math.max(maxValues[i], getValue(factorCounts[i]));
        }

        private static int getValue(int n) {
            if ((n & (n - 1)) == 0)
                return n;

            return 0;
        }

        private static void test() throws IOException {
            Output output = new Output();
            while (true) {
                limit = in.readInt();
                initialize();

                output.print(factorCounts);
                output.print(maxValues);
                output.flush();
            }
        }

        private static void solve() throws IOException {
            initialize();
            int t = in.readInt();
            StringBuilder sb = new StringBuilder(t << 3);

            while (t > 0) {
                t--;

//                sb.append(maxValues[in.readInt()]).append('\n');
                sb.append(Integer.highestOneBit(maxValues[in.readInt()])).append('\n');
            }

            out.print(sb);
        }
    }

    /**
     * Fast Input Reader copied from
     * https://github.com/AshokRajpurohit/karani/blob/master/src/com/ashok/lang/inputs/InputReader.java
     */
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
