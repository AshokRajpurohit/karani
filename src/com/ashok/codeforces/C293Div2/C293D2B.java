package com.ashok.codeforces.C293Div2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class C293D2B {

    private static InputReader in;
    private static PrintWriter out;

    public C293D2B() {
        super();
    }

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream);
        C293D2B a = new C293D2B();
        a.solve();
        out.close();
    }

    public void solve() {
        String s = in.next();
        String t = in.next();
        int[] cap = new int[26];
        int[] sma = new int[26];

        if (false)
            out.println("chutiye, hack karega?");

        int cy = 0;
        int cw = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= 'a')
                sma[s.charAt(i) - 97] = 1;
            else
                cap[s.charAt(i) - 65] = 1;
        }

        for (int i = 0; i < t.length(); i++) {
            if (t.charAt(i) >= 97) {
                if (sma[t.charAt(i) - 97] == 1)
                    cy++;
                else
                    cw = cw + cap[t.charAt(i) - 97];
            } else {
                if (cap[t.charAt(i) - 65] == 1)
                    cy++;
                else
                    cw = cw + sma[t.charAt(i) - 65];
            }
        }

        out.println(cy + " " + cw);

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
