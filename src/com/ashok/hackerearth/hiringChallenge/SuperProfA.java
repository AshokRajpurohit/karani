package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit ashok1113@gmail.com
 * problem Link:
 */

public class SuperProfA {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        SuperProfA a = new SuperProfA();
        a.solve();
        out.close();
    }

    private static String rev(String s) {
        char[] ar = new char[s.length()];
        for (int i = 0; i < s.length(); i++)
            ar[s.length() - i - 1] = s.charAt(i);

        return String.valueOf(ar);
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        String[] ar = new String[n];
        for (int i = 0; i < n; i++)
            ar[i] = in.read();

        TST tst = new TST(ar);
        long res = 0;
        for (int i = 0; i < n; i++) {
            tst.remove(ar[i]);
            res += tst.getCount(rev(ar[i]));
        }

        out.println(res);
    }

    final static class TST {
        TST left, right, equal;
        boolean end = false;
        Character ch;
        int count = 0;

        public TST(String s) {
            this(s, 0);
        }

        /**
         * initializes the instance with first string and then add the remaining
         * strings.
         *
         * @param ar
         */

        public TST(String[] ar) {
            this(ar[0], 0);
            for (int i = 1; i < ar.length; i++)
                this.add(ar[i], 0);
        }

        private TST(String s, int pos) {
            if (pos == s.length())
                return;

            if (ch == null) {
                ch = s.charAt(pos);
                if (pos == s.length() - 1) {
                    end = true;
                    count++;
                } else
                    equal = new TST(s, pos + 1);
            } else {
                if (ch < s.charAt(pos)) {
                    if (right == null)
                        right = new TST(s, pos);
                    else
                        right.add(s, pos);
                } else if (ch == s.charAt(pos)) {
                    if (pos == s.length() - 1) {
                        end = true;
                        count++;
                    } else if (equal == null) {
                        equal = new TST(s, pos + 1);
                    } else {
                        equal.add(s, pos + 1);
                    }
                } else {
                    if (left == null)
                        left = new TST(s, pos);
                    else
                        left.add(s, pos);
                }
            }
        }

        public int getCount(String s) {
            return getCount(s, 0);
        }

        public int getCount(String s, int pos) {
            //            if (pos == s.length())
            //                return this.count;

            if (s.charAt(pos) == this.ch) {
                pos++;
                if (pos == s.length())
                    return this.count;
                if (equal == null)
                    return 0;
                return equal.getCount(s, pos);
            }

            if (s.charAt(pos) > this.ch) {
                if (right == null)
                    return 0;
                return right.getCount(s, pos);
            }

            if (left == null)
                return 0;

            return left.getCount(s, pos);
        }

        public void remove(String s) {
            remove(s, 0);
        }

        private void remove(String s, int pos) {
            if (pos == s.length())
                return;

            if (s.charAt(pos) == this.ch) {
                pos++;
                if (pos == s.length()) {
                    this.count--;
                    return;
                }
                if (equal == null)
                    return;

                equal.remove(s, pos);
                return;
            }

            if (s.charAt(pos) > this.ch) {
                if (right == null)
                    return;

                right.remove(s, pos);
                return;
            }

            if (left == null)
                return;

            left.remove(s, pos);
        }

        public void add(String s) {
            this.add(s, 0);
        }

        public void add(String[] ar) {
            for (int i = 0; i < ar.length; i++)
                add(ar[i], 0);
        }

        private void add(String s, int pos) {
            if (s.charAt(pos) == this.ch) {
                if (pos == s.length() - 1) {
                    this.end = true;
                    count++;
                    return;
                } else {
                    if (equal == null)
                        equal = new TST(s, pos + 1);
                    else
                        equal.add(s, pos + 1);
                    return;
                }
            } else if (s.charAt(pos) < this.ch) {
                if (left == null) {
                    left = new TST(s, pos);
                    return;
                } else
                    left.add(s, pos);
            } else {
                if (right == null) {
                    right = new TST(s, pos);
                    return;
                } else {
                    right.add(s, pos);
                    return;
                }
            }
            return;
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
