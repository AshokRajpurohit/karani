package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link:
 */

public class PolicyBazaarB {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] car = new int[256];
    private static String np = "Not possible!";
    private static char[] carray =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    static {
        for (int i = '0'; i <= '9'; i++)
            car[i] = i - '0';
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        PolicyBazaarB a = new PolicyBazaarB();
        a.solve();
        out.close();
    }

    private static void solve(String s) {
        StringBuilder sb = new StringBuilder(s.length() + 1);
        int[] ar = new int[10];
        for (int i = 0; i < s.length(); i++)
            ar[car[s.charAt(i)]]++;

        int count = 0;
        for (int i = 1; i < 10; i++)
            if (ar[i] != 0)
                count++;

        if (count == 1 || s.length() < 3) {
            out.println(np);
            return;
        }

        char a, b, c, p, q, r;
        // solve for 3rd smallest value.

        int i = 9;
        while (ar[i] == 0)
            i--;
        c = carray[i];

        i--;
        while (ar[i] == 0)
            i--;
        b = carray[i];
        if (ar[car[c]] > 1) {
            a = b;
            b = c;
            ar[car[a]]--;
            ar[car[b]] -= 2;
            r = a;
            p = c;
            q = c;
        } else if (ar[car[b]] > 1) {
            a = b;
            ar[car[b]] -= 2;
            ar[car[c]]--;
            r = a;
            q = a;
            p = c;
        } else {
            i--;
            while (ar[i] == 0)
                i--;
            a = carray[i];
            ar[car[a]]--;
            ar[car[b]]--;
            ar[car[c]]--;
            r = c;
            p = b;
            q = a;
        }

        for (int j = 1; j < 10 && j <= car[a]; j++) {
            for (int k = 0; k < ar[j]; k++)
                sb.append(carray[j]);
        }
        sb.append(p).append(q).append(r);
        for (int j = car[c]; j < 10; j++) {
            for (int k = 0; k < ar[j]; k++)
                sb.append(carray[j]);
        }
        sb.append(' ');
        out.print(sb);
        out.flush();
        ar[car[p]]++;
        ar[car[q]]++;
        ar[car[r]]++;

        // Solve for 3rd largest value.
        i = 0;
        while (ar[i] == 0)
            i++;
        a = carray[i];

        i++;
        while (ar[i] == 0)
            i++;
        b = carray[i];
        if (ar[car[a]] > 1) {
            c = b;
            b = a;
            ar[car[a]] -= 2;
            ar[car[c]]--;
            p = a;
            q = a;
            r = c;
        } else if (ar[car[b]] > 1) {
            c = b;
            ar[car[b]] -= 2;
            ar[car[a]]--;
            p = a;
            q = b;
            r = b;
        } else {
            i++;
            while (ar[i] == 0)
                i++;
            c = carray[i];
            ar[car[a]]--;
            ar[car[b]]--;
            ar[car[c]]--;
            p = b;
            q = c;
            r = a;
        }

        sb = new StringBuilder(s.length() + 1);
        for (int j = 9; j > 0 && j >= car[c]; j--) {
            for (int k = 0; k < ar[j]; k++)
                sb.append(carray[j]);
        }
        sb.append(p).append(q).append(r);
        for (int j = car[a]; j > 0; j--) {
            for (int k = 0; k < ar[j]; k++)
                sb.append(carray[j]);
        }
        sb.append('\n');
        out.print(sb);
        out.flush();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            String s = in.read();
            solve(s);
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
