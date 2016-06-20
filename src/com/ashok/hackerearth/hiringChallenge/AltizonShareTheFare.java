package com.ashok.hackerearth.hiringChallenge;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Problem: Share The Fare
 * Challenge: Altizon Hiring Challenge
 *
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 */

public class AltizonShareTheFare {

    private static PrintWriter out;
    private static InputStream in;
    private static String zero = " neither owes nor is owed\n", owe =
            " owes ", owed = " is owed ";

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        AltizonShareTheFare a = new AltizonShareTheFare();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 5);

        while (t > 0) {
            t--;
            int n = in.readInt(), q = in.readInt();
            HashMap<String, Integer> list = new HashMap<String, Integer>(n);
            String[] names = new String[n];

            for (int i = 0; i < n; i++)
                names[i] = in.read();

            for (String e : names)
                list.put(e, 0);

            while (q > 0) {
                q--;
                String payer = in.read();
                int amount = in.readInt(), persons = in.readInt() + 1;
                amount = amount - amount % persons;
                int own = amount / persons;
                list.put(payer, list.get(payer) + amount - own);

                own = amount / persons;
                while (persons > 1) {
                    persons--;
                    String name = in.read();
                    list.put(name, list.get(name) - own);
                }
            }

            for (String e : names) {
                int money = list.get(e);
                if (money == 0)
                    sb.append(e).append(zero);
                else if (money > 0)
                    sb.append(e).append(owed).append(money).append('\n');
                else
                    sb.append(e).append(owe).append(-money).append('\n');
            }
        }

        out.print(sb);
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
