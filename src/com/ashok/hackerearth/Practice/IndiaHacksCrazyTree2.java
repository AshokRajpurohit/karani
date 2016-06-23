package com.ashok.hackerearth.Practice;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Problem: Crazy Tree 2
 * Code Your Way to IndiaHacks Conference
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class IndiaHacksCrazyTree2 {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1299709;
    private static long[] tree;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        IndiaHacksCrazyTree2 a = new IndiaHacksCrazyTree2();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int l = in.readInt(), q = in.readInt();
        format(l);
        StringBuilder sb = new StringBuilder(q << 3);

        while (q > 0) {
            q--;
            sb.append(get(in.readInt() - 1, in.readInt() - 1)).append('\n');
        }

        out.print(sb);
    }

    private static long get(int x, int y) {
        if (x == 0)
            return tree[y];

        long value = tree[y] - tree[x - 1];

        if (value < 0)
            return value + mod;

        return value;
    }

    private static void format(int level) {
        tree = new long[level];
        
        if (level == 1) {
            tree[0] = 1;
            return;
        }
        
        LinkedList<Long> list = new LinkedList<Long>();
        level--;
        int n = 1 << level;
        tree[level] = 1L * n * (n + 1) / 2;
        tree[level] %= mod;
        long sum = 0;
        level--;

        for (int i = 1; i <= n; i += 2) {
            long value = 1L * i * (i + 1) % mod;
            list.add(value);
            sum += value;
        }

        tree[level] = sum % mod;
        level--;

        while (level >= 0) {
            sum = 0;
            LinkedList<Long> temp = new LinkedList<Long>();
            Iterator<Long> iter = list.iterator();

            while (iter.hasNext()) {
                long value = iter.next() * iter.next() % mod;
                temp.add(value);
                sum += value;
            }

            tree[level] = sum % mod;
            list = temp;
            level--;
        }

        for (int i = 1; i < tree.length; i++) {
            tree[i] = (tree[i] + tree[i - 1]) % mod;
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
    }
}
