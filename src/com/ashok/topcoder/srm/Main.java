package com.ashok.topcoder.srm;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link:
 */

public class Main {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        Main a = new Main();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        EllysTimeMachine e = new EllysTimeMachine();
        while (true) {
            out.println(e.getTime(in.read()));
            out.flush();
        }
    }

    class EllysTimeMachine {
        public String getTime(String time) {
            int h = hour(time), m = minute(time);

            return minuteToHour(m) + ":" + hourToMinute(h);
        }

        String hourToMinute(int hour) {
            int minute = hour * 5 % 60;

            if (minute >= 10)
                return "" + minute;

            return "0" + minute;
        }

        String minuteToHour(int minute) {
            int hour = minute / 5;
            if (hour == 0)
                return "12";

            if (hour >= 10)
                return "" + hour;

            return "0" + hour;
        }

        int hour(String time) {
            return time.charAt(0) * 10 + time.charAt(1) - 11 * '0';
        }

        int minute(String time) {
            return time.charAt(3) * 10 + time.charAt(4) - 11 * '0';
        }
    }

    class MutaliskEasy {
        private int[][] bomb =
        { { 1, 3, 9 }, { 1, 9, 3 }, { 3, 9, 1 }, { 3, 1, 9 }, { 9, 1, 3 },
          { 9, 3, 1 } };

        private int[][] bomb2 =
        { { 1, 3 }, { 1, 9 }, { 3, 1 }, { 3, 9 }, { 9, 1 }, { 9, 3 } };

        public int minimalAttacks(int[] x) {
            if (x.length == 1) {
                boolean[] vis = new boolean[61];
                int[] bak = new int[61];
                return solve(x[0], vis, bak);
            } else if (x.length == 2) {
                boolean[][] vis = new boolean[61][61];
                int[][] bak = new int[61][61];
                return solve(x, vis, bak);
            } else {
                boolean[][][] vis = new boolean[61][61][61];
                int[][][] bak = new int[61][61][61];
                return solve(x, vis, bak);
            }
        }

        private int solve(int x, boolean[] vis, int[] bak) {
            if (vis[x])
                return bak[x];

            vis[x] = true;
            int max = 60;
            if (x >= 9)
                max = Math.min(max, solve(x - 9, vis, bak));
            bak[x] = max + 1;
            return bak[x];
        }

        private int solve(int[] x, boolean[][] vis, int[][] bak) {
            if ((x[0] == 0 && x[1] == 0) || vis[x[0]][x[1]])
                return bak[x[0]][x[1]];
            int max = 60;
            vis[x[0]][x[1]] = true;
            for (int i = 0; i < 6; i++) {
                int[] temp = x.clone();
                sub(temp, bomb2[i]);
                max = Math.min(max, solve(temp, vis, bak));
            }
            bak[x[0]][x[1]] = max + 1;
            return bak[x[0]][x[1]];
        }

        private int solve(int[] x, boolean[][][] vis, int[][][] bak) {
            if ((x[0] == 0 && x[1] == 0 && x[2] == 0) || vis[x[0]][x[1]][x[2]])
                return bak[x[0]][x[1]][x[2]];

            int max = 60;
            vis[x[0]][x[1]][x[2]] = true;
            for (int i = 0; i < 6; i++) {
                int[] temp = x.clone();
                sub(temp, bomb[i]);
                max = Math.min(max, solve(temp, vis, bak));
            }
            bak[x[0]][x[1]][x[2]] = max + 1;
            return bak[x[0]][x[1]][x[2]];
        }

        private void sub(int[] x, int[] a) {
            for (int i = 0; i < x.length; i++) {
                x[i] = x[i] >= a[i] ? x[i] - a[i] : 0;
            }
        }
    }

    class InfiniteString {
        public String equal(String s, String t) {
            if (s.length() < t.length())
                return solve(s, t);
            return solve(t, s);
        }

        private String solve(String s, String t) {
            if (t.indexOf(s) == -1)
                return "Not equal";
            if (t.equals(s))
                return "Equal";
            return equal(s, t.substring(s.length()));
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
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
