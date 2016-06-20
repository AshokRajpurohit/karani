package com.ashok.codechef.marathon.year15.FEB15;

//package com.ashok.codechef.client.com.ashok.codechef.marathon.year15.FEB15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class CHEFCSC {
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
            String s = in.next();
            int n = in.nextInt();
            int d = in.nextInt();

            int[] f = new int[n];
            int[] p = new int[n];
            int[] u = new int[n];
            int[] score = new int[s.length() + 1];

            for (int i = 0; i < s.length(); i++)
                score[i + 1] = score[i] + s.charAt(i) + 1 - 'a';


            for (int i = 0; i < n; i++)
                f[i] = in.nextInt();

            for (int i = 0; i < n; i++)
                p[i] = in.nextInt();

            solver(f, p, u, score, s, 0, 0, d);

            for (int i = 0; i < n; i++)
                out.print(u[i] + " ");
            out.println();

        }

        private int solver(int[] f, int[] p, int[] u, int[] score, String s,
                           int i, int si, int c) {
            if (i == p.length)
                return 0;

            if ((si == s.length()) || (c <= 0)) {
                u[i] = -1;
                return 0;
            }

            if (p[i] > c || f[i] > s.length() - si) {
                u[i] = -1;
                return solver(f, p, u, score, s, i + 1, si, c);
            }

            int mc = 0;
            int mind = si;
            int tc = 0;
            int mi = si;
            int fl = f[i] - 1;
            for (mi = si; mi <= s.length() - f[i]; mi++) {
                tc =
 score[mi + f[i]] - score[mi] + solver(f, p, u, score, s, i + 1, mi + f[i],
                                       c - p[i]);
                if (mc < tc) {
                    mc = tc;
                    mind = mi;
                }
            }
            int tm = solver(f, p, u, score, s, i + 1, si, c);

            if (mc >= tm) {
                u[i] = mind;
                solver(f, p, u, score, s, i + 1, si + f[i], c - p[i]);
            } else {
                mc = tm;
                u[i] = -1;
                solver(f, p, u, score, s, i + 1, si, c);
            }

            return mc;
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
