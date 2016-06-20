package com.ashok.codeforces.Common;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class T {
    
    private static InputReader in;
    private static PrintWriter out;
    
    public T() {
        super();
    }

    public static void main(String[] args) {
        FileInputStream inf;
//        FileOutputStream outf;
        try {
            inf = new FileInputStream("input.txt");
//            outf = new FileOutputStream("output.txt");
            InputStream inputStream = System.in;
            OutputStream outputStream = System.out;
            in = new InputReader(inputStream);
            out = new PrintWriter(outputStream);
            T a = new T();
            a.solve();
            inf.close();
//            outf.close();
            out.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

    }
    
    public void solve() {
        int count = 0;
        long t1 = System.currentTimeMillis();
        String a = "";
        try {
            while (true) {
                a = in.next();
                count++;
            }
        } catch (Exception e) {
            // TODO: Add catch code
//            e.printStackTrace();
        }
        long t2 = System.currentTimeMillis();
        out.println(count);
        out.println(t2-t1);
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
