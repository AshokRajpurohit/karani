package com.ashok.edrepublic;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Predict Outcome of Tic Tac Toe Game
 *
 * Given a Tic-Tac-Toe board representation as array of Strings find if the
 * board position is INVALID, DRAW, FIRST or SECOND. Where FIRST would
 * represent winning by player using O's and SECOND would represent winning
 * by player using X's.
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class TicTacToe {

    private static PrintWriter out;
    private static InputStream in;
    private final static String DRAW = "DRAW", FIRST = "FIRST", SECOND =
        "SECOND", INVALID = "INVALID";

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        TicTacToe a = new TicTacToe();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        String[] board = new String[3];
        board[0] = in.read();
        board[1] = in.read();
        board[2] = in.read();

        out.println(analyze(board));
    }

    private static String analyze(String[] board) {
        // let's check vaility of the board
        int x = 0, o = 0;
        for (String s : board) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == 'X')
                    x++;
                else if (s.charAt(i) == 'O')
                    o++;
            }
        }

        if (x > o)
            return INVALID;

        // let's check winnings
        boolean first = false, second = false;
        for (String s : board) {
            if (s.charAt(0) == s.charAt(1) && s.charAt(1) == s.charAt(2)) {
                if (s.charAt(0) == 'O')
                    first = true;
                else if (s.charAt(0) == 'X')
                    second = true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (board[0].charAt(i) == board[1].charAt(i) &&
                board[1].charAt(i) == board[2].charAt(i)) {
                if (board[0].charAt(i) == 'O')
                    first = true;
                else if (board[0].charAt(i) == 'X')
                    second = true;
            }
        }

        if (first && second)
            return INVALID;

        if (first)
            return FIRST;

        if (second)
            return SECOND;

        if (x == 1) {
            if (board[1].charAt(1) == 'X')
                return DRAW;

            return FIRST;
        }

        return DRAW;
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

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
