package com.ashok.codechef.marathon.year16.MARCH16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Problem: Chef And Hungry Birds
 * https://www.codechef.com/MARCH16/problems/HBIRD
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class HBIRD {

    private static PrintWriter out;
    private static InputStream in;
    private int[][] rect;
    private int[][] hsum, vsum;
    private Pair[] pairs;
    private int[] countSum;
    private long[] valueSum;
    int n, m;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        HBIRD a = new HBIRD();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        n = in.readInt();
        m = in.readInt();
        int q = in.readInt();
        StringBuilder sb = new StringBuilder(q << 2);
        rect = new int[n][];

        for (int i = 0; i < n; i++)
            rect[i] = in.readIntArray(m);

        process();

        while (q > 0) {
            q--;
            sb.append(find(in.readInt())).append('\n');
        }

        out.print(sb);
    }

    private int find(int grains) {
        int index = search(grains);
        int count = 0;
        if (index >= 0) {
            count = countSum[index];
            grains -= valueSum[index];
        }

        if (grains == 0 || index >= pairs.length - 1)
            return count;

        count += grains / pairs[index + 1].value;
        return count;
    }

    private int search(int value) {
        if (value < valueSum[0])
            return -1;

        if (value >= valueSum[pairs.length - 1])
            return pairs.length - 1;

        int start = 0, end = pairs.length - 1;
        int mid = (start + end) >>> 1;

        while (mid > start) {
            if (value >= valueSum[mid])
                start = mid;
            else
                end = mid;

            mid = (start + end) >>> 1;
        }

        if (value >= valueSum[mid])
            return mid;

        return start;
    }

    private void process() {
        hsum = new int[n][m];
        vsum = new int[n][m];

        for (int i = 0; i < n; i++) {
            hsum[i][0] = rect[i][0];
        }

        for (int i = 0; i < m; i++) {
            vsum[0][i] = rect[0][i];
        }

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < m; j++) {
                hsum[i][j] = hsum[i][j - 1] + rect[i][j];
            }
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < m; j++) {
                vsum[i][j] = vsum[i - 1][j] + rect[i][j];
            }
        }

        HashMap<Integer, Single> map = new HashMap<Integer, Single>(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                put(map, i, j);
            }
        }

        pairs = new Pair[map.size()];
        int index = 0;

        for (Map.Entry<Integer, Single> entry : map.entrySet()) {
            pairs[index] = new Pair(entry.getKey(), entry.getValue().count);
            index++;
        }

        Arrays.sort(pairs);

        countSum = new int[pairs.length];
        valueSum = new long[pairs.length];

        countSum[0] = pairs[0].count;
        valueSum[0] = 1L * pairs[0].count * pairs[0].value;

        for (int i = 1; i < pairs.length; i++) {
            countSum[i] = countSum[i - 1] + pairs[i].count;
            valueSum[i] =
                    valueSum[i - 1] + 1L * pairs[i].count * pairs[i].value;
        }
    }

    private void put(HashMap<Integer, Single> map, int x, int y) {
        int value = rect[x][y];

        for (int i = x + 1, j = y + 1; i < n && j < m; i++, j++) {
            value += sameRow(i, y, j);
            put(map, value);
        }

        value = rect[x][y];
        for (int i = x + 1, j = y - 1; i < n && j >= 0; i++, j--) {
            value += sameRow(i, j, y);
            put(map, value);
        }

        value = rect[x][y];
        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
            value += sameRow(i, j, y);
            put(map, value);
        }

        value = rect[x][y];
        for (int i = x - 1, j = y + 1; i >= 0 && j < m; i--, j++) {
            value += sameRow(i, y, j);
            put(map, value);
        }

        value = rect[x][y];
        for (int i = x + 1, j = y + 1, k = y - 1; i < n && j < m && k >= 0;
             i++, j++, k--) {
            value += sameRow(i, k, j);
            put(map, value);
        }

        value = rect[x][y];
        for (int i = x - 1, j = y + 1, k = y - 1; i >= 0 && j < m && k >= 0;
             i--, j++, k--) {
            value += sameRow(i, k, j);
            put(map, value);
        }

        value = rect[x][y];
        for (int i = x - 1, j = x + 1, k = y + 1; i >= 0 && j < n && k < m;
             i--, j++, k++) {
            value += sameCol(k, i, j);
            put(map, value);
        }

        value = rect[x][y];
        for (int i = x - 1, j = x + 1, k = y - 1; i >= 0 && j < n && k >= 0;
             i--, j++, k--) {
            value += sameCol(k, i, j);
            put(map, value);
        }
    }

    private int sameRow(int row, int start, int end) {
        if (start == 0)
            return hsum[row][end];

        return hsum[row][end] - hsum[row][start - 1];
    }

    private int sameCol(int col, int start, int end) {
        if (start == 0)
            return vsum[end][col];

        return vsum[end][col] - vsum[start - 1][col];
    }

    private void put(HashMap<Integer, Single> map, int value) {
        if (value == 0)
            return;

        if (map.containsKey(value)) {
            Single single = map.get(value);
            single.count++;
        } else
            map.put(value, new Single());
    }

    final static class Single {
        int count = 1;
    }

    final static class Pair implements Comparable<Pair> {
        int value, count;

        Pair(int v, int c) {
            value = v;
            count = c;
        }

        public int compareTo(Pair pair) {
            return this.value - pair.value;
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
}
