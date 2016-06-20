package com.ashok.codechef.marathon.year15.FEB15;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class XRMTRX {
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
                int l = in.nextInt(); //Long.parseLong(in.next());
                int r = in.nextInt(); //Long.parseLong(in.next());
                int a = r + 1 - l;

                //            try {
                //                a = (int)(r + 1 - l);
                //            } catch (Exception e) {
                //                // TODO: Add catch code
                //                e.printStackTrace();
                //                out.println("ghantaa");
                //            }

                long[][] mat = new long[a][a];
                long[][] check = new long[a][a];
                long[][] val = new long[a][a];

                for (int i = 0; i < a; i++)
                    for (int j = 0; j < a; j++)
                        mat[i][j] = (l + i) ^ (l + j);

                long[] res =
                    new long[2]; // for k and value, k at 0 and value at 1
                solver(mat, check, val, res);
                long result = 0;
                long k = 0;
                for (int i = 0; i < a; i++)
                    k = k > check[i][i] ? k : check[i][i];

                for (int i = 0; i < a; i++) {
                    if (k == check[i][i]) {
                        result = result + val[i][i];
                        result = result % 1000000007;
                    }
                }


                //            for (int i = 0; i < a; i++) {
                //                for (int j = 0; j < a; j++)
                //                    out.print(mat[i][j] + "\t");
                //                out.println();
                //            }
                //
                //            out.println();
                //            out.println();
                //            out.println("---------------------------------------------");
                //            out.println();
                //
                //
                //            for (int i = 0; i < a; i++) {
                //                for (int j = 0; j < a; j++)
                //                    out.print(val[i][j] + "\t");
                //                out.println();
                //            }
                //            out.println();
                //            out.println("---------------------------------------------");
                //            out.println();
                //
                //            for (int i = 0; i < a; i++) {
                //                for (int j = 0; j < a; j++)
                //                    out.print(check[i][j] + "\t");
                //                out.println();
                //            }
                //            out.println();

                out.println(k + " " + result);

            }
        }

        private void solver(long[][] mat, long[][] check, long[][] val,
                            long[] res) {
            for (int i = 0; i < mat.length; i++)
                solver(mat, check, val, i, i, 0, res);
        }

        private void solver(long[][] mat, long[][] check, long[][] val, int l,
                            int r, int k, long[] res) {
            if (l < 0 || r < 0 || l >= mat.length || r >= mat.length) {
                res[0] = -1;
                res[1] = 0;
                return; // 0;
            }


            if (mat[l][r] != k) {
                res[0] = -1;
                res[1] = 0;
                return; // 0;
            }

            if (val[l][r] != 0 || check[l][r] != 0) {
                res[0] = check[l][r];
                res[1] = val[l][r];
                return; // val[l][r];
            }

            //        if (check[l][r] != 0)
            //            return; // val[l][r];

            long ko = -1;
            long value = 0;
            long a, b, c, d, e, f, g, h;


            solver(mat, check, val, l - 1, r, k + 1, res);
            a = res[0];
            ko = ko > res[0] ? ko : res[0];
            e = res[1];

            solver(mat, check, val, l + 1, r, k + 1, res);
            b = res[0];
            ko = ko > res[0] ? ko : res[0];
            f = res[1];

            solver(mat, check, val, l, r - 1, k + 1, res);
            ko = ko > res[0] ? ko : res[0];
            c = res[0];
            g = res[1];

            solver(mat, check, val, l, r + 1, k + 1, res);
            ko = ko > res[0] ? ko : res[0];
            d = res[0];
            h = res[1];

            if (ko == a)
                value = value + e;
            if (ko == b)
                value = value + f;
            if (ko == c)
                value = value + g;
            if (ko == d)
                value = value + h;

            ko = ko + 1;

            if (ko == 0)
                value = 1;
            else
                value = value % 1000000007;

            res[0] = ko;
            res[1] = value;
            check[l][r] = ko;
            val[l][r] = value;


            //        ko = ko > getk(check, l - 1, r) ? ko : getk(check, l - 1, r);
            //        ko = ko > getk(check, l + 1, r) ? ko : getk(check, l + 1, r);
            //        ko = ko > getk(check, l, r - 1) ? ko : getk(check, l, r - 1);
            //        ko = ko > getk(check, l, r + 1) ? ko : getk(check, l, r + 1);
            //
            //        if (ko == -1) {
            //            check[l][r] = 0;
            //            val[l][r] = 1;
            //            return;// 0;
            //        }
            //
            //        if (ko == getk(check, l - 1, r))
            //            value = value + solver(mat, check, val, l - 1, r, k + 1, res);
            //
            //        if (ko == getk(check, l + 1, r))
            //            value = value + solver(mat, check, val, l + 1, r, k + 1, res);
            //
            //        if (ko == getk(check, l, r - 1))
            //            value = value + solver(mat, check, val, l, r - 1, k + 1, res);
            //
            //        if (ko == getk(check, l, r + 1))
            //            value = value + solver(mat, check, val, l, r + 1, k + 1, res);
            //
            //        value = value % 1000000007;
            //        check[l][r] = ko + 1;
            //        value = value == 0 ? 1 : value;
            //        val[l][r] = value;
            return; // value;

            //        if(ko>k) {
            //            if(ko == getk(check,l-1,r))
            //                value = value+solver(mat, check, val, l - 1, r, k + 1);
            //
            //            if(ko == getk(check,l+1,r))
            //                value = value + solver(mat, check, val, l + 1, r, k + 1);
            //
            //            if(ko == getk(check,l,r-1))
            //                value = value + solver(mat, check, val, l, r-1, k + 1);
            //
            //            if(ko == getk(check,l,r+1))
            //                value = value + solver(mat, check, val, l, r+1, k + 1);
            //        } else {
            //            ko = 0;
            //            value = 1;
            //        }
            //
            //        value = value % 1000000007;
            //        value = value == 0 ? 1 : value;


            //        if(ko < getk(check,l-1,r)) {
            //            ko = getk(check,l-1,r);
            //            value = solver(mat, check, val, l - 1, r, k + 1);
            //        } else if (ko == getk(check,l-1,r)) {
            //            value = value+solver(mat, check, val, l - 1, r, k + 1);
            //        }
            //
            //        if(ko < getk(check,l+1,r)) {
            //            ko = getk(check,l+1,r);
            //            value = solver(mat, check, val, l + 1, r, k + 1);
            //        } else if(ko == getk(check,l+1,r)) {
            //            value = value + solver(mat, check, val, l + 1, r, k + 1);
            //        }
            //
            //        if(ko < getk(check,l,r-1)) {
            //            ko = getk(check,l,r-1);
            //            value = solver(mat, check, val, l, r-1, k + 1);
            //        } else if(ko < getk(check,l,r-1)) {
            //            value = value + solver(mat, check, val, l, r-1, k + 1);
            //        }
            //
            //        if(ko < getk(check,l,r+1)) {
            //            ko = getk(check,l,r+1);
            //            value = solver(mat, check, val, l, r+1, k + 1);
            //        } else if(ko < getk(check,l,r+1)) {
            //            value = value + solver(mat, check, val, l, r+1, k + 1);
            //        }


            //        k = k > getk(check, l - 1, r) ? k : getk(check, l - 1, r);
            //        k = k > getk(check, l + 1, r) ? k : getk(check, l + 1, r);
            //        k = k > getk(check, l, r - 1) ? k : getk(check, l, r - 1);
            //        k = k > getk(check, l, r + 1) ? k : getk(check, l, r + 1);

            //        value = value == 0 ? 1 : value;

            //        val[l][r] = value;
            //        check[l][r] = ko;
            //        return value;
        }

        //    private int getk(int[][] check, int l, int r) {
        //        if (l < 0 || r < 0 || l >= check.length || r >= check.length)
        //            return -2;
        //        return check[l][r];
        //    }

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
