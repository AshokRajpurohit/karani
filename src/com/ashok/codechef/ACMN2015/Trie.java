package com.ashok.codechef.ACMN2015;
import java.io.BufferedReader;
//import static java.lang.System.in;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.StringTokenizer;

public class Trie {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        Trie a = new Trie();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        StringBuilder sb = new StringBuilder();
        int t = in.readInt();
        StringBuilder sbuf = new StringBuilder();

        while (t > 0) {
            t--;
            int n = in.readInt();
            String[] ar = new String[n];

            if (n > 0) {
                for (int i = 0; i < n; i++) {
                    ar[i] = in.read();
                }

                Node trie = new Node(ar[0], 0);
                for (int i = 1; i < n; i++)
                    trie.add(ar[i], 0);

                trie.print(sb, sbuf);
            }
        }
        out.print(sb);
    }

    private void sort(String[] ar) {
        String[] br = new String[ar.length - 1];
        int maxl = 0;
        for (int i = 0; i < ar.length; i++)
            maxl = maxl > ar[i].length() ? maxl : ar[i].length();

        int sort_pos = 0;
    }

    class Node {
        Node left, right, equal;
        boolean end = false;
        Character ch;
        int count = 0;

        public Node(String s, int pos) {
            if (ch == null) {
                ch = s.charAt(pos);
                if (pos == s.length() - 1) {
                    end = true;
                    count++;
                } else
                    equal = new Node(s, pos + 1);
            } else {
                if (ch < s.charAt(pos)) {
                    if (right == null)
                        right = new Node(s, pos);
                    else
                        right.add(s, pos);
                } else if (ch == s.charAt(pos)) {
                    if (pos == s.length() - 1) {
                        end = true;
                        count++;
                    } else if (equal == null) {
                        equal = new Node(s, pos + 1);
                    } else {
                        equal.add(s, pos + 1);
                    }
                } else {
                    if (left == null)
                        left = new Node(s, pos);
                    else
                        left.add(s, pos);
                }
            }
        }

        private void add(String s, int pos) {
            if (s.charAt(pos) == this.ch) {
                if (pos == s.length() - 1) {
                    this.end = true;
                    count++;
                    return;
                } else {
                    equal.add(s, pos + 1);
                    return;
                }
            } else if (s.charAt(pos) < this.ch) {
                if (left == null) {
                    left = new Node(s, pos);
                    return;
                } else
                    left.add(s, pos);
            } else {
                if (right == null) {
                    right = new Node(s, pos);
                    return;
                } else {
                    right.add(s, pos);
                    return;
                }
            }
            return;
        }

        public void print(StringBuilder sb, StringBuilder sbuf) {
            int clen = sbuf.length();
            if (left != null) {
                left.print(sb, sbuf);
                sbuf.delete(clen, sbuf.length());
            }
            sbuf.append(ch);
            while (this.count > 0) {
                this.count--;
                sb.append(sbuf).append('\n');
            }

            if (equal != null)
                equal.print(sb, sbuf);

            sbuf.deleteCharAt(clen);

            if (right != null) {
                right.print(sb, sbuf);
            }

            left = null;
            right = null;
            equal = null;
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
