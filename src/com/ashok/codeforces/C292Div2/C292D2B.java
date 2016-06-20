package com.ashok.codeforces.C292Div2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class C292D2B {
    public C292D2B() {
        super();
    }

    public void don() {
        InputReader in = new InputReader(System.in);
        OutputStream outputStream = System.out;
        PrintWriter out = new PrintWriter(outputStream);
        int a = in.nextInt();
        int b = in.nextInt();
        a = a < 0 ? -a : a;
        b = b < 0 ? -b : b;
        int sa = a + b;
        int s = in.nextInt();
        if (sa > s) {
            out.println("NO");
        } else if (sa == s) {
            out.println("YES");
        } else {
            int ds = s - sa;
            if ((ds & 1) == 0) {
                out.println("YES");
            } else {
                out.println("NO");
            }
        }
        out.close();
    }

    public static void main(String[] args) {
        C292D2B b = new C292D2B();
        b.don();
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
