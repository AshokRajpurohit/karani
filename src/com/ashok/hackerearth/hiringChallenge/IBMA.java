package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit ashok1113
 * problem Link:
 */

public class IBMA {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] prime = gen_prime(100000);
    private static String error = "No crymeth today";


    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        IBMA a = new IBMA();
        a.solve();
        out.close();
    }

    private static void solve(int x, int y, int k) {
        int hcf = gcd(x, y);
        int count = factor(hcf);

        if (k > count) {
            out.println(error);
            return;
        }

        if (k == 1) {
            out.println(hcf);
            return;
        }

        out.println(factor(hcf, count + 1 - k));
    }

    private static int factor(int a, int b) {
        if (b == 1)
            return 1;

        int count = 1;
        int i = 2;

        for (; i <= a && count < b; i++)
            if (a % i == 0)
                count++;

        return i - 1;
    }

    private static int factor(int a) {
        int temp = 1, count = 1, i = 0;
        int root = (int) Math.sqrt(a);

        while (prime[i] <= root && a != 1) {
            temp = 1;
            while (a % prime[i] == 0) {
                temp++;
                a = a / prime[i];
            }
            i++;
            count *= temp;
        }
        if (a > 1)
            return count << 1;

        return count;
    }

    private static int[] get_factors(int a) {
        int[] ar = new int[factor_count(a)];
        int root = (int) Math.sqrt(a);

        int i = 0;
        for (int j = 0; prime[j] <= root && a != 1; ) {
            if (a % prime[j] == 0) {
                a = a / prime[j];
                ar[i] = prime[j];
                i++;
            } else
                j++;
        }

        if (a > 1)
            ar[i] = a;

        return ar;
    }

    private static int factor_count(int a) {
        int count = 0, root = (int) Math.sqrt(a);

        for (int i = 0; prime[i] <= root && a != 1; )
            if (a % prime[i] == 0) {
                a = a / prime[i];
                count++;
            } else
                i++;

        if (a > 1)
            count++;

        return count;
    }

    private static int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    private static int[] gen_prime(int n) {
        boolean[] ar = new boolean[n + 1];
        int root = (int) Math.sqrt(n);

        for (int i = 2; i <= root; i++) {
            while (ar[i])
                i++;
            for (int j = i + 1; j <= n; j++) {
                if (!ar[j]) {
                    ar[j] = j % i == 0;
                }
            }
        }

        int count = 0;
        for (int i = 2; i <= n; i++)
            if (!ar[i])
                count++;

        int[] ret = new int[count];

        for (int i = 2, j = 0; i <= n; i++) {
            if (!ar[i]) {
                ret[j] = i;
                j++;
            }
        }
        return ret;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            solve(in.readInt(), in.readInt(), in.readInt());
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
