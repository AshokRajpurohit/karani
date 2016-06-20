package com.ashok.codechef.marathon.year15.FEB15;

//package com.ashok.codechef.client.com.ashok.codechef.marathon.year15.FEB15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class STRQ {
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
            String s = in.next();
            int q = in.nextInt();

            if (s.length() == 1) {
                while (q > 0) {
                    q--;
                    String a = in.next();
                    String b = in.next();
                    int l = in.nextInt();
                    int r = in.nextInt();
                    out.println(0);
                }
                return;
            }
            long[] c = new long[s.length() + 1];
            long[] h = new long[s.length() + 1];
            long[] e = new long[s.length() + 1];
            long[] f = new long[s.length() + 1];


            for (int i = s.length() - 1; i >= 0; i--) {
                if (s.charAt(i) == 'c') {
                    c[i] = c[i + 1] + 1;
                    h[i] = h[i + 1];
                    e[i] = e[i + 1];
                    f[i] = f[i + 1];
                } else if (s.charAt(i) == 'h') {
                    c[i] = c[i + 1];
                    h[i] = h[i + 1] + 1;
                    e[i] = e[i + 1];
                    f[i] = f[i + 1];
                } else if (s.charAt(i) == 'e') {
                    c[i] = c[i + 1];
                    h[i] = h[i + 1];
                    e[i] = e[i + 1] + 1;
                    f[i] = f[i + 1];
                } else {
                    c[i] = c[i + 1];
                    h[i] = h[i + 1];
                    e[i] = e[i + 1];
                    f[i] = f[i + 1] + 1;
                }
            }

            long[] ch = new long[s.length() + 1];
            long[] ce = new long[s.length() + 1];
            long[] cf = new long[s.length() + 1];
            long[] hc = new long[s.length() + 1];
            long[] he = new long[s.length() + 1];
            long[] hf = new long[s.length() + 1];
            long[] ec = new long[s.length() + 1];
            long[] eh = new long[s.length() + 1];
            long[] ef = new long[s.length() + 1];
            long[] fc = new long[s.length() + 1];
            long[] fh = new long[s.length() + 1];
            long[] fe = new long[s.length() + 1];
            long c1 = 0;
            long h1 = 0;
            long e1 = 0;
            long f1 = 0;

            for (int i = s.length() - 1; i >= 0; i--) {
                if (s.charAt(i) == 'c') {
                    c1++;
                    cf[i] = cf[i + 1] + f1;
                    ch[i] = ch[i + 1] + h1;
                    ce[i] = ce[i + 1] + e1;
                    hc[i] = hc[i + 1];
                    he[i] = he[i + 1];
                    hf[i] = hf[i + 1];
                    ec[i] = ec[i + 1];
                    eh[i] = eh[i + 1];
                    ef[i] = ef[i + 1];
                    fc[i] = fc[i + 1];
                    fh[i] = fh[i + 1];
                    fe[i] = fe[i + 1];
                } else if (s.charAt(i) == 'h') {
                    h1++;
                    cf[i] = cf[i + 1];
                    ch[i] = ch[i + 1];
                    ce[i] = ce[i + 1];
                    hc[i] = hc[i + 1] + c1;
                    he[i] = he[i + 1] + e1;
                    hf[i] = hf[i + 1] + f1;
                    ec[i] = ec[i + 1];
                    eh[i] = eh[i + 1];
                    ef[i] = ef[i + 1];
                    fc[i] = fc[i + 1];
                    fh[i] = fh[i + 1];
                    fe[i] = fe[i + 1];
                } else if (s.charAt(i) == 'e') {
                    e1++;
                    cf[i] = cf[i + 1];
                    ch[i] = ch[i + 1];
                    ce[i] = ce[i + 1];
                    hc[i] = hc[i + 1];
                    he[i] = he[i + 1];
                    hf[i] = hf[i + 1];
                    ec[i] = ec[i + 1] + c1;
                    eh[i] = eh[i + 1] + h1;
                    ef[i] = ef[i + 1] + f1;
                    fc[i] = fc[i + 1];
                    fh[i] = fh[i + 1];
                    fe[i] = fe[i + 1];
                } else {
                    f1++;
                    cf[i] = cf[i + 1];
                    ch[i] = ch[i + 1];
                    ce[i] = ce[i + 1];
                    hc[i] = hc[i + 1];
                    he[i] = he[i + 1];
                    hf[i] = hf[i + 1];
                    ec[i] = ec[i + 1];
                    eh[i] = eh[i + 1];
                    ef[i] = ef[i + 1];
                    fc[i] = fc[i + 1] + c1;
                    fh[i] = fh[i + 1] + h1;
                    fe[i] = fe[i + 1] + e1;
                }
            }

            while (q > 0) {
                q--;
                char a = in.next().charAt(0);
                char b = in.next().charAt(0);
                int l = in.nextInt();
                int r = in.nextInt();

                if (l == r)
                    out.println(0);
                else {
                    if (a == 'c') {
                        if (b == 'h')
                            out.println(ch[l - 1] - ch[r] -
                                        (c[l - 1] - c[r]) * h[r]);
                        else if (b == 'e')
                            out.println(ce[l - 1] - ce[r] -
                                        (c[l - 1] - c[r]) * e[r]);
                        else
                            out.println(cf[l - 1] - cf[r] -
                                        (c[l - 1] - c[r]) * f[r]);
                    } else if (a == 'h') {
                        if (b == 'c')
                            out.println(hc[l - 1] - hc[r] -
                                        (h[l - 1] - h[r]) * c[r]);
                        else if (b == 'e')
                            out.println(he[l - 1] - he[r] -
                                        (h[l - 1] - h[r]) * e[r]);
                        else
                            out.println(hf[l - 1] - hf[r] -
                                        (h[l - 1] - h[r]) * f[r]);
                    } else if (a == 'e') {
                        if (b == 'c')
                            out.println(ec[l - 1] - ec[r] -
                                        (e[l - 1] - e[r]) * c[r]);
                        else if (b == 'h')
                            out.println(eh[l - 1] - eh[r] -
                                        (e[l - 1] - e[r]) * h[r]);
                        else
                            out.println(ef[l - 1] - ef[r] -
                                        (e[l - 1] - e[r]) * f[r]);
                    } else {
                        if (b == 'c')
                            out.println(fc[l - 1] - fc[r] -
                                        (f[l - 1] - f[r]) * c[r]);
                        else if (b == 'h')
                            out.println(fh[l - 1] - fh[r] -
                                        (f[l - 1] - f[r]) * h[r]);
                        else
                            out.println(fe[l - 1] - fe[r] -
                                        (f[l - 1] - f[r]) * e[r]);
                    }
                }

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
