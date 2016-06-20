package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 * problem: MMORPG Dota2
 * Link: Capillary Hiring Challenge
 */

public class CapillaryJavaB {

    private static PrintWriter out;
    private static InputStream in;
    private int Ms, Mt, Lt;
    private int[] x, res;
    private int ans = 0;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        CapillaryJavaB a = new CapillaryJavaB();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        Ms = in.readInt();
        Mt = in.readInt();
        Lt = in.readInt();
        int n = in.readInt();
        x = in.readIntArray(n);

        if (Lt == 1) {
            out.println(n * Math.min(Ms, Mt));
            return;
        }

        if (Ms * Lt <= Mt) {
            out.println(Ms * n);
            return;
        }

        process();
        out.println(res[0]);
    }

    private void process() {
        Arrays.sort(x);
        res = new int[1001];
        for (int i = 0; i < 1001; i++)
            res[i] = -1;
        process(0, 0);
    }

    private int process(int ri, int xi) {
        while (xi < x.length && x[xi] < ri)
            xi++;

        if (xi == x.length)
            return 0;
        if (res[ri] != -1)
            return res[ri];

        int nri = Math.max(ri, x[xi] - Lt + 1);
        res[nri] =
                Math.min(Mt + process(nri + Lt, xi), Ms + process(x[xi] + 1, xi));

        while (ri < nri) {
            res[ri] = res[nri];
            ri++;
        }
        return res[ri];
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
    }
}
