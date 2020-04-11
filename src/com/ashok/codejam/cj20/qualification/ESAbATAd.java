/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.cj20.qualification;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ESAbATAd {
    private static final PrintWriter out = new PrintWriter(System.out);
    private static final InputReader in = new InputReader();
    private static final String CASE = "Case #";
    private static InterativeSystem interactiveSystem;

    public static void main(String[] args) throws IOException {
        interactiveSystem = new MockInteractiveSystem(in.readIntArray(in.readInt()));
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt(), b = in.readInt();
        for (int i = 1; i <= t; i++) {
            if (!interactiveSystem.checkAnswer(process(b))) return;
        }
    }

    private static String process(int bits) throws IOException {
        int[] bitArray = new int[bits];
        Arrays.fill(bitArray, -1);
        if (bits <= 10) {
            for (int i = 0; i < bits; i++) bitArray[i] = interactiveSystem.submitQuery(i + 1);
        } else {
            interact(bitArray);
        }

        StringBuilder sb = new StringBuilder();
        for (int e : bitArray) sb.append(e);
        return sb.toString();
    }

    private static void interact(int[] ar) throws IOException {
        /**
         * Lists containing indices, where values are same for all indices.
         */
        List<Integer> zeroValueIndices = new ArrayList<>(); // same value even when the array is reversed, 0
        List<Integer> oneValueIndices = new ArrayList<>(); // same value even when the array is reversed, 1
        List<Integer> differentValueIndices = new ArrayList<>(); // different value when the array is reversed, 0<->1

        int n = ar.length;
        int queries = 1, index = 1, counterPart = n;
        while (index <= counterPart) {
            int iv = interactiveSystem.submitQuery(index);
            queries++;
            int cpv = interactiveSystem.submitQuery(counterPart);
            queries++;

            if (iv == cpv) {
                if (iv == 0) {
                    zeroValueIndices.add(index);
                } else {
                    oneValueIndices.add(index);
                }
            } else {
                differentValueIndices.add(iv == 0 ? index : counterPart);
            }

            index++;
            counterPart--;

            if (queries % 10 == 1) { // time to reset list of indices.
                int zv = 0, ov = 1, df = 0;
                if (!zeroValueIndices.isEmpty()) {
                    zv = interactiveSystem.submitQuery(zeroValueIndices.get(0));
                    queries++;
                }

                if (zeroValueIndices.isEmpty() && !oneValueIndices.isEmpty()) {
                    ov = interactiveSystem.submitQuery(oneValueIndices.get(0));
                    queries++;
                }

                if (zv == 1 || ov == 0) {
                    List<Integer> temp = zeroValueIndices;
                    zeroValueIndices = oneValueIndices;
                    oneValueIndices = temp;
                }

                if (!differentValueIndices.isEmpty()) {
                    df = interactiveSystem.submitQuery(differentValueIndices.get(0));
                    queries++;
                }

                if (df == 1) {
                    List<Integer> temp = differentValueIndices.stream().map(v -> n + 1 - v).collect(Collectors.toList());
                    differentValueIndices = temp;
                }
            }
        }

        zeroValueIndices.stream().forEach(v -> ar[v - 1] = 0);
        zeroValueIndices.stream().forEach(v -> ar[n - v] = 0);
        oneValueIndices.stream().forEach(v -> ar[v - 1] = 1);
        oneValueIndices.stream().forEach(v -> ar[n - v] = 1);
        differentValueIndices.stream().forEach(v -> ar[v - 1] = 0);
        differentValueIndices.stream().forEach(v -> ar[n - v] = 1);
    }

    interface InterativeSystem {
        boolean checkAnswer(String answer) throws IOException;

        int submitQuery(int n) throws IOException;
    }

    final static class DefaultInteractiveSystem implements InterativeSystem {

        @Override
        public boolean checkAnswer(String answer) throws IOException {
            out.println(answer);
            out.flush();
            String result = in.read();
            return result.equals("Y");
        }

        @Override
        public int submitQuery(int n) throws IOException {
            out.println(n);
            out.flush();
            return in.readInt();
        }
    }

    final static class MockInteractiveSystem implements InterativeSystem {
        private static final int flip = 0, reverse = 1, combo = 2, nothing = 3;

        private final int[] bits;
        private final Random random = new Random();
        private int queryCount = 0;

        MockInteractiveSystem(int[] bits) {
            this.bits = bits.clone();
        }

        @Override
        public boolean checkAnswer(String answer) throws IOException {
            out.println("validating " + answer);
            out.flush();
            if (answer.length() != bits.length) return false;
            char[] chars = answer.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                int bit = chars[i] - '0';
                if (bit != bits[i]) return false;
            }

            out.println("validation succeeded");
            out.flush();
            return true;
        }

        @Override
        public int submitQuery(int n) throws IOException {
            if (queryCount % 10 == 0) refresh();
            queryCount++;
            return bits[n - 1];
        }

        private void refresh() {
            int op = random.nextInt(4);
            switch (op) {
                case flip:
                    toggle();
                    break;
                case reverse:
                    reverse();
                    break;
                case combo:
                    toggle();
                    reverse();
                    break;
            }
        }

        private void toggle() {
            for (int i = 0; i < bits.length; i++) {
                bits[i] = 1 ^ bits[i];
            }
        }

        private void reverse() {
            for (int i = 0, j = bits.length - 1; i < j; i++, j--) {
                int temp = bits[i];
                bits[i] = bits[j];
                bits[j] = temp;
            }
        }
    }

    private static class InputReader {
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;
        InputStream in;

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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
