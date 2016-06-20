package com.ashok.codechef.marathon.year15.SEP15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * problem: Chef and Chetris
 * Link: https://www.codechef.com/SEPT15/problems/CHTTRS
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class CHTTRS {

    private static PrintWriter out;
    private static InputStream in;
    private static boolean[][] grid = new boolean[15][15];
    private static int[] rowsum = new int[15];
    private static Block[][] dabba = new Block[7][];
    private static char[] rot = { 'a', 'b', 'c', 'd' };
    private static int GameOver = 28563;

    static {
        dabba[0] = new Block[2];
        // 1.a
        dabba[0][0] = new Block(1, 4);
        for (int i = 0; i < 4; i++)
            dabba[0][0].block[0][i] = true;

        // 1.b
        dabba[0][1] = dabba[0][0].rotateLeft();

        dabba[1] = new Block[4];
        // 2.a
        dabba[1][0] = new Block(2, 3);
        dabba[1][0].block[0][0] = true;
        for (int i = 0; i < 3; i++)
            dabba[1][0].block[1][i] = true;

        // 2.b
        dabba[1][1] = dabba[1][0].rotateLeft();
        // 2.c
        dabba[1][2] = dabba[1][0].rotateRight();
        // 2.d
        dabba[1][3] = dabba[1][1].rotateLeft();

        // 3.a
        dabba[2] = new Block[4];
        dabba[2][0] = new Block(2, 3);
        dabba[2][0].block[0][2] = true;
        for (int i = 0; i < 3; i++)
            dabba[2][0].block[1][i] = true;

        // 3.b, c, d
        dabba[2][1] = dabba[2][0].rotateRight();
        dabba[2][2] = dabba[2][1].rotateRight();
        dabba[2][3] = dabba[2][2].rotateRight();

        // 4
        dabba[3] = new Block[1];
        dabba[3][0] = new Block(2, 2);
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                dabba[3][0].block[i][j] = true;

        // 5.a, b
        dabba[4] = new Block[2];
        dabba[4][0] = new Block(2, 3);
        for (int i = 0; i < 2; i++) {
            dabba[4][0].block[0][i + 1] = true;
            dabba[4][0].block[1][i] = true;
        }
        dabba[4][1] = dabba[4][0].rotateLeft();

        // 6.a, b, c, d
        dabba[5] = new Block[4];
        dabba[5][0] = new Block(2, 3);
        dabba[5][0].block[0][1] = true;
        for (int i = 0; i < 3; i++)
            dabba[5][0].block[1][i] = true;

        for (int i = 1; i < 4; i++)
            dabba[5][i] = dabba[5][i - 1].rotateRight();

        // 7. a, b
        dabba[6] = new Block[2];
        dabba[6][0] = new Block(2, 3);
        for (int i = 0; i < 2; i++) {
            dabba[6][0].block[0][i] = true;
            dabba[6][0].block[1][i + 1] = true;
        }

        dabba[6][1] = dabba[6][0].rotateLeft();

    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CHTTRS a = new CHTTRS();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int type = in.readInt();

        while (type != GameOver) {
            if (type == 1)
                process(in.readInt() - 1);
            else
                process(in.readInt() - 1, in.readInt() - 49);

            printGrid(); // shows matrix status and row count

            out.flush();
            type = in.readInt();
        }
    }

    private static void printGrid() {
        StringBuilder sb = new StringBuilder(241);
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (grid[i][j])
                    sb.append('*');
                else
                    sb.append('o');
            }
            sb.append('\t').append(rowsum[i]).append('\n');
        }
        out.print(sb);
    }

    private static void process(int figure) {
        // write code for all possible rotations
        int row = 0, col = 0, ori = 0;
        for (int i = 0; i < dabba[figure].length; i++) {
            int res = findPosFor(dabba[figure][i]);
            if (row < (res >>> 4)) {
                row = (res >>> 4);
                col = res & 15;
                ori = i;
            }
        }
        place(dabba[figure][ori], row, col);
        out.println(rot[ori] + " " + row + " " + (col + 1));
    }

    private static void process(int figure, int rotation) {
        int res = findPosFor(dabba[figure][rotation]);
        int row = res >>> 4;
        int col = res & 15;
        place(dabba[figure][rotation], row, col);
        out.println(rot[rotation] + " " + row + " " + (col + 1));
        // write code for fixed rotation
    }

    private static int findPosFor(Block box) {
        int row = 0, col = 0;
        for (int j = 0; j <= 15 - box.block[0].length; j++) {
            for (int i = 0; i <= 15 - box.block.length; i++) {
                if (isValid(box, i, j)) {
                    if (row < i) {
                        row = i;
                        col = j;
                    }
                } else
                    break;
            }
        }

        row += box.block.length;

        return (row << 4) | col;
    }

    private static void place(Block box, int row, int col) {
        row -= box.block.length;
        if (row < 0)
            return;

        for (int i = 0; i < box.block.length; i++)
            for (int j = 0; j < box.block[0].length; j++)
                grid[row + i][col + j] |= box.block[i][j];

        for (int i = 0; i < box.block.length; i++)
            for (int j = 0; j < box.block[0].length; j++)
                if (box.block[i][j])
                    rowsum[row + i]++;

        for (int i = 0; i < box.block.length; i++)
            if (rowsum[row + i] == 15)
                reset(row + i);

    }

    private static void reset(int row) {
        if (row == 0)
            return;
        // reset this row and all the points above it :)
        for (int i = row; i >= 1 && rowsum[i] > 0; i--) {
            rowsum[i] = rowsum[i - 1];
            for (int j = 0; j < 15; j++)
                grid[i][j] = grid[i - 1][j];
        }
    }

    private static boolean isValid(Block box, int row, int col) {
        if (row + box.block.length > 15 || col + box.block[0].length > 15)
            return false;

        for (int i = 0; i < box.block.length; i++)
            for (int j = 0; j < box.block[0].length; j++)
                if (box.block[i][j] && grid[row + i][col + j])
                    return false;

        return true;
    }

    final static class Block {
        boolean[][] block;

        Block(int a, int b) {
            block = new boolean[a][b];
        }

        Block rotateLeft() {
            int a = block.length, b = block[0].length;

            Block res = new Block(b, a);
            for (int i = 0; i < a; i++)
                for (int j = 0; j < b; j++)
                    res.block[b - j - 1][i] = block[i][j];

            return res;
        }

        Block rotateRight() {
            int a = block.length, b = block[0].length;

            Block res = new Block(b, a);
            for (int i = 0; i < a; i++)
                for (int j = 0; j < b; j++)
                    res.block[j][a - 1 - i] = block[i][j];

            return res;
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
