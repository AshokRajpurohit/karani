package com.ashok.codejam.CJ15Q;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * CodeJam 2015 Qualification Round | Problem B. Infinite House of Pancakes
 */

public class B {

    private static PrintWriter out;
    private static InputStream in;
    private static String format = "Case #";
    private static Node head, tail;
    private static int count;

    public static void main(String[] args) throws IOException {
        //        OutputStream outputStream = System.out;
        //        in = System.in;
        //        out = new PrintWriter(outputStream);

        String input = "B.in", output = "B.out";
        FileInputStream fip = new FileInputStream(input);
        FileOutputStream fop = new FileOutputStream(output);
        in = fip;
        out = new PrintWriter(fop);
        B a = new B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 4);

        for (int i = 1; i <= t; i++) {
            int D = in.readInt();
            int[] ar = new int[D];
            for (int j = 0; j < D; j++) {
                ar[j] = in.readInt();
            }
            sb.append(format).append(i).append(": ");
            sb.append(solve(ar)).append('\n');
        }
        out.print(sb);
    }

    private int solve(int[] ar) {
        //        int max = 0;
        //        int min = 0;
        //        for (int i = 0; i < ar.length; i++) {
        //            max = ar[max] < ar[i] ? i : max;
        //            min = ar[min] > ar[i] ? i : min;
        //        }
        //        if (ar[max] == ar[min])
        //            return max;
        //
        //        int sec = 0, tmax = ar[max], tmin = ar[min];
        //        for (int i = 0; i < ar.length; i++) {
        //            if (ar[i] != max && sec < ar[i])
        //                sec = ar[i];
        //        }


        sort(ar);
        if (ar[0] < 4)
            return ar[0];
        count = ar.length;

        //        if (ar[0] < ar[ar.length - 1] + count) {
        //            int a = ar[0];
        //            int res = 0;
        //            while ((a + 1) / 2 + count < a) {
        //                res = res + count;
        //                count = count << 1;
        //                a = (a + 1) >> 1;
        //            }
        //            return res + a;
        //        }

        create_list(ar);
        return time();
    }

    private void create_list(int[] ar) {
        head = new Node(ar[0]);
        tail = head;
        for (int i = 1; i < ar.length; i++) {
            tail.next = new Node(ar[i]);
            tail = tail.next;
        }
    }

    private int time() {
        int time = 0;
        while (true) {
            if (head.data <= 3)
                return head.data + time;

            //            if (head.data <= tail.data + count) {
            //                int a = head.data;
            //                int res = 0;
            //                while ((a + 1) / 2 + count < a + count / 2) {
            //                    res = res + count;
            //                    count = count << 1;
            //                    a = (a + 1) >> 1;
            //                }
            //                return res + a;
            //            }

            Node temp = head;
            int num = 1;
            while (temp.next != null) {
                if (head.data - temp.next.data > num)
                    break;
                temp = temp.next;
                num++;
            }
            if (temp.next == null && head.data <= tail.data + count) {
                int a = head.data;
                int res = 0;
                while ((a + 1) / 2 + count < a) {
                    res = res + count;
                    count = count << 1;
                    a = (a + 1) >> 1;
                }
                return res + a + time;
            }

            Node[] ar = new Node[num];
            temp = head;
            time = time + num;
            for (int i = 0; i < num; i++) {
                ar[i] = temp;
                temp = temp.next;
            }
            Node t1, t2;
            for (int i = 0; i < num; i++) {
                t1 = new Node(ar[i].data / 2);
                t2 = new Node(ar[i].data - t1.data);
                insert(t1);
                insert(t2);
                delete(ar[i]);
            }
            //            System.out.println(head.data + "," + tail.data + "," + time + "," +
            //                               count);
        }

        //        goeasy();
        //        int time1 = head.data;
        //        Node thead = head;
        //        Node t1 = new Node(head.data >> 1);
        //        Node t2 = new Node(head.data - t1.data);
        //        count = count + 2;
        //        insert(t1, t2);
        //        head = head.next;
        //        int time2 = time();
        //        time1 = time1 > 1 + time2 ? 1 + time2 : time1;
        //        delete(t1);
        //        delete(t2);
        //        head = thead;
        //        count = count - 2;
        //        return time1;
    }

    private static void delete(Node t) {
        count--;
        if (t.data == head.data) {
            head = head.next;
            return;
        }
        Node temp = head;
        while (temp.next != null) {
            if (temp.next.data == t.data) {
                temp.next = temp.next.next;
                if (temp.next == null)
                    tail = temp;
                return;
            }
            temp = temp.next;
        }
    }

    private static void insert(Node t) {
        count++;
        if (t.data == head.data) {
            t.next = head;
            head = t;
            return;
        }
        Node temp = head;
        while (temp.next != null && temp.next.data > t.data)
            temp = temp.next;
        t.next = temp.next;
        temp.next = t;
        if (t.next == null)
            tail = t;
    }

    private static void insert(Node t1, Node t2) {
        Node temp = head;
        t2.next = t1;
        while (temp.next != null) {
            if (temp.next.data < t2.data) {
                t1.next = temp.next;
                temp.next = t2;
                return;
            }
            temp = temp.next;
        }
        temp.next = t2;
        tail = t1;
    }

    class Node {
        int data;
        Node next;

        Node(int n) {
            data = n;
        }
    }

    /**
     * sorting in decreasing order.
     * @param a
     */

    public static void sort(int[] a) {
        int[] b = new int[a.length];
        sort(a, b, 0, a.length - 1);
    }

    private static void sort(int[] a, int[] b, int begin, int end) {
        if (begin == end) {
            return;
        }

        int mid = (begin + end) / 2;
        sort(a, b, begin, mid);
        sort(a, b, mid + 1, end);
        merge(a, b, begin, end);
    }

    private static void merge(int[] a, int[] b, int begin, int end) {
        int mid = (begin + end) / 2;
        int i = begin;
        int j = mid + 1;
        int k = begin;
        while (i <= mid && j <= end) {
            if (a[i] < a[j]) {
                b[k] = a[j];
                j++;
            } else {
                b[k] = a[i];
                i++;
            }
            k++;
        }
        if (j <= end) {
            while (j <= end) {
                b[k] = a[j];
                k++;
                j++;
            }
        }
        if (i <= mid) {
            while (i <= mid) {
                b[k] = a[i];
                i++;
                k++;
            }
        }

        i = begin;
        while (i <= end) {
            a[i] = b[i];
            i++;
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
    }
}
