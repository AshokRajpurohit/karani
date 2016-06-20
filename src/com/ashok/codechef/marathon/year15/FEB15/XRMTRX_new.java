package com.ashok.codechef.marathon.year15.FEB15;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class XRMTRX_new {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskA solver = new TaskA();
        solver.solve(in, out);
        out.close();
    }

    final static class TaskA {
        public void solve(InputReader in, PrintWriter out) {
            int t = in.nextInt();

            while (t > 0) {
                t--;
                long value = 0;
                long k = 0;
                long l = Long.parseLong(in.next());
                long r = Long.parseLong(in.next());
                long[] res = new long[2];

                for (long i = l; i <= r; i++) {
                    solve(l, r, i, i, 0, res);
                    if (k < res[0]) {
                        k = res[0];
                        value = res[1];
                    } else if (k == res[0])
                        value = value + res[1];
                }
                out.println(k + " " + value);
            }
        }

        private static void solve(long l, long r, long i, long j, long k,
                                  long[] res) {
            if (i < l || i > r || j < l || j > r || (i ^ j) != k) {
                res[0] = -1;
                res[1] = 0;
                return;
            }


            long a, b, c, d, e, f, g, h;
            long ko = -1;
            long value = 0;

            solve(l, r, i - 1, j, k + 1, res);
            if (ko < res[0]) {
                ko = res[0];
                value = res[1];
            } else if (ko == res[0]) {
                value = value + res[1];
            }

            solve(l, r, i + 1, j, k + 1, res);
            if (ko < res[0]) {
                ko = res[0];
                value = res[1];
            } else if (ko == res[0]) {
                value = value + res[1];
            }

            solve(l, r, i, j - 1, k + 1, res);
            if (ko < res[0]) {
                ko = res[0];
                value = res[1];
            } else if (ko == res[0]) {
                value = value + res[1];
            }

            solve(l, r, i, j + 1, k + 1, res);
            if (ko < res[0]) {
                ko = res[0];
                value = res[1];
            } else if (ko == res[0]) {
                value = value + res[1];
            }

            ko = ko + 1;

            if (ko == 0)
                value = 1;
            else
                value = value % 1000000007;

            res[0] = ko;
            res[1] = value;
        }
    }

    final static class InputReader {
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
}
