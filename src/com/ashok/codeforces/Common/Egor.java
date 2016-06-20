package com.ashok.codeforces.Common;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.InputMismatchException;
//package Common;

public class Egor {
    private static InputReader in;
    private static PrintWriter out;

    public Egor() {
        super();
    }

    public static void main(String[] args) {
        Egor a = new Egor();

        FileInputStream inf;
        //        FileOutputStream outf;
        try {
            inf = new FileInputStream("input.txt");
            //            outf = new FileOutputStream("output.txt");
            InputStream inputStream = inf;
            OutputStream outputStream = System.out;
            in = new InputReader(inputStream);
            out = new PrintWriter(outputStream);
            a.solve();
            inf.close();
            //            outf.close();
            out.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

    }

    public void solve() {
        int count = 0;
        int a = -1;
        long t1 = System.currentTimeMillis();
        try {
            while (true) {
                a = in.readInt();
                count++;
            }
        } catch (Exception e) {
            // TODO: Add catch code
            //            e.printStackTrace();
        }
        long t2 = System.currentTimeMillis();
        out.println(count);
        out.println(t2 - t1);
    }


    static class InputReader {

        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (numChars == -1)
                throw new InputMismatchException();
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            StringBuilder res = new StringBuilder();
            do {
                if (Character.isValidCodePoint(c))
                    res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            if (filter != null)
                return filter.isSpaceChar(c);
            return isWhitespace(c);
        }

        public static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
        }
    }
}
