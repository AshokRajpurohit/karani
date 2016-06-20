package com.ashok.codeforces.C291Div2;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class C291D2B {

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskA solver = new TaskA();
        solver.solve(in, out);
    }

    final static class TaskA {
        public void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int a = in.nextInt();
            int b = in.nextInt();

            int[] x = new int[n];
            int[] y = new int[n];
            double[] sl = new double[n];

            for (int i = 0; i < n; i++) {
                x[i] = in.nextInt();
                y[i] = in.nextInt();
            }

            for (int i = 0; i < n; i++) {
                if (x[i] == a && y[i] == b)
                    sl[i] = Integer.MAX_VALUE;
                else if (x[i] == a)
                    sl[i] = Integer.MIN_VALUE;
                else
                    sl[i] = ((double)(y[i] - b)) / ((double)(x[i] - a));
            }

            sort(sl);
            int count = 1;

            for (int i = 1; i < n; i++) {
                if (sl[i] == Integer.MAX_VALUE) {
                    out.println(count);
                    return;
                }

                if (sl[i] != sl[i - 1])
                    count++;
            }

            out.println(count);


        }

        private static void sort(double[] a) {
            double[] b = new double[a.length];
            sort(a, b, 0, a.length - 1);
        }

        private static void sort(double[] a, double[] b, int begin, int end) {
            if (begin == end) {
                return;
            }

            int mid = (begin + end) / 2;
            sort(a, b, begin, mid);
            sort(a, b, mid + 1, end);
            merge(a, b, begin, end);
        }

        private static void merge(double[] a, double[] b, int begin, int end) {
            int mid = (begin + end) / 2;
            int i = begin;
            int j = mid + 1;
            int k = begin;
            while (i <= mid && j <= end) {
                if (a[i] > a[j]) {
                    b[k] = a[j];
                    j++;
                } else {
                    b[k] = a[i];
                    i++;
                }
                k++;
            }
            if (j <= end) {
                while (j <= end) {
                    b[k] = a[j];
                    k++;
                    j++;
                }
            }
            if (i <= mid) {
                while (i <= mid) {
                    b[k] = a[i];
                    i++;
                    k++;
                }
            }

            i = begin;
            while (i <= end) {
                a[i] = b[i];
                i++;
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
