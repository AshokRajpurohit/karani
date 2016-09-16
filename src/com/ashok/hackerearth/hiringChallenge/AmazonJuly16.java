package com.ashok.hackerearth.hiringChallenge;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Problem Name: Amazon SDE Hiring Challenge
 * Link: https://www.hackerearth.com/amazon-sde-hiring-challenge/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AmazonJuly16 {
    private static PrintWriter out, sysOut = new Output(System.out);
    private static InputReader
            in, sysIn = new InputReader();

    public static void main(String[] args) throws IOException {
        while (true) {
            in = new InputReader("D:\\Java " +
                    "Projects\\karani\\src\\com\\ashok\\lang\\template\\" +
                    sysIn.read());

            out = new Output("D:\\Java " +
                    "Projects\\karani\\src\\com\\ashok\\lang\\template\\" +
                    sysIn.read());

            First.solve();
            in.close();
            out.close();
        }
    }

    /**
     * Rhezo and divisibility by 7
     */
    final static class First {
        private static int[] charToDigits = new int[256];
        private static int[] inverse = new int[7];

        static {
            for (int i = '1'; i <= '9'; i++)
                charToDigits[i] = i - '0';

            inverse[1] = 1;
            inverse[2] = 4;
            inverse[3] = 5;
            inverse[4] = 2;
            inverse[5] = 3;
            inverse[6] = 6;
        }

        private static void solve() throws IOException {
            String number = in.read();
            int n = number.length();
            int[] mods = new int[n + 1];
            mods[0] = 1;
            mods[1] = 3;

            for (int i = 2; i < mods.length; i++)
                mods[i] = (mods[i - 1] * 3) % 7;

            int[] numbers = new int[number.length()];
            int r = 3;
            numbers[n - 1] = charToDigits[number.charAt(n - 1)];
            for (int i = n - 2; i >= 0; i--) {
                numbers[i] = r * charToDigits[number.charAt(i)] + numbers[i +
                        1];

                numbers[i] %= 7;
                r = (r * 3) % 7;
            }

            int q = in.readInt();
            StringBuilder sb = new StringBuilder(q << 2);
            String yes = "YES\n", no = "NO\n";

            while (q > 0) {
                q--;

                int s = in.readInt() + 1, e = in.readInt() + 1;
                int excludeLength = n - e;
                int num = numbers[s - 1];

                if (excludeLength != 0) {
                    num -= numbers[e];

                    if (num < 0)
                        num += 7;

                    num = (num * inverse[mods[excludeLength]]);
                }

                if (num % 7 == 0)
                    sb.append(yes);
                else
                    sb.append(no);
            }

            out.println(sb);
        }
    }

    /**
     * Milly and equal array
     */
    final static class Second {
        private static void solve() throws IOException {
            int t = in.readInt();
            String yes = "She can", no = "She can't";

            while (t > 0) {
                t--;

                int n = in.readInt(), x = in.readInt(), y = in.readInt(), z =
                        in.readInt();

                int[] ar = in.readIntArray(n);
                for (int i = 0; i < n; i++) {
                    while (ar[i] % x == 0)
                        ar[i] /= x;

                    while (ar[i] % y == 0)
                        ar[i] /= y;

                    while (ar[i] % z == 0)
                        ar[i] /= z;
                }

                boolean can = true;
                for (int i = 1; i < n && can; i++) {
                    if (ar[i] != ar[i - 1])
                        can = false;
                }

                if (can)
                    out.println(yes);
                else
                    out.println(no);
            }
        }
    }

    /*final static class InputReader {
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
    }*/
}
