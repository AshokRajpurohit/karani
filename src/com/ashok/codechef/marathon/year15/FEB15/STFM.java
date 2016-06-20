package com.ashok.codechef.marathon.year15.FEB15;

//package com.ashok.codechef.client.com.ashok.codechef.marathon.year15.FEB15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.math.BigInteger;

import java.util.StringTokenizer;

public class STFM {

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
            int m = in.nextInt();

            if (m == 1) {
                out.println(0);
                return;
            }

            BigInteger bm = new BigInteger(String.valueOf(m));
            BigInteger bmn = bm.subtract(BigInteger.ONE);
            BigInteger tw = new BigInteger("2");
            long res = 0;

            for (int i = 0; i < n; i++) {
                String te = in.next();

                if (m != 1) {
                    long p1 = 0;
                    long p2 = 0;

                    BigInteger nt = new BigInteger(te);
                    te = nt.toString();

                    if (te.length() >= 19) {
                        BigInteger temp = new BigInteger(te);
                        BigInteger bp1 =
                            temp.multiply(temp).multiply(temp.add(BigInteger.ONE));
                        bp1 = bp1.divide(tw);

                        BigInteger bp2 = bp1.divide(bm);
                        bp2 = bp2.multiply(bm);
                        bp1 = bp1.subtract(bp2);

                        p1 = Integer.valueOf(bp1.toString());
                        p1 = p1 - 1;
                        p2 = 0;

                    } else if (te.length() >= 10) {
                        long a = Long.valueOf(te);
                        p2 = 0;
                        long b = a;
                        if ((a & 1) == 1) {
                            b = b >> 1;
                            a = a % m;
                            p1 = a * a;
                            p1 = p1 % m;
                            p1 = p1 * b - 1;
                        } else {
                            a = a >> 1;
                            b = b % m;
                            a = a % m;
                            p1 = b * b;
                            p1 = p1 % m;
                            p1 = p1 * a - 1;
                        }

                    } else {
                        int a = Integer.valueOf(te);
                        int b = a + 1;

                        if (a + 1 < m) {
                            long count = b;
                            p2 = 1;

                            while (count > 1) {
                                p2 = p2 * count;
                                p2 = p2 % m;
                                count--;
                                if (p2 == 0)
                                    break;
                            }
                        }

                        if ((a & 1) == 1) {
                            b = b >> 1;
                            p1 = a * a;
                            p1 = p1 % m;
                            p1 = p1 * b - 1;
                        } else {
                            p1 = b * a;
                            p1 = p1 % m;
                            a = a >> 1;
                            p1 = p1 * a - 1;
                        }

                    }

                    res = res + p1 + p2 + m;
                    res = res % m;
                }
            }


            out.println(res);
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
