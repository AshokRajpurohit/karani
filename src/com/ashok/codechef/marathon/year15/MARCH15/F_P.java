package com.ashok.codechef.marathon.year15.MARCH15;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

/**
 * @author  Ashok Rajpurohit
 * http://www.codechef.com/MARCH15/problems/MTRWY
 *
 */
public class F_P {

    private static PrintWriter out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream);
        F_P a = new F_P();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        StringBuilder sb = new StringBuilder();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int res = 0;
            int t_res = 0;
            int n = in.readInt();
            int m = in.readInt();
            int q = in.readInt();
            //            int[][] mat = new int[n][m];
            int[][] wall_mat = new int[n][m];
            boolean[][] vis_mat; // = new boolean[n][m];
            int x, y, x1, y1;

            if (n == 0 || m == 0) {
                while (q > 0) {
                    q--;
                    int q_type = in.readInt();
                    if (q_type == 1 || q_type == 2) {
                        x = in.readInt();
                        y = in.readInt();
                    } else if (q_type == 3) {
                        x = in.readInt();
                        y = in.readInt();
                        x1 = in.readInt();
                        y1 = in.readInt();
                    }
                }
            } else {
                try {
                    while (q > 0) {
                        q--;
                        int q_type = in.readInt();
                        if (q_type == 1) {
                            x = in.readInt() - 1;
                            y = in.readInt() - 1;
                            if (x < n && y < m && x >= 0 && y >= 0)
                                wall_mat[x][y] = wall_mat[x][y] | 1;
                            //                    vis_mat = null;
                        } else if (q_type == 2) {
                            x = in.readInt() - 1;
                            y = in.readInt() - 1;
                            if (x < n && y < m && x >= 0 && y >= 0)
                                wall_mat[x][y] = wall_mat[x][y] | 2;
                            //                    vis_mat = null;
                        } else if (q_type == 3) {
                            vis_mat = new boolean[n][m];
                            x = in.readInt() - 1;
                            y = in.readInt() - 1;
                            x1 = in.readInt() - 1;
                            y1 = in.readInt() - 1;
                            res = res + connect(wall_mat, x, y, x1, y1);
                        } else {
                            //                    vis_mat = new boolean[n][m];
                            res = res + l_path(wall_mat);
                            //                    vis_mat = null;
                        }
                    }
                } catch (Exception e) {
                    // TODO: Add catch code
                    // bekar ka exception
                }
            }
            sb.append(res).append('\n');
        }
        out.print(sb);
    }

    private int l_path(int[][] wall_mat) {
        int[][] plen_mat; // = new int[wall_mat.length][wall_mat[0].length];
        boolean[][] vis_mat; // = new boolean[wall_mat.length][wall_mat[0].length];
        int long_path = 0;

        for (int i = 0; i < wall_mat.length; i++) {
            for (int j = 0; j < wall_mat[i].length; j++) {

                if (i == 0 || i == wall_mat.length - 1) {
                    if (j == 0 || j == wall_mat[i].length - 1) {
                        vis_mat = null;
                        vis_mat =
                                new boolean[wall_mat.length][wall_mat[0].length];
                        plen_mat =
                                new int[wall_mat.length][wall_mat[0].length];
                        int temp = l_path(wall_mat, plen_mat, vis_mat, i, j);
                        long_path = long_path > temp ? long_path : temp;
                    } else {
                        if (i == 0 &&
                            ((wall_mat[i][j - 1] & 1) == 1 || (wall_mat[i][j] !=
                                                               0))) {
                            vis_mat = null;
                            vis_mat =
                                    new boolean[wall_mat.length][wall_mat[0].length];
                            plen_mat =
                                    new int[wall_mat.length][wall_mat[0].length];
                            int temp =
                                l_path(wall_mat, plen_mat, vis_mat, i, j);
                            long_path = long_path > temp ? long_path : temp;
                        } else if ((wall_mat[i][j - 1] & 1) == 1 ||
                                   (wall_mat[i][j] & 1) == 1 ||
                                   (wall_mat[i - 1][j] & 2) == 2) {
                            vis_mat = null;
                            vis_mat =
                                    new boolean[wall_mat.length][wall_mat[0].length];
                            plen_mat =
                                    new int[wall_mat.length][wall_mat[0].length];
                            int temp =
                                l_path(wall_mat, plen_mat, vis_mat, i, j);
                            long_path = long_path > temp ? long_path : temp;
                        }
                    }
                } else if (j == 0 || j == wall_mat[i].length - 1) {
                    if (j == 0 &&
                        ((wall_mat[i - 1][j] & 2) == 2 || wall_mat[i][j] !=
                         0)) {
                        vis_mat = null;
                        vis_mat =
                                new boolean[wall_mat.length][wall_mat[0].length];
                        plen_mat =
                                new int[wall_mat.length][wall_mat[0].length];
                        int temp = l_path(wall_mat, plen_mat, vis_mat, i, j);
                        long_path = long_path > temp ? long_path : temp;
                    } else if (j == wall_mat[i].length - 1) {
                        if ((wall_mat[i - 1][j] & 2) == 2 ||
                            (wall_mat[i][j - 1] & 1) == 1 ||
                            (wall_mat[i][j] & 2) == 2) {
                            vis_mat = null;
                            vis_mat =
                                    new boolean[wall_mat.length][wall_mat[0].length];
                            plen_mat =
                                    new int[wall_mat.length][wall_mat[0].length];
                            int temp =
                                l_path(wall_mat, plen_mat, vis_mat, i, j);
                            long_path = long_path > temp ? long_path : temp;
                        }
                    }
                } else {
                    int count =
                        wall_mat[i][j] & 1 + (wall_mat[i][j]) >> 1 + (wall_mat[i -
                                                                      1][j]) >>
                        1 + wall_mat[i][j - 1] & 1;
                    if (count == 3) {
                        vis_mat = null;
                        vis_mat =
                                new boolean[wall_mat.length][wall_mat[0].length];
                        plen_mat =
                                new int[wall_mat.length][wall_mat[0].length];
                        int temp = l_path(wall_mat, plen_mat, vis_mat, i, j);
                        long_path = long_path > temp ? long_path : temp;
                    }
                }

            }
        }

        return long_path;
    }

    private int l_path(int[][] wall_mat, int[][] plen_mat, boolean[][] vis_mat,
                       int x, int y) {
        //        System.out.println(x+","+y);
        if (x < 0 || y < 0 || x >= wall_mat.length || y >= wall_mat[0].length)
            return 0;

        if (vis_mat[x][y])
            return plen_mat[x][y];

        vis_mat[x][y] = true;
        int len = 1, l1 = 0, l2 = 0, l3 = 0, l4 = 0;

        if (y != 0 && (wall_mat[x][y - 1] & 1) == 0 &&
            vis_mat[x][y - 1] == false)
            l1 = l_path(wall_mat, plen_mat, vis_mat, x, y - 1);

        if (x != 0 && (wall_mat[x - 1][y] & 2) == 0 &&
            vis_mat[x - 1][y] == false)
            l2 = l_path(wall_mat, plen_mat, vis_mat, x - 1, y);

        if (y != wall_mat[0].length - 1 && (wall_mat[x][y] & 1) == 0 &&
            vis_mat[x][y + 1] == false)
            l3 = l_path(wall_mat, plen_mat, vis_mat, x, y + 1);

        if (x != wall_mat.length - 1 && (wall_mat[x][y] & 2) == 0 &&
            vis_mat[x + 1][y] == false)
            l4 = l_path(wall_mat, plen_mat, vis_mat, x + 1, y);

        len = l1 > l2 ? l1 : l2;
        len = len > l3 ? len : l3;
        len = len > l4 ? len : l4;
        len++;
        plen_mat[x][y] = len;
        return len;
    }

    private int connect(int[][] wall_mat, int x, int y, int x1, int y1) {
        boolean[][] vis_mat = new boolean[wall_mat.length][wall_mat[0].length];
        return connect(wall_mat, vis_mat, x, y, x1, y1);
    }

    private int connect(int[][] wall_mat, boolean[][] vis_mat, int x, int y,
                        int x1, int y1) {
        //        System.out.println(x+","+y+","+x1+","+y1);

        if (x >= wall_mat.length || x < 0 || y >= wall_mat[0].length || y < 0)
            return 0;

        if (x == x1 && y == y1)
            return 1;

        if (vis_mat[x][y])
            return 0;


        int res = 0;
        vis_mat[x][y] = true;
        if ((wall_mat[x][y] & 1) == 0) {
            res = connect(wall_mat, vis_mat, x, y + 1, x1, y1);
        }
        if (res == 1)
            return 1;

        if ((wall_mat[x][y] & 2) == 0) {
            res = connect(wall_mat, vis_mat, x + 1, y, x1, y1);
        }
        if (res == 1)
            return 1;

        if (y != 0 && (wall_mat[x][y - 1] & 1) == 0 &&
            vis_mat[x][y - 1] == false)
            res = connect(wall_mat, vis_mat, x, y - 1, x1, y1);

        if (res == 1)
            return 1;

        if (x != 0 && (wall_mat[x - 1][y] & 2) == 0 &&
            vis_mat[x - 1][y] == false)
            res = connect(wall_mat, vis_mat, x - 1, y, x1, y1);

        return res;
    }

    final static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int readInt() {
            return Integer.parseInt(next());
        }

    }
}
