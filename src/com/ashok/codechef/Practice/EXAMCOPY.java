package com.ashok.codechef.Practice;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Exam Copy
 * Link: https://www.codechef.com/LOCJUN16/problems/EXAMCOPY
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class EXAMCOPY {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[] students, checker;
    private static int D;
    private static final int noCheat = -1;

    public static void main(String[] args) throws IOException {
        EXAMCOPY a = new EXAMCOPY();
        a.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        int n = in.readInt(), m = in.readInt();
        students = new int[n + 1];
        for (int i = 1; i <= n; i++)
            students[i] = in.readInt();

        checker = new int[n + 1];
        for (int i = 1; i <= m; i++) {
            int l = in.readInt(), r = in.readInt();

            for (int j = l; j <= r; j++)
                checker[j] = i;
        }

        D = in.readInt();
        process();
    }

    private static void process() {
        if (D < 5) {
//            bruteForce();
//            return;
        }
        int n = students.length - 1;
        StringBuilder sb = new StringBuilder(n << 2);

        int leftWindowLeft = 0, leftWindowRight = 0, rightWindowLeft = 1, rightWindowRight = 1;
        int maxLeft = 0, maxLeftCount = 0, maxRight = 0, maxRightCount = 0;

        while (rightWindowLeft < n && checker[rightWindowLeft] == checker[1])
            rightWindowLeft++;

        rightWindowRight = rightWindowLeft + 1;
        maxRight = students[rightWindowLeft];
        maxRightCount = 1;

        while (rightWindowRight <= 1 + D && rightWindowRight <= n) {
            if (students[rightWindowRight] > maxRight) {
                maxRight = students[rightWindowRight];
                maxRightCount = 1;
            } else if (students[rightWindowRight] == maxRight)
                maxRightCount++;

            rightWindowRight++;
        }

        for (int i = 1; i <= n; i++) {
            int max = maxLeft, count = maxLeftCount;

            if (rightWindowLeft <= i + D) {
                if (maxRight > max) {
                    max = maxRight;
                    count = maxRightCount;
                } else if (maxRight == max) {
                    count += maxRightCount;
                }
            }

            if (max > students[i])
                sb.append(max).append(' ').append(count).append('\n');
            else
                sb.append(noCheat).append('\n');

            if (leftWindowLeft < i - D) {
                if (students[leftWindowLeft] == maxLeft) {
                    maxLeftCount--;
                }

                leftWindowLeft++;
            }

            while (checker[leftWindowRight] != checker[i]) {
                if (students[leftWindowRight] < maxLeft) {
                    leftWindowRight++;
                    continue;
                }

                if (students[leftWindowRight] == maxLeft)
                    maxLeftCount++;
                else {
                    maxLeft = students[leftWindowRight];
                    maxLeftCount = 1;
                }

                leftWindowRight++;
            }

            while (rightWindowLeft <= n && checker[rightWindowLeft] == checker[i]) {
                if (students[rightWindowLeft] == maxRight) {
                    maxRightCount--;
                }

                rightWindowLeft++;
            }

            if (rightWindowRight <= n && rightWindowRight < i + D) {
                if (students[rightWindowRight] == maxRight)
                    maxRightCount++;
                else if (students[rightWindowRight] > maxRight)
                    maxRightCount = 1;

                rightWindowRight++;
            }
        }

        out.print(sb);
    }

    private static void bruteForce() {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < students.length; i++) {
            int max = 0, count = 0;
            int j = i > D ? i - D : 1, k = Math.min(i + D, students.length - 1);

            for (int l = j; l <= k; l++) {
                if (checker[l] == checker[i])
                    continue;

                if (students[l] > max) {
                    max = students[l];
                    count = 1;
                } else if (students[l] == max)
                    count++;
            }

            if (max > students[i])
                sb.append(max).append(' ').append(count).append('\n');
            else
                sb.append(noCheat).append('\n');
        }

        out.print(sb);
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
