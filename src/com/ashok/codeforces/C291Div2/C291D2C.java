package com.ashok.codeforces.C291Div2;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

public class C291D2C {

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskA solver = new TaskA();
        solver.solve(in, out);
        out.close();
    }

    static class TaskA {
        public void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int m = in.nextInt();


            if (m == 0)
                return;

            if (n == 0) {
                for (int i = 0; i < m; i++)
                    out.println("NO");
                return;
            }


            HashMap<String, Integer> hm = new HashMap<String, Integer>(n);

            for (int i = 0; i < n; i++) {
                String key = in.next();
                if (!hm.containsKey(key))
                    hm.put(key, 1);
            }
            Set<String> set = hm.keySet();
            Iterator iter = set.iterator();
            String[] a = new String[hm.size()];

            for (int i = 0; i < hm.size(); i++) {
                a[i] = (String)iter.next();
            }
            n = hm.size();

            int[] b = sortIndex(a);

            for (int i = 0; i < m; i++) {
                String t = in.next();

                int ind, end;
                ind = 0;
                end = n - 1;
                while (end > ind + 1) {
                    int mid = (ind + end) >> 1;
                    if (a[b[mid]].length() > t.length()) {
                        end = mid;
                    } else if (a[b[mid]].length() < t.length()) {
                        ind = mid;
                    } else {
                        ind = end = mid;
                    }
                }

                while (ind >= 0 && a[b[ind]].length() == t.length())
                    ind--;
                ind++;

                while (end < n && a[b[end]].length() == t.length())
                    end++;
                end--;

                int count = 0;
                int j;
                for (j = ind; j <= end; j++) {
                    if (check(a[b[j]], t))
                        break;
                }

                if (j > end) {
                    out.println("NO");
                } else {
                    out.println("YES");
                }

            }
        }

        private static boolean check(String a, String b) {
            if (a.length() != b.length())
                return false;

            int count = 0;
            for (int i = 0; i < a.length(); i++) {
                if (a.charAt(i) != b.charAt(i))
                    count++;
                if (count > 1)
                    return false;
            }
            if (count == 1)
                return true;
            return false;
        }

        public static int[] sortIndex(String[] ar) {
            int[] a = new int[ar.length];
            int[] b = new int[a.length];
            int[] c = new int[a.length];

            for (int i = 0; i < ar.length; i++) {
                a[i] = ar[i].length();
            }

            for (int i = 0; i < a.length; i++) {
                c[i] = i;
            }
            sort(a, b, c, 0, a.length - 1);
            return c;
        }

        private static void sort(int[] a, int[] b, int[] c, int begin,
                                 int end) {
            if (begin == end) {
                return;
            }

            int mid = (begin + end) / 2;
            sort(a, b, c, begin, mid);
            sort(a, b, c, mid + 1, end);
            merge(a, b, c, begin, end);
        }

        public static void merge(int[] a, int[] b, int[] c, int begin,
                                 int end) {
            int mid = (begin + end) / 2;
            int i = begin;
            int j = mid + 1;
            int k = begin;
            while (i <= mid && j <= end) {
                if (a[c[i]] > a[c[j]]) {
                    b[k] = c[j];
                    j++;
                } else {
                    b[k] = c[i];
                    i++;
                }
                k++;
            }
            if (j <= end) {
                while (j <= end) {
                    b[k] = c[j];
                    k++;
                    j++;
                }
            }
            if (i <= mid) {
                while (i <= mid) {
                    b[k] = c[i];
                    i++;
                    k++;
                }
            }

            i = begin;
            while (i <= end) {
                c[i] = b[i];
                i++;
            }
        }


    }

    static class InputReader {
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
