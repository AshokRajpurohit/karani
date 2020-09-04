/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.june20;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

/**
 * Problem Name: Covid Sampling
 * Link: https://www.codechef.com/JUNE20A/problems/COVDSMPL
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class CovidSampling {
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
            int n = in.readInt(), p = in.readInt();
            Grader grader = new CodeChefGrader();
            CovidSampler sampler = new CovidRowSampler(n, p, grader);
            GraderReponses reponse = sampler.run();
            if (reponse != GraderReponses.CONTINUE) break;
        }
    }

    private static void test() throws IOException {
        while (true) {
            int n = in.readInt(), p = in.readInt();
            while (true) {
                Grader grader = new CustomGrader(n, p);
                CovidSampler sampler = new CovidRowSampler(n, p, grader);
                GraderReponses response = sampler.run();
                out.println("response is: " + response);
                out.flush();
                if (response != GraderReponses.CONTINUE) {
                    sampler = new CovidRowSampler(n, p, grader);
                    response = sampler.run();
                    break;
                }
            }
        }
    }

    interface CovidSampler {
        GraderReponses run();
    }

    abstract static class AbstractCovidSampler implements CovidSampler {
        final Grader grader;
        final int n, p;
        final int[][] matrix;

        AbstractCovidSampler(final int n, final int p, Grader grader) {
            this.n = n;
            this.p = p;
            this.grader = grader;
            matrix = new int[n][n];
            for (int[] row : matrix) Arrays.fill(row, -1);
        }

        protected final GraderReponses submit() {
            return grader.submit(matrix) == GraderReponses.CORRECT ? GraderReponses.CONTINUE : GraderReponses.TERMINATE;
        }
    }

    final static class SimpleCovidSampler extends AbstractCovidSampler {

        SimpleCovidSampler(final int n, final int p, Grader grader) {
            super(n, p, grader);
        }

        @Override
        public GraderReponses run() {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    int response = grader.query(i, j, i, j);
                    if (response == -1) return GraderReponses.TERMINATE;
                    matrix[i - 1][j - 1] = response;
                }
            }
            return submit();
        }
    }

    final static class CovidRowSampler extends AbstractCovidSampler {

        CovidRowSampler(final int n, final int p, Grader grader) {
            super(n, p, grader);
        }

        @Override
        public GraderReponses run() {
            int response = grader.query(1, 1, n, n);
            if (response == n * n) {
                for (int[] row : matrix) Arrays.fill(row, 1);
                return submit();
            } else if (response == 0) {
                for (int[] row : matrix) Arrays.fill(row, 0);
                return submit();
            }

            // check for each row
            for (int r = 1; r <= n; r++) {
                int[] row = matrix[r - 1];
                int rowCount = grader.query(r, 1, r, n);
                if (rowCount == 0) Arrays.fill(row, 0);
                else if (rowCount == n) Arrays.fill(row, 1);
            }

            // check for each column
            IntUnaryOperator columnCountFunc = col -> { // col value is [0, n-1], not (0, n]
                int count = 0;
                for (int r = 0; r < n; r++) if (matrix[r][col] != -1) count++;
                return count;
            };
            for (int c = 1; c <= n; c++) {
                final int col = c - 1;
                int processedCount = columnCountFunc.applyAsInt(c - 1);
                if (processedCount == n) continue;
                int oneCount = (int) IntStream.range(0, n).filter(r -> matrix[r][col] == 1).count();
                int colCount = grader.query(1, c, n, c);
                colCount -= oneCount;
                final int val;
                if (colCount == 0) val = 0;
                else if (colCount == n - processedCount) val = 1;
                else val = -1;
                IntStream.range(0, n).filter(r -> matrix[r][col] == -1).forEach(r -> matrix[r][col] = val);
            }

            final int nHalf = (n + 1) >> 1;

            for (int r = 1; r <= n; r++) {
                for (int c = 1; c <= n; c++) {
                    if (matrix[r - 1][c - 1] != -1) continue;
                    int r1 = 1, r2 = r, c1 = 1, c2 = c;
                    if (r < nHalf) {
                        r1 = r;
                        r2 = n;
                    }

                    if (c < nHalf) {
                        c1 = c;
                        c2 = n;
                    }

                    if (r2 - r1 > c2 - c1) {
                        c1 = c;
                        c2 = c;
                    } else {
                        r1 = r;
                        r2 = r;
                    }

                    response = grader.query(r1, c1, r2, c2);
                    if (r1 == r2) {
                        if (c1 == c) c1++;
                        else c2--;
                    } else {
                        if (r1 == r) r1++;
                        else r2--;
                    }

                    int temp = grader.query(r1, c1, r2, c2);
                    matrix[r - 1][c - 1] = temp == response ? 0 : 1;
                }
            }

            return submit();
        }
    }

    interface Grader {
        int query(int r1, int c1, int r2, int c2);

        GraderReponses submit(int[][] matrix);
    }

    final static class CodeChefGrader implements Grader {

        @Override
        public int query(int r1, int c1, int r2, int c2) {
            out.println("1 " + r1 + " " + c1 + " " + r2 + " " + c2);
            out.flush();
            return read();
        }

        @Override
        public GraderReponses submit(int[][] matrix) {
            out.println(2);
            StringBuilder sb = new StringBuilder();
            for (int[] row : matrix) {
                for (int e : row) sb.append(e).append(' ');
                sb.append('\n');
            }

            out.print(sb);
            out.flush();
            return read() == 1 ? GraderReponses.CORRECT : GraderReponses.INCORRECT;
        }

        private int read() {
            try {
                return in.readInt();
            } catch (IOException e) {
                return -1;
            }
        }
    }

    final static class CustomGrader implements Grader {
        private final int n, p;
        private final int[][] matrix;
        private final int[][] sumMatrix;

        CustomGrader(final int n, final int p) {
            this.n = n;
            this.p = p;
            Random random = new Random();
            matrix = new int[n][n];
            sumMatrix = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = random.nextInt(p) == 0 ? 1 : 0;
                }
            }

            for (int i = 0; i < n; i++) sumMatrix[i][0] = matrix[i][0];
            // populating row sums
            for (int r = 0; r < n; r++) {
                for (int c = 1; c < n; c++) {
                    sumMatrix[r][c] = sumMatrix[r][c - 1] + matrix[r][c];
                }
            }

            // combining with columns
            for (int r = 1; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    sumMatrix[r][c] += sumMatrix[r - 1][c];
                }
            }
        }

        @Override
        public int query(int r1, int c1, int r2, int c2) {
            r1--;
            r2--;
            c1--;
            c2--;
            return query(r2, c2) - query(r1 - 1, c2) - query(r2, c1 - 1) + query(r1 - 1, c1 - 1);
//            return query(r2 - 1, c2 - 1) - query(r2 - 2, c1 - 1) - query(r1 - 1, c2 - 2) + query(r1 - 2, c1 - 2);
        }

        private int query(int r, int c) {
            return r < 0 || c < 0 ? 0 : sumMatrix[r][c];
        }

        @Override
        public GraderReponses submit(int[][] matrix) {
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (this.matrix[i][j] != matrix[i][j]) return GraderReponses.INCORRECT;

            return GraderReponses.CORRECT;
        }
    }

    enum GraderReponses {
        TERMINATE, CORRECT, INCORRECT, CONTINUE;
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
    }
}