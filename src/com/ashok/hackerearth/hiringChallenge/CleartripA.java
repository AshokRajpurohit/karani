package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit ashok1113@gmail.com
 * problem: Clertrip Hiring Challenge | The art of verification
 */

public class CleartripA {

    private static PrintWriter out;
    private static InputStream in;
    private static String[] list = new String[5];
    private static int[] stringCode = new int[5];
    private static int mod = 1000000007;

    static {
        list[0] = "username";
        list[1] = "pwd";
        list[2] = "profile";
        list[3] = "role";
        list[4] = "key";

        for (int i = 0; i < 5; i++)
            stringCode[i] = hashCode(list[i]);
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        CleartripA a = new CleartripA();
        a.solve();
        out.close();
    }

    private static String process(String url) {
        StringBuilder sb = new StringBuilder(50);
        boolean[] exists = new boolean[5];
        String[] values = new String[5];
        int[] order = new int[5];
        int next = 0;

        int i = 0;
        while (i < url.length()) {
            while (i < url.length() && url.charAt(i) != '=')
                i++;

            if (i == url.length())
                break;

            int j = i - 1;
            long code = 0;
            while (j >= 0 && url.charAt(j) != '&' && url.charAt(j) != '?') {
                code = (code * 29 + url.charAt(j) - 'a') % mod;
                j--;
            }

            int index = find((int) code);
            if (index != -1) {
                exists[index] = true;
                StringBuilder tsb = new StringBuilder();
                j = i + 1;
                while (j < url.length() && url.charAt(j) != '&') {
                    tsb.append(url.charAt(j));
                    j++;
                }
                i = j;
                values[index] = tsb.toString();
                order[next] = index;
                next++;
            } else {
                i++;
            }
        }

        for (i = 0; i < next; i++) {
            sb.append(list[order[i]]).append(": ").append(values[order[i]]).append('\n');
            //            if (exists[i]) {
            //                sb.append(list[i]).append(": ").append(values[i]).append('\n');
            //            }
        }

        return sb.toString();
    }

    private static int find(int code) {
        for (int i = 0; i < 5; i++)
            if (code == stringCode[i])
                return i;

        return -1;
    }

    private static int hashCode(String s) {
        long code = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            code = (code * 29 + s.charAt(i) - 'a') % mod;
        }
        return (int) code;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        out.print(process(in.read()));
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

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = readLong();

            return ar;
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

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
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
            for (int i = 0; offset < bufferSize && i < n; ++offset) {
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
