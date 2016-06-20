package com.ashok.codechef.marathon.year15.MARCH15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problem Link: http://www.codechef.com/MARCH15/problems/DEVCLASS
 */
public class E {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        E a = new E();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        StringBuilder sb = new StringBuilder();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int type = in.readInt();
            String s = in.read();
            if (s.length() < 2) {
                sb.append("0\n");
            } else {
                sb.append(solve(s, type)).append('\n');
            }
        }
        out.print(sb);
    }

    private long solve(String s, int type) {

        long cost = 0;
        int bc = 0, gc = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'B')
                bc++;
            else
                gc++;
        }

        if (bc > gc + 1 || gc > bc + 1)
            return -1;

        char[] ar;

        if (type == 0) {
            if (bc > gc) {
                for (int i = 0; i < s.length(); i = i + 2) {
                    if (s.charAt(i) == 'G')
                        cost++;
                }
                return cost;
            } else if (gc > bc) {
                for (int i = 0; i < s.length(); i = i + 2) {
                    if (s.charAt(i) == 'B')
                        cost++;
                }
                return cost;
            }

            for (int i = 0; i < s.length(); i = i + 2) {
                if (s.charAt(i) == 'B')
                    cost++;
            }

            long c1 = cost;
            cost = 0;

            for (int i = 0; i < s.length(); i = i + 2) {
                if (s.charAt(i) == 'G')
                    cost++;
            }

            return c1 > cost ? cost : c1;

        } else {

            if (bc > gc) {
                ar = s.toCharArray();
                cost = 0;
                //                sb.delete(0, s.length());
                //                sb.append(s);
                int b_ind = 0, g_ind = 1, bc_ind = 0, gc_ind = 0;

                for (; b_ind < s.length() && g_ind < s.length();
                     b_ind = b_ind + 2, g_ind = g_ind + 2) {
                    if (ar[b_ind] == 'G') {
                        bc_ind = bc_ind > b_ind ? bc_ind : b_ind + 1;
                        //                        bc_ind = b_ind + 1;
                        while (ar[bc_ind] == 'G') {
                            bc_ind++;
                        }
                        ar[b_ind] = 'B';
                        ar[bc_ind] = 'G';
                        //                        sb.setCharAt(b_ind, 'B');
                        //                        sb.setCharAt(c_ind, 'G');
                        cost = cost + bc_ind - b_ind;
                    }
                    if (ar[g_ind] == 'B') {
                        gc_ind = gc_ind > g_ind ? gc_ind : g_ind + 1;
                        //                        gc_ind = g_ind + 1;
                        while (ar[gc_ind] == 'B') {
                            gc_ind++;
                        }
                        ar[g_ind] = 'G';
                        ar[gc_ind] = 'B';
                        //                        sb.setCharAt(g_ind, 'G');
                        //                        sb.setCharAt(c_ind, 'B');
                        cost = cost + gc_ind - g_ind;
                    }
                }
                return cost;
            } else if (gc > bc) {
                ar = s.toCharArray();
                cost = 0;
                //                sb.delete(0, s.length());
                //                sb.append(s);
                int b_ind = 1, g_ind = 0, gc_ind = 0, bc_ind = 0;

                for (; b_ind < s.length() && g_ind < s.length();
                     b_ind = b_ind + 2, g_ind = g_ind + 2) {
                    if (ar[g_ind] == 'B') {
                        //                        cost++;
                        gc_ind = gc_ind > g_ind ? gc_ind : g_ind + 1;
                        //                        gc_ind = g_ind + 1;
                        while (ar[gc_ind] == 'B') {
                            gc_ind++;
                            //                            cost++;
                        }
                        ar[g_ind] = 'G';
                        ar[gc_ind] = 'B';
                        //                        sb.setCharAt(g_ind, 'G');
                        //                        sb.setCharAt(c_ind, 'B');
                        cost = cost + gc_ind - g_ind;
                    }
                    if (ar[b_ind] == 'G') {
                        //                        cost++;
                        bc_ind = bc_ind > b_ind ? bc_ind : b_ind + 1;
                        //                        bc_ind = b_ind + 1;
                        while (ar[bc_ind] == 'G') {
                            bc_ind++;
                            //                            cost++;
                        }
                        ar[b_ind] = 'B';
                        ar[bc_ind] = 'G';
                        //                        sb.setCharAt(b_ind, 'B');
                        //                        sb.setCharAt(c_ind, 'G');
                        cost = cost + bc_ind - b_ind;
                    }
                }
                return cost;
            } else {
                ar = s.toCharArray();
                cost = 0;
                //                sb.delete(0, s.length());
                //                sb.append(s);
                int b_ind = 1, g_ind = 0, bc_ind = 0, gc_ind = 0;

                for (; b_ind < s.length() && g_ind < s.length();
                     b_ind = b_ind + 2, g_ind = g_ind + 2) {
                    if (ar[g_ind] == 'B') {

                        gc_ind = gc_ind > g_ind ? gc_ind : g_ind + 1;

                        while (ar[gc_ind] == 'B') {
                            gc_ind++;
                        }

                        ar[g_ind] = 'G';
                        ar[gc_ind] = 'B';
                        cost = cost + gc_ind - g_ind;
                    }

                    if (ar[b_ind] == 'G') {
                        bc_ind = bc_ind > b_ind ? bc_ind : b_ind + 1;

                        while (ar[bc_ind] == 'G') {
                            bc_ind++;
                        }

                        ar[b_ind] = 'B';
                        ar[bc_ind] = 'G';
                        cost = cost + bc_ind - b_ind;
                    }
                }
                long c1 = cost;

                b_ind = 0;
                g_ind = 1;
                bc_ind = 0;
                gc_ind = 0;
                ar = s.toCharArray();
                //                sb.delete(0, s.length());
                //                sb.append(s);
                cost = 0;

                for (; b_ind < s.length() && g_ind < s.length();
                     b_ind = b_ind + 2, g_ind = g_ind + 2) {
                    if (ar[b_ind] == 'G') {

                        bc_ind = bc_ind > b_ind ? bc_ind : b_ind + 1;

                        while (ar[bc_ind] == 'G') {
                            bc_ind++;
                        }

                        ar[b_ind] = 'B';
                        ar[bc_ind] = 'G';
                        cost = cost + bc_ind - b_ind;
                    }
                    if (ar[g_ind] == 'B') {
                        gc_ind = gc_ind > g_ind ? gc_ind : g_ind + 1;

                        while (ar[gc_ind] == 'B') {
                            gc_ind++;
                        }

                        ar[g_ind] = 'G';
                        ar[gc_ind] = 'B';
                        cost = cost + gc_ind - g_ind;
                    }
                }

                //                cost = c1 > cost ? cost : c1;
                return c1 > cost ? cost : c1;
            }
        }
    }

    //    private long pow(long cost, int type) {
    //        return cost;
    ////        if (type == 1 || cost == 1)
    ////            return cost;
    ////
    ////        return cost * cost;
    //    }

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
