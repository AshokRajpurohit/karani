package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link: PrimeString
 */

/**
 * Alice has just learnt about primeStrings. A string is a primeString
 * if the number of distinct alphabets used in the string is a prime and also
 * the number of occurrences of each alphabet in the string is also a prime.
 * Given a String you need to tell if it is a primeString or not.
 */

public class PolicyBazaarA {

    private static PrintWriter out;
    private static InputStream in;
    private static boolean[] prime = new boolean[318];
    private static int[] car = new int[256];

    static {
        for (int i = 2; i < 318; i++)
            prime[i] = true;

        for (int i = 2; i < 18; i++) {
            while (!prime[i])
                i++;
            for (int j = i + 1; j < 318; j++) {
                if (prime[j])
                    prime[j] = j % i != 0;
            }
        }

        for (int i = 'a'; i <= 'z'; i++)
            car[i] = i - 'a';
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        PolicyBazaarA a = new PolicyBazaarA();
        a.solve();
        out.close();
    }

    private static boolean solve(String s) {
        int[] ar = new int[26];
        for (int i = 0; i < s.length(); i++)
            ar[car[s.charAt(i)]]++;

        int count = 0;
        for (int i = 0; i < 26; i++)
            if (ar[i] != 0)
                count++;

        if (!prime[count])
            return false;

        for (int i = 0; i < 26; i++) {
            if (ar[i] != 0 && !check(ar[i]))
                return false;
        }
        return true;
    }

    private static boolean check(int n) {
        if (n < 318)
            return prime[n];

        for (int i = 2; i < 318; i++) {
            if (prime[i] && n % i == 0)
                return false;
        }
        return true;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        String yes = "YES\n", no = "NO\n";
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            String s = in.read();
            if (solve(s))
                sb.append(yes);
            else
                sb.append(no);
        }
        out.print(sb);
        out.flush();
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
