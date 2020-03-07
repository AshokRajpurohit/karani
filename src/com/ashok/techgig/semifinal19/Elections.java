/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.techgig.semifinal19;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Problem Name: Elections
 * Link: Code Gladiators 2019 Semifinal
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Elections {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        Node node = new Node(3);
        out.println(node.oneCount + ", " + node.onePlusCount + ", " + node.ops);
        out.flush();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int phases = in.readInt(), states = in.readInt();
            int[] phaseCounts = in.readIntArray(phases), stateCounts = in.readIntArray(states);
            out.println(process(phaseCounts, stateCounts) ? "YES" : "NO");
        }
    }

    private static boolean process(int[] phaseCounts, int[] stateCounts) {
        phaseCounts = Arrays.stream(phaseCounts).filter(i -> i > 0).toArray();
        stateCounts = Arrays.stream(stateCounts).filter(i -> i > 0).toArray();
        if (phaseCounts.length == 0 && stateCounts.length == 0) return false;
        long phaseTotal = sum(phaseCounts), stateTotal = sum(stateCounts);
        int phaseMax = Arrays.stream(phaseCounts).max().orElse(0), stateMax = Arrays.stream(stateCounts).max().orElse(0);
        if (phaseTotal != stateTotal || phaseMax > stateCounts.length || stateMax > phaseCounts.length) return false;
        return phaseWay0(phaseCounts, stateCounts) || stateWay(phaseCounts, stateCounts);
    }

    private static boolean bruteForce(int[] phaseCounts, int[] stateCounts) {
        Arrays.sort(phaseCounts);
        Arrays.sort(stateCounts);
        reverse(phaseCounts);
        reverse(stateCounts);

        for (int e : stateCounts) {
            decrement(phaseCounts, e);
            if (!validate(phaseCounts, e)) return false;
            restructure(phaseCounts, e - 1);
        }

        return true;
    }

    private static boolean bruteForce1(int[] phaseCounts, int[] stateCounts) {
        Arrays.sort(stateCounts);
        reverse(stateCounts);
        PriorityQueue<Integer> phases = new PriorityQueue<>((a, b) -> b - a);
        for (int phaseCount : phaseCounts) {
            phases.add(phaseCount);
        }

        List<Integer> pcs = new ArrayList<>();
        for (int stateCount : stateCounts) {
            if (phases.size() < stateCount) return false;
            pcs.clear();
            while (stateCount > 0) {
                stateCount--;
                pcs.add(phases.remove());
            }

            pcs.stream().filter(i -> i > 1).forEach(i -> phases.add(i));
        }

        return true;
    }

    private static boolean phaseWay(int[] phaseCounts, int[] stateCounts) {
        Arrays.sort(phaseCounts);
        Arrays.sort(stateCounts);
        reverse(stateCounts);
        int index = 0, ops = 0, pl = phaseCounts.length;
        int[] delta = new int[pl];
        for (int e : stateCounts) {
            if (index + e > pl) return false;
            if (index + e != pl) delta[index + e]--;
            ops++;
            if (phaseCounts[index] > ops) continue;
            index++;
            int burden = 0;
            while (index < pl) {
                ops += delta[index];
                burden += ops - phaseCounts[index];
                if (burden < 0) break;
                index++;
            }

            if (burden > 0) return false;
        }

        return index == pl;
    }

    private static boolean phaseWay0(int[] phaseCounts, int[] stateCounts) {
        Arrays.sort(phaseCounts);
        Arrays.sort(stateCounts);
        reverse(phaseCounts);
        int index = 0, ops = 0, len = stateCounts.length;
        int[] opCounts = new int[len];
        for (int pc : phaseCounts) {
            if (index + pc > len) return false;
            if (index + pc < len) opCounts[index + pc]--;
            ops++;
            if (stateCounts[index] > ops) continue;
            do {
                index++;
            } while (index < len && stateCounts[index] <= (ops += opCounts[index]));
        }

        return true;
    }

    private static boolean stateWay(int[] phaseCounts, int[] stateCounts) {
        return phaseWay0(stateCounts, phaseCounts);
    }

    private static boolean phaseWay1(int[] phaseCounts, int[] stateCounts) {
        return false;
    }

    private static void decrement(int[] ar, int len) {
        for (int i = 0; i < len; i++) ar[i]--;
    }

    private static boolean validate(int[] ar, int len) {
        return ar[len - 1] >= 0;
    }

    private static void restructure(int[] ar, int index) {
        if (index == ar.length - 1 || ar[index] >= ar[index + 1]) return;
        int small_count = countBackwards(ar, index), large_count = countForward(ar, index + 1);
        int swapLength = Math.min(small_count, large_count);
        swap(ar, index + 1 - small_count, index + 1 + large_count - swapLength, swapLength);
    }

    private static int countForward(int[] ar, int index) {
        int count = 1;
        for (int i = index + 1; i < ar.length && ar[i] == ar[index]; i++) count++;
        return count;
    }

    private static int countBackwards(int[] ar, int index) {
        int count = 1;
        for (int i = index - 1; i >= 0 && ar[i] == ar[index]; i--) count++;
        return count;
    }

    private static void swap(int[] ar, int src, int tgt, int len) {
        while (len > 0) {
            len--;
            int temp = ar[src];
            ar[src] = ar[tgt];
            ar[tgt] = temp;
            src++;
            tgt++;
        }
    }

    private static void reverse(int[] ar) {
        for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
            int temp = ar[i];
            ar[i] = ar[j];
            ar[j] = temp;
        }
    }

    private static long sum(int[] ar) {
        long sum = 0;
        for (int e : ar) sum += e;
        return sum;
    }

    private static int max(int[] ar) {
        int max = ar[0];
        for (int e : ar) if (e > max) max = e;
        return max;
    }

    final static class Node {
        final int size;
        int oneCount, onePlusCount, ops, min = Integer.MAX_VALUE;

        Node(int size) {
            this.size = size;
            oneCount++;
        }

        void apply(int count) {

        }

        int remaining() {
            return oneCount + onePlusCount;
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