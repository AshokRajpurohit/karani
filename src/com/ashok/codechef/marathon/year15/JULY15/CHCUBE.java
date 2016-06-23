package com.ashok.codechef.marathon.year15.JULY15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Chef and Cube
 * http://www.codechef.com/JULY15/problems/CHCUBE
 */

public class CHCUBE {

    private static PrintWriter out;
    private static InputStream in;
    private static String yes = "YES\n", no = "NO\n";
    private static int[] pattern = new int[6];

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CHCUBE a = new CHCUBE();
        a.solve();
        out.close();
    }

    /**
     * We are taking input strings as Integers, as we know the largest string
     * is orange and it's decimal representation is smaller than Largest
     * Integer. We can generate hashCode also in this case as there is very
     * less probability of hash collision (unless you are using the same
     * hash code for all strings or number of characters in string, like these
     * stupid methods). Comparing two integers is always faster than comparing
     * two strings.
     *
     * @throws IOException
     */
    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;

            for (int i = 0; i < 6; i++)
                pattern[i] = in.readInt();

            if (process())
                sb.append(yes);
            else
                sb.append(no);
        }
        out.print(sb);
    }

    /**
     * let's say three faces a, b and c are adjacent to each other (one corner
     * in common). Let's say c is the top face. Similarly d is the bottom
     * face. So a, b and d are also adjacent to each other.
     * If a and b are equal then a ^ b is zero.
     * Now we can check if a is equal to c or d.
     * if a is equal to one of c and d then (a ^ c) * (a ^ d) should be zero
     * as either a ^ c or a ^ d will be zero.
     * Now we can combine both the condition as if a and b are equal and then
     * a is equal to c or d then a ^ b + (a ^ c) * (a ^ d) should be zero.
     * We are checking this condition for all the four faces front, back,
     * left and right.
     * a is one of these face and b is adjacent to a and one of these.
     *
     * @return
     */
    private static boolean process() {
        if (((pattern[0] ^ pattern[3]) |
             ((pattern[0] ^ pattern[5]) * (pattern[0] ^ pattern[4]))) == 0) {
            return true;
        }

        if (((pattern[3] ^ pattern[1]) |
             ((pattern[3] ^ pattern[5]) * (pattern[3] ^ pattern[4]))) == 0)
            return true;

        if (((pattern[1] ^ pattern[2]) |
             ((pattern[1] ^ pattern[5]) * (pattern[1] ^ pattern[4]))) == 0)
            return true;

        if (((pattern[2] ^ pattern[0]) |
             ((pattern[2] ^ pattern[5]) * (pattern[2] ^ pattern[4]))) == 0)
            return true;

        return false;
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
