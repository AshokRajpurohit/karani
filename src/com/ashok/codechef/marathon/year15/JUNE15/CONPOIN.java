package com.ashok.codechef.marathon.year15.JUNE15;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit ashok1113
 *  problem: Connect Points
 *  http://www.codechef.com/JUNE15/problems/CONPOIN
 */

public class CONPOIN {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        CONPOIN a = new CONPOIN();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 1);

        while (t > 0) {
            t--;
            int n = in.readInt();
            int m = in.readInt();
            List[] list = new List[n];
            for (int i = 0; i < n; i++)
                list[i] = new List();

            for (int i = 0; i < m; i++) {
                int a = in.readInt() - 1;
                int b = in.readInt() - 1;
                add(list[a], b);
                add(list[b], a);
            }

            if (possible(list, n, m))
                sb.append('0').append('\n');
            else
                sb.append('1').append('\n');
        }
        out.print(sb);
    }

    private static boolean possible(List[] list, int n, int m) {
        int[][] ar = process(list);

        //        for (int i = 0; i < n; i++) {
        //            for (int e : ar[i])
        //                System.out.print(e + ", ");
        //            System.out.println();
        //        }

        if (m >= (n - 2) * 3)
            return false;

        if (n >= m)
            return true;

        return true;
    }

    private static int[][] process(List[] list) {
        int[][] ar = new int[list.length][];
        for (int i = 0; i < list.length; i++) {
            if (list[i].count > 0) {
                Node node = list[i].head;
                ar[i] = new int[list[i].count];
                for (int j = 0; j < list[i].count; j++) {
                    ar[i][j] = node.data;
                    node = node.next;
                }
                sort(ar[i]);
            }
        }
        return ar;
    }

    private static boolean possible(int n, int m, int[][] ar) {
        if (2 * m == n * (n - 1))
            return false;
        return true;
    }

    private static void add(List list, int a) {
        list.add(new Node(a));
    }

    private static void sort(int[] a) {
        if (a.length < 2)
            return;
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
            if (a[i] > a[j]) {
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

    final static class List {
        Node head;
        int count = 0;

        void add(Node node) {
            count++;
            if (head == null)
                head = node;
            else {
                node.next = head;
                head = node;
            }
        }
    }

    final static class Node {
        int data;
        Node next;

        Node(int n) {
            data = n;
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
