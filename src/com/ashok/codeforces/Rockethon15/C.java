package com.ashok.codeforces.Rockethon15;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class C {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskA solver = new TaskA();
        solver.solve(in, out);
        out.close();
    }
}

class TaskA {
    public void solve(InputReader in, PrintWriter out) {
        int n = in.nextInt();
        double temp = 0;
        double a = 0;
        double b = 0;


        for (int i = 0; i < n; i++) {
            temp = in.nextInt() + in.nextInt();
            temp = temp / 2;
            if (temp > b) {
                b = temp;
                if (b > a) {
                    temp = a;
                    a = b;
                    b = temp;
                }
            }
        }
        out.println(b);
    }
}

class InputReader {
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
