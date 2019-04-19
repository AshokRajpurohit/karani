package com.ashok.hackerRank.Strings;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 *         problem: Morgan and a String
 *         https://www.hackerrank.com/challenges/morgan-and-a-string
 */

public class MorganString {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
/*

        String input = "input_file.in", output = "output_file.out";
        FileInputStream fip = new FileInputStream(input);
        FileOutputStream fop = new FileOutputStream(output);
        in = fip;
        out = new PrintWriter(fop);*/

        play();
        MorganString a = new MorganString();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(1000000);

        while (t > 0) {
            t--;
            process(sb, in.read(), in.read());
            sb.append('\n');
        }
        out.print(sb);
    }

    private static void play() throws IOException {
        InputReader in = new InputReader();
        while (true) {
            StringBuilder sb = new StringBuilder();
            process(sb, in.read(), in.read());
            out.println(sb);
            out.flush();
        }
    }

    /**
     * Work in progress
     *
     * @param a
     * @param b
     * @return
     */
    private static void process(StringBuilder sb, String a, String b) {
        char[] ar = a.toCharArray(), br = b.toCharArray();
        int i = 0, j = 0, alen = ar.length, blen = br.length;

        for (; i < alen && j < blen; ) {
            if (ar[i] < br[j]) {
                sb.append(ar[i++]);
            } else if (ar[i] > br[j]) {
                sb.append(br[j++]);
            } else {
                int dis = nextDiffCharIndex(ar, br, i, j);
                int ai = i + dis, bi = j + dis;
                int ch = ar[i], cha = ai < alen ? ar[ai] : ar[alen - 1], chb = bi < blen ? br[bi] : br[blen - 1];

                if (cha <= chb) {
                    append(sb, ar, i, dis);
                    i = ai;
                } else {
                    append(sb, br, j, dis);
                    j = bi;
                }
            }
        }

        append(sb, ar, i);
        append(sb, br, j);
    }

    private static void append(StringBuilder sb, char[] ar, int fromIndex) {
        while (fromIndex < ar.length)
            sb.append(ar[fromIndex++]);
    }

    private static void append(StringBuilder sb, char[] ar, int fromIndex, int len) {
        int maxIndex = Math.min(ar.length, fromIndex + len);
        while (fromIndex < maxIndex) {
            sb.append(ar[fromIndex++]);
            len--;
        }
    }

    private static int nextDiffCharIndex(char[] ar, int index) {
        int ref = ar[index];
        while (index < ar.length && ar[index] == ref) index++;

        return index;
    }

    private static int nextDiffCharIndex(char[] ar, char[] br, int i, int j) {
        int dis = 0, ref = ar[i], cont_len = 0;
        while (i < ar.length && j < br.length && ar[i] == ref && br[j] == ref) {
            i++;
            j++;
            dis++;
            cont_len++;
        }

        while (i < ar.length && j < br.length && ar[i] <= ref && br[j] <= ref && ar[i] == br[j]) {
            while (i < ar.length && j < br.length && ar[i] == br[j] && ar[i] < ref && br[j] < ref) {
                i++;
                j++;
                dis++;
            }

            int temp_cont_len = 0;
            while (i < ar.length && j < br.length && ar[i] == ref && br[j] == ref) {
                i++;
                j++;
                temp_cont_len++;
            }

            if (temp_cont_len > cont_len) return dis;
            dis += temp_cont_len;
        }

        if (i == ar.length) {
            while (j < br.length && br[j] < ref) {
                j++;
                dis++;
            }
        } else if (j == br.length) {
            while (i < ar.length && ar[i] < ref) {
                i++;
                dis++;
            }
        }

        return dis;
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
