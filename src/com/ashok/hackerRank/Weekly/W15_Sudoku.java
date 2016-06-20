package com.ashok.hackerRank.Weekly;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Sudoku Swap
 * https://www.hackerrank.com/contests/w15/challenges/sudoku-swap
 * (x1,y1) <-> (x2,y2)
 */

public class W15_Sudoku {

    private static PrintWriter out;
    private static InputStream in;
    private static String correct = "Serendipity\n";

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        W15_Sudoku a = new W15_Sudoku();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        String test = "Case #";
        StringBuilder sb = new StringBuilder(t << 4);

        for (int i = 1; i <= t; i++) {
            sb.append(test).append(i).append(":\n").append(sudoku(in.readSudoku()));
        }

        out.print(sb);
    }

    private static String sudoku(int[][] table) {
        int r1, r2, r1c1, r1c2, r2c1, r2c2;
        int i = 0;
        boolean allRows = checkAllRows(table), allCol = checkAllCol(table);
        if (allRows && allCol)
            return correct;

        if (allRows) {
            return sudokuCol(table);
        }

        if (allCol)
            return sudokuRows(table);

        return sudokuGen(table);
    }

    private static String sudokuGen(int[][] table) {
        int i = 0, r1 = 0, r2 = 0;
        int r1c1 = 0, r1c2 = 0, r2c1 = 0, r2c2 = 0, rc1 = 0, rc2 = 0;
        StringBuilder sb = new StringBuilder();

        int temp = getFaultiRows(table);
        r1 = temp >>> 4;
        r2 = temp & 15;

        //        while (i < 9 && checkRow(table[i]))
        //            i++;
        //
        //        r1 = i;
        //        i++;
        //        while (i < 9 && checkCol(table, i))
        //            i++;
        //        r2 = i;

        temp = getRow(table[r1]);
        r1c1 = temp >>> 4;
        r1c2 = temp & 15;

        temp = getRow(table[r2]);
        r2c1 = temp >>> 4;
        r2c2 = temp & 15;

        if (checkCol(table, r1c1))
            rc1 = r1c2;
        else
            rc1 = r1c1;

        if (checkCol(table, r2c1))
            rc2 = r2c2;
        else
            rc2 = r2c1;

        sb.append(formString(r1, rc1, r2, rc2));
        return sb.toString();

        //        return "Rajpurohit\n";
    }

    private static String sudokuRows(int[][] table) {
        int i = 0, r1 = 0, r2 = 0;
        int r1c1, r1c2, r2c1, r2c2;
        StringBuilder sb = new StringBuilder();

        int temp = getFaultiRows(table);
        r1 = temp >>> 4;
        r2 = temp & 15;

        temp = getRow(table[r1]);
        r1c1 = temp >>> 4;
        r1c2 = temp & 15;

        temp = getRow(table[r2]);
        r2c1 = temp >>> 4;
        r2c2 = temp & 15;

        //        if (r1c1 == r2c1) {
        swap(table, r1, r1c1, r2, r2c1);
        if (checkRow(table[r1]) && checkRow(table[r2]) &&
            !cubeWrong(table, r1, r1c1) && !cubeWrong(table, r2, r2c1))
            sb.append(formString(r1, r1c1, r2, r2c1));
        swap(table, r1, r1c1, r2, r2c1);
        //        }

        //        if (r1c1 == r2c2) {
        swap(table, r1, r1c1, r2, r2c2);
        if (checkRow(table[r1]) && checkRow(table[r2]) &&
            !cubeWrong(table, r1, r1c1) && !cubeWrong(table, r2, r2c2))
            sb.append(formString(r1, r1c1, r2, r2c2));
        swap(table, r1, r1c1, r2, r2c2);
        //        }

        //        if (r1c2 == r2c1) {
        swap(table, r1, r1c2, r2, r2c1);
        if (checkRow(table[r1]) && checkRow(table[r2]) &&
            !cubeWrong(table, r1, r1c2) && !cubeWrong(table, r2, r2c1))
            sb.append(formString(r1, r1c2, r2, r2c1));
        swap(table, r1, r1c2, r2, r2c1);
        //        }

        //        if (r1c2 == r2c2) {
        swap(table, r1, r1c2, r2, r2c2);
        if (checkRow(table[r1]) && checkRow(table[r2]) &&
            !cubeWrong(table, r1, r1c2) && !cubeWrong(table, r2, r2c2))
            sb.append(formString(r1, r1c2, r2, r2c2));
        swap(table, r1, r1c2, r2, r2c2);
        //        }

        return sb.toString();

        //        if (r1c1 == r2c1 && r1c2 == r2c2)
        //            return formString(r1, r1c1, r1, r1c2) +
        //                formString(r2, r2c1, r2, r2c2);
        //
        //        if (r1c1 == r2c1)
        //            return formString(r1, r1c1, r2, r2c1);
        //
        //        if (r1c2 == r2c1)
        //            return formString(r1, r1c2, r2, r2c1);
        //
        //        if (r1c1 == r2c2)
        //            return formString(r1, r1c1, r2, r2c2);
        //
        //        return formString(r1, r1c2, r2, r2c2);
    }

    private static String sudokuCol(int[][] table) {
        int i = 0, c1 = 0, c2 = 0;
        int c1r1, c1r2, c2r1, c2r2;
        StringBuilder sb = new StringBuilder();

        int temp = getFaultiCols(table);
        c1 = temp >>> 4;
        c2 = temp & 15;

        temp = getCol(table, c1);
        c1r1 = temp >>> 4;
        c1r2 = temp & 15;

        temp = getCol(table, c2);
        c2r1 = temp >>> 4;
        c2r2 = temp & 15;

        //        if (c1r1 == c2r1) {
        swap(table, c1r1, c1, c2r1, c2);
        if (checkCol(table, c1) && checkCol(table, c2) &&
            !cubeWrong(table, c1r1, c1) && !cubeWrong(table, c2r1, c2))
            sb.append(formString(c1r1, c1, c2r1, c2));
        swap(table, c1r1, c1, c2r1, c2);
        //        }

        //        if (c1r1 == c2r2) {
        swap(table, c1r1, c1, c2r2, c2);
        if (checkCol(table, c1) && checkCol(table, c2) &&
            !cubeWrong(table, c1r1, c1) && !cubeWrong(table, c2r2, c2))
            sb.append(formString(c1r2, c1, c2r1, c2));
        swap(table, c1r1, c1, c2r2, c2);
        //        }

        //        if (c1r2 == c2r1) {
        swap(table, c1r2, c1, c2r1, c2);
        if (checkCol(table, c1) && checkCol(table, c2) &&
            !cubeWrong(table, c1r2, c1) && !cubeWrong(table, c2r1, c2))
            sb.append(formString(c1r1, c1, c2r1, c2));
        swap(table, c1r2, c1, c2r1, c2);
        //        }

        //        if (c1r2 == c2r2) {
        swap(table, c1r2, c1, c2r2, c2);
        if (checkCol(table, c1) && checkCol(table, c2) &&
            !cubeWrong(table, c1r2, c1) && !cubeWrong(table, c2r2, c2))
            sb.append(formString(c1r2, c1, c2r2, c2));
        swap(table, c1r2, c1, c2r2, c2);
        //        }

        return sb.toString();

        //        if (c1r1 == c2r1 && c1r2 == c2r2)
        //            return formString(c1r1, c1, c1r1, c2) +
        //                formString(c1r2, c1, c1r2, c2);
        //
        //        if (c1r1 == c2r1)
        //            return formString(c1r1, c1, c1r1, c2);
        //
        //        if (c1r2 == c2r1)
        //            return formString(c1r2, c1, c1r2, c2);
        //
        //        if (c1r1 == c2r2)
        //            return formString(c1r1, c1, c1r1, c2);
        //
        //        return formString(c1r2, c1, c1r2, c2);
    }

    private static int getFaultiRows(int[][] table) {
        int res = 0;
        for (int i = 0; i < 9; i++)
            if (!checkRow(table[i]))
                res = (res << 4) + i;
        return res;
    }

    private static int getFaultiCols(int[][] table) {
        int res = 0;
        for (int i = 0; i < 9; i++)
            if (!checkCol(table, i))
                res = (res << 4) + i;
        return res;
    }

    private static void swap(int[][] table, int r1, int c1, int r2, int c2) {
        int temp = table[r1][c1];
        table[r1][c1] = table[r2][c2];
        table[r2][c2] = temp;
    }

    private static String formString(int x1, int y1, int x2, int y2) {
        // (x1,y1) <-> (x2,y2)
        x1++;
        y1++;
        x2++;
        y2++;
        return "(" + x1 + ',' + y1 + ") <-> (" + x2 + ',' + y2 + ")\n";
    }

    private static String solve(int[][] table) {
        boolean rows = checkAllRows(table);
        boolean cols = checkAllCol(table);

        if (rows) {
            if (cols)
                return correct;
            return col_way(table);
        }
        if (cols)
            return row_way(table);
        return generic_way(table);
    }

    private static String row_way(int[][] table) {
        StringBuilder sb = new StringBuilder();
        int row1 = 0, row2 = 0, i = 0;
        while (i < 9 && checkRow(table[i]))
            i++;

        row1 = i;
        int row1_param = getRow(table[i]);
        i++;

        while (i < 9 && checkRow(table[i]))
            i++;

        row2 = i;
        int row2_param = getRow(table[i]);

        int row1a = row1_param >>> 4, row1b = row1_param & 15;
        int row2a = row2_param >>> 4, row2b = row2_param & 15;

        if (row1a == row2a && row1b == row2b) {
            if (sameCube(row1a, row1b)) {
                //  (x1,y1) <-> (x2,y2)
                sb.append('(' + row1 + ',' + row1a + ") <-> (" + row2 + ',' +
                          row2a + ")\n");
                sb.append('(' + row1 + ',' + row1b + ") <-> (" + row2 + ',' +
                          row2b + ")\n");
                return sb.toString();
            }
            if (cubeWrong(table, row1, row1a)) {
                sb.append('(' + row1 + ',' + row1a + ") <-> (" + row2 + ',' +
                          row2a + ")\n");
                return sb.toString();
            }
            sb.append('(' + row1 + ',' + row1b + ") <-> (" + row2 + ',' +
                      row2b + ")\n");
            return sb.toString();
        }

        if (row1a == row2a) {
            sb.append('(' + row1 + ',' + row1a + ") <-> (" + row2 + ',' +
                      row2a + ")\n");
            return sb.toString();
        }

        if (row1a == row2b)

            sb.append('(' + row1 + ',' + row1b + ") <-> (" + row2 + ',' +
                      row2b + ")\n");
        return sb.toString();
    }

    private static String col_way(int[][] table) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < 9 && checkCol(table, i))
            i++;
        int col1 = i;
        int col1_param = getCol(table, i);
        i++;

        while (i < 9 && checkCol(table, i))
            i++;
        int col2 = i;
        int col2_param = getCol(table, i);

        int col1a = col1_param >>> 4, col1b = col1_param & 15;
        int col2a = col2_param >>> 4, col2b = col2_param & 15;

        if (col1a == col2a && col1b == col2b) {
            if (sameCube(col1a, col1b)) {
                //  (x1,y1) <-> (x2,y2)
                sb.append('(' + col1a + ',' + col1 + ") <-> (" + col2a + ',' +
                          col2 + ")\n");
                sb.append('(' + col1b + ',' + col1 + ") <-> (" + col2b + ',' +
                          col2 + ")\n");
                return sb.toString();
            }
            if (cubeWrong(table, col1a, col1)) {
                sb.append('(' + col1a + ',' + col1 + ") <-> (" + col2a + ',' +
                          col2 + ")\n");
                return sb.toString();
            }
            sb.append('(' + col1b + ',' + col1 + ") <-> (" + col2b + ',' +
                      col2 + ")\n");
            return sb.toString();
        }

        //        if (col1a == col1b) {
        //            sb.append('(' + row1 + ',' + row1a + ") <-> (" + row2 + ',' +
        //                      row2a + ")\n");
        //            return sb.toString();
        //        }
        //
        //        sb.append('(' + row1 + ',' + row1b + ") <-> (" + row2 + ',' + row2b +
        //                  ")\n");
        //        return sb.toString();

        return "Rajpurohit\n";
    }

    private static boolean cubeWrong(int[][] table, int row, int col) {
        row = row - row % 3;
        col = col - col % 3;
        int sum = 0;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                sum += table[row + i][col + j];

        return sum != 45;
    }

    private static boolean sameCube(int x, int y) {
        return x / 3 == y / 3;
    }

    private static boolean sameCube(int r1, int c1, int r2, int c2) {
        if (r1 / 3 != r2 / 3)
            return false;

        if (c1 / 3 != c2 / 3)
            return false;

        return true;
    }

    private static int getRow(int[] row) {
        int[] temp = new int[10];
        for (int e : row)
            temp[e]++;

        int d = 0;
        for (int i = 0; i < 9; i++) {
            if (temp[row[i]] == 2)
                d = (d << 4) + i;
        }
        return d;
    }

    private static int getCol(int[][] table, int col) {
        int[] temp = new int[10];
        for (int i = 0; i < 9; i++)
            temp[table[i][col]]++;

        int d = 0;
        for (int i = 0; i < 9; i++)
            if (temp[table[i][col]] == 2)
                d = (d << 4) + i;

        return d;
    }

    private static boolean checkRow(int[] row) {
        int sum = 0;
        for (int i = 0; i < 9; i++)
            sum += row[i];

        return sum == 45;
    }

    private static boolean checkCol(int[][] table, int col) {
        if (col >= 9)
            return true;
        int sum = 0;
        for (int i = 0; i < 9; i++)
            sum += table[i][col];

        return sum == 45;
    }

    private static boolean checkAllRows(int[][] table) {
        for (int i = 0; i < 9; i++)
            if (!checkRow(table[i]))
                return false;

        return true;
    }

    private static boolean checkAllCol(int[][] table) {
        for (int i = 0; i < 9; i++)
            if (!checkCol(table, i))
                return false;

        return true;
    }

    private static String generic_way(int[][] table) {
        return "Rajpurohit\n";
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }

        public int[][] readSudoku() throws IOException {
            int[][] ar = new int[9][];
            for (int i = 0; i < 9; i++)
                ar[i] = readIntArray(9);
            return ar;
        }
    }
}
