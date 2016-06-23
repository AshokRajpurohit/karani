package com.ashok.hackerearth.hiringChallenge;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashSet;

/**
 * Problem: Navi's Party
 * Challenge: Lucid Technologies Java Hiring Challenge
 *
 * @author Ashok Rajpurohit ashok1113@gmail.com
 */

public class LucidNaviParty {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        LucidNaviParty a = new LucidNaviParty();
        a.solve();
        out.close();
    }

    private static int num(String s) {
        if (s.charAt(0) > '9')
            return 100;

        int num = 0;
        for (int i = 0; i < s.length(); i++) {
            num = (num << 3) + (num << 1) + s.charAt(i) - '0';
        }

        return num;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt(), q = in.readInt();
        //        HashMap<String, Count> names = new HashMap<String, Count>();
        HashSet<String> set = new HashSet<String>();

        for (int i = 0; i < n; i++) {
            String name = in.read();
            if (!set.contains(name)) {
                set.add(name);
            }
        }

        int missings = 0;
        for (int i = 0; i < q; i++) {
            String guest = in.read();
            int age = num(guest);

            if (age == 100) {
                if (!set.contains(guest)) {
                    missings++;
                }
            } else if (age < 21) {
                missings++;
            }
        }

        out.println(missings);
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
