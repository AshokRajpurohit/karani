package com.ashok.codechef.marathon.year15.MARCH15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problem Link: http://www.codechef.com/MARCH15/problems/SEAPROAR
 */
public class D {

    private static PrintWriter out;
    private static InputStream in;
    private static long S, lg;
    private static StringBuilder sb;
    private static String[] lgc;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        D a = new D();
        a.solve();
        out.print(sb);
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        //        System.out.println(Integer.MAX_VALUE);
        sb = new StringBuilder();
        int t = in.readInt();
        lgc = new String[2000];


        while (t > 0) {
            t--;
            String s = in.read();
            solve(s);
        }

    }

    private static void formatSar() {
        char[] ar = new char[33];
        lg = 0;
        while (lg <= 500) {
            for (int i = 0; i < 33; i++) {
                ar[i] = lcg() == 0 ? '0' : '1';
            }
            lgc[(int)lg] = ar.toString();
            lg++;
        }
    }

    private static void solve(String s) {
        if (s.length() <= 500) {
            sb.append(s);
            return;
        }
        S = 0;
        while (S <= 500) {

            S++;
        }
        sb.append("Xorshift\n");
    }

    private static long lcg() {
        lg = lg * 1103515245 + 12345;
        lg = lg & 2147483647;
        return (lg >> 16) & 1;
    }

    //    private static long xor_shift() {
    //        long t = x ^ (x << 15);
    //        //        t = t & 2147483647;
    //        x = y;
    //        y = z;
    //        z = w;
    //        w = w ^ (w >> 21) ^ t ^ (t >> 4);
    //        w = w & 2147483647;
    //        return w & 1;
    //    }

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

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
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
