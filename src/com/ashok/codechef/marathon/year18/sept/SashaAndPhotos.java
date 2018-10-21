/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.sept;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Sasha and Photos
 * Link: https://www.codechef.com/SEPT18A/problems/PHOTOCOM
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class SashaAndPhotos {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int LIMIT = 1000000;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int h1 = in.readInt(), w1 = in.readInt();
            Image image1 = new Image(in.read(h1 * w1), h1, w1);
            int h2 = in.readInt(), w2 = in.readInt();
            Image image2 = new Image(in.read(h2 * w2), h2, w2);
            out.println(process(image1, image2));
        }
    }

    private static int gcd(int a, int b) {
        return a == 0 ? b : gcd(b % a, a);
    }

    private static long process(Image a, Image b) {
        if (a.size() > b.size())
            return process(b, a);

        if (a.height == b.height && a.width == b.width)
            return sameSize(a, b);

        int heightGcd = gcd(a.height, b.height), widthGcd = gcd(a.width, b.width);
        long heightMul = 1L * a.height * b.height / heightGcd, widthMul = 1L * a.width * b.width / widthGcd;
        UpscaledImage ua = new UpscaledImage(a, heightMul / a.height, widthMul / a.width);
        UpscaledImage ub = new UpscaledImage(b, heightMul / b.height, widthMul / b.width);

        int zeroCount = count(a, false), size = a.size();
        if (zeroCount == size)
            return count(ub, false);

        if (zeroCount == 0)
            return count(ub, true);

        zeroCount = count(b, false);
        size = b.size();
        if (zeroCount == size)
            return count(ua, false);

        if (zeroCount == 0)
            return count(ua, true);

        long res = 0;
        for (int i = 0; i < a.height; i++) {
            int j = 0;
            while (j < a.width) {
                boolean value = a.pixels[i][j];
                int k = j;
                while (k < a.width && a.pixels[i][k] == value) j++;
                SubMatrix upscaledMatrix = ua.getUpscaledSubMatrix(i, j, k - 1);
                SubMatrix subMatrixB = ub.getOriginalSubMatrix(upscaledMatrix);
                for (int r = (int) subMatrixB.fr; r <= subMatrixB.tr; r++)
                    for (int c = (int) subMatrixB.fc; c <= subMatrixB.tc; c++) {
                        if (b.pixels[r][c] != a.pixels[i][j])
                            continue;

                        res += ub.getUpscaledSubMatrix(r, c).overlap(upscaledMatrix).size;
                    }

                j = k;
            }
        }

        return res;
    }

    private static boolean allSame(Image a, boolean value) {
        for (boolean[] row : a.pixels)
            for (boolean v : row)
                if (v != value) return false;

        return true;
    }

    private static long count(UpscaledImage ui, boolean value) {
        return ui.upscaleFactor * count(ui.image, value);
    }

    private static int count(Image a, boolean value) {
        int res = 0;
        for (boolean[] row : a.pixels)
            res += count(row, value);

        return res;
    }

    private static int count(boolean[] ar, boolean value) {
        int res = 0;
        for (boolean v : ar) if (v == value) res++;

        return res;
    }

    private static int sameSize(Image a, Image b) {
        int res = 0;
        for (int i = 0; i < a.height; i++)
            for (int j = 0; j < a.width; j++)
                if (a.pixels[i][j] == b.pixels[i][j])
                    res++;

        return res;
    }

    final static class UpscaledImage {
        final Image image;
        final long heightFactor, widthFactor, upscaleFactor, size;

        private UpscaledImage(Image image, long h, long w) {
            this.image = image;
            heightFactor = h;
            widthFactor = w;
            upscaleFactor = heightFactor * widthFactor;
            size = upscaleFactor * image.size();
        }

        SubMatrix getUpscaledSubMatrix(int r, int c) {
            long fr = heightFactor * r, fc = widthFactor * c;
            long tr = fr + heightFactor - 1, tc = fc + widthFactor - 1;
            return new SubMatrix(fr, fc, tr, tc);
        }

        SubMatrix getUpscaledSubMatrix(int r, int fc, int tc) {
            long fr = heightFactor * r, frc = widthFactor * fc;
            long tr = fr + heightFactor - 1, toc = widthFactor * tc + widthFactor - 1;
            return new SubMatrix(fr, frc, tr, toc);
        }

        Point originalPoint(long r, long c) {
            return new Point(originalRow(r), originalCol(c));
        }

        int originalRow(long upscaledRow) {
            return (int) (upscaledRow / heightFactor);
        }

        int originalCol(long upscaledCol) {
            return (int) (upscaledCol / widthFactor);
        }

        SubMatrix getOriginalSubMatrix(SubMatrix upscaled) {
            return new SubMatrix(originalPoint(upscaled.fr, upscaled.fc), originalPoint(upscaled.tr, upscaled.tc));
        }
    }

    final static class Point {
        final int row, col;

        Point(int r, int c) {
            row = r;
            col = c;
        }
    }

    final static class SubMatrix {
        final long fr, tr, fc, tc; // from-to row and from-to column;
        final long size;

        SubMatrix(long fr, long fc, long tr, long tc) {
            this.fr = fr;
            this.fc = fc;
            this.tr = tr;
            this.tc = tc;
            size = (tc + 1 - fc) * (tr + 1 - fr);
        }

        SubMatrix(Point from, Point to) {
            this(from.row, from.col, to.row, to.col);
        }

        SubMatrix overlap(SubMatrix m) {
            return new SubMatrix(Math.max(fr, m.fr), Math.max(fc, m.fc), Math.min(tr, m.tr), Math.min(tc, m.tc));
        }
    }

    final static class Image {
        final int height, width;
        final boolean[][] pixels;

        private Image(String s, int h, int w) {
            height = h;
            width = w;
            pixels = new boolean[h][w];

            int index = 0;
            for (int i = 0; i < h; i++)
                for (int j = 0; j < w; j++)
                    pixels[i][j] = s.charAt(index++) == '1';
        }

        private int size() {
            return height * width;
        }
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

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