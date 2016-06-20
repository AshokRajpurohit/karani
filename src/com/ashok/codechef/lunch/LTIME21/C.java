package com.ashok.codechef.lunch.LTIME21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class C {

    private static InputReader in;
    private static PrintWriter out;
    private static int mod = 1000000007;

    public C() {
        super();
    }

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream);
        C a = new C();
        a.solve();
        out.close();
    }

    public void solve() {
        int t = in.nextInt();
        while (t > 0) {
            t--;
            String s = in.next();
            process(s);
        }
    }

    public void process(String s) {
        StringBuffer sbuf = new StringBuffer(s);
        StringBuffer sad = new StringBuffer(s.length());
        // b1,g2,r3
        int cr = 0;
        int cb = 0;
        int cg = 0;
        int sr = 0;
        int sb = 0;
        int sg = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'G') {
                cg++;
                sg = sg + i;
            } else if (s.charAt(i) == 'R') {
                cr++;
                sr = sr + i;
            } else {
                cb++;
                sb = sb + i;
            }
            int sgav = sg / cg;
            int sbav = sb / cb;
            int srav = sr / cr;

            int max, min;
            if (sgav > sbav) {
                if (sbav > srav) {
                    max = 2;
                    min = 3;
                } else if (sgav == srav) {
                    max = 2;
                    min = cb > cr ? 1 : 3;
                } else if (sgav > srav) {
                    max = 2;
                    min = 1; // incorrect @TODO
                }
            }
        }
    }

    final static class InputReader {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

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
