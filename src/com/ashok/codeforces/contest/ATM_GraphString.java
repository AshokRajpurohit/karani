package com.ashok.codeforces.contest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem:
 * problem Link:
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class ATM_GraphString {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        ATM_GraphString a = new ATM_GraphString();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt(), m = in.readInt();
        String yes = "Yes\n", no = "No";

        if (m == 0) {
            if (n == 1)
                out.println(yes + "a");
            else if (n == 2)
                out.println(yes + "ac");
            else
                out.println(no);

            return;
        }
        LinkedList<Integer> temp = new LinkedList<Integer>();
        LinkedList<Integer>[] sets =
            (LinkedList<Integer>[])Array.newInstance(temp.getClass(), n);

        for (int i = 0; i < n; i++) {
            sets[i] = new LinkedList<Integer>();
            sets[i].add(i);
        }

        for (int i = 0; i < m; i++) {
            int u = in.readInt(), v = in.readInt();
            sets[u - 1].add(v - 1);
            sets[v - 1].add(u - 1);
        }

        LinkedList<Integer> a = new LinkedList<Integer>();
        LinkedList<Integer> b = new LinkedList<Integer>();
        LinkedList<Integer> c = new LinkedList<Integer>();

        //        Integer[][] map = generate(sets);
        int one = 0, two = -1, three;
        //let's find char b index

        for (int i = 0; i < n; i++)
            if (sets[i].size() == n)
                b.add(i);

        boolean[] check = new boolean[n];
        boolean[] checkb = new boolean[n];
        for (Integer e : b) {
            check[e] = true;
            checkb[e] = true;
        }

        for (int i = 0; i < n; i++)
            if (!check[i]) {
                if (a.size() == 0) {
                    for (Integer e : sets[i]) {
                        if (!checkb[e] && e != i)
                            a.add(e);

                        check[e] = true;
                    }
                } else {
                    for (Integer e : sets[i]) {
                        if (!checkb[e])
                            c.add(e);

                        check[e] = true;
                    }
                }
                //                check[i] = true;
            }


        if (a.size() == 0 && c.size() == 0) {
            a = b;
            b = new LinkedList<Integer>();

            for (int i = 0; i < n; i++)
                if (!check[i])
                    c.add(i);
        }


        if (a.size() + b.size() + c.size() != n) {
            out.println(no);
            return;
        }

        out.print(yes);

        char[] ar = new char[n];
        for (Integer e : a)
            ar[e] = 'a';

        for (Integer e : b)
            ar[e] = 'b';

        for (Integer e : c)
            ar[e] = 'c';

        out.println(ar);
    }

    private static boolean same(Integer[] a, Integer[] b) {
        for (int i = 0; i < a.length; i++)
            if (a[i] != b[i])
                return false;

        return true;
    }

    private static Integer[][] generate(LinkedList<Integer>[] set) {
        Integer[][] ar = new Integer[set.length][];

        for (int i = 0; i < set.length; i++) {
            ar[i] = new Integer[set[i].size()];

            ar[i] = set[i].toArray(ar[i]);
            Arrays.sort(ar);
        }

        return ar;
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
