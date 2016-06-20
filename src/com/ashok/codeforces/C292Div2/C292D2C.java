package com.ashok.codeforces.C292Div2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class C292D2C {
    public C292D2C() {
        super();
    }

    public static void main(String[] args) {
        C292D2C c = new C292D2C();
        c.done();
    }

    public void done() {
        InputReader in = new InputReader(System.in);
        OutputStream outputStream = System.out;
        PrintWriter out = new PrintWriter(outputStream);

        int[][] a = new int[10][10];
        a[2][2] = 1;
        a[3][3] = 1;
        a[4][2] = 2;
        a[4][3] = 1;
        a[5][5] = 1;
        a[6][3] = 1;
        a[6][5] = 1;
        a[7][7] = 1;
        a[8][7] = 1;
        a[8][2] = 3;
        a[9][7] = 1;
        a[9][2] = 1;
        a[9][3] = 2;

        int[] count = new int[10];
        count[0] = 0;
        count[1] = 0;
        count[2] = 1;
        count[3] = 1;
        count[4] = 3;
        count[5] = 1;
        count[6] = 2;
        count[7] = 1;
        count[8] = 4;
        count[9] = 4;


        int[] p = new int[10];
        int n = in.nextInt();
        String s = in.next();
        //        int dc = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 10; j++) {
                p[j] = p[j] + a[s.charAt(i) - '0'][j];
            }
        }

        for (int i = 9; i >= 0; i--) {
            while (p[i] > 0) {
                out.print(i);
                p[i]--;
            }
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
