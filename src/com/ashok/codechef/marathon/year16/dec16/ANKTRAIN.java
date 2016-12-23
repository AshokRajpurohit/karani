package com.ashok.codechef.marathon.year16.dec16;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Train Partner
 * Link: https://www.codechef.com/DEC16/problems/ANKTRAIN
 * <p>
 * This problem is at cake walk level. Let's say that the
 * berth number N is in Cth compartment.
 * <p>
 * For simplicity let's say in each compartment, the berth
 * indices are relative, i.e. 1 to 8 as explained in the first
 * example.
 * <p>
 * So we can denote berth as compartment number (Cn) and relative
 * berth number (Rbn)
 * <p>
 * So N = (Cn - 1) * 8 + Rbn
 * <p>
 * For berth number Rbn, we can get the partner relative berth number (Prbn)
 * as given in problem statement,
 * <p>
 * So partner berth number Pbn = (Cn - 1) * 8 + Prbn
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ANKTRAIN {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final String LB = "LB", UB = "UB", MB = "MB", SL = "SL", SU = "SU";
    private static String[] berths = new String[]{LB, MB, UB, LB, MB, UB, SL, SU};
    private static int[] partnerIndex = new int[]{3, 4, 5, 0, 1, 2, 7, 6};

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;

            int number = in.readInt() - 1;
            int relativeIndex = number & 7, offset = number - relativeIndex;
            int relativePartnerIndex = partnerIndex[relativeIndex];
            sb.append(offset + relativePartnerIndex + 1).append(berths[relativePartnerIndex]).append('\n');
        }

        out.print(sb.toString());
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
    }
}
