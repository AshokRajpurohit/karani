package com.ashok.leetcode.practice;


import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

import java.util.Random;

/**
 * Problem Name: Set Matrix Zeroes
 * Link: https://leetcode.com/problems/set-matrix-zeroes/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class SetMatrixZeroes {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        SetMatrixZeroes a = new SetMatrixZeroes();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        while (true) {
            int m = in.readInt();
            int n = in.readInt();
            int[][] matrix = in.readIntTable(m, n);
            out.print(matrix);
            chachaWay(matrix);
            out.print("-----------------------------\n");
            out.print(matrix);
            out.flush();
        }
    }

    public void chachaWay(int[][] matrix) {
        int row = -1, col = -1;

        for (int i = 0; i < matrix.length && row == -1; i++)
            for (int j = 0; j < matrix[0].length & row == -1; j++)
                if (matrix[i][j] == 0) {
                    row = i;
                    col = j;
                }

        if (row == -1)
            return;

        for (int i = 0; i < matrix.length; i++)
            if (matrix[i][col] == 0)
                matrix[i][col] = 1;
            else
                matrix[i][col] = 0;

        for (int j = 0; j < matrix[0].length; j++)
            if (matrix[row][j] == 0)
                matrix[row][j] = 1;
            else
                matrix[row][j] = 0;

        for (int i = 0; i < matrix.length; i++)
            if (i != row)
                for (int j = 0; j < matrix[0].length; j++)
                    if (j != col && matrix[i][j] == 0) {
                        matrix[row][j] = 1;
                        matrix[i][col] = 1;
                    }

        out.print("-------------------------\n");
        out.print(matrix);

        for (int i = 0; i < matrix.length; i++)
            if (i != row && matrix[i][col] == 1) {
                for (int j = 0; j < matrix[0].length; j++)
                    if (j != col)
                        matrix[i][j] = 0;
            }

        for (int j = 0; j < matrix[0].length; j++)
            if (j != col && matrix[row][j] == 1) {
                for (int i = 0; i < matrix.length; i++)
                    if (i != row)
                        matrix[i][j] = 0;
            }

        for (int i = 0; i < matrix.length; i++)
            matrix[i][col] = 0;

        for (int i = 0; i < matrix[0].length; i++)
            matrix[row][i] = 0;
    }

    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        if (m == 0)
            return;

        int n = matrix[0].length;
        if (n == 0)
            return;

        int value = getValue(matrix);

        out.println("value is : " + value);

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (matrix[i][j] == 0)
                    setColRows(matrix, i, j, value);

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (matrix[i][j] == value)
                    matrix[i][j] = 0;
    }

    private int getValue(int[][] matrix) {
        Random random = new Random();
        int res = random.nextInt();
        boolean notUnik = true;

        while (notUnik) {
            notUnik = false;
            res = random.nextInt();
            for (int i = 0; i < matrix.length && !notUnik; i++)
                for (int j = 0; j < matrix[0].length && !notUnik; j++)
                    if (res == matrix[i][j])
                        notUnik = true;
        }

        return res;
    }

    private void setColRows(int[][] matrix, int row, int col, int value) {
        for (int i = 0; i < matrix.length; i++)
            if (matrix[i][col] != 0)
                matrix[i][col] = value;

        for (int i = 0; i < matrix[0].length; i++)
            if (matrix[row][i] != 0)
                matrix[row][i] = value;
    }
}
