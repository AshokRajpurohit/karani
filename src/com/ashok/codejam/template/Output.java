package com.ashok.codejam.template;



import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Output extends PrintWriter {
    //    private PrintWriter out;

    public Output() {
        super(System.out);
    }

    public Output(OutputStream outputStream) {
        super(outputStream);
    }

    public Output(String file) throws FileNotFoundException {
        super(new FileOutputStream(file));
    }

    public void print(int[] ar) {
        StringBuilder sb = new StringBuilder(ar.length << 1);
        for (int e : ar)
            sb.append(e).append(' ');

        print(sb);
    }

    public void println(int[] ar) {
        print(ar);
        println();
    }

    public void print(int[][] ar) {
        for (int[] e : ar)
            print(e);
    }

    public void print(long[] ar) {
        StringBuilder sb = new StringBuilder(ar.length << 1);
        for (long e : ar)
            sb.append(e).append(' ');

        println(sb);
    }

    public void print(long[][] ar) {
        for (long[] e : ar)
            print(e);
    }

    public void print(String[] ar) {
        for (String e : ar)
            println(e);
    }
}
