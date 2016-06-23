package com.ashok.codechef.marathon.year16.MARCH16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.LinkedList;
import java.util.List;

/**
 * Problem: Parity tree
 * https://www.codechef.com/MARCH16/problems/PARITREE
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class PARITREE {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007;
    private static long inverseTwo = pow(2, mod - 2);

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        PARITREE a = new PARITREE();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            int n = in.readInt(), q = in.readInt();
            for (int i = 0; i < n - 1; i++) {
                in.readInt();
                in.readInt();
            }
            long res = pow(2, n - 1);
            while (q > 0) {
                q--;
                int a = in.readInt(), b = in.readInt(), c = in.readInt();
                res = res * inverseTwo % mod;
            }

            sb.append(res).append('\n');
        }

        out.print(sb);
    }

    private static long pow(long a, long b) {
        if (b == 0)
            return 1;

        a = a % mod;
        if (a < 0)
            a += mod;

        if (a == 1 || a == 0 || b == 1)
            return a;

        long r = Long.highestOneBit(b), res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res * a) % mod;
            }
        }
        return res;
    }

    final static class Node {
        Node parent;
        int value;

        List<Node> childs = new LinkedList<Node>();

        Node(int v) {
            value = v;
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
