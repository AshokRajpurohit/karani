package com.ashok.hackerearth.Practice;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Easy Calculation
 * Link: https://www.hackerearth.com/problem/algorithm/easy-calculation/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class EasyCalculation {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int mod = 1000000000;

    public static void main(String[] args) throws IOException {
        EasyCalculation a = new EasyCalculation();
        a.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        int t = in.readInt();

        while (t > 0) {
            t--;
            out.println(process(in.read()));
        }
    }

    private static long process(String s) {
        long time = System.currentTimeMillis();
        long res = 0;

        s = s.replaceAll("PLUS", "+");
        s = s.replaceAll("MINUS", "-");
        s = s.replaceAll("MULTIPLY", "*");
        s = s.replace("DIVIDE", "/");

        out.println(s);

        String[] adds = s.split("[+]");
        for (String e : adds) {
            res += addition(e);
        }

        out.println(System.currentTimeMillis() - time);
        return res % mod;
    }

    private static double addition(String s) {
        String[] minus = s.split("[-]");
        double res = minus(minus[0]);

        for (int i = 1; i < minus.length; i++)
            res -= minus(minus[i]);

        return res % mod;
    }

    private static double minus(String s) {
        double res = 1.0;
        String[] multi = s.split("[*]");

        for (String e : multi)
            res = res * multi(e) % mod;

        return res;
    }

    private static double multi(String s) {
        double res = 1.0;

        String[] divisions = s.split("[/]");
        res = Integer.parseInt(divisions[0]) * 1.0;

        for (int i = 1; i < divisions.length; i++)
            res = res / Integer.parseInt(divisions[i]);

        return res;
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
