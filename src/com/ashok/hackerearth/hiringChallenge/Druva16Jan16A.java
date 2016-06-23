package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem: Scoring in Exam
 * Challenge: Druva Developer Hiring Challenge
 *
 * @author Ashok Rajpurohit ashok1113@gmail.com
 */

public class Druva16Jan16A {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        Druva16Jan16A a = new Druva16Jan16A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt(), q = in.readInt();
        StringBuilder sb = new StringBuilder(q << 3);
        Question[] ar = new Question[n];
        int[] time = in.readIntArray(n), score = in.readIntArray(n);

        for (int i = 0; i < n; i++)
            ar[i] = new Question(time[i], score[i]);

        Arrays.sort(ar);
        long[] sum = new long[n];
        sum[0] = ar[0].time;

        for (int i = 1; i < n; i++)
            sum[i] = sum[i - 1] + ar[i].time;

        while (q > 0) {
            q--;
            sb.append(sum[in.readInt() - 1]).append('\n');
        }

        out.print(sb);
    }

    final static class Question implements Comparable<Question> {
        int time, score;

        Question(int a, int b) {
            time = a;
            score = b;
        }

        public int compareTo(Question q) {
            return q.score - this.score;
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
