package com.ashok.codechef.marathon.year15.FEB15;

//package com.ashok.codechef.client.com.ashok.codechef.marathon.year15.FEB15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class CHEFCH {

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskA solver = new TaskA();
        solver.solve(in, out);
        out.close();
    }

    final static class TaskA {
        public void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();

            while (n > 0) {
                n--;
                String s = in.next();
                int a = 0;
                int b = 0;
                boolean t = true;

                for (int i = 0; i < s.length(); i++) {
                    if (t) {
                        if (s.charAt(i) == '+')
                            b++;
                        else
                            a++;
                        t = false;
                    } else {
                        if (s.charAt(i) == '+')
                            a++;
                        else
                            b++;
                        t = true;
                    }
                }
                out.println((a > b) ? b : a);
            }
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
