package com.ashok.leetcode.practice;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link: https://leetcode.com/problems/two-sum/
 */

public class TwoSum {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        TwoSum a = new TwoSum();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            int sum = in.readInt();
            int[] ar = in.readIntArray(n);
            System.out.println(ar[28] + ar[45]);

            for (int e : twosum(sum, ar))
                out.print(e + "\t");
            out.println();
        }
    }

    private static int[] twosum(int sum, int[] ar) {
        int[] sar = MergeSort.sortIndex(ar);
        int i = 0, j = ar.length - 1;

        while (ar[sar[i]] + ar[sar[j]] != sum) {
            while (j > 0 && ar[sar[i]] + ar[sar[j]] > sum)
                j--;

            while (ar[sar[i]] + ar[sar[j]] < sum)
                i++;
        }

        int[] ret = { (sar[i] + 1), (sar[j] + 1) };

        if (ret[0] > ret[1]) {
            int c = ret[0];
            ret[0] = ret[1];
            ret[1] = c;
        }

        return ret;
    }

    final static class MergeSort {
        private MergeSort() {
            super();
        }

        public static void sort(int[] a) {
            int[] b = new int[a.length];
            sort(a, b, 0, a.length - 1);
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

        private static void sort(int[] a, int[] b, int[] c, int begin,
                                 int end) {
            if (begin == end) {
                return;
            }

            int mid = (begin + end) / 2;
            sort(a, b, c, begin, mid);
            sort(a, b, c, mid + 1, end);
            merge(a, b, c, begin, end);
        }

        public static void merge(int[] a, int[] b, int[] c, int begin,
                                 int end) {
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

        private static void sort(int[] a, int[] b, int begin, int end) {
            if (begin == end) {
                return;
            }

            int mid = (begin + end) / 2;
            sort(a, b, begin, mid);
            sort(a, b, mid + 1, end);
            merge(a, b, begin, end);
        }

        private static void merge(int[] a, int[] b, int begin, int end) {
            int mid = (begin + end) / 2;
            int i = begin;
            int j = mid + 1;
            int k = begin;
            while (i <= mid && j <= end) {
                if (a[i] > a[j]) {
                    b[k] = a[j];
                    j++;
                } else {
                    b[k] = a[i];
                    i++;
                }
                k++;
            }
            if (j <= end) {
                while (j <= end) {
                    b[k] = a[j];
                    k++;
                    j++;
                }
            }
            if (i <= mid) {
                while (i <= mid) {
                    b[k] = a[i];
                    i++;
                    k++;
                }
            }

            i = begin;
            while (i <= end) {
                a[i] = b[i];
                i++;
            }
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

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }

        public String read() throws IOException {
            StringBuilder sb = new StringBuilder();
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                 '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                    buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }
    }
}
