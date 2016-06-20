package com.ashok.hackerRank.Contests;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Counter Code 2015 | Poisonous Plants
 * https://www.hackerrank.com/contests/countercode/challenges/poisonous-plants
 */

public class CounterCode15D {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CounterCode15D a = new CounterCode15D();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        out.println(process(in.readIntArray(n)));
    }

    private static int process(int[] ar) {
        int days = 0;
        boolean yes = true;
        Node head = new Node(ar[0]);
        Node temp = head;
        for (int i = 1; i < ar.length; i++) {
            temp.next = new Node(ar[i]);
            temp.next.prev = temp;
            temp = temp.next;
        }

        Node tail = temp;

        while (yes) {
            yes = false;
            temp = tail;
            //            temp = head;
            //            while (temp.next != null)
            //                temp = temp.next;

            while (temp != head) {
                if (temp.data > temp.prev.data) {
                    yes = true;
                    temp = temp.prev;
                    Node fenk = temp.next;
                    temp.next = fenk.next;
                    if (fenk.next != null)
                        fenk.next.prev = temp;
                } else
                    temp = temp.prev;

                if (temp.next == null)
                    tail = temp;
            }
            if (yes)
                days++;
        }

        return days;
    }

    final static class Node {
        Node prev, next;
        int data;

        Node(int data) {
            this.data = data;
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
