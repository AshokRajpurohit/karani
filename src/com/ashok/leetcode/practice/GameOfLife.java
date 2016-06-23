package com.ashok.leetcode.practice;


import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name: Game of Life
 * Link: https://leetcode.com/problems/game-of-life/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class GameOfLife {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        GameOfLife a = new GameOfLife();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        int m = in.readInt();
        int n = in.readInt();
        int[][] board = in.readIntTable(m, n);

        out.print(board);
        out.println("------------------");
        gameOfLife(board);
        out.print(board);
    }

    public void gameOfLife(int[][] board) {
        if (board.length == 0 || board[0].length == 0)
            return;

        int m = board.length, n = board[0].length;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                int count = 0;
                if (i > 0) {
                    if (board[i - 1][j] > 0)
                        count++;

                    if (j > 0 && board[i - 1][j - 1] > 0)
                        count++;

                    if (j < n - 1 && board[i - 1][j + 1] > 0)
                        count++;
                }

                if (i < m - 1) {
                    if (board[i + 1][j] > 0)
                        count++;

                    if (j > 0 && board[i + 1][j - 1] > 0)
                        count++;

                    if (j < n - 1 && board[i + 1][j + 1] > 0)
                        count++;
                }

                if (j > 0 && board[i][j - 1] > 0)
                    count++;

                if (j < n - 1 && board[i][j + 1] > 0)
                    count++;

                out.println(i + ", " + j + ": " + count);

                if (count < 2 || count > 3) {
                    if (board[i][j] == 1)
                        board[i][j] = 2;
                } else if (board[i][j] == 0 && count == 3)
                    board[i][j] = -1;
            }

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (board[i][j] == -1)
                    board[i][j] = 1;
                else if (board[i][j] == 2)
                    board[i][j] = 0;
    }
}
