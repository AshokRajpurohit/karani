/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.ashok.lang.concurrency;

import com.ashok.lang.annotation.ThreadSafe;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Timer;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Problem Name: Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class TestConcurrency {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private static InputUtils inputUtils = new InputUtils();
    private static OutputUtils outputUtils = new OutputUtils();
    private static final CountDownLatch endGate = new CountDownLatch(2);

    private static Thread inputThread = new Thread(new Runnable() {
        public void run() {
            try {
                inputLoader();
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                endGate.countDown();
            }
        }
    });

    private static Thread outputThread = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                outputWriter();
            } finally {
                out.println("output-thread is complete");
                out.flush();
                endGate.countDown();
            }
        }
    });

    public static void main(String[] args) throws IOException,
            InterruptedException {
        TestConcurrency a = new TestConcurrency();
        long time = System.currentTimeMillis();
        inputThread.setName("input-thread");
        outputThread.setName("output-thread");
        inputThread.start();
        outputThread.start();
        a.solve();
        endGate.await();

        out.println(System.currentTimeMillis() - time);

        out.close();
    }

    private void solve() throws InterruptedException {
        Timer timer = new Timer();
        while (true) {
            int n = inputUtils.nextInt();
            if (n == -1)
                outputUtils.outputComplete = true;

            outputUtils.println(n);
        }
    }

    @ThreadSafe
    final static class FutureRenderer {
        private static final int NTHREADS = 100;
        private final ExecutorService executor = Executors.newScheduledThreadPool(NTHREADS);

        void renderPage(CharSequence source) {
        }
    }

    final static class LifeCycleWebServer {
        private static final int NTHREADS = 100;
        private final ExecutorService exec = Executors.newScheduledThreadPool(NTHREADS);

        public void start() throws IOException {
            ServerSocket socket = new ServerSocket(80);
            while (!exec.isShutdown()) {
                try {
                    final Socket connection = socket.accept();
                    exec.execute(new Runnable() {
                        @Override
                        public void run() {
                            handleRequest(connection);
                        }
                    });
                } catch (RejectedExecutionException e) {
                    if (!exec.isShutdown())
                        out.println("task submission rejected", e);
                }
            }
        }

        public void stop() {
            exec.shutdown();
        }

        private void handleRequest(Socket connection) {
            try {
                InputStream inputStream = connection.getInputStream();
                InputReader inputReader = new InputReader(inputStream);
                boolean res = true;
                int req = inputReader.readInt();

                if (isShutdownRequest(req))
                    stop();
                else
                    dispatchRequest(req);
            } catch (IOException e) {
                out.println("socket input stream reading failed", e);
            }
        }

        private static void dispatchRequest(int req) {
            // do anything you want
        }

        private static boolean isShutdownRequest(int req) {
            return (req & 1) == 1;
        }
    }

    final static class TaskExecutionWebServer {
        private static final int NTHREADS = 100;
        private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

        private static void run() throws IOException {
            ServerSocket socket = new ServerSocket(80);
            while (true) {
                final Socket connection = socket.accept();

                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        handleRequest(connection);
                    }
                };

                exec.execute(task);
            }
        }

        private static void handleRequest(@NotNull Socket connection) {
            // do anything you want with connection
        }
    }

    final static class HtmlServerTest {
        private static void run() throws IOException {
            ServerSocket socket = new ServerSocket(80);
            Socket connection = socket.accept();
            socket.getLocalSocketAddress();
        }
    }

    private static void outputWriter() {
        while (!outputUtils.outputComplete) {
            outputUtils.flush();
        }
    }

    final static class OutputUtils {
        private LinkedBlockingQueue<String> outputBuffer = new LinkedBlockingQueue();
        private LinkedList<String> outputList = new LinkedList<>();
        volatile boolean outputComplete = false;
        private final static String newLine = "\n";
        AtomicInteger size = new AtomicInteger();

        final static class Lock {
        }

        private final Lock notEmpty = new Lock(), flushLock = new Lock();

        void outputComplete() {
            outputComplete = true;
            flush();
        }

        void println(Object obj) throws InterruptedException {
            synchronized (flushLock) {
                outputList.addLast(String.valueOf(obj) + newLine);
            }
        }

        void flush() {
            synchronized (flushLock) {
                while (outputList.size() > 0) {
                    out.print(outputList.removeFirst());
                }

                out.flush();
            }
        }
    }

    private static void inputLoader() throws IOException, InterruptedException {
        int t = in.readInt();
        inputUtils.loadInt(t);

        while (t > 0) {
            t--;

            int n = in.readInt();
            inputUtils.loadInt(n);

            for (int i = 0; i < n; i++)
                inputUtils.loadLong(in.readLong());

            int q = in.readInt();
            inputUtils.loadInt(q);

            while (q > 0) {
                q--;
                inputUtils.loadLong(in.readLong());
            }
        }
    }

    final static class InputUtils {
        private LinkedList<Integer> intList = new LinkedList<>();
        private LinkedList<Long> longList = new LinkedList<>();

        final static class Lock {
        }

        ;

        private final Lock lockInt = new Lock(), lockLong = new Lock();

        int nextInt() throws InterruptedException {
            synchronized (lockInt) {
                while (intList.isEmpty())
                    lockInt.wait();

                return intList.removeFirst();
            }
        }

        long nextLong() throws InterruptedException {
            synchronized (lockLong) {
                while (longList.isEmpty())
                    lockLong.wait();

                return longList.removeFirst();
            }
        }

        void loadInt(int n) throws IOException, InterruptedException {
            synchronized (lockInt) {
                intList.addLast(n);

                if (intList.size() == 1)
                    lockInt.notify();
            }
        }

        void loadLong(long n) throws IOException, InterruptedException {
            synchronized (lockLong) {
                longList.addLast(n);

                if (longList.size() == 1)
                    lockLong.notify();
            }
        }

    }

}
