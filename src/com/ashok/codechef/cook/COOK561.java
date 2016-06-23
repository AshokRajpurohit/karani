package com.ashok.codechef.cook;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link: http://www.codechef.com/COOK56/problems/DIVGOLD
 */

public class COOK561 {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        COOK561 a = new COOK561();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder op = new StringBuilder(2600);

        while (t > 0) {
            t--;
            int n = in.readInt();
            StringBuilder sb = new StringBuilder(n + 2);
            sb.append(in.read());
            if (n > 1)
                process(sb);
            op.append(sb).append('\n');
        }
        out.print(op);
    }

    private static void process(StringBuilder sb) {
        int i = 0;
        while (i < sb.length() - 1 && sb.charAt(i) <= sb.charAt(i + 1))
            i++;
        if (i == sb.length() - 1) {
            return;
        }
        int tip = i;
        i++;
        char min = sb.charAt(i);
        int minindex = i;
        i++;
        while (i < sb.length()) {
            if (min > sb.charAt(i)) {
                min = sb.charAt(i);
                minindex = i;
            }
            i++;
        }

        i = 0;
        while (min >= sb.charAt(i) && i < sb.length()) {
            i++;
        }
        if (i == sb.length())
            return;
        if (minindex == i + 1) {
            char t = sb.charAt(tip);
            sb.deleteCharAt(tip);
            i = tip;
            while (i < sb.length() && sb.charAt(i) <= t)
                i++;
            if (i == sb.length())
                sb.append(t);
            else
                sb.insert(i, t);
            return;
        }
        sb.delete(minindex, minindex + 1);
        sb.insert(i, min);
        return;
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
