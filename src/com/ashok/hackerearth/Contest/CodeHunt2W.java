package com.ashok.hackerearth.Contest;

import java.io.*;
import java.text.DecimalFormat;

/**
 * Problem Name: Snack Ruiner
 * Link: https://www.hackerearth.com/code-hunt-2w/algorithm/snack-ruiner/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class CodeHunt2W {
    private static Output out;
    private static CodeHunt2W.InputReader in;

    public static void main(String[] args) {
        CodeHunt2W.in = new CodeHunt2W.InputReader();
        CodeHunt2W.out = new Output();

        try {
            SnakeRuiner.solve();
        } catch (IOException ioe) {
            // do nothing
        }
        CodeHunt2W.out.close();
    }

    final static class MarioQueen {
        private static char[][] matrix;
        private static final char Mario = 'M', Queen = 'Q', Dragon = '$';
        private static void solve() throws IOException {
            int t = in.readInt();

            while(t > 0) {
                t--;
                int n = in.readInt();
                matrix = new char[n][];

                for (int i = 0; i < n; i++)
                    matrix[i] = in.read().toCharArray();























































































































































































































































































































































































































































































































































































































































































































































































































































































































            }
        }
    }

    final static class SnakeRuiner {
        private static void solve() throws IOException {
            int t = CodeHunt2W.in.readInt();
            DecimalFormat df = new DecimalFormat("#0.000");
            StringBuilder sb = new StringBuilder();

            while (t > 0) {
                t--;
                int n = CodeHunt2W.in.readInt();
                double[] d = CodeHunt2W.in.readDoubleArray(n), f = CodeHunt2W.in.readDoubleArray(n);
                double multiSum = 0.0, max = 0.0;
                int index = 0;

                for (int i = 0; i < n; i++) {
                    double prod = d[i] * f[i];
                    multiSum += prod;
                    if (prod > max) {
                        max = prod;
                        index = i;
                    }
                }

                double result = max / multiSum;
                sb.append(index).append(' ').append(df.format(result)).append('\n');
            }

            CodeHunt2W.out.print(sb);
        }
    }

    static final class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset;
        protected int bufferSize;

        public InputReader(InputStream input) {
            this.in = input;
        }

        public InputReader() {
            this.in = System.in;
        }

        public InputReader(String file) throws FileNotFoundException {
            this.in = new FileInputStream(file);
        }

        public void close() throws IOException {
            this.in.close();
        }

        public boolean hasNext() throws IOException {
            if (this.offset == this.bufferSize) {
                this.offset = 0;
                this.bufferSize = this.in.read(this.buffer);
            }

            return this.bufferSize != -1;
        }

        public boolean isNewLine() {
            return this.buffer[this.offset] == '\n' || this.buffer[this.offset] == '\r';
        }

        public char nextChar() throws IOException {
            if (this.offset == this.bufferSize) {
                this.offset = 0;
                this.bufferSize = this.in.read(this.buffer);
            }

            if (this.bufferSize == -1 || this.bufferSize == 0)
                throw new IOException("No new bytes");

            return (char) this.buffer[this.offset++];
        }

        public char nextValidChar() throws IOException {
            if (this.offset == this.bufferSize) {
                this.offset = 0;
                this.bufferSize = this.in.read(this.buffer);
            }

            if (this.bufferSize == -1 || this.bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 this.buffer[this.offset] == ' ' || this.buffer[this.offset] == '\t' || this.buffer[this.offset] ==
                         '\n' || this.buffer[this.offset] == '\r'; ++this.offset) {
                if (this.offset == this.bufferSize - 1) {
                    this.offset = -1;
                    this.bufferSize = this.in.read(this.buffer);
                }
            }

            return (char) this.buffer[this.offset++];

        }

        public int readInt() throws IOException {
            int number = 0;
            int s = 1;
            if (this.offset == this.bufferSize) {
                this.offset = 0;
                this.bufferSize = this.in.read(this.buffer);
            }
            if (this.bufferSize == -1)
                throw new IOException("No new bytes");
            for (; this.buffer[this.offset] < 0x30 || this.buffer[this.offset] == '-'; ++this.offset) {
                if (this.buffer[this.offset] == '-')
                    s = -1;
                if (this.offset == this.bufferSize - 1) {
                    this.offset = -1;
                    this.bufferSize = this.in.read(this.buffer);
                }
            }
            for (; this.offset < this.bufferSize && this.buffer[this.offset] > 0x2f; ++this.offset) {
                number = (number << 3) + (number << 1) + this.buffer[this.offset] - 0x30;
                if (this.offset == this.bufferSize - 1) {
                    this.offset = -1;
                    this.bufferSize = this.in.read(this.buffer);
                }
            }
            ++this.offset;
            return number * s;
        }

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = this.readInt();

            return ar;
        }

        public int[] readIntArray(int n, int d) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = this.readInt() + d;

            return ar;
        }

        public int[][] readIntTable(int m, int n) throws IOException {
            int[][] res = new int[m][];
            for (int i = 0; i < m; i++)
                res[i] = this.readIntArray(n);

            return res;
        }

        public int[][] readIntTable(int m, int n, int d) throws IOException {
            int[][] res = new int[m][];
            for (int i = 0; i < m; i++)
                res[i] = this.readIntArray(n, d);

            return res;
        }

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (this.offset == this.bufferSize) {
                this.offset = 0;
                this.bufferSize = this.in.read(this.buffer);
            }
            for (; this.buffer[this.offset] < 0x30 || this.buffer[this.offset] == '-'; ++this.offset) {
                if (this.buffer[this.offset] == '-')
                    s = -1;
                if (this.offset == this.bufferSize - 1) {
                    this.offset = -1;
                    this.bufferSize = this.in.read(this.buffer);
                }
            }
            for (; this.offset < this.bufferSize && this.buffer[this.offset] > 0x2f; ++this.offset) {
                res = (res << 3) + (res << 1) + this.buffer[this.offset] - 0x30;
                if (this.offset == this.bufferSize - 1) {
                    this.offset = -1;
                    this.bufferSize = this.in.read(this.buffer);
                }
            }
            ++this.offset;
            if (s == -1)
                res = -res;
            return res;
        }

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = this.readLong();

            return ar;
        }

        public long[] readLongArray(int n, long d) throws IOException {
            long[] ar = new long[n];
            for (int i = 0; i < n; i++)
                ar[i] = this.readLong() + d;

            return ar;
        }

        public String read() throws IOException {
            StringBuilder sb = new StringBuilder();
            if (this.offset == this.bufferSize) {
                this.offset = 0;
                this.bufferSize = this.in.read(this.buffer);
            }

            if (this.bufferSize == -1 || this.bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 this.buffer[this.offset] == ' ' || this.buffer[this.offset] == '\t' || this.buffer[this.offset] ==
                         '\n' || this.buffer[this.offset] == '\r'; ++this.offset) {
                if (this.offset == this.bufferSize - 1) {
                    this.offset = -1;
                    this.bufferSize = this.in.read(this.buffer);
                }
            }
            for (; this.offset < this.bufferSize; ++this.offset) {
                if (this.buffer[this.offset] == ' ' || this.buffer[this.offset] == '\t' ||
                        this.buffer[this.offset] == '\n' || this.buffer[this.offset] == '\r')
                    break;
                if (Character.isValidCodePoint(this.buffer[this.offset])) {
                    sb.appendCodePoint(this.buffer[this.offset]);
                }
                if (this.offset == this.bufferSize - 1) {
                    this.offset = -1;
                    this.bufferSize = this.in.read(this.buffer);
                }
            }
            return sb.toString();
        }

        public boolean isSpaceCharacter(char ch) {
            return ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n';
        }

        public String readLine() throws IOException {
            StringBuilder sb = new StringBuilder();
            if (this.offset == this.bufferSize) {
                this.offset = 0;
                this.bufferSize = this.in.read(this.buffer);
            }

            if (this.bufferSize == -1 || this.bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 this.buffer[this.offset] == ' ' || this.buffer[this.offset] == '\t' || this.buffer[this.offset] ==
                         '\n' || this.buffer[this.offset] == '\r'; ++this.offset) {
                if (this.offset == this.bufferSize - 1) {
                    this.offset = -1;
                    this.bufferSize = this.in.read(this.buffer);
                }
            }
            for (; this.offset < this.bufferSize; ++this.offset) {
                if (this.buffer[this.offset] == '\n' || this.buffer[this.offset] == '\r')
                    break;
                if (Character.isValidCodePoint(this.buffer[this.offset])) {
                    sb.appendCodePoint(this.buffer[this.offset]);
                }
                if (this.offset == this.bufferSize - 1) {
                    this.offset = -1;
                    this.bufferSize = this.in.read(this.buffer);
                }
            }
            return sb.toString();
        }

        public String readLines(int lines) throws IOException {
            StringBuilder sb = new StringBuilder();

            if (this.offset == this.bufferSize) {
                this.offset = 0;
                this.bufferSize = this.in.read(this.buffer);
            }

            if (this.bufferSize == -1 || this.bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 this.buffer[this.offset] == ' ' || this.buffer[this.offset] == '\t' || this.buffer[this.offset] ==
                         '\n' || this.buffer[this.offset] == '\r'; ++this.offset) {
                if (this.offset == this.bufferSize - 1) {
                    this.offset = -1;
                    this.bufferSize = this.in.read(this.buffer);
                }
            }

            for (; this.offset < this.bufferSize && lines > 0; ++this.offset) {
                if (this.buffer[this.offset] == '\n' || this.buffer[this.offset] == '\r')
                    lines--;

                if (Character.isValidCodePoint(this.buffer[this.offset])) {
                    sb.appendCodePoint(this.buffer[this.offset]);
                }

                if (this.offset == this.bufferSize - 1) {
                    this.offset = -1;
                    this.bufferSize = this.in.read(this.buffer);
                }
            }

            return sb.toString();
        }

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
            if (this.offset == this.bufferSize) {
                this.offset = 0;
                this.bufferSize = this.in.read(this.buffer);
            }

            if (this.bufferSize == -1 || this.bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 this.buffer[this.offset] == ' ' || this.buffer[this.offset] == '\t' || this.buffer[this.offset] ==
                         '\n' || this.buffer[this.offset] == '\r'; ++this.offset) {
                if (this.offset == this.bufferSize - 1) {
                    this.offset = -1;
                    this.bufferSize = this.in.read(this.buffer);
                }
            }
            for (int i = 0; this.offset < this.bufferSize && i < n; ++this.offset) {
                if (this.buffer[this.offset] == ' ' || this.buffer[this.offset] == '\t' ||
                        this.buffer[this.offset] == '\n' || this.buffer[this.offset] == '\r')
                    break;
                if (Character.isValidCodePoint(this.buffer[this.offset])) {
                    sb.appendCodePoint(this.buffer[this.offset]);
                }
                if (this.offset == this.bufferSize - 1) {
                    this.offset = -1;
                    this.bufferSize = this.in.read(this.buffer);
                }
            }
            return sb.toString();
        }

        public String[] readStringArray(int size) throws IOException {
            String[] res = new String[size];
            for (int i = 0; i < size; i++)
                res[i] = this.read();

            return res;
        }

        public String[] readStringArray(int size, int len) throws IOException {
            String[] res = new String[size];
            for (int i = 0; i < size; i++)
                res[i] = this.read(len);

            return res;
        }

        public double readDouble() throws IOException {
            return Double.parseDouble(this.read());
        }

        public double[] readDoubleArray(int n) throws IOException {
            double[] ar = new double[n];
            for (int i = 0; i < n; i++)
                ar[i] = this.readDouble();

            return ar;
        }
    }

    final static class Output extends PrintWriter {
        public Output() {
            super(System.out);
        }

        public Output(OutputStream outputStream) {
            super(outputStream);
        }

        public Output(String file) throws FileNotFoundException {
            super(new FileOutputStream(file));
        }

        public void print(int[] ar) {
            StringBuilder sb = new StringBuilder(ar.length << 1);
            for (int e : ar)
                sb.append(e).append(' ');

            println(sb.toString());
        }

        public void print(int[][] ar) {
            for (int[] e : ar)
                print(e);
        }

        public void print(long[] ar) {
            StringBuilder sb = new StringBuilder(ar.length << 1);
            for (long e : ar)
                sb.append(e).append(' ');

            println(sb.toString());
        }

        public void print(long[][] ar) {
            for (long[] e : ar)
                print(e);
        }

        public void print(String[] ar) {
            int size = 0;
            for (String e : ar)
                size += e.length() + 1;

            StringBuilder sb = new StringBuilder(size);

            for (String e : ar)
                sb.append(e).append('\n');

            print(sb.toString());
        }
    }

}
