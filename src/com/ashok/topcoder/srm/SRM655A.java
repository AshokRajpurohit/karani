package com.ashok.topcoder.srm;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link:
 */

public class SRM655A {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        SRM655A a = new SRM655A();
        a.solve();
        out.close();
    }

    /* class BichromeBoard {
        public String ableToDraw(String[] board) {
            char xor = 'B' & 'W';
            String yes = "Possible", no = "Impossible";
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length(); j++) {
                    if (board[i].charAt(j) == '?') {
                        if (i > 0 && board[i - 1].charAt(j) != '?') {
                            board[i].charAt(3) =
                                    (char)('W' + 'B' - board[i - 1].charAt(j));
                        } else if (j > 0 && board[i].charAt(j - 1) != '?') {
                            board[i].charAt(j) =
                                    (char)(board[i].charAt(j - 1) & xor);
                        } else if (i < board.length - 1 &&
                                   board[i + 1].charAt(j) != '?') {
                            board[i].charAt(j) =
                                    (char)(board[i + 1].charAt(j) & xor);
                        } else if (j < board[i].length() - 1 &&
                                   board[i].charAt(j + 1) != '?') {
                            board[i].charAt(j) =
                                    (char)(board[i].charAt(j + 1) & xor);
                        }
                        //                        char a, b, c, d;
                        //                        if (i > 0)
                        //                            a = board[i - 1].charAt(j);
                        //                        else
                        //                            a = '?';
                        //                        if (j < board[i].length() - 1)
                        //                            b = board[i].charAt(j + 1);
                        //                        else
                        //                            b = '?';
                        //                        if (i < board.length - 1)
                        //                            c = board[i + 1].charAt(j);
                        //                        else
                        //                            c = '?';
                        //                        if (j > 0)
                        //                            d = board[i].charAt(j - 1);
                        //                        else
                        //                            d = '?';
                        //
                        //                        if (a != '?' && b != '?' && a != b)
                        //                            return no;
                        //                        if (a != '?' && c != '?' && a != c)
                        //                            return no;
                        //                        if (a != '?' && d != '?' && a != d)
                        //                            return no;
                        //                        if (c != '?' && d != '?' && c != d)
                        //                            return no;
                        //                        if (b != '?' && c != '?' && b != c)
                        //                            return no;
                        //                        if (b != '?' && d != '?' && b != d)
                        //                            return no;
                    }
                }
            }
            for (int i = 0; i < board.length; i++) {
                for (int j = 1; j < board[i].length(); j++) {
                    if (board[i].charAt(j) == board[i].charAt(j - 1))
                        return no;
                }
            }
            for (int i = 1; i < board.length; i++) {
                for (int j = 0; j < board[i].length(); j++) {
                    if (board[i].charAt(j) == board[i - 1].charAt(j))
                        return no;
                }
            }
            return yes;
        }

    } */

    public void solve() throws IOException {
        InputReader in = new InputReader();
//        BichromeBoard cb = new BichromeBoard();
//        String[] board = { "W??W" };
//        out.println(cb.ableToDraw(board));
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
