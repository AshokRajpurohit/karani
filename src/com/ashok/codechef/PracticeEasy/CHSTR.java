package com.ashok.codechef.PracticeEasy;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Chef and String
 * Link: https://www.codechef.com/problems/CHSTR
 */

public class CHSTR {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007;
    private static long[] fact = new long[5001];

    static {
        fact[0] = 1;
        for (int i = 1; i < 5001; i++)
            fact[i] = fact[i - 1] * i % mod;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CHSTR a = new CHSTR();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] ar = new int[n + 1];
            int q = in.readInt();
            StringBuilder sb = new StringBuilder(q);
            String s = in.read(n);
            TST tst = new TST(s);
            process(tst, s);
            if (n > 0) {
                System.out.println("le");
                return;
            }
            tst.traverse(ar);

            while (q > 0) {
                q--;
                sb.append(getNumber(ar, in.readInt())).append('\n');
            }
            out.print(sb);
        }
    }

    private static long getNumber(int[] ar, int n) {
        long res = 0;
        for (int i = n; i < ar.length; i++)
            res += ar[i] * nc(i, n);

        return res % mod;
    }

    private static long nc(int n, int r) {
        if (n == r)
            return 1;

        return (fact[n] * pow(fact[r], mod - 2) % mod) *
            pow(fact[n - r], mod - 2) % mod;
    }

    private static long pow(long a, int b) {
        int r = Integer.highestOneBit(b);
        long res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res * a) % mod;
            }
        }
        return res;
    }

    private static void process(TST tst, String s) {
        for (int i = 1; i < s.length(); i++)
            tst.add(s, i);
    }

    final static class TST {
        private TST left, right, equal;
        //        private boolean end = false;
        private char ch;
        private int count = 0;

        public TST(String s) {
            this(s, 0);
        }

        /**
         * initializes the instance.
         * @param s String s you want to initialize the instance.
         * @param pos   start pos in string s to be inserted in instance.
         */
        private TST(String s, int pos) {
            this.ch = s.charAt(pos);
            count = 1;

            //            return;

            if (pos != s.length() - 1)
                this.equal = new TST(s, pos + 1);

            return;
            /*
            if (pos == s.length())
                return;

            if (ch == null) {
                ch = s.charAt(pos);
                count = 1;
                if (pos < s.length() - 1)
                    equal = new TST(s, pos + 1);
                return;
            } else {
                if (ch < s.charAt(pos)) {
                    if (right == null)
                        right = new TST(s, pos);
                    else
                        right.add(s, pos);
                    return;
                } else if (ch == s.charAt(pos)) {
                    count++;
                    if (pos == s.length() - 1) {
                        return;
                    } else if (equal == null) {
                        equal = new TST(s, pos + 1);
                    } else {
                        equal.add(s, pos + 1);
                    }
                    return;
                } else {
                    if (left == null)
                        left = new TST(s, pos);
                    else
                        left.add(s, pos);
                }
            }
            */
        }

        /**
         * to add new string into existing TST instance.
         * @param s String s to be added to instance.
         * @param pos start char pos in String s.
         */
        private void add(String s, int pos) {
            if (s.charAt(pos) == this.ch) {
                count++;
                if (pos == s.length() - 1) {
                    return;
                } else {
                    if (this.equal == null)
                        equal = new TST(s, pos + 1);
                    else
                        equal.add(s, pos + 1);
                    return;
                }
            } else if (s.charAt(pos) < this.ch) {
                if (left == null) {
                    left = new TST(s, pos);
                    return;
                } else
                    left.add(s, pos);
            } else {
                if (right == null) {
                    right = new TST(s, pos);
                    return;
                } else {
                    right.add(s, pos);
                    return;
                }
            }
            return;
        }

        private void traverse(int[] ar) {
            ar[count]++;
            if (left != null)
                left.traverse(ar);

            if (equal != null)
                equal.traverse(ar);

            if (right != null)
                right.traverse(ar);
        }
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

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
