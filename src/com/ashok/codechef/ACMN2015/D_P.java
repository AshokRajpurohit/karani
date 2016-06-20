package com.ashok.codechef.ACMN2015;
import java.io.BufferedReader;
//import static java.lang.System.in;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class D_P {

    private static PrintWriter out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream);
        D_P a = new D_P();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        StringBuilder sb = new StringBuilder();
        int t = in.readInt();


        while (t > 0) {
            t--;
            int n = in.readInt();
            String[] ar = new String[n];

            for (int i = 0; i < n; i++) {
                ar[i] = in.read();
            }

            sort(ar);
            for (int i = 0; i < n; i++)
                sb.append(ar[i]).append('\n');
        }
        out.print(sb);
    }


    private static void sort(String[] ar) {
        String[] br = new String[ar.length];
        sort(ar, br, 0, ar.length - 1);

    }

    private static void sort(String[] a, String[] b, int begin, int end) {
        if (begin == end) {
            return;
        }

        int mid = (begin + end) / 2;
        sort(a, b, begin, mid);
        sort(a, b, mid + 1, end);
        merge(a, b, begin, end);
    }

    private static void merge(String[] a, String[] b, int begin, int end) {
        int mid = (begin + end) / 2;
        int i = begin;
        int j = mid + 1;
        int k = begin;
        while (i <= mid && j <= end) {
            if (a[i].compareTo(a[j]) > 0) {
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

    final static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String read() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int readInt() {
            return Integer.parseInt(read());
        }

    }
}
