package com.ashok.codechef.marathon.year16.JAN16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.lang.reflect.Array;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem: Sereja and Salesman
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class SEAKAM {

    private static PrintWriter out;
    private static InputStream in;
    private static long[] fact;
    private static int mod = 1000000007, n;
    private static Pair[] ar;

    private static void populate(int n) {
        fact = new long[n + 1];
        fact[0] = 1;
        for (int i = 1; i <= n; i++)
            fact[i] = fact[i - 1] * i % mod;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        SEAKAM a = new SEAKAM();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        int[] arn = new int[t], arm = new int[t];
        Pair[][] arp = new Pair[t][];
        int max = 0;

        for (int i = 0; i < t; i++) {
            arn[i] = in.readInt();
            max = Math.max(max, arn[i]);
            arm[i] = in.readInt();
            arp[i] = new Pair[arm[i]];

            for (int j = 0; j < arm[i]; j++)
                arp[i][j] = new Pair(in.readInt(), in.readInt());
        }

        populate(max);

        for (int i = 0; i < t; i++) {
            n = arn[i];
            ar = arp[i];
            format();
            out.println(process());
        }

    }

    private static void format() {
        if (ar.length == 0)
            return;

        if (ar.length == 1 && ar[0].a == ar[0].b) {
            ar = new Pair[0];
            return;
        }

        Arrays.sort(ar);

        int count = 0;
        if (ar[0].a != ar[0].b)
            count = 1;

        for (int i = 1; i < ar.length; i++)
            if (!ar[i].equals(ar[i - 1]) && ar[i].a != ar[i].b)
                count++;

        if (count == ar.length)
            return;

        if (count == 0) {
            ar = new Pair[0];
            return;
        }

        Pair[] temp = new Pair[count];
        int i = 1, j = 0;
        if (ar[0].a != ar[0].b) {
            temp[0] = ar[0];
            j = 1;
        }

        for (; j < count; i++) {
            if (!ar[i].equals(ar[i - 1]) && ar[i].a != ar[i].b) {
                temp[j++] = ar[i];
            }
        }

        ar = temp;
    }

    private static long process() {
        if (ar.length == 0 || n == 1)
            return fact[n];

        if (ar.length == 1) {
            return (fact[n] + ((mod - fact[n - 1]) << 1)) % mod;
        }

        long res = fact[n] + 1L * ar.length * mod;
        LinkedList<Pair> list = new LinkedList<Pair>();
        for (int i = 0; i < ar.length; i++)
            res -= process(i, list);

        return res % mod;
    }

    private static long process(int index, LinkedList<Pair> list) {
        list.addLast(ar[index]);
        long res = getPerm(list) + 1L * mod * ar.length;

        for (int i = index + 1; i < ar.length; i++) {
            res -= process(i, list);
        }

        list.removeLast();

        return res % mod;
    }

    private static long getPerm(LinkedList<Pair> listF) {
        if (listF.size() == 0)
            return fact[n];

        if (listF.size() == 1)
            return fact[n - 1] * 2 % mod;

        if (listF.size() == 2) {
            Pair a = listF.getFirst(), b = listF.getLast();
            if (a.a == b.a || a.a == b.b || a.b == b.a || a.b == b.b)
                return fact[n - 2] * 2 % mod;
            else
                return fact[n - 2] * 4 % mod;
        }

        LinkedList<Pair> list = new LinkedList<Pair>();

        for (Pair e : listF)
            list.add(e);

        int[] ar = new int[15];
        int i = 0;
        for (Pair e : list) {
            ar[i] = e.a;
            i++;
            ar[i] = e.b;
            i++;
        }

        Arrays.sort(ar);
        i = 0;
        while (ar[i] == 0)
            i++;

        i++;
        int k = i; // backup
        /**
         * checking triple occurance of a point in the list. if a point is
         * in three or more edges than it's impossible to make an arrangement.
         */
        for (; i < 15; i++)
            if (ar[i] == ar[i - 2])
                return 0;

        i = k;
        boolean duplicate = false;
        while (i < 15 && !duplicate) {
            if (ar[i] == ar[i - 1])
                duplicate = true;

            i++;
        }

        if (!duplicate) {
            return fact[n - list.size()] * (1L << list.size()) % mod;
        }

        LinkedList<Integer> temp = new LinkedList<Integer>();
        LinkedList<Integer>[] lar =
            (LinkedList<Integer>[])Array.newInstance(temp.getClass(),
                                                     list.size());
        int loc = 0;
        int j = 0;
        while (!list.isEmpty()) {
            boolean Flag = true;
            lar[j] = new LinkedList<Integer>();
            Pair first = list.removeFirst();
            lar[j].add(first.a);
            lar[j].add(first.b);
            while (!list.isEmpty() && Flag) {
                Flag = false;

                for (Pair e : list) {
                    if ((e.a == lar[j].getFirst() &&
                         e.b == lar[j].getLast()) ||
                        (e.b == lar[j].getFirst() && e.a == lar[j].getLast()))
                        return 0;
                }

                LinkedList<Pair> toBeRemoved = new LinkedList<Pair>();

                for (Pair e : list) {
                    if (e.a == lar[j].getFirst()) {
                        lar[j].addFirst(e.b);
                        toBeRemoved.add(e);
                        //                        list.remove(e);
                        Flag = true;
                        break;
                    } else if (e.a == lar[j].getLast()) {
                        lar[j].addLast(e.b);
                        toBeRemoved.add(e);
                        //                        list.remove(e);
                        Flag = true;
                        break;
                    } else if (e.b == lar[j].getFirst()) {
                        lar[j].addFirst(e.a);
                        toBeRemoved.add(e);
                        //                        list.remove(e);
                        Flag = true;
                        break;
                    } else if (e.b == lar[j].getLast()) {
                        lar[j].addLast(e.a);
                        toBeRemoved.add(e);
                        //                        list.remove(e);
                        Flag = true;
                        break;
                    }
                }

                for (Pair e : toBeRemoved)
                    list.remove(e);
            }
            j++;
        }

        int count = 0;
        loc = j;
        for (j = 0; j < loc; j++)
            count += lar[j].size();

        long res = (1L << loc);
        res = res * fact[n - count + loc] % mod;

        return res;
        //        return 563289; // not a random number
    }

    final static class Pair implements Comparable<Pair> {
        int a, b;

        Pair(int x, int y) {
            if (x < y) {
                a = x;
                b = y;
            } else {
                a = y;
                b = x;
            }
        }

        public boolean equals(Pair o) {
            return this.a == o.a && this.b == o.b;
        }

        public int compareTo(Pair o) {
            if (this.a != o.a)
                return this.a - o.a;

            return this.b - o.b;
        }
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

        public int readInt() throws IOException {
            int number = 0;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            if (bufferSize == -1)
                throw new IOException("No new bytes");
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                number = (number << 3) + (number << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            return number;
        }
    }
}
