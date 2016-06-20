package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 * problem: The Competitive Class
 */

/**
 * Problem Statement:
 * Jiro is a fun loving lad. His classmates are very competitive and are
 * striving for getting a good rank, while he loves seeing them fight for it.
 * The results are going to be out soon. He is anxious to know what is the rank
 * of each of his classmates. Rank of i'th student = 1+ (Number of students
 * having strictly greater marks than him. Students with equal marks are ranked
 * same.) Can you help Jiro figure out rank of all his classmates?
 * Input:
 * The first line contains an integers N, denoting the number of students in
 * the class.
 * The second line contains N space separated integers, Mi are the marks of
 * the i'th student.
 * Ouput:
 * Print N space separated integers i.e., ranks of the students in a new line.
 *
 */

public class XSEEDB {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        XSEEDB a = new XSEEDB();
        a.solve();
        out.close();
    }

    private static int[] process(int[] ar) {
        int[] rank = sortIndex(ar);
        int[] result = new int[ar.length];
        result[rank[0]] = 1;
        int count = 2;

        for (int i = 1; i < ar.length; i++) {
            if (ar[rank[i]] == ar[rank[i - 1]]) {
                result[rank[i]] = result[rank[i - 1]];
                count++;
            } else {
                result[rank[i]] = count;
                count++;
            }
        }

        return result;
    }

    private static int[] sortIndex(int[] a) {
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
            if (a[c[i]] < a[c[j]]) {
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

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] ar = in.readIntArray(n);
        StringBuilder sb = new StringBuilder(n << 2);

        for (int e : process(ar))
            sb.append(e).append(' ');

        sb.append('\n');
        out.print(sb);
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
}
