package com.ashok.hackerearth.Practice;
import java.io.*;

/**
 * @author Ashok Rajpurohit
 * problem Link: https://www.hackerearth.com/tracks/pledge-2015-hard/chandan-and-balanced-strings/
 */

public class H4 {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] ar = new int[1000000], bar = new int[1000000];

    public static void main(String[] args) throws IOException {
        //        OutputStream outputStream = System.out;
        //        in = System.in;
        //        out = new PrintWriter(outputStream);

        String input = "H4_input.txt", output = "H4_output.txt";
        FileInputStream fip = new FileInputStream(input);
        FileOutputStream fop = new FileOutputStream(output);
        in = fip;
        out = new PrintWriter(fop);
        H4 a = new H4();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            String s = in.read();
            int c = 0;
            long res = 0;
            ar[0] = s.charAt(0);
            int[] car = new int[256];
            car[0] = 1;

            for (int i = 1; i < s.length(); i++) {
                ar[i] = ar[i - 1] ^ s.charAt(i);
            }

            for (int i = 0; i < s.length(); i++) {
                res = res + car[ar[i]];
                car[ar[i]]++;
            }


            sb.append(res).append('\n');
        }
        out.print(sb);
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
