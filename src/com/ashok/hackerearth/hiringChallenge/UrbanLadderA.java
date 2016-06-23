package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Urban Ladder Hiring Challenge | Numerical Armageddon Round 1
 * https://www.hackerearth.com/urban-ladder-coding-challenge/algorithm/numerical-armageddon-round-1-1/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class UrbanLadderA {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] charToDigit = new int[256];
    private static int mod = 1000000007;

    static {
        for (int i = '0'; i <= '9'; i++)
            charToDigit[i] = i - '0';
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        UrbanLadderA a = new UrbanLadderA();
        a.solve();
        out.close();
    }

    private static long process(String s) {
        long res = 0;
        long[] temp = new long[3];
        long[] temp2 = new long[3];

        for (int i = 0; i < s.length(); i++) {
            int t = charToDigit[s.charAt(i)];
            if ((t & 1) == 0)
                res += temp[(3 - t % 3) % 3];

            if (t == 0 || t == 6)
                res++;

            for (int j = 0; j < 3; j++) {
                temp2[(j + t) % 3] = temp[j] + temp[(j + t) % 3];
            }

            temp2[t % 3]++;
            for (int j = 0; j < 3; j++)
                temp[j] = temp2[j] % mod;
        }
        return res % mod;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        while (t > 0) {
            t--;
            out.println(process(in.read()));
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
    }
}
