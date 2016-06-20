package com.ashok.codechef.marathon.year15.FEB15;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;
//package com.ashok.codechef.client.com.ashok.codechef.marathon.year15.FEB15;

public class RANKLIST {

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
                int n = in.nextInt();
                long s = Long.parseLong(in.next());

                if (s == n) {
                    out.println(n - 1);
                } else {
                    double d = Math.sqrt(s << 1);
                    int m = (int)d;

                    if (m == d)
                        m--;

                    long ts = (long)m * (long)(m + 1);
                    ts = ts >> 1;

                    long ds = s - ts;

                    while (m + ds < n) {
                        ds = ds + m;
                        m--;
                    }

                    out.println(n - m);
                }
            }
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
