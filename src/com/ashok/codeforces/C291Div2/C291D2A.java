package com.ashok.codeforces.C291Div2;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class C291D2A {

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
        StringBuffer sb = new StringBuffer(in.next());
        int temp;
        int i = 0;

        if (sb.charAt(0) == '9')
            i = 1;

        for (i = i; i < sb.length(); i++) {
            if (sb.charAt(i) > '4') {
                temp = sb.charAt(i);
                temp = 9 + '0' - temp + '0';
                sb.setCharAt(i, (char)temp);
            }
        }

        out.println(sb);
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
