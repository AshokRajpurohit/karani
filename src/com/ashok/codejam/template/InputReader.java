package com.ashok.codejam.template;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class InputReader {
    private InputStream in;
    byte[] buffer = new byte[8192];
    int offset = 0;
    int bufferSize = 0;

    public InputReader(InputStream input) {
        in = input;
    }

    public InputReader() {
        in = System.in;
    }

    public InputReader(String file) throws FileNotFoundException {
        in = new FileInputStream(file);
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

    public int[] readIntArray(int n) throws IOException {
        int[] ar = new int[n];
        for (int i = 0; i < n; i++)
            ar[i] = readInt();

        return ar;
    }

    public int[] readIntArray(int n, int d) throws IOException {
        int[] ar = new int[n];
        for (int i = 0; i < n; i++)
            ar[i] = readInt() + d;

        return ar;
    }

    public int[][] readIntTable(int m, int n) throws IOException {
        int[][] res = new int[m][];
        for (int i = 0; i < m; i++)
            res[i] = readIntArray(n);

        return res;
    }

    public int[][] readIntTable(int m, int n, int d) throws IOException {
        int[][] res = new int[m][];
        for (int i = 0; i < m; i++)
            res[i] = readIntArray(n, d);

        return res;
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

    public long[] readLongArray(int n, long d) throws IOException {
        long[] ar = new long[n];
        for (int i = 0; i < n; i++)
            ar[i] = readLong() + d;

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

    public String readLine() throws IOException {
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
            if (buffer[offset] == '\n' || buffer[offset] == '\r')
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

    public String[] readStringArray(int size) throws IOException {
        String[] res = new String[size];
        for (int i = 0; i < size; i++)
            res[i] = read();

        return res;
    }

    public String[] readStringArray(int size, int len) throws IOException {
        String[] res = new String[size];
        for (int i = 0; i < size; i++)
            res[i] = read(len);

        return res;
    }

    public double readDouble() throws IOException {
        return Double.parseDouble(read());
    }

    public double[] readDoubleArray(int n) throws IOException {
        double[] ar = new double[n];
        for (int i = 0; i < n; i++)
            ar[i] = readDouble();

        return ar;
    }
}
