package com.ashok.codeforces.C294Div;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;


public class A {

    private static InputReader in;
    private static PrintWriter out;

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream);
        A a = new A();
        a.solve();
        out.close();
    }

    public void solve() {

        long rb = 0;
        long rw = 0;

        String w = "White";
        String b = "Black";
        for (int j = 0; j < 8; j++) {
            String s = in.next();
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != '.') {
                    if (s.charAt(i) > 'a') {
                        if (s.charAt(i) == 'q')
                            rb = rb + 9;
                        else if (s.charAt(i) == 'r')
                            rb = rb + 5;
                        else if (s.charAt(i) == 'b')
                            rb = rb + 3;
                        else if (s.charAt(i) == 'k')
                            rb = rb + 3;
                        else if (s.charAt(i) == 'p')
                            rb = rb + 1;
                    } else {
                        if (s.charAt(i) == 'Q')
                            rw = rw + 9;
                        else if (s.charAt(i) == 'R')
                            rw = rw + 5;
                        else if (s.charAt(i) == 'B')
                            rw = rw + 3;
                        else if (s.charAt(i) == 'K')
                            rw = rw + 3;
                        else if (s.charAt(i) == 'P')
                            rw = rw + 1;
                    }
                }
            }
        }
        if (rb > rw)
            System.out.println("Black");
        else if (rw > rb)
            System.out.println("White");
        else
            System.out.println("Draw");
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
