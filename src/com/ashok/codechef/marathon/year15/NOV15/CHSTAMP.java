package com.ashok.codechef.marathon.year15.NOV15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Chef and collection of stamps
 * https://www.codechef.com/NOV15/problems/CHSTAMP
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class CHSTAMP {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CHSTAMP a = new CHSTAMP();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            int n = in.readInt(), m = in.readInt();
            int[] stamps = in.readIntArray(n);
            Offer[] offers = new Offer[m];
            for (int i = 0; i < m; i++)
                offers[i] =
                        new Offer(in.readInt(), in.readInt(), in.readInt());

            sb.append(process(stamps, offers)).append('\n');
        }
        out.print(sb);
    }

    private static long process(int[] stamp, Offer[] offer) {
        int max = stamp[0], n = stamp.length, m = offer.length;
        for (int i = 0; i < n; i++)
            max = Math.max(max, stamp[i]);

        for (int i = 0; i < m; i++)
            max = Math.max(max, offer[i].b);

        int[] ar = new int[max + 1];
        for (int i = 1; i <= max; i++)
            ar[i] = i;

        sort(offer);

        for (int i = 0; i < m; ) {
            int j = i + 1;
            while (j < m && offer[j].day == offer[j - 1].day)
                j++;

            if (j == i + 1) {
                int a = offer[i].a, b = offer[i].b;
                if (ar[a] > ar[b])
                    ar[b] = ar[a];
                else
                    ar[a] = ar[b];
                i++;
            } else {
                boolean cont = true;
                while (cont) {
                    cont = false;
                    for (int k = i; k < j; k++) {
                        int a = offer[k].a, b = offer[k].b;
                        if (ar[a] == ar[b])
                            continue;

                        cont = true;

                        if (ar[a] < ar[b]) {
                            ar[a] = ar[b];
                        } else {
                            ar[b] = ar[a];
                        }
                    }
                }

                i = j;
            }
        }

        long res = 0;
        for (int i = 0; i < n; i++)
            res += ar[stamp[i]];

        return res;
    }

    /**
     * The sorting algorithm used is count sort. As we know there can be at
     * max 50,000 days, So count sort is viable option.
     *
     * @param offer array of Offers to be sorted by day
     */
    private static void sort(Offer[] offer) {
        if (offer.length == 1)
            return;

        int max = 0;
        for (int i = 0; i < offer.length; i++)
            max = Math.max(max, offer[i].day);

        Array[] ar = new Array[max + 1];
        int[] count = new int[max + 1];

        for (Offer o : offer)
            count[o.day]++;

        for (int i = 1; i <= max; i++)
            if (count[i] > 0)
                ar[i] = new Array(count[i]);

        for (Offer o : offer)
            ar[o.day].add(o);

        for (int i = 0, j = max; i < offer.length && j > 0; j--) {
            if (ar[j] != null) {
                for (int k = 0; k < ar[j].size; k++, i++)
                    offer[i] = ar[j].get(k);
            }
        }
    }

    final static class Array {
        int size, index = 0;
        Offer[] ar;

        Array(int s) {
            size = s;
            ar = new Offer[s];
        }

        void add(Offer o) {
            ar[index++] = o;
        }

        Offer get(int n) {
            return ar[n];
        }
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

        public int readInt() throws IOException {
            int number = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            if (bufferSize == -1)
                throw new IOException("No new bytes");
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
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
            return number * s;
        }

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }

    final static class Offer implements Comparable<Offer> {
        int day, a, b;

        Offer(int day, int A, int B) {
            this.day = day;
            a = A;
            b = B;
        }

        public int compareTo(Offer o) {
            return o.day - this.day;
        }
    }
}
