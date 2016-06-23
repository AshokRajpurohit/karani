package com.ashok.lang.inputs;

import java.io.*;

public class Output extends PrintWriter {
    public Output() {
        super(System.out);
    }

    public Output(OutputStream outputStream) {
        super(outputStream);
    }

    public Output(String file) throws FileNotFoundException {
        super(new FileOutputStream(file));
    }

    public Output(String file, boolean append) throws FileNotFoundException {
        super(new FileOutputStream(file, append));
    }

    public Output(File file, boolean append) throws FileNotFoundException {
        super(new FileOutputStream(file, append));
    }

    public Output(File file) throws FileNotFoundException {
        super(new FileOutputStream(file));
    }

    public void print(Object[] objects) {
        StringBuilder sb = new StringBuilder();

        for (Object object : objects) {
            sb.append(object.toString()).append(' ');
        }

        print(sb.toString());
    }

    public void println(Object[] objects) {
        print(objects);
        println();
    }

    public void print(int[] ar) {
        StringBuilder sb = new StringBuilder(ar.length << 1);
        for (int e : ar)
            sb.append(e).append(' ');

        println(sb.toString());
    }

    public void print(int[][] ar) {
        for (int[] e : ar)
            print(e);
    }

    public void print(long[] ar) {
        StringBuilder sb = new StringBuilder(ar.length << 1);
        for (long e : ar)
            sb.append(e).append(' ');

        println(sb.toString());
    }

    public void print(long[][] ar) {
        for (long[] e : ar)
            print(e);
    }

    public void print(String[] ar) {
        int size = 0;
        for (String e : ar)
            size += e.length() + 1;

        StringBuilder sb = new StringBuilder(size);

        for (String e : ar)
            sb.append(e).append('\n');

        print(sb.toString());
    }
}
