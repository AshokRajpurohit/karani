package com.ashok.lang.inputs;

import java.io.*;

/**
 * The {@code Output} class is to implement methods for various data types.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Output extends PrintWriter {
    public char seperator = ' ';

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

    public void finalize() {
        flush();
        close();
    }

    public void print(Iterable iterable) {
        StringBuilder sb = new StringBuilder();

        for (Object object : iterable)
            sb.append(object.toString()).append(seperator);

        print(sb);
    }

    public void println(Iterable iterable) {
        print(iterable);
        println();
    }

    public void print(Object[] objects) {
        StringBuilder sb = new StringBuilder();

        for (Object object : objects) {
            sb.append(object.toString()).append(seperator);
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
            sb.append(e).append(seperator);

        println(sb.toString());
    }

    public void print(int[][] ar) {
        for (int[] e : ar)
            print(e);
    }

    public void print(long[] ar) {
        StringBuilder sb = new StringBuilder(ar.length << 1);
        for (long e : ar)
            sb.append(e).append(seperator);

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
