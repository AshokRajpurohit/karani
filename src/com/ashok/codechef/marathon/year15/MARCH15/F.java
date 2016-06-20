package com.ashok.codechef.marathon.year15.MARCH15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problem Link:    http://www.codechef.com/MARCH15/problems/MTRWY
 *
 */
public class F {

    private static PrintWriter out;
    private static InputStream in;
    private static boolean[][] vis_mat;
    private static int[][] wall_mat;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        F a = new F();
        a.solve();
        out.close();
    }

    /**
     *  This is the main function for the task.
     *  every input, output is handled by this function.
     *  based on query type (q_type) this function passes parameters to
     *  respective functions and appends the result to StringBuffer for
     *  output.
     * @throws IOException
     */

    public void solve() throws IOException {
        InputReader in = new InputReader();
        StringBuilder sb = new StringBuilder();
        int t = in.readInt(); // no of test cases.

        while (t > 0) {
            t--;
            int res = 0; // result to be shown finally.
            int n = in.readInt(); // no of rows
            int m = in.readInt(); // no of collumns
            int q = in.readInt(); // no of queries

            /**
             * matrix wall_mat is the actual matrix. it can have 4 type of
             * values.
             * 1 for wall in right side only.
             * 2 wall in lower side only.
             * 3 wall on right and lower side both.
             * 0 no wall.
             * we can represent this as two bit number where the lsb
             * representing wall on right side and msb for lower side.
             * vis_mat is to check visited location.
             */


            int x, y, x1, y1;

            /**
             * p_qtype is to store the prev query type.
             * if no queries of type 1 and 2 are made then for query type 4
             * we can use the already calculated result sotred in last_path.
             */
            int p_qtype = 1, q_type, last_path = 0;

            if (n == 0 || m == 0) {
                while (q > 0) {
                    q--;
                    q_type = in.readInt();
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
                wall_mat = new int[n][m];
                vis_mat = new boolean[n][m];
                while (q > 0) {
                    q--;
                    q_type = in.readInt();
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
                        x = in.readInt() - 1;
                        y = in.readInt() - 1;
                        x1 = in.readInt() - 1;
                        y1 = in.readInt() - 1;
                        res = res + connect(x, y, x1, y1);
                    } else {
                        if (p_qtype == 1 || p_qtype == 2)
                            last_path = l_path();

                        //                        sb.append(last_path).append(" ,");

                        res = res + last_path;
                        //                    vis_mat = new boolean[n][m];

                        //                    vis_mat = null;
                    }
                }
            }
            sb.append('\n').append(res).append('\n');
        }
        out.print(sb);
    }

    /**
     *  This is the primary function that calls the secondary function for
     *  longest path with the same name.
     *  it checks the longest path starting only from corner points
     *  having atleast two side wall or boundry.
     *  it is based on assumption that the end points of longest path are
     *  always points with atleast two boundries or maximum two degree
     *  freedom.
     * @param wall_mat the actual wall matrix.
     * @return  longest path in matrix.
     */
    private int l_path() {
        //        int[][] plen_mat;// = new int[wall_mat.length][wall_mat[0].length];
        //        boolean[][] vis_mat; // = new boolean[wall_mat.length][wall_mat[0].length];
        int long_path = 0;

        for (int i = 0; i < wall_mat.length; i++) {
            for (int j = 0; j < wall_mat[i].length; j++) {

                if (i == 0 || i == wall_mat.length - 1) {
                    if (j == 0 || j == wall_mat[i].length - 1) {
                        int temp = l_path(i, j);
                        long_path = long_path > temp ? long_path : temp;
                    } else {
                        if (i == 0 &&
                            ((wall_mat[i][j - 1] & 1) == 1 || (wall_mat[i][j] !=
                                                               0))) {
                            int temp = l_path(i, j);
                            long_path = long_path > temp ? long_path : temp;
                        } else if (i != 0 && j != 0 &&
                                   ((wall_mat[i][j - 1] & 1) == 1 ||
                                    (wall_mat[i][j] & 1) == 1 ||
                                    (wall_mat[i - 1][j] & 2) == 2)) {
                            int temp = l_path(i, j);
                            long_path = long_path > temp ? long_path : temp;
                        }
                    }
                } else if (j == 0 || j == wall_mat[i].length - 1) {
                    if (j == 0 &&
                        ((wall_mat[i - 1][j] & 2) == 2 || wall_mat[i][j] !=
                         0)) {
                        int temp = l_path(i, j);
                        long_path = long_path > temp ? long_path : temp;
                    } else if (j == wall_mat[i].length - 1) {
                        if ((wall_mat[i - 1][j] & 2) == 2 ||
                            (wall_mat[i][j - 1] & 1) == 1 ||
                            (wall_mat[i][j] & 2) == 2) {
                            int temp = l_path(i, j);
                            long_path = long_path > temp ? long_path : temp;
                        }
                    }
                } else {
                    int count = wall_mat[i][j] & 1 + (wall_mat[i][j]) >> 1; //
                    //+ (wall_mat[i -1][j]) >>1 + wall_mat[i][j - 1] & 1;
                    if (i > 0)
                        count = count + wall_mat[i - 1][j] >> 1;
                    if (j > 0)
                        count = count + wall_mat[i - 1][j] & 1;
                    if (count == 3) {
                        int temp = l_path(i, j);
                        long_path = long_path > temp ? long_path : temp;
                    }
                }

            }
        }

        return long_path;
    }

    /**
     *  This is the secondary (level) function for longest path in matrix.
     *  x, y are position of cell in matrix from where the longest path
     *  can start.
     * @param wall_mat  wall matrix
     * @param
     * @param y
     * @return
     */
    //    private int l_path(int x, int y) {
    //        if (x < 0 || y < 0 || x >= wall_mat.length || y >= wall_mat[0].length)
    //            return 0;
    //
    //        return l_path(x, y);
    //    }


    /**
     *  Tertiary Function for longest path (:D). this function actually uses
     *  vis_mat and plen_mat supplied by Secondary Level Function to store the
     *  calculated values and to avoid recalculation.
     *  plen_mat is the matrics storing the longest path from that point.
     *  recursively it is checking with neighbouring cells.(without walls)
     * @param wall_mat
     * @param plen_mat
     * @param vis_mat
     * @param x
     * @param y
     * @return
     */
    private int l_path(int x, int y) {
        //        System.out.println(x+","+y);
        if (x >= wall_mat.length || x < 0 || y >= wall_mat[0].length ||
            y < 0 || vis_mat[x][y])
            return 0;

        vis_mat[x][y] = true;
        int l1 = 0, l2 = 0;


        if (y != 0 && (wall_mat[x][y - 1] & 1) == 0 &&
            vis_mat[x][y - 1] == false)
            l1 = l_path(x, y - 1);


        if (x != 0 && (wall_mat[x - 1][y] & 2) == 0 &&
            vis_mat[x - 1][y] == false)
            l2 = l_path(x - 1, y);

        if (l1 < l2)
            l1 = l2;

        if (y != wall_mat[0].length - 1 && (wall_mat[x][y] & 1) == 0 &&
            vis_mat[x][y + 1] == false) {
            l2 = l_path(x, y + 1);
        }

        if (l1 < l2)
            l1 = l2;

        if (x != wall_mat.length - 1 && (wall_mat[x][y] & 2) == 0 &&
            vis_mat[x + 1][y] == false) {
            l2 = l_path(x + 1, y);
        }

        if (l1 < l2)
            l1 = l2;

        vis_mat[x][y] = false;
        return l1 + 1;
    }


    /**
     *  The connect function checks whether two cells x,y and x1, y1 are
     *  connected to each other or not.
     *  This function again calls the secondary connect function to check
     *  the connectivity supplying vis_mat as the extra parameter.
     * @param wall_mat
     * @param x
     * @param y
     * @param x1
     * @param y1
     * @return  1 if connected else 0
     */
    //    private int connect(int[][] wall_mat, int x, int y, int x1, int y1) {
    //        if (x >= wall_mat.length || x < 0 || y >= wall_mat[0].length || y < 0)
    //            return 0;
    //
    //        if (x1 >= wall_mat.length || x1 < 0 || y1 >= wall_mat[0].length ||
    //            y1 < 0)
    //            return 0;
    //
    //        boolean[][] vis_mat = new boolean[wall_mat.length][wall_mat[0].length];
    //        return connect(wall_mat, vis_mat, x, y, x1, y1);
    //    }

    /**
     *  The actual connect function (Secondary) to check the connectivity
     *  between two cells. it checks with neighbouring connected cells
     *  recursively and stores the result in vis_mat
     *  (visited/not_visited matrix).
     * @param wall_mat
     * @param vis_mat
     * @param x
     * @param y
     * @param x1
     * @param y1
     * @return
     */
    private int connect(int x, int y, int x1, int y1) {
        //        System.out.println(x+","+y+","+x1+","+y1);
        if (x == x1 && y == y1)
            return 1;

        if (x >= wall_mat.length || x < 0 || y >= wall_mat[0].length ||
            y < 0 || vis_mat[x][y])
            return 0;

        int res = 0;
        vis_mat[x][y] = true;
        if ((wall_mat[x][y] & 1) == 0) {
            res = connect(x, y + 1, x1, y1);
        }

        if (res == 1) {
            vis_mat[x][y] = false;
            return 1;
        }

        if ((wall_mat[x][y] & 2) == 0) {
            res = connect(x + 1, y, x1, y1);
        }

        if (res == 1) {
            vis_mat[x][y] = false;
            return 1;
        }


        if (y != 0 && (wall_mat[x][y - 1] & 1) == 0)
            res = connect(x, y - 1, x1, y1);


        if (res == 1) {
            vis_mat[x][y] = false;
            return 1;
        }

        if (x != 0 && (wall_mat[x - 1][y] & 2) == 0) {
            res = connect(x - 1, y, x1, y1);
        }

        vis_mat[x][y] = false;
        return res;
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
