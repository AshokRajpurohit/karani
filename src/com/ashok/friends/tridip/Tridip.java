/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.tridip;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.concurrent.Semaphore;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Tridip {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private static Semaphore s1 = new Semaphore(0), s2 = new Semaphore(0);

    public static void main(String[] args) throws IOException {
        Tridip a = new Tridip();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        Thread t1 = new Thread(Tridip::method1), t2 = new Thread(Tridip::method2);
        t1.start();
        t2.start();
        while (true) {
            out.println(in.read());
            out.flush();
        }
    }

    private static void method1() {
        try {
            System.out.println("Thread 1 entry");
            s1.wait();
            s2.notify();
//            s1.acquire();
//            s2.release();
            System.out.println("Exiting method 1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void method2() {
        try {
            System.out.println("Thread 2 entry");
            s2.wait();
            s1.notify();
//            s2.acquire();
//            s1.release();
            System.out.println("Exiting method 2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
