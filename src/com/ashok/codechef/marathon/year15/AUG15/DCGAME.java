package com.ashok.codechef.marathon.year15.AUG15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Devu And Churu
 * https://www.codechef.com/AUG15/problems/DCGAME
 */

class DCGAME {

    private static PrintWriter out;
    private static InputStream in;
    private int[] ar, sort, left, right;
    private long[] counts, sums;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        DCGAME a = new DCGAME();
        a.solve();
        out.close();
    }

    /**
     * @throws IOException
     */
    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int m = in.readInt();
        StringBuilder sb = new StringBuilder(m);
        ar = in.readIntArray(n);
        process();
        char xor = (char)('D' ^ 'C');

        for (int i = 0; i < m; ++i) {
            String qtype = in.read(1);
            int num = in.readInt();
            String player = in.read(1);
            int index = 0;
            long count = 0;
            if (qtype.charAt(0) == '=') {
                index = find(num);
                if (index != -1 && ar[index] == num) {
                    count = sums[index];
                    if (index > 0)
                        count -= sums[index - 1];
                }
            } else if (qtype.charAt(0) == '<') {
                index = find(num - 1);
                if (index != -1)
                    count = sums[index];
            } else {
                index = find(num);
                if (index != -1)
                    count = sums[ar.length - 1] - sums[index];
                else
                    count = sums[ar.length - 1];
            }
            if ((count & 1) == 1)
                sb.append(player.charAt(0));
            else
                sb.append((char)(xor ^ player.charAt(0)));
        }
        out.println(sb);
    }

    private void process() {
        sort = sortIndex(ar);
        setLeftArray();
        setRightArray();
        setCounts();
        setSums();
        restruct();
    }

    private void setLeftArray() {
        left = new int[ar.length];
        left[0] = -1;
        for (int i = 1; i < ar.length; i++)
            left[i] = getLeft(ar, left, i);
    }

    private void setRightArray() {
        right = new int[ar.length];
        right[ar.length - 1] = ar.length;
        for (int i = ar.length - 2; i >= 0; i--)
            right[i] = getRight(ar, right, i);
    }

    private void setCounts() {
        counts = new long[ar.length];
        for (int i = 0; i < ar.length; i++)
            counts[i] = getCount(left, right, sort[i]);
    }

    private void setSums() {
        sums = new long[ar.length];
        sums[0] = counts[0];
        for (int i = 1; i < ar.length; i++)
            sums[i] = sums[i - 1] + counts[i];
    }

    private void restruct() {
        int len = 1;
        for (int i = 1; i < ar.length; i++)
            if (ar[sort[i]] != ar[sort[i - 1]])
                len++;

        int[] sar = new int[len];
        sar[0] = ar[sort[0]];
        for (int i = 1, j = 1; i < ar.length && j < len; i++)
            if (ar[sort[i]] != ar[sort[i - 1]]) {
                sar[j] = ar[sort[i]];
                j++;
            }

        long[] sums_temp = new long[len];
        /*
        for (int i = 1, j = 0; i < ar.length && j < len; i++)
            if (ar[sort[i]] != ar[sort[i - 1]]) {
                sums_temp[j] = sums[i - 1];
                j++;
            }
        */
        sums_temp[len - 1] = sums[ar.length - 1];
        for (int i = ar.length - 2, j = len - 2; i >= 0 && j >= 0; i--)
            if (ar[sort[i]] != ar[sort[i + 1]]) {
                sums_temp[j] = sums[i];
                j--;
            }

        ar = sar;
        sums = sums_temp;
    }

    private int find(int n) {
        if (n < ar[0])
            return -1;

        if (n >= ar[ar.length - 1])
            return ar.length - 1;

        int start = 0, end = ar.length - 1, mid = (start + end) >>> 1;
        while (start != mid) {
            if (ar[mid] == n)
                return mid;

            if (ar[mid] > n)
                end = mid;
            else
                start = mid;

            mid = (start + end) >>> 1;
        }
        return start;
    }

    /**
     * Returns the nearest non-smaller element left to the element at index.
     * @param ar
     * @param left left array which contains the left index of
     * @param index element to compare
     * @return
     */
    private static int getLeft(int[] ar, int[] left, int index) {
        int value = ar[index];
        index--;
        while (index >= 0 && ar[index] < value) {
            index = left[index];
        }
        return index;
    }

    /**
     *  Returns the index of nearest element in right side which is
     *  equal to or greater than the element at index.
     * @param ar
     * @param right
     * @param index
     * @return
     */
    private static int getRight(int[] ar, int[] right, int index) {
        int value = ar[index];
        index++;
        while (index < ar.length && ar[index] <= value) {
            index = right[index];
        }
        return index;
    }

    /**
     * This function gives the number of sub-arrays for which
     * the maximum element is ar[sort[index]].
     * @param left
     * @param right
     * @param index
     * @return
     */
    private static long getCount(int[] left, int[] right, int index) {
        return 1L * (index - left[index]) * (right[index] - index);
    }

    /**
     * find function returns the index in sort array the element at ar[sort[index]]
     * is smaller than or equal to the integer n.
     * let's say the ar is 1, 3, 6, 2, 4
     * and so sort array is 0, 3, 1, 4, 2
     * if we want to search for number 5 then it should return 3
     * as at index 3 in sort array the value is 4 and at the 4th index the value is 4
     * smaller than 5 but at index 4 the value in sort is 2 and so value in ar is 6
     * that is greater than 5.
     *
     *
     * @param ar original array to compare values
     * @param sort sorted indexed array of array ar.
     * @param n the queried number.
     * @return the index in sort array.
     */
    private static int find(int[] ar, int[] sort, int n) {
        if (n < ar[sort[0]])
            return -1;

        if (n >= ar[sort[ar.length - 1]])
            return ar.length - 1;

        int start = 0, end = ar.length - 1, mid = (start + end) >>> 1;
        while (start != mid) {
            if (ar[sort[mid]] <= n)
                start = mid;
            else
                end = mid;
            mid = (start + end) >>> 1;
        }
        return start;
    }

    private static int[] sortIndex(int[] a) {
        int[] b = new int[a.length];
        int[] c = new int[a.length];
        for (int i = 0; i < a.length; ++i) {
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

    private static void merge(int[] a, int[] b, int[] c, int begin, int end) {
        int mid = (begin + end) / 2;
        int i = begin;
        int j = mid + 1;
        int k = begin;
        while (i <= mid && j <= end) {
            if (a[c[i]] > a[c[j]]) {
                b[k] = c[j];
                ++j;
            } else {
                b[k] = c[i];
                ++i;
            }
            ++k;
        }
        if (j <= end) {
            while (j <= end) {
                b[k] = c[j];
                ++k;
                ++j;
            }
        }
        if (i <= mid) {
            while (i <= mid) {
                b[k] = c[i];
                ++i;
                ++k;
            }
        }

        i = begin;
        while (i <= end) {
            c[i] = b[i];
            ++i;
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
            for (int i = 0; i < n; ++i)
                ar[i] = readInt();

            return ar;
        }

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
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
            for (int i = 0; offset < bufferSize && i < n; ++offset) {
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
