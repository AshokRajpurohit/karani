package com.ashok.codejam.B12016;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem:
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class B {

    private static PrintWriter out;
    private static InputStream in;
    private static String format = "Case #";

    public static void main(String[] args) throws IOException {
        //        OutputStream outputStream = System.out;
        //        in = System.in;
        //        out = new PrintWriter(outputStream);

        String path = "D:\\GitHub\\Competetions\\CodeJam\\Code\\src\\B12016\\";
        String input = "bsmall.in", output = "bsmall.out";
        FileInputStream fip = new FileInputStream(path + input);
        FileOutputStream fop = new FileOutputStream(path + output);
        in = fip;
        out = new PrintWriter(fop);
        B a = new B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 4);

        for (int i = 1; i <= t; i++) {
            sb.append(format).append(i).append(": ");
            sb.append(process(in.read(), in.read())).append('\n');
        }

        out.print(sb);
    }

    private static String process(String a, String b) {
        long x = 0, y = 0;
        int comp = 0;
        StringBuilder c = new StringBuilder(), d = new StringBuilder();

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == '?' && b.charAt(i) == '?') {
                if (comp == 0) {
                    int diff = diff(a, b, i + 1);

                    if (diff > 5) {
                        c.append('0');
                        d.append('1');
                        x = x * 10;
                        y = y * 10 + 1;
                    } else if (diff < -5) {
                        c.append('1');
                        d.append('0');
                        x = x * 10 + 1;
                        y = y * 10;
                    } else {
                        x = x * 10;
                        y = y * 10;
                        c.append('0');
                        d.append('0');
                    }
                } else if (comp == 1) {
                    x *= 10;
                    y = y * 10 + 9;

                    c.append('0');
                    d.append('9');
                } else {
                    x = x * 10 + 9;
                    y = y * 10;

                    c.append('9');
                    d.append('0');
                }
            } else if (a.charAt(i) == '?') {
                int v = b.charAt(i) - '0';
                y = y * 10 + v;
                d.append(v);

                if (comp == 0) {
                    //                    x = x * 10 + v;
                    //                    c.append(v);

                    int diff = diff(a, b, i + 1);

                    if (diff > 4 && v != 0) {
                        c.append(v - 1);
                        //                        d.append(v);
                        x = x * 10 + v - 1;
                        //                        y = y * 10 + v;
                    } else if (diff < -4 && v != 9) {
                        c.append(v + 1);
                        //                        d.append(v);
                        x = x * 10 + v + 1;
                        //                        y = y * 10 + v;
                    } else {
                        x = x * 10 + v;
                        //                        y = y * 10 + v;
                        c.append(v);
                        //                        d.append(v);
                    }
                } else if (comp == 1) {
                    x = x * 10;
                    c.append('0');
                } else {
                    x = x * 10 + 9;
                    c.append('9');
                }
            } else if (b.charAt(i) == '?') {
                int v = a.charAt(i) - '0';
                x = x * 10 + v;
                c.append(v);

                if (comp == 0) {
                    //                    y = y * 10 + v;
                    //                    d.append(v);

                    //                    x = x * 10 + v;
                    //                    c.append(v);

                    int diff = diff(a, b, i + 1);

                    if (diff > 4 && v != 9) {
                        //                        c.append(v);
                        d.append(v + 1);
                        //                        x = x * 10 + v 1;
                        y = y * 10 + v + 1;
                    } else if (diff < -4 && v != 0) {
                        d.append(v - 1);
                        //                        d.append(v);
                        //                        x = x * 10 + v + 1;
                        y = y * 10 + v - 1;
                    } else {
                        //                        x = x * 10 + v;
                        y = y * 10 + v;
                        //                        c.append(v);
                        d.append(v);
                    }
                } else if (comp == 1) {
                    y = y * 10 + 9;
                    d.append(9);
                } else {
                    y = y * 10;
                    d.append(0);
                }
            } else {
                x = x * 10 + a.charAt(i) - '0';
                y = y * 10 + b.charAt(i) - '0';

                c.append(a.charAt(i));
                d.append(b.charAt(i));
            }

            comp = compare(x, y);
        }

        c.append(' ').append(d);
        return c.toString();
    }

    private static int diff(String a, String b, int n) {
        if (n == a.length())
            return 0;

        return diff(a.charAt(n), b.charAt(n));
    }

    private static int diff(char a, char b) {
        if (a == '?' || b == '?')
            return 0;

        return a - b;
    }

    private static int compare(long a, long b) {
        if (a == b)
            return 0;

        if (a > b)
            return 1;

        return -1;
    }

    private static String process(String s) {
        int[] ar = new int[10];

        char[] car = s.toCharArray();
        int[] map = new int[256];

        for (char c : car)
            map[c]++;

        for (int i = 0; i < car.length; i++) {
            char c = car[i];
            if (c == 'Z') {
                ar[0]++;
            } else if (c == 'W') {
                ar[2]++;
            } else if (c == 'U') {
                ar[4]++;
            } else if (c == 'X') {
                ar[6]++;
            } else if (c == 'G') {
                ar[8]++;
            }
        }

        ar[0] = map['Z'];
        ar[2] = map['W'];
        ar[4] = map['U'];
        ar[6] = map['X'];
        ar[8] = map['G'];


        ar[7] = map['S'] - ar[6];
        ar[5] = map['V'] - ar[7];
        ar[3] = map['H'] - ar[8];
        ar[9] = map['I'] - ar[5] - ar[6] - ar[8];
        ar[1] = map['O'] - ar[0] - ar[2] - ar[4];

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            while (ar[i] > 0) {
                ar[i]--;
                sb.append(i);
            }
        }

        return sb.toString();
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

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = readLong();

            return ar;
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
