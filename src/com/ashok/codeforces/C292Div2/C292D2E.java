package com.ashok.codeforces.C292Div2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class C292D2E {
    public C292D2E() {
        super();
    }

    public static void main(String[] args) {
        C292D2E e = new C292D2E();
        e.done();
    }

    public void done() {
        InputReader in = new InputReader(System.in);
        OutputStream outputStream = System.out;
        PrintWriter out = new PrintWriter(outputStream);
        int n = in.nextInt();
        int m = in.nextInt();
        int[] h = new int[n];
        int[] d = new int[n];

        for (int i = 0; i < n; i++) {
            d[i] = in.nextInt();
        }

        int[] dsum = new int[n + 1];

        for (int i = 0; i < n; i++) {
            dsum[i + 1] = dsum[i] + d[i];
        }
        dsum[0] = dsum[n];

        for (int i = 0; i < n; i++) {
            h[i] = in.nextInt();
        }

        int[][] cal = new int[n][n];

        for (int i = 0; i < n; i++) {
            int max = 0;
            int j = i + 1;
            j = j % n;
            int dis = 0;
            while (j != i) {
                dis = dis + d[j];
                int temp = (h[i] << 1) + (h[j] << 1) + dis;
                max = max > temp ? max : temp;
                cal[i][j] = max;
                j++;
                j = j % n;
            }
        }

        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();

            for (int j = a - 1; j < b; j++) {

            }
            out.println(cal[a - 1][b - 1]);
        }


        out.close();
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
