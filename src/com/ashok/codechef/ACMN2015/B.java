package com.ashok.codechef.ACMN2015;


//import static java.lang.System.in;
import java.io.IOException;
import java.io.InputStream;

        import java.io.OutputStream;
import java.io.PrintWriter;

public class B {
    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        B a = new B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        StringBuilder sb = new StringBuilder();

        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt();
            int s = in.readInt();
            int[] ar = new int[n];

            for (int i = 0; i < n; i++)
                ar[i] = in.readInt();

            int res = solve(s, 0, ar);
            sb.append(res).append('\n');
        }
        out.print(sb);
    }

    private int solve(int sum, int index, int[] ar) {
        if (sum == 0)
            return 1;

        if (sum < 0 || index == ar.length)
            return 0;

        return solve(sum - ar[index], index + 1, ar) +
            solve(sum, index + 1, ar);
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
    }
}
