package com.ashok.codechef.marathon.year15.OCT15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Arrays;

/**
 * Problem: Misha and Gym
 * https://www.codechef.com/OCT15/problems/MGCHGYM
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class MGCHGYM {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] w;
    private static int N, min;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        MGCHGYM a = new MGCHGYM();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        N = in.readInt();
        int q = in.readInt();
        w = in.readIntArray(N);
        StringBuilder sb = new StringBuilder();
        String yes = "Yes\n", no = "No\n";

        while (q > 0) {
            q--;
            int t = in.readInt();
            if (t == 1)
                replace(in.readInt() - 1, in.readInt());
            else if (t == 2)
                reverse(in.readInt() - 1, in.readInt() - 1);
            else {
                if (query(in.readInt() - 1, in.readInt() - 1, in.readInt()))
                    sb.append(yes);
                else
                    sb.append(no);
            }
        }

        out.print(sb);
    }

    private static void replace(int index, int value) {
        w[index] = value;
    }

    private static void reverse(int start, int end) {
        for (int i = start, j = end; i < j; i++, j--) {
            int temp = w[i];
            w[i] = w[j];
            w[j] = temp;
        }
    }

    private static void reverse() {

    }

    private static int getIndex(int n, int[] left, int[] right) {
        for (int i = 0; i < left.length; i++) {
            n = left[i] + right[i] - n;
        }
        return n;
    }

    private static boolean query(int start, int end, int value) {
        if (start == end)
            return value == w[start];

        int min = Integer.MAX_VALUE;
        for (int i = start; i <= end; i++)
            min = Math.min(min, w[i]);

        if (value < min)
            return false;

        long sum = 0;
        for (int i = start; i <= end; i++)
            sum += w[i];

        if (value > sum)
            return false;

        if (value == sum)
            return true;

        int len = 10 > end - start + 1 ? end - start + 1 : 10;
        int[] ar = new int[len];
        int count = 1;
        ar[0] = w[start];
        for (int i = start + 1; i <= end; i++) {
            int j = 0;
            for (j = 0; j < count; j++)
                if (w[i] == ar[j]) {
                    break;
                }

            if (j == count) {
                count++;
                ar[j] = w[i];
            }
        }

        int[] war = new int[count];
        for (int i = 0; i < count; i++)
            war[i] = ar[i];

        Arrays.sort(war);
        int[] var = new int[count];

        for (int i = start; i <= end; i++) {
            int j = 0;
            for (; j < count; j++)
                if (w[i] == war[j]) {
                    var[j]++;
                    break;
                }
        }

        int[] sin = sortIndex(var);
        ar = new int[count];
        for (int i = 0; i < count; i++)
            ar[i] = war[sin[i]];

        int[] vcount = new int[count];
        for (int i = 0; i < count; i++)
            vcount[i] = var[sin[i]];

        int[] gar = new int[count]; // gcd array from right side
        gar[count - 1] = ar[count - 1];
        for (int i = count - 2; i >= 0; i--)
            gar[i] = gcd(gar[i + 1], ar[i]);

        int[] mar = new int[count]; // min array from right side
        mar[count - 1] = ar[count - 1];
        for (int i = count - 2; i >= 0; i--)
            mar[i] = Math.min(ar[i], mar[i + 1]);

        return check(war, var, gar, mar, value, sum, 0);
    }

    private static boolean check(int[] ar, int[] count, int[] gcd, int[] min,
                                 int value, long sum, int index) {
        if (value == 0 || value == sum || value == min[index])
            return true;

        if (value < min[index] || value > sum || value % gcd[index] != 0)
            return false;

        if (index == ar.length - 1)
            return value <= ar[index] * count[index] && value % ar[index] == 0;

        if (index == ar.length - 2)
            return checkTwo(ar, count, gcd, value, index);

        int vcount = count[index];
        sum -= 1L * ar[index] * count[index];
        for (int i = 0; i <= vcount && value >= 0; i++) {
            if (check(ar, count, gcd, min, value, sum, index + 1))
                return true;

            value -= ar[index];
        }
        return false;
    }

    private static boolean checkTwo(int[] ar, int[] count, int[] gcd,
                                    int value, int index) {
        if (value % gcd[index] != 0)
            return false;

        if (value % ar[index] == 0 && value <= ar[index] * count[index])
            return true;

        if (value % ar[index + 1] == 0 &&
            value <= ar[index + 1] * count[index + 1])
            return true;

        int t = count[index];
        int nextval = ar[index + 1];
        int diff = ar[index];
        int nextCount = count[index + 1];

        //        if (count[index + 1] < t) {
        //            t = count[index + 1];
        //            nextval = ar[index];
        //            diff = ar[index + 1];
        //            nextCount = count[index];
        //        }
        while (t > 0 && value >= nextval) {
            if (value % nextval == 0 && value <= nextval * nextCount)
                return true;

            value -= diff;
            t--;
        }

        return false;
    }

    private static int gcd(int a, int b) {
        if (b == 0)
            return a;

        return gcd(b, a % b);
    }

    private static void format() {
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

    public static int[] sortIndex(int[] a) {
        int[] b = new int[a.length];
        int[] c = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = i;
        }
        sort(a, b, c, 0, a.length - 1);
        return c;
    }

    private static void sort(int[] a, int[] b, int[] c, int begin, int end) {
        if (begin == end) {
            return;
        }

        int mid = (begin + end) / 2;
        sort(a, b, c, begin, mid);
        sort(a, b, c, mid + 1, end);
        merge(a, b, c, begin, end);
    }

    public static void merge(int[] a, int[] b, int[] c, int begin, int end) {
        int mid = (begin + end) / 2;
        int i = begin;
        int j = mid + 1;
        int k = begin;
        while (i <= mid && j <= end) {
            if (a[c[i]] > a[c[j]]) {
                b[k] = c[j];
                j++;
            } else {
                b[k] = c[i];
                i++;
            }
            k++;
        }
        if (j <= end) {
            while (j <= end) {
                b[k] = c[j];
                k++;
                j++;
            }
        }
        if (i <= mid) {
            while (i <= mid) {
                b[k] = c[i];
                i++;
                k++;
            }
        }

        i = begin;
        while (i <= end) {
            c[i] = b[i];
            i++;
        }
    }
}
