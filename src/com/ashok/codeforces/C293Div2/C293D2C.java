package com.ashok.codeforces.C293Div2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class C293D2C {

    private static InputReader in;
    private static PrintWriter out;

    public C293D2C() {
        super();
    }

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream);
        C293D2C a = new C293D2C();
        a.solve();
        out.close();
    }

    public void solve() {
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        int[] ar = new int[n + 1];
        int[] ind = new int[n + 1];
        long gc = 0;

        for (int i = 1; i < n + 1; i++) {
            ar[i] = in.nextInt();
            ind[ar[i]] = i;
        }

        if (false) {
            out.println("fir se karega?");
        }

        int temp = 0;

        for (int i = 0; i < m; i++) {
            temp = in.nextInt();
            int index = ind[temp];
            gc = gc + index / k;
            if (index % k != 0)
                gc++;

            if (index > 1) {
                int tv = ar[index - 1];
                ind[tv]++;
                ind[temp]--;
                ar[index - 1] = ar[index];
                ar[index] = tv;
            }
        }

        out.println(gc);


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
