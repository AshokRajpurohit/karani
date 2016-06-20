package com.ashok.topcoder.srm;


import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Contest ID: SRM 685
 * Link: https://community.topcoder.com/stat?c=round_overview&rd=16689
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class SRM685 {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        SRM685 a = new SRM685();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        MultiplicationTable2Easy o = new MultiplicationTable2Easy();
        while (true) {
            int n = in.readInt();
            int[] table = in.readIntArray(n);

            n = in.readInt();
            int[] t = in.readIntArray(n);

            out.println(o.isGoodSet(table, t));
            out.flush();
        }
    }

    class MultiplicationTable2Easy {
        public String isGoodSet(int[] table, int[] t) {
            int n = (int)Math.sqrt(table.length);
            boolean[] check = new boolean[n];
            for (int i = 0; i < t.length; i++)
                check[t[i]] = true;

            boolean good = true;

            for (int i = 0; i < t.length && good; i++) {
                for (int j = i; j < t.length && good; j++) {
                    good =
check[table[t[i] * n + t[j]]] && check[table[t[j] * n + t[i]]];
                }
            }

            if (good)
                return "Good";

            return "Not Good";
        }
    }
}
