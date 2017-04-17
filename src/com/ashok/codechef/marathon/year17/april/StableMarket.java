/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.april;

import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * Problem Name: Stable market
 * Link: https://www.codechef.com/APRIL17/problems/SMARKET
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class StableMarket {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
//        test();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            process();
        }
    }

    private static void process() throws IOException {
        int n = in.readInt(), q = in.readInt();
        int[] ar = in.readIntArray(n);
        StringBuilder sb = new StringBuilder(q << 2);

        int[] blockSizeArray = getBlockSizeArray(ar);
        int[][] blockIndexMap = getBlockIndexMap(blockSizeArray);

        while (q > 0) {
            q--;

            int L = in.readInt() - 1, R = in.readInt() - 1, k = in.readInt();
            sb.append(query(blockSizeArray, blockIndexMap, L, R, k)).append('\n');
//            sb.append(bruteForce(blockSizeArray, L, R, k)).append('\n');
        }

        out.print(sb);
    }

    private static int query(int[] blockSizeArray, int[][] blockIndexMap, int start, int end, int blockSize) {
        if (blockSize >= blockIndexMap.length)
            return 0;

        if (end == start)
            return 1;

        if (blockSize == end + 1 - start)
            return blockSizeArray[start] >= blockSize ? 1 : 0;

        int[] ar = blockIndexMap[blockSize];
        int startIndex = findAfter(ar, start + 1), endIndex = findBefore(ar, end + 1 - blockSize);
        int count = endIndex + 1 - startIndex;
        if (blockSizeArray[start] >= blockSize)
            count++;

        return count;
    }

    private static int[] getBlockSizeArray(int[] ar) {
        int[] blockSizeArray = new int[ar.length];
        Arrays.fill(blockSizeArray, 1);

        for (int i = ar.length - 2; i >= 0; i--) {
            if (ar[i] == ar[i + 1])
                blockSizeArray[i] = blockSizeArray[i + 1] + 1;
        }

        return blockSizeArray;
    }

    private static int[][] getBlockIndexMap(int[] blockArray) {
        int maxBlockSize = max(blockArray);
        LinkedList<Integer>[] blockListMap = new LinkedList[maxBlockSize + 1];

        for (int i = 0; i < blockArray.length; ) {
            int length = blockArray[i];
            ensureInitialized(blockListMap, length);
            blockListMap[length].addLast(i);
            i += length;
        }

        for (int i = maxBlockSize - 1; i > 0; i--) {
            ensureInitialized(blockListMap, i);
            blockListMap[i].addAll(blockListMap[i + 1]);
        }

        return toArray(blockListMap);
    }

    private static int max(int[] ar) {
        int max = ar[0];

        for (int e : ar)
            max = Math.max(e, max);

        return max;
    }

    private static void ensureInitialized(LinkedList<Integer>[] ar, int index) {
        if (ar[index] == null)
            ar[index] = new LinkedList<>();
    }

    private static int[][] toArray(LinkedList<Integer>[] listArray) {
        int[][] map = new int[listArray.length][];
        for (int i = 1; i < listArray.length; i++) {
            map[i] = toArray(listArray[i]);
        }

        return map;
    }

    private static int[] toArray(LinkedList<Integer> list) {
        int[] ar = new int[list.size()];
        int index = 0;

        for (int e : list)
            ar[index++] = e;

        Arrays.sort(ar);
        return ar;
    }

    private static int findAfter(int[] ar, int val) {
        int start = 0, end = ar.length - 1, mid = (start + end) >>> 1;
        if (ar[end] < val)
            return end + 1;

        while (start != mid) {
            if (ar[mid] == val) return mid;

            if (ar[mid] > val) end = mid;
            else start = mid;

            mid = (start + end) >>> 1;
        }

        return ar[mid] >= val ? mid : mid + 1;
    }

    private static int findBefore(int[] ar, int val) {
        int start = 0, end = ar.length - 1, mid = (start + end) >>> 1;

        while (start != mid) {
            if (ar[mid] == val) return mid;

            if (ar[mid] > val) end = mid;
            else start = mid;

            mid = (start + end) >>> 1;
        }

        if (ar[end] <= val) return end;

        return ar[mid] <= val ? mid : mid - 1;
    }

    private static int find(int[] ar, int val) {
        int start = 0, end = ar.length - 1, mid = (start + end) >>> 1;

        while (start != mid) {
            if (ar[mid] == val) return mid;

            if (ar[mid] > val) end = mid;
            else start = mid;

            mid = (start + end) >>> 1;
        }

        return ar[end] <= val ? end : mid;
    }

    private static int bruteForce(int[] blockSizeArray, int startIndex, int endIndex, int blockSize) {
        int count = 0, index = startIndex;

        while (index <= endIndex + 1 - blockSize) {
            if (blockSizeArray[index] >= blockSize)
                count++;

            index += blockSizeArray[index];
        }

        return count;
    }

    private static void test() throws IOException {
        Output output = new Output();
        while (true) {
            int n = in.readInt(), q = in.readInt();
            int[] ar = Generators.generateRandomIntegerArray(n, 1, 3);
            int[] blockSizeArray = getBlockSizeArray(ar);
            int[][] blockIndexMap = getBlockIndexMap(blockSizeArray);
            StringBuilder sb = new StringBuilder(q << 3);
            int[] lefts = Generators.generateRandomIntegerArray(q, 0, n - 1),
                    rights = Generators.generateRandomIntegerArray(q, 0, n - 1);

            int[] ks = new int[q];
            Random random = new Random();

            for (int i = 0; i < q; i++) {
                if (lefts[i] > rights[i]) {
                    int t = lefts[i];
                    lefts[i] = rights[i];
                    rights[i] = t;
                }

                ks[i] = random.nextInt(rights[i] - lefts[i] + 1) + 1;
            }

            for (int i = 0; i < q; i++) {
                int L = lefts[i], R = rights[i], k = ks[i];
                int count = 0;
                try {
                    count = query(blockSizeArray, blockIndexMap, L, R, k);
                } catch (Exception e) {
                    sb.append("Error: " + e.getMessage() + "\n");
                    count = -1;
                }
                int brCount = bruteForce(blockSizeArray, L, R, k);

                if (count != brCount) {
                    output.println("Error: you want to debug? 0 for No");
                    output.flush();

                    if (in.readInt() != 0)
                        count = query(blockSizeArray, blockIndexMap, L, R, k);

                    sb.append("for " + (L + 1) + " " + (R + 1) + " " + k + ", expected: " + brCount + " actual: " + count + "\n");
                }
            }

            output.print(ar);
            output.println(sb);
            output.flush();
        }
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

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
