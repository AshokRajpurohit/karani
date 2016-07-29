package com.ashok.hackerearth.marathon.july;

import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

/**
 * Problem Name: Benny and the Universe
 * Link: https://www.hackerearth.com/july-circuits/algorithm/benny-and-the-universe/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class BennyAndUniverse {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static long limit = 0;
    private static String path = "D:\\Java " +
            "Projects\\karani\\src\\com\\ashok\\hackerearth\\marathon" +
            "\\july\\input.txt";

    public static void main(String[] args) throws IOException {
        BennyAndUniverse a = new BennyAndUniverse();
        generateTestCases();
        a.solve();
        in.close();
        out.close();
    }

    private static void generateTestCases() throws IOException {
        int n = in.readInt(), q = in.readInt();
        Output output = new Output(path + "input.txt");
        output.println(n + " " + q);
        Random random = new Random();
        int[] ar = Generators.generateRandomIntegerArray(n, 2, 1000000000);
        ar[0] = 1 + random.nextInt(10000);

        output.print(ar);
        output.print(Generators.generateRandomIntegerArray(q, 1, 1000000000));
        output.close();
    }

    private void solve() throws IOException {
        com.ashok.lang.inputs.InputReader in = new com.ashok.lang.inputs
                .InputReader(path + "input.txt");
        String yes = "YES\n", no = "NO\n";
        int n = in.readInt(), q = in.readInt();
        int[] yaan = in.readIntArray(n);
        Arrays.sort(yaan);
        StringBuilder sb = new StringBuilder(q << 2);

        if (yaan[0] == 1) {
            for (int i = 0; i < q; i++) {
                in.readInt();
                sb.append(yes);
            }

            out.print(sb);
            return;
        }

        int gcd = gcd(yaan);
        format(yaan, gcd);
        limit = limit(yaan);
        resetLimit(yaan);

        while (q > 0) {
            q--;

            int temp = in.readInt();

            if (temp % gcd != 0 || !process(temp / gcd, yaan, gcdArray(yaan)))
                sb.append(no);
            else
                sb.append(yes);
        }

        out.print(sb);
        in.close();
    }

    private static int[] gcdArray(int[] ar) {
        int[] res = new int[ar.length];
        res[0] = ar[0];

        for (int i = 1; i < ar.length; i++)
            res[i] = gcd(ar[i], res[i - 1]);

        return res;
    }

    private static void format(int[] ar, int d) {
        for (int i = 0; i < ar.length; i++)
            ar[i] /= d;
    }

    private static boolean process(int n, int[] ar, int[] gcd) {
        out.flush();
        if (n > limit)
            return true;

        if (exists(ar, n))
            return true;

        if (existsMultiple(ar, n))
            return true;

        return validate(ar, gcd, n, ar.length - 1);
    }

    private static boolean validate(int[] ar, int[] gcd, int n, int index) {
        if (index == -1 || n < ar[0] || n % gcd[index] != 0)
            return false;

//        for (int i = index; i >= 0; i--)
//            if (n % ar[i] == 0)
//                return true;

//        if (exists(ar, n) || existsMultiple(ar, n))
//            return true;

        for (int i = index; i >= 0; i--) {
            int e = ar[i];

            if (e > n)
                continue;

            if (n % e == 0)
                return true;

            if (validate(ar, gcd, n - e, i))
                return true;
        }

        if (validate(ar, gcd, n, index - 1))
            return true;

        return false;
    }

    private static boolean existsMultiple(int[] ar, int n) {
        for (int e : ar)
            if (n % e == 0)
                return true;
            else if (n < e)
                return false;

        return false;
    }

    private static boolean exists(int[] ar, int n) {
        int start = 0, end = ar.length - 1;
        int mid = (start + end) >>> 1;

        if (n < ar[start] || n > ar[end])
            return false;

        while (start != mid) {
            if (n == ar[mid])
                return true;

            if (n > ar[mid])
                start = mid;
            else
                end = mid;

            mid = (start + end) >>> 1;
        }

        return n == ar[start] || n == ar[end];
    }

    private static long limit(int[] ar) {
        if (ar.length == 1)
            return ar[0] == 1 ? 0 : -1;

        long limit = Long.MAX_VALUE;
        for (int i = 0; i < ar.length; i++) {
            if (ar[i] == 1)
                return 0;

            for (int j = i + 1; j < ar.length; j++) {
                long tempLimit = limit(ar[i], ar[j]);
                if (tempLimit != -1)
                    limit = Math.min(tempLimit, limit);
            }
        }

        return limit;
    }

    private static void resetLimit(int[] ar) {
        if (limit < 1)
            return;

        int index = ar.length - 1;

        while (index >= 0 && ar[index] > limit)
            index--;

        while (index >= 0 && ar[index] == limit) {
            index--;
            limit--;
        }
    }

    private static long limit(int a, int b) {
        if (gcd(a, b) != 1)
            return -1;

        return 1L * a * b - a - b;
    }

    private static int gcd(int[] ar) {
        int gcd = ar[0];

        for (int e : ar) {
            gcd = gcd(e, gcd);

            if (gcd == 1)
                return gcd;
        }

        return gcd;
    }

    private static int gcd(int a, int b) {
        if (b == 0)
            return a;

        return gcd(b, a % b);
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
