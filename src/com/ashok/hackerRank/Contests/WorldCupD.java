package com.ashok.hackerRank.Contests;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * problem: Bishop War
 * https://www.hackerrank.com/contests/worldcup/challenges/bishop-war
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class WorldCupD {

    private static PrintWriter out;
    private static InputStream in;
    private static char[] mark =
    { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        WorldCupD a = new WorldCupD();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt(), m = in.readInt();
        String[] ar = new String[n];

        for (int i = 0; i < n; i++)
            ar[i] = in.read(m);

        out.println(getCount(ar));
    }

    private static int getCount(String[] ar) {
        int count = 0;
        char[][] board = new char[ar.length][ar[0].length()];
        copy(ar, board);

        for (int i = 0; i < ar.length; i++) {
            if (ar[0].charAt(i) == '.') {
                place(board, 0, i);
                count += getCount(board, 1);
                clean(board, 0, i);
            }
        }

        return count;
    }

    private static int getCount(char[][] board, int row) {
        if (row == board.length)
            return 1;

        int count = 0;
        for (int i = 0; i < board[0].length; i++)
            if (board[row][i] == '.') {
                place(board, row, i);
                count += getCount(board, row + 1);
                clean(board, row, i);
            }

        return count;
    }

    private static void clean(char[][] board, int row, int col) {
        for (int i = row, j = col;
             i < board.length && j < board[0].length && board[i][j] != '*';
             i++, j++)
            if (board[i][j] == mark[row])
                board[i][j] = '.';

        for (int i = row, j = col;
             i < board.length && j >= 0 && board[i][j] != '*'; i++, j--)
            if (board[i][j] == mark[row])
                board[i][j] = '.';
    }

    private static void copy(String[] ar, char[][] board) {
        for (int i = 0; i < ar.length; i++)
            for (int j = 0; j < ar[0].length(); j++)
                board[i][j] = ar[i].charAt(j);
    }

    private static void place(char[][] board, int row, int col) {
        board[row][col] = mark[row];
        for (int i = row, j = col;
             i < board.length && j < board[0].length && board[i][j] != '*';
             i++, j++)
            if (board[i][j] == '.')
                board[i][j] = mark[row];

        for (int i = row, j = col;
             i < board.length && j >= 0 && board[i][j] != '*'; i++, j--)
            if (board[i][j] == '.')
                board[i][j] = mark[row];
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
