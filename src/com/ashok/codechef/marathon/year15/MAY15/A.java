package com.ashok.codechef.marathon.year15.MAY15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit ashok1113
 *  problem Link: http://www.codechef.com/MAY15/problems/CHEFRP
 *
 *  find the minimum element and if it is less than 2 the answer is -1 as
 *  it won't be possible for chef to select two item of this minimum quantity
 *  type.
 *  in worst case at last we get the second item of minimum quantity type
 *  after getting all the items of all other types.
 */

public class A {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        A a = new A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int min = Integer.MAX_VALUE, sum = 0, temp;
            int n = in.readInt();

            for (int i = 0; i < n; i++) {
                temp = in.readInt();
                sum += temp;
                if (min > temp)
                    min = temp;
            }
            if (min < 2)
                out.println("-1");
            else {
                out.println(sum + 2 - min);
            }
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
    }
}
