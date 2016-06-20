package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 * problem: Final Value
 * Link: Real Image Hiring Challenge
 */

public class RealImageA {

    private static PrintWriter out;
    private static InputStream in;
    private int x = 0;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        RealImageA a = new RealImageA();
        a.solve();
        out.close();
    }

    private static int getNum(String s, int i) {
        int res = 0;
        for (; i < s.length(); i++)
            res = res * 10 + s.charAt(i) - '0';

        return res;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();

        while (n > 0) {
            --n;
            evaluate(in.read());
        }
        out.println(x);
    }

    private void evaluate(String s) {
        if (s.charAt(1) == '=') {
            if (s.charAt(3) == '+')
                x += getNum(s, 4);
            else if (s.charAt(3) == '-')
                x -= getNum(s, 4);
            else if (s.charAt(3) == '*')
                x *= getNum(s, 4);
            else if (s.charAt(3) == '/')
                x /= getNum(s, 4);
            else
                x = getNum(s, 2);

            return;
        }

        int num = 0;
        for (int i = 3; i < s.length(); i++)
            num = num * 10 + s.charAt(i) - '0';

        if (s.charAt(1) == '+')
            x += num;
        else if (s.charAt(1) == '-')
            x -= num;
        else if (s.charAt(1) == '*')
            x *= num;
        else
            x /= num;
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
