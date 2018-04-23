package com.ashok.friends.tridip.migo;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.InvalidParameterException;

public class ValidBinarySearchTree {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final String YES = "YES", NO = "NO";

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            TreeValidator validator = new PreOrderTreeTraversalValidator(ar);
            out.println(validator.validate() ? YES : NO);
        }
    }

    private static int[] nextHigher(int[] ar) {
        int[] res = new int[ar.length];
        res[ar.length - 1] = ar.length;

        for (int i = ar.length - 2; i >= 0; i--) {
            int j = i + 1;

            while (j < ar.length && ar[j] <= ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    interface TreeValidator {
        boolean validate();
    }

    final static class PreOrderTreeTraversalValidator implements TreeValidator {
        final int[] ar, nextHigher;
        final SparseTable minElementSearch, maxElementSearch;

        PreOrderTreeTraversalValidator(int[] ar) {
            this.ar = ar;
            nextHigher = nextHigher(ar);
            minElementSearch = new SparseTable(ar, (a, b) -> Math.min(a, b));
            maxElementSearch = new SparseTable(ar, (a, b) -> Math.max(a, b));
        }

        @Override
        public boolean validate() {
            return validate(0, ar.length - 1);
        }

        private boolean validate(int from, int to) {
            if (from >= to)
                return true;

            int root = ar[from], right = nextHigher[from];
            if (right <= to && minElementSearch.query(right, to) <= root)
                return false;

            if (right > from + 1 && maxElementSearch.query(from + 1, right - 1) > root)
                return false;

            return validate(from + 1, right - 1) && validate(right, to);
        }
    }

    @FunctionalInterface
    interface Operator {
        int operate(int a, int b);
    }

    final static class SparseTable {
        private int[][] mar;
        final Operator operator;

        public SparseTable(int[] ar, Operator operator) {
            this.operator = operator;
            format(ar);
        }

        public int query(int L, int R) {
            if (R >= mar[1].length || L < 0)
                throw new IndexOutOfBoundsException(L + ", " + R);

            if (L > R)
                throw new InvalidParameterException("start index can not be after end index: " + L + ", " + R);

            int half = Integer.highestOneBit(R + 1 - L);
            return operator.operate(mar[half][L], mar[half][R + 1 - half]);
        }

        private void format(int[] ar) {
            mar = new int[ar.length + 1][];
            mar[1] = ar;
            int bit = 2;
            while (bit < mar.length) {
                int half = bit >>> 1;
                mar[bit] = new int[ar.length - bit + 1];
                for (int i = 0; i <= ar.length - bit; i++) {
                    mar[bit][i] = operator.operate(mar[half][i], mar[half][i + half]);
                }
                bit <<= 1;
            }
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