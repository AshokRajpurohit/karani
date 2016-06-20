package com.ashok.codechef.Codebyte15;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class A {

    private static InputReader in;
    private static PrintWriter out;

    public A() {
        super();
    }

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream);
        A a = new A();
        a.solve();
        out.close();
    }

    public void solve() {
        StringBuilder sb = new StringBuilder();
        int t = in.nextInt();

        while (t > 0) {
            t--;
            String s = in.next();
            int ar[] = new int[26];
            for (int i = 0; i < s.length(); i++) {
                ar[s.charAt(i) - 'a']++;
            }
            char ch = '0';
            for (int i = 0; i < 26; i++) {
                if (ar[i] == 0) {
                    ch = (char)('a' + i);
                    break;
                }
            }
            if (ch == '0') {
                int[] dar = new int[676];
                int ind;
                for (int i = 0; i < s.length() - 1; i++) {
                    ind = (s.charAt(i) - 'a') * 26 + s.charAt(i + 1) - 'a';
                    dar[ind]++;
                }

                for (int i = 0; i < 676; i++) {
                    if (dar[i] == 0) {
                        sb.append((char)('a' + i / 26));
                        ch = (char)('a' + i % 26);
                        break;
                    }
                }
            }
            if (ch == '0') {
                int[] tar = new int[17576];
                int ind;
                for (int i = 0; i < s.length() - 2; i++) {
                    ind =
(s.charAt(i) - 'a') * 676 + (s.charAt(i + 1) - 'a') * 26 + s.charAt(i) - 'a';
                    tar[ind]++;
                }

                for (int i = 0; i < 17576; i++) {
                    if (tar[i] == 0) {
                        sb.append((char)('a' + i / 676)).append((char)('a' +
                                                                       (i %
                                                                        676) /
                                                                       26));
                        System.out.print((i / 676) + "," + "");
                        ch = (char)(i % 26 + 'a');
                    }
                }
            }

            if (ch == '0') {
                int[] qar = new int[456976];
                int ind;
                for (int i = 0; i < s.length() - 3; i++) {
                    ind =
(s.charAt(i) - 'a') * 17576 + (s.charAt(i + 1) - 'a') * 676 +
  (s.charAt(i + 2) - 'a') * 26 + s.charAt(i + 3) - 'a';
                }

                for (int i = 0; i < 456976; i++) {
                    if (qar[i] == 0) {
                        sb.append((char)('a' + i / 17576)).append((char)('a' +
                                                                         (i %
                                                                          17576) /
                                                                         676)).append((char)('a' +
                                                                                             (i %
                                                                                              676) /
                                                                                             26));
                        ch = (char)(i % 26 + 'a');
                    }
                }
            }
            sb.append(ch).append("\n");
        }
        out.println(sb);
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
