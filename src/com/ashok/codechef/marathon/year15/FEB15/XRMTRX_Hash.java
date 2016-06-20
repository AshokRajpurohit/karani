package com.ashok.codechef.marathon.year15.FEB15;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.StringTokenizer;

public class XRMTRX_Hash {
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
            int t = in.nextInt();

            while (t > 0) {
                t--;
                long l = Long.parseLong(in.next());
                long r = Long.parseLong(in.next());

                //            HashMap<String,Long> hc;
                //            hc = new HashMap<String,Long>();

                HashMap hc = new HashMap<String, Long>(1000);
                HashMap hv = new HashMap<String, Integer>(1000);


                for (long i = l; i <= r; i++) {
                    solve(l, r, i, i, 0, hc, hv);
                }

                StringBuffer sb = new StringBuffer();
                long ko = -1;
                int value = 0;

                for (long i = 1; i <= r; i++) {
                    String s = i + "" + i;
                    long temp = -1;
                    int temp1 = 0;
                    try {
                        temp = (Long)hc.get(s);
                        temp1 = (Integer)hv.get(s);
                    } catch (Exception e) {
                        // TODO: Add catch code
                        //                    e.printStackTrace();
                        temp = -1;
                        temp1 = 0;
                    }
                    if (ko < temp) {
                        ko = temp;
                        value = temp1;
                    } else if (ko == temp)
                        value = value + temp1;
                }
                value = value % 1000000007;

                out.println(ko + " " + value);


            }
        }

        private static void solve(long l, long r, long i, long j, long k,
                                  HashMap hc, HashMap hv) {
            if (i < l || i > r || j < l || j > r || (i ^ j) != k) {
                //            String a = i + "" + j;
                //            String b = "-1";
                //            hc.put(a, -1);
                //            hv.put(a, 0);
                return;
            }

            //        StringBuffer sb = new StringBuffer();
            //        StringBuffer key = new StringBuffer();

            //        key.append(i).append(" ").append(j);
            String key = i + "" + j;

            if (hc.containsKey(key)) {
                return;
            }

            long ko = -1;
            int value = 0;


            if (checkij(l, r, i - 1, j)) {
                solve(l, r, i - 1, j, k + 1, hc, hv);
                String keyt = (i - 1) + "" + j;
                int temp;
                try {
                    temp = (Integer)hc.get(keyt);
                } catch (Exception e) {
                    // TODO: Add catch code
                    //                e.printStackTrace();
                    temp = -1;
                }
                if (ko < temp) {
                    ko = temp;
                    value = (Integer)hv.get(keyt);
                } else if (ko == temp && ko != -1)
                    value = value + (Integer)hv.get(keyt);
            }

            if (checkij(l, r, i + 1, j)) {
                solve(l, r, i + 1, j, k + 1, hc, hv);
                String keyt = (i + 1) + "" + j;
                int temp;
                try {
                    temp = (Integer)hc.get(keyt);
                } catch (Exception e) {
                    // TODO: Add catch code
                    //                e.printStackTrace();
                    temp = -1;
                }
                if (ko < temp) {
                    ko = temp;
                    value = (Integer)hv.get(keyt);
                } else if (ko == temp && ko != -1)
                    value = value + (Integer)hv.get(keyt);
            }

            if (checkij(l, r, i, j - 1)) {
                solve(l, r, i, j - 1, k + 1, hc, hv);
                String keyt = i + "" + (j - 1);
                int temp;
                try {
                    temp = (Integer)hc.get(keyt);
                } catch (Exception e) {
                    // TODO: Add catch code
                    //                e.printStackTrace();
                    temp = -1;
                }
                if (ko < temp) {
                    ko = temp;
                    value = (Integer)hv.get(keyt);
                } else if (ko == temp && ko != -1)
                    value = value + (Integer)hv.get(keyt);
            }

            if (checkij(l, r, i, j + 1)) {
                solve(l, r, i, j + 1, k + 1, hc, hv);
                String keyt = i + "" + (j + 1);
                int temp;
                try {
                    temp = (Integer)hc.get(keyt);
                } catch (Exception e) {
                    // TODO: Add catch code
                    //                e.printStackTrace();
                    temp = -1;
                }
                if (ko < temp) {
                    ko = temp;
                    value = (Integer)hv.get(keyt);
                } else if (ko == temp && ko != -1)
                    value = value + (Integer)hv.get(keyt);
            }

            ko = ko + 1;

            if (ko == 0)
                value = 1;

            value = value % 1000000007;
            hc.put(key, ko);
            hv.put(key, value);


            //        sb.append(hm.get(key));

            //        if (!sb.equals("-1")) {
            //            long temp = Long.parseLong(sb.substring(0, sb.indexOf(" ")));
            //            long tempo = Long.parseLong(sb.substring(sb.indexOf(" ") + 1));
            //            if (ko < temp) {
            //                ko = temp;
            //                value = tempo;
            //            }
            //        }

        }

        private static boolean checkij(long l, long r, long i, long j) {
            if (i < l || i > r || j < l || j > r)
                return false;
            return true;
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
