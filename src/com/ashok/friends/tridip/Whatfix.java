package com.ashok.friends.tridip;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

public class Whatfix {
    private static PrintWriter out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        KFrequency.solve();
        in.close();
        out.close();
    }

    final static class KFrequency {
        private static void solve() throws IOException {
            int t = in.readInt();
            while (t > 0) {
                t--;
                int res = process(in.read().toCharArray(), in.readInt());
                if (res == -1)
                    out.println(res);
                else
                    out.println((char) (res + 'a'));
            }
        }

        private static int process(char[] ar, int k) {
            Pair[] pairs = getPairs();
            for (char ch : ar)
                pairs[ch - 'a'].count++;

            Pair[] processedPairs = Arrays.stream(pairs)
                    .filter((pair -> pair.count != 0))
                    .sorted((a, b) -> a.count == b.count ? a.id - b.id : b.count - a.count)
                    .toArray((t) -> new Pair[t]);

            LinkedList linkedList = new LinkedList();


            if (k == 1)
                return processedPairs[0].id;

            Pair prev = processedPairs[0];
            k--;
            for (Pair pair : processedPairs) {
                if (k == 0)
                    break;

                if (pair.count == prev.count)
                    continue;

                prev = pair;
                k--;
            }

            return k != 0 ? -1 : prev.id;
        }

        private static Pair[] getPairs() {
            Pair[] pairs = new Pair[26];
            for (int i = 0; i < 26; i++)
                pairs[i] = new Pair(i);

            return pairs;
        }
    }

    final static class SheldonGreed {
        private static void solve() throws IOException {
            while (true) {
                int n = in.readInt();
                int[] ar = in.readIntArray(n);
                out.println(process(ar));
                out.flush();
            }
        }

        private static int process(int[] ar) {
            return 0;
        }
    }

    final static class Pair implements Comparable<Pair> {
        final int id;
        int count = 0;

        Pair(int id) {
            this.id = id;
        }

        @Override
        public int compareTo(Pair o) {
            return 0;
        }
    }
}