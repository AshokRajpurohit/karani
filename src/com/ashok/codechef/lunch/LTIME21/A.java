package com.ashok.codechef.lunch.LTIME21;

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
    private static int mod = 1000000007;

    public A() {
        super();
    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream);
        A a = new A();
        a.solve();
        out.close();
    }

    public void solve() {
        int t = in.nextInt();
        while (t > 0) {
            t--;
            String s = in.next();
            int count = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '4')
                    count++;
            }
            out.println(count);
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
