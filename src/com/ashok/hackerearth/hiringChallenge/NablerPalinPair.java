package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit ashok1113@gmail.com
 * problem Link: Nabler Palin Pair
 */

public class NablerPalinPair {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        NablerPalinPair a = new NablerPalinPair();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        long res = 0;
        String[] ar = new String[n];
        for (int i = 0; i < n; i++)
            ar[i] = in.read();

        TST tst = new TST(ar);
        for (int i = 0; i < n; i++)
            res += tst.find(ar[i]);

        out.println((res >> 1));
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

        public int find(String s) {
            return find(s, s.length() - 1);
        }

        private int find(String s, int pos) {
            if (pos == -1)
                return 0;

            if (s.charAt(pos) == this.ch) {
                pos--;
                if (pos == -1)
                    return count;
                if (equal == null)
                    return 0;
                return equal.find(s, pos);
            }

            if (s.charAt(pos) > this.ch) {
                if (right == null)
                    return 0;
                return right.find(s, pos);
            }

            if (left == null)
                return 0;

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
