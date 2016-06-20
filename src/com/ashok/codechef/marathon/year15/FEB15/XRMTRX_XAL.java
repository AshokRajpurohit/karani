package com.ashok.codechef.marathon.year15.FEB15;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.StringTokenizer;
//package com.ashok.codechef.client.com.ashok.codechef.marathon.year15.FEB15;

public class XRMTRX_XAL {

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        com.ashok.codechef.marathon.year15.FEB15.InputReader in = new com.ashok.codechef.marathon.year15.FEB15.InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        com.ashok.codechef.marathon.year15.FEB15.TaskA solver = new com.ashok.codechef.marathon.year15.FEB15.TaskA();
        solver.solve(in, out);
        out.close();
    }
}

class TaskA {
    public static HashMap<String, com.ashok.codechef.marathon.year15.FEB15.node> hm; // = new HashMap<String, node> (10000);

    public void solve(com.ashok.codechef.marathon.year15.FEB15.InputReader in, PrintWriter out) {
        int t = in.nextInt();

        while (t > 0) {
            t--;
            hm = new HashMap<String, com.ashok.codechef.marathon.year15.FEB15.node>(1000);
            long l = Long.parseLong(in.next());
            long r = Long.parseLong(in.next());
            long mlen = 0;
            int mval = 0;
            for (long i = l; i <= r; i++) {
                com.ashok.codechef.marathon.year15.FEB15.node temp = solve(l, r, i, i, 0);
                if (mlen < temp.len) {
                    mlen = temp.len;
                    mval = temp.val;
                } else if (mlen == temp.len) {
                    mval = mval + temp.val;
                    mval = mval % 1000000007;
                }
            }
            out.println(mlen + " " + mval);
            //            hm.clear();
        }
    }

    public static com.ashok.codechef.marathon.year15.FEB15.node solve(long l, long r, long i, long j, long k) {
        if (i < l || i > r || j < l || j > r || (i ^ j) != k)
            return null;

        String key = i + "" + j;

        if (hm.containsKey(key)) {
            //            System.out.println("hm contains key " + hm.containsKey(key));
            return hm.get(key);
        }

        com.ashok.codechef.marathon.year15.FEB15.node cur = new com.ashok.codechef.marathon.year15.FEB15.node(i, j);
        cur.up = solve(l, r, i - 1, j, k + 1);
        cur.down = solve(l, r, i + 1, j, k + 1);
        cur.left = solve(l, r, i, j - 1, k + 1);
        cur.right = solve(l, r, i, j + 1, k + 1);

        long mlen = -1;
        int mval = 0;
        if (cur.up != null) {
            if (mlen < cur.up.len) {
                mlen = cur.up.len;
                mval = cur.up.val;
            } else if (mlen == cur.up.len) {
                mval = mval + cur.up.val;
                mval = mval % 1000000007;
            }
        }

        if (cur.left != null) {
            if (mlen < cur.left.len) {
                mlen = cur.left.len;
                mval = cur.left.val;
            } else if (mlen == cur.left.len) {
                mval = mval + cur.left.val;
                mval = mval % 1000000007;
            }
        }

        if (cur.right != null) {
            if (mlen < cur.right.len) {
                mlen = cur.right.len;
                mval = cur.right.val;
            } else if (mlen == cur.right.len) {
                mval = mval + cur.right.val;
                mval = mval % 1000000007;
            }
        }

        if (cur.down != null) {
            if (mlen < cur.down.len) {
                mlen = cur.down.len;
                mval = cur.down.val;
            } else if (mlen == cur.down.len) {
                mval = mval + cur.down.val;
                mval = mval % 1000000007;
            }
        }

        mlen = mlen + 1;
        cur.len = mlen;
        cur.val = mlen == 0 ? 1 : mval;

        hm.put(key, cur);
        return cur;
    }
}

class node {
    public long l;
    public long r;
    public long len;
    public int val;
    public com.ashok.codechef.marathon.year15.FEB15.node left;
    public com.ashok.codechef.marathon.year15.FEB15.node right;
    public com.ashok.codechef.marathon.year15.FEB15.node up;
    public com.ashok.codechef.marathon.year15.FEB15.node down;

    node(long i, long j) {
        l = i;
        r = j;
    }
}

class InputReader {
    public BufferedReader reader;
    public StringTokenizer tokenizer;

    public InputReader(InputStream stream) {
        reader = new BufferedReader(new InputStreamReader(stream), 32768);
        tokenizer = null;
    }

    public String next() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(reader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return tokenizer.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

}
