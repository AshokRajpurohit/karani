package com.ashok.friends;

import com.ashok.lang.dsa.RandomStrings;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

import java.util.Arrays;


/**
 * Problem Name: Ankit Soni
 * Link: Office E-MAIL ID
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AnkitSoni {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        AnkitSoni a = new AnkitSoni();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        RandomStrings random = new RandomStrings();
        while (true) {
            String s = random.nextStringabc(in.readInt());
            //            out.println(s);
            long t = System.currentTimeMillis();
            Pair[] pairs = new Pair[s.length()];

            for (int i = 0; i < s.length(); i++) {
                pairs[i] = new Pair(s.charAt(i), i);
            }

            Arrays.sort(pairs);

            StringBuilder sb = new StringBuilder(s.length() << 2);

            for (Pair pair : pairs)
                sb.append(pair.key).append(", ");

            sb.append('\n');

            for (Pair pair : pairs)
                sb.append(pair.value).append(", ");

            //            out.println(sb);

            out.println(System.currentTimeMillis() - t);
            out.flush();
        }
    }
    
    private static int reach(int[][] matrix, int sr, int sc, int er, int ec) {
        if (sr == er && sc == ec)
            return 0;
        
        return 4;
    }

    class Pair implements Comparable<Pair> {
        char key;
        int value;

        Pair(char k, int v) {
            key = k;
            value = v;
        }

        public int compareTo(Pair o) {
            return this.key - o.key;
        }
    }
}
