package com.ashok.learnings.jcip;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Code snippets for Chapter 6, Java Concurrency in Practice by Brian Goetz et al.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Chapter6 {
    private static final Output out = new Output();
    private static final InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        Chapter6 a = new Chapter6();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            out.println(in.read());
            out.flush();
        }
    }

    final static class PrimeGenerator extends Thread {
        public void cancle() {
            interrupt();
        }
    }
}
