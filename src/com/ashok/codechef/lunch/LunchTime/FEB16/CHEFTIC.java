package com.ashok.codechef.lunch.LunchTime.FEB16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Chef and Tic-Tac-Toe
 * https://www.codechef.com/LTIME33/problems/CHEFTIC
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class CHEFTIC {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CHEFTIC a = new CHEFTIC();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);
        String yes = "YES\n", no = "NO\n";

        while (t > 0) {
            t--;
            int n = in.readInt(), k = in.readInt();
            String[] ar = new String[n];

            for (int i = 0; i < n; i++)
                ar[i] = in.read(n);

            if (check(ar, k))
                sb.append(yes);
            else
                sb.append(no);
        }

        out.print(sb);
    }

    private static boolean check(String[] board, int k) {
        boolean win = false;
        for (int i = 0; i < board.length && !win; i++)
            win = check(board[i], k);

        if (win)
            return true;

        for (int i = 0; i < board.length && !win; i++) {
            int sum = 0, space = 0;
            for (int j = 0; j < k; j++)
                if (board[j].charAt(i) == 'X')
                    sum++;
                else if (board[j].charAt(i) == '.')
                    space++;

            win = sum == k - 1 && space == 1;

            for (int j = 1, m = k; m < board.length && !win; j++, m++) {
                if (board[j - 1].charAt(i) == 'X')
                    sum--;
                else if (board[j - 1].charAt(i) == '.')
                    space--;

                if (board[m].charAt(i) == 'X')
                    sum++;
                else if (board[m].charAt(i) == '.')
                    space++;

                win = sum == k - 1 && space == 1;
            }
        }

        if (win)
            return true;

        for (int i = 0; i <= board.length - k && !win; i++) {
            for (int j = 0; j <= board.length - k && !win; j++) {
                int sum = 0, space = 0;
                for (int m = 0; m < k; m++) {
                    if (board[i + m].charAt(j + m) == 'X')
                        sum++;
                    else if (board[i + m].charAt(j + m) == '.')
                        space++;
                }

                win = sum == k - 1 && space == 1;

                for (int m = k;
                     i + m < board.length && j + m < board.length && !win;
                     m++) {
                    if (board[i + m - k].charAt(j + m - k) == 'X')
                        sum--;
                    else if (board[i + m - k].charAt(j + m - k) == '.')
                        space--;

                    if (board[i + m].charAt(j + m) == 'X')
                        sum++;
                    else if (board[i + m].charAt(j + m) == '.')
                        space++;

                    win = sum == k - 1 && space == 1;
                }
            }
        }

        if (win)
            return win;

        int n = board.length - 1;
        for (int i = 0; i <= board.length - k && !win; i++) {
            for (int j = n; j >= k - 1 && !win; j--) {
                int sum = 0, space = 0;
                for (int m = 0; m < k; m++) {
                    if (board[i + m].charAt(j - m) == 'X')
                        sum++;
                    else if (board[i + m].charAt(j - m) == '.')
                        space++;
                }

                win = sum == k - 1;

                for (int m = k; i + m < board.length && j - m >= 0 && !win;
                     m++) {
                    if (board[i + m - k].charAt(j - m + k) == 'X')
                        sum--;
                    else if (board[i + m - k].charAt(j - m + k) == '.')
                        space--;

                    if (board[i + m].charAt(j - m) == 'X')
                        sum++;
                    else if (board[i + m].charAt(j - m) == '.')
                        space++;

                    win = sum == k - 1;
                }
            }
        }

        return win;
    }

    private static boolean check(String row, int k) {
        int sum = 0, space = 0;
        for (int i = 0; i < k; i++)
            if (row.charAt(i) == 'X')
                sum++;
            else if (row.charAt(i) == '.')
                space++;


        for (int i = 1, j = k; j < row.length() && sum != k - 1; i++, j++) {
            if (row.charAt(i - 1) == 'X')
                sum--;
            else if (row.charAt(i - 1) == '.')
                space--;

            if (row.charAt(j) == 'X')
                sum++;
            else if (row.charAt(j) == '.')
                space++;
        }

        return sum == k - 1 && space == 1;
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
