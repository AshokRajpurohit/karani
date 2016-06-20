package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 * problem Link: Idea Device | Drag Racing
 */

public class IdeaDeviceDragRace {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        IdeaDeviceDragRace a = new IdeaDeviceDragRace();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int count = (1 << n) - 1;
        String[] win = new String[count];
        String[] lose = new String[count];
        for (int i = 0; i < count; i++) {
            win[i] = in.read();
            lose[i] = in.read();
        }

        TST tst = new TST(lose);
        for (int i = 0; i < count; i++) {
            if (!tst.find(win[i])) {
                out.println(win[i]);
                return;
            }
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

    class TST {
        private TST left, right, equal;
        private boolean end = false;
        private Character ch;
        private int count = 0;

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

        /**
         * initializes the instance.
         *
         * @param s   String s you want to initialize the instance.
         * @param pos start pos in string s to be inserted in instance.
         */
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

        public boolean find(String s) {
            return find(s, 0);
        }

        private boolean find(String s, int pos) {
            if (pos == s.length())
                return true;

            if (s.charAt(pos) == this.ch) {
                pos++;
                if (pos == s.length())
                    return true;
                if (equal == null)
                    return false;
                return equal.find(s, pos);
            }

            if (s.charAt(pos) > this.ch) {
                if (right == null)
                    return false;
                return right.find(s, pos);
            }

            if (left == null)
                return false;

            return left.find(s, pos);
        }

        /**
         * adds the given string to the TST structure.
         *
         * @param s
         */

        public void add(String s) {
            this.add(s, 0);
        }

        /**
         * to add new string into existing TST instance.
         *
         * @param s   String s to be added to instance.
         * @param pos start char pos in String s.
         */
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
}
