package com.ashok.lang.concurrency;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Problem Name: Inputs, Outputs and Main program all in seperate threads.
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class TestConcurrentInputOutput {
    private static final String path = "C:/Projects/karani/src/com/ashok/lang/template/";
    private static Output out;// = new Output();
    private static InputReader in;// = new InputReader();
    private static InputUtils inputUtils = new InputUtils();
    private static OutputUtils outputUtils = new OutputUtils();
    private static CountDownLatch startGate = new CountDownLatch(1), endGate = new CountDownLatch(2);

    private static Thread inputThread = new Thread(new Runnable() {
        public void run() {
            try {
//                startGate.await();
                System.out.println("wait over for input thread");
                inputLoader();
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("input thread is closing");
                endGate.countDown();
            }
        }
    });

    private static Thread outputThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
//                startGate.await();
                System.out.println("wait over for output thread");
                outputWriter();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("output thread is closing " + endGate.getCount());
                endGate.countDown();
            }
        }
    });

    public static void main(String[] args) throws IOException,
            InterruptedException {
        long time = System.currentTimeMillis();
        inputThread.setName("input-thread");
        outputThread.setName("output-thread");
        TestConcurrentInputOutput a = new TestConcurrentInputOutput();
        in = new InputReader(path + "Input.in");
        out = new Output(path + "Output.out");
        inputThread.start();
        outputThread.start();
        startGate.countDown();
        a.solve();

        endGate.await();

        System.out.println(System.currentTimeMillis() - time);

        out.close();
    }

    @Override
    protected void finalize() throws Throwable {
        out.close();
    }

    private void solve() throws IOException, InterruptedException {
        int t = inputUtils.nextInt();

        while (t > 0) {
            t--;

            int n = inputUtils.nextInt();
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = inputUtils.nextLong();

            Arrays.sort(ar);
            int q = inputUtils.nextInt();

            while (q > 0) {
                q--;

                long num = inputUtils.nextLong();
                int index = Arrays.binarySearch(ar, num);
                boolean found = ar[n >> 1] == num;
                outputUtils.println(found);
            }
        }

        // System.out.println("solve finish");

        outputUtils.outputComplete();// = true;
    }

    private static void outputWriter() throws InterruptedException {
        while (!outputUtils.outputComplete) {
            outputUtils.flush();
        }

        outputUtils.flush();
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

        void outputComplete() throws InterruptedException {
            outputComplete = true;
//            flush();
        }

        void println(Object obj) throws InterruptedException {
            synchronized (notEmpty) {
                boolean notify = outputList.isEmpty();
                outputList.addLast(String.valueOf(obj) + newLine);

                if (notify)
                    notEmpty.notify();
            }
        }

        void flush() throws InterruptedException {
            while (outputList.size() > 0) {
                out.print(take());
            }
        }

        String take() throws InterruptedException {
            synchronized (notEmpty) {
                while (outputList.isEmpty())
                    notEmpty.wait();

                return outputList.removeFirst();
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
