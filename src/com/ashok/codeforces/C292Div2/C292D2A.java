package com.ashok.codeforces.C292Div2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class C292D2A {

    public static void main(String[] args) {
        C292D2A a = new C292D2A();
        a.done();
    }

    public void done() {
        InputReader in = new InputReader(System.in);
        OutputStream outputStream = System.out;
        PrintWriter out = new PrintWriter(outputStream);

        int n = in.nextInt();
        int m = in.nextInt();
        boolean[] b = new boolean[n];
        boolean[] g = new boolean[m];
        int ba = in.nextInt();

        if (ba == n) {
            out.println("YES");
            out.close();
            return;
        }

        for (int i = 0; i < ba; i++) {
            b[in.nextInt()] = true;
        }

        int ga = in.nextInt();

        if (ga == 0 && ba == 0) {
            out.println("NO");
            out.close();
            return;
        }

        int dif = n - m;
        dif = dif > 0 ? dif : -dif;

        //        if(n%dif!=0 && m%dif!=0) {
        //            out.println("YES");
        //            out.close();
        //            return;
        //        }


        if (ga == m) {
            out.println("YES");
            out.close();
            return;
        }

        int gc = gcd(n, m);
        int lc = (n * m) / gc;

        for (int i = 0; i < ga; i++) {
            g[in.nextInt()] = true;
        }

        for (int i = 0; i < lc; i++) {
            b[i % n] = b[i % n] || g[i % m];
            g[i % m] = b[i % n];
        }

        boolean res = true;

        for (int i = 0; i < n; i++) {
            res = res && b[i];
        }

        boolean res1 = true;

        for (int i = 0; i < m; i++) {
            res1 = res1 && g[i];
        }

        res = res || res1;


        if (res)
            out.println("YES");
        else
            out.println("NO");

        out.close();
    }

    public int gcd(int a, int b) {
        if (b == 0)
            return a;
        if (a < b)
            return gcd(b, a);
        return gcd(a - b, b);
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
