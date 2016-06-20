package com.ashok.codeforces.C293Div2;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class C293D2A {

    private static InputReader in;
    private static PrintWriter out;

    public C293D2A() {
        super();
    }

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream);
        C293D2A a = new C293D2A();
        a.solve();
        out.close();
    }

    public void solve() {
        String msg = "No such string";
        String s = in.next();
        String t = in.next();
        int i;

        for (i = 0; i < s.length(); i++) {
            if (t.charAt(i) > s.charAt(i))
                break;
        }
        if (i == s.length()) {
            out.println(msg);
            return;
        }

        char c;
        StringBuffer sb = new StringBuffer(s);

        if (t.charAt(i) - s.charAt(i) > 1) {
            c = (char)(s.charAt(i) + 1);
            sb.setCharAt(i, c);
            out.println(sb);
            return;
        }
        if (i == s.length() - 1) {
            out.println(msg);
            return;
        }

        int l = i;

        for (i = i + 1; i < s.length(); i++) {
            if (s.charAt(i) != 'z' || t.charAt(i) != 'a') {
                if (s.charAt(i) != 'z') {
                    sb.setCharAt(i, 'z');
                    out.println(sb);
                    return;
                } else {
                    sb.setCharAt(l, (char)(s.charAt(l) + 1));
                    for (int j = l + 1; j < s.length(); j++)
                        sb.setCharAt(j, 'a');
                    out.println(sb);
                    return;
                }
            }
        }

        out.println(msg);
        //        return;

        if (i == s.length()) {
            out.println(msg);
            return;
        }

        c = (char)(s.charAt(i) + 1);
        sb.setCharAt(i, c);
        out.println(sb);
        return;

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
