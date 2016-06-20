package com.ashok.topcoder.practice;


import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Problem Name:
 * Contest ID:
 * Link:
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class SRM670R1D2 {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        //        String input = "input_file.in", output = "output_file.out";
        //        FileInputStream fip = new FileInputStream(input);
        //        FileOutputStream fop = new FileOutputStream(output);
        //        in = new InputReader(fip);
        //        out = new Output(fop);

        SRM670R1D2 a = new SRM670R1D2();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        //        long t = System.currentTimeMillis();
        Drbalance cdgame = new Drbalance();
        while (true)
            System.out.println(cdgame.lesscng(in.read(), in.readInt()));
        //        out.println(System.currentTimeMillis() - t);
    }

    private static long square(int n) {
        return 1L * n * n;
    }

    class Drbalance {
        public int lesscng(String s, int k) {
            int[] bal = new int[s.length()];
            int count = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '-')
                    count++;
                else
                    count--;

                bal[i] = count;
            }

            int negativity = 0;
            for (int i = 0; i < s.length(); i++)
                if (bal[i] > 0)
                    negativity++;

            int steps = 0;
            int i = 0;
            while (negativity > k) {
                while (i < s.length() && s.charAt(i) == '+')
                    i++;

                steps++;

                for (int j = i; j < s.length(); j++) {
                    bal[j] -= 2;
                    if (bal[j] > -2 && bal[j] < 1)
                        negativity--;
                }
            }

            return steps;
        }
    }

    class Cdgame {
        public int rescount(int[] a, int[] b) {
            HashSet<Integer> hs = new HashSet<Integer>(a.length);
            int sumA = 0, sumB = 0;
            for (int i = 0; i < a.length; i++) {
                sumA += a[i];
                sumB += b[i];
            }

            int[] p = getUnik(a);
            int[] q = getUnik(b);

            for (int e : p)
                for (int f : q)
                    hs.add((sumA - e + f) * (sumB + e - f));

            return hs.size();
        }

        private int[] getUnik(int[] ar) {
            int max = 0;
            for (int i = 0; i < ar.length; i++)
                if (ar[i] > max)
                    max = ar[i];

            boolean[] br = new boolean[max + 1];
            for (int i = 0; i < ar.length; i++)
                br[ar[i]] = true;

            int count = 0;
            for (int i = 1; i < max + 1; i++)
                if (br[i])
                    count++;

            int[] res = new int[count];
            count--;
            for (int i = 1; i < 101 && count >= 0; i++)
                if (br[i]) {
                    res[count] = i;
                    count--;
                }

            return res;
            //            Arrays.sort(ar);
            //            int count = 1;
            //            for (int i = 1; i < ar.length; i++)
            //                if (ar[i] != ar[i - 1])
            //                    count++;
            //
            //            int[] br = new int[count];
            //            br[0] = ar[0];
            //            int j = 1;
            //            for (int i = 1; i < ar.length && j < count; i++)
            //                if (ar[i] != ar[i - 1]) {
            //                    br[j] = ar[i];
            //                    j++;
            //                }
            //
            //            return br;
        }
    }
}
