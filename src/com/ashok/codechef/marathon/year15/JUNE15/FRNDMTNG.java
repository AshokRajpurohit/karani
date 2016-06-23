package com.ashok.codechef.marathon.year15.JUNE15;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit ashok1113
 *  problem:Chef and His Friend
 *  http://www.codechef.com/JUNE15/problems/FRNDMTNG
 */

public class FRNDMTNG {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        FRNDMTNG a = new FRNDMTNG();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            sb.append(solve(in.readInt(), in.readInt(), in.readInt(),
                            in.readInt())).append('\n');
        }
        out.print(sb);
    }

    private double solve(int T1, int T2, int t1, int t2) {
        if (t1 == 0 && t2 == 0)
            if (T1 == 0 && T2 == 0)
                return 1;
            else
                return 0;

        return solve(T1, T2, t1) + solve(T2, T1, t2);
    }

    private double solve(int T1, int T2) {
        if (T1 == 0 && T2 == 0)
            return 1;
        return 0;
    }

    /**
     * returns the probability of two friends meeting when second guy arrive
     * after first guy.
     * The result is sum of two cases mentioned in commented form for
     * clarification. the first part is the probability when can wait for t1
     * seconds for the second guy to arrive.
     * second part is when first guy need to wait for less time for the second
     * guy. as we know there is no point in waiting beyond T2  time and he
     * can't wait beyond T1 + t1 as this is his upper limit. So T_2 is this
     * upper limit of waiting time for first guy.
     * first guy need not to arrive after T2 time or T1 time whichever is
     * earlier as there is no point arriving after this time.
     * So T_dash is the upper limit of time of arrival for first guy.
     * contact me for the derivation of formula.
     * @param T1 Time duration of arrival for first person. [0, T1]
     * @param T2 Time interval of arrival for second person. [0, T2]
     * @param t1 waiting time in seconds for the first person.
     * @return
     */
    private double solve(int T1, int T2, int t1) {
        double T = Math.min(T2 - t1, T1), T_dash = Math.min(T1, T2), T_2 =
            Math.min(T2, T1 + t1);
        if (T < 0)
            T = 0;

        /*
        double result = 1.0 * t1 * T / (1.0 * T1 * T2);
        result += (T_dash - T) * (2 * T_2 - T_dash - T) / (2.0 * T1 * T2);
        return result;
         */

        return (1.0 * t1 * T +
                1.0 * (T_dash - T) * (2 * T_2 - T_dash - T) / 2) /
            (1.0 * T1 * T2);
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
    }
}
