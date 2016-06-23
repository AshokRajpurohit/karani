package com.ashok.codeforces.contest;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Processing Queries
 * problem Link: http://codeforces.com/contest/644/problem/B
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class CROC2016B {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        CROC2016B a = new CROC2016B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt(), size = in.readInt();
        List list = new List(size + 1);
        StringBuilder sb = new StringBuilder(n * 3);
        long[] output = new long[n];
        int index = 0;

        for (int i = 0; i < n; i++) {
            Node temp = new Node(i, in.readInt(), in.readInt());
            long time = temp.t;
            if (list.head == null) {
                temp.e = temp.t + temp.d;
                list.add(temp);
                output[i] = temp.e;
                continue;
            }

            time = list.head.e;
            while (time <= temp.t && !list.isEmpty()) {
                Node node = list.remove();
                output[node.index] = node.e;

                if (!list.isEmpty()) {
                    time = list.head.e;
                }
            }

            if (list.isFull()) {
                output[i] = -1;
                continue;
            } else {

                list.add(temp);
                output[i] = temp.e;
            }
        }

        for (long e : output)
            sb.append(e).append(' ');

        out.println(sb.toString());
    }

    private final static class Node {
        int index = 0;
        long t, d, e = 0;
        Node next;

        Node(int i, int time, int duration) {
            index = i;
            t = time;
            d = duration;
        }

        void set(long time) {
            if (time > t)
                t = time;

            e = t + d;
        }
    }

    private final static class List {
        Node head, tail;
        int capacity, size = 0;

        List(int size) {
            capacity = size;
        }

        void add(Node node) {
            if (head == null) {
                tail = node;
                head = tail;
                node.e = node.t + node.d;
            } else {
                tail.next = node;
                node.set(tail.e);
                tail = tail.next;
            }

            size++;
        }

        boolean isFull() {
            return size == capacity;
        }

        boolean isEmpty() {
            return size == 0;
        }

        Node remove() {
            if (size == 0)
                return null;

            Node res = head;
            head = head.next;
            if (head == null)
                tail = null;

            size--;
            return res;
        }
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

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
    }
}
