package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 * problem: Marut and Girls
 */

public class BookMyShowB {

    private static PrintWriter out;
    private static InputStream in;
    private boolean[] qualities = new boolean[10001], temp =
            new boolean[10001];
    private int numQ;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        BookMyShowB a = new BookMyShowB();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int m = in.readInt();
        numQ = m;
        for (int i = 0; i < m; i++)
            qualities[in.readInt()] = true;
        int n = in.readInt();
        int count = 0;

        while (n > 0) {
            n--;
            if (check(in.readIntArray()))
                count++;
        }
        out.println(count);
    }

    private boolean check(int[] ar) {
        if (ar.length < numQ)
            return false;

        int count = 0;
        for (int i = 0; i < ar.length; i++) {
            if (!temp[ar[i]] && qualities[ar[i]])
                count++;
            temp[ar[i]] = true;
        }

        for (int i = 0; i < ar.length; i++)
            temp[ar[i]] = false;

        return count == numQ;
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

        public int[] readIntArray() throws IOException {
            String s = readLine();
            int count = 1;
            for (int i = 0; i < s.length(); i++)
                if (s.charAt(i) == ' ')
                    count++;

            int[] result = new int[count];
            int index = 0;
            for (int i = 0; i < s.length() && index < count; ) {
                int temp = 0;
                for (; i < s.length() && s.charAt(i) != ' '; i++)
                    temp = (temp << 3) + (temp << 1) + s.charAt(i) - '0';

                result[index] = temp;
                index++;
                i++;
            }
            return result;
        }

        public String readLine() throws IOException {
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
                if (buffer[offset] == '\n' || buffer[offset] == '\r')
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
