package com.ashok.codechef.marathon.year15.APRIL15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link:  http://www.codechef.com/APRIL15/problems/FRMQ
 */

public class G {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] ar, sar;
    private static int x, y;
    private static Node root;


    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        G a = new G();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        ar = new int[n];

        for (int i = 0; i < n; i++) {
            ar[i] = in.readInt();
        }

        int m = in.readInt();
        x = in.readInt();
        y = in.readInt();
        long sum = 0;

        if (check_inc()) {
            sum = ar[y];
            for (int i = 1; i < m; i++) {
                sexy();
                if (x > y)
                    sum = sum + ar[x];
                else
                    sum = sum + ar[y];
            }
            out.println(sum);
            return;
        }

        if (check_dec()) {
            sum = ar[x];
            for (int i = 1; i < m; i++) {
                sexy();
                if (x > y)
                    sum = sum + ar[y];
                else
                    sum = sum + ar[x];
            }
            out.println(sum);
            return;
        }
        sar = sortIndex(ar);
        root = new Node(sar[0]);
        formatTree();
        sum = sum + find();
        //        printTree();
        //        out.println(find());
        //        out.println(x + "," + y);
        //        System.out.println(x + "," + y);

        for (int i = 1; i < m; i++) {
            sexy();
            //            System.out.println(x + "," + y);
            //            out.println(x + "," + y);
            //                    System.out.println(find());
            //            sb.append(find()).append('\n');
            sum = sum + find();
        }

        out.println(sum);
    }

    private static int find() {
        if (x == y) {
            return ar[x];
        }

        if (x > y && x < y + 50) {
            return find_easy(y, x);
        }

        if (y < x + 50)
            return find_easy(x, y);

        if (x > y)
            return find(y, x);
        return find(x, y);
    }

    private static int find_easy(int i, int j) {
        int max = 0;
        for (; i <= j; i++) {
            max = max < ar[i] ? ar[i] : max;
        }
        return max;
    }

    private static int find(int x, int y) {
        Node temp = root;
        while (true) {
            if (temp.data < x) {
                //                if (temp.right == null)
                //                    return ar[temp.data];
                temp = temp.right;
            } else if (temp.data > y) {
                //                if (temp.left == null)
                //                    return ar[temp.data];
                temp = temp.left;
            } else {
                return ar[temp.data];
            }
        }
    }

    private static void printTree() {
        root.print();
    }

    private static void sexy() {
        x = x + 7;
        x = x % (ar.length - 1);
        y = y + 11;
        y = y % ar.length;
    }

    private static void formatTree() {
        Node temp = root;
        for (int i = 1; i < sar.length; i++) {
            formatTree(sar[i]);
        }
    }

    private static void formatTree(int i) {
        Node temp = root;
        while (true) {
            if (temp.data > i) {
                if (temp.left == null) {
                    temp.add_left(i);
                    return;
                }
                temp = temp.left;
            } else {
                if (temp.right == null) {
                    temp.add_right(i);
                    return;
                }
                temp = temp.right;
            }
        }
    }

    private static boolean check_inc() {
        for (int i = 1; i < ar.length; i++) {
            if (ar[i] < ar[i - 1])
                return false;
        }
        return true;
    }

    private static boolean check_dec() {
        for (int i = 1; i < ar.length; i++) {
            if (ar[i] > ar[i - 1])
                return false;
        }
        return true;
    }

    class Node {
        Node left, right;
        int data;

        Node(int i) {
            data = i;
        }

        void add(int i) {
            if (i < data) {
                if (left == null) {
                    left = new Node(i);
                    return;
                }
                left.add(i);
                return;
            }
            if (right == null) {
                right = new Node(i);
                return;
            }
            right.add(i);
        }

        void add_right(int i) {
            right = new Node(i);
        }

        void add_left(int i) {
            left = new Node(i);
        }

        int find(int x, int y) {
            if (data < x) {
                return right.find(x, y);
            }
            if (y < data) {
                return left.find(x, y);
            }
            return data;
        }

        void print() {
            out.println(data);
            if (left != null) {
                left.print();
            }
            if (right != null) {
                right.print();
            }
        }
    }

    public static int[] sortIndex(int[] a) {
        int[] b = new int[a.length];
        int[] c = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = i;
        }
        sort(a, b, c, 0, a.length - 1);
        return c;
    }

    private static void sort(int[] a, int[] b, int[] c, int begin, int end) {
        if (begin == end) {
            return;
        }

        int mid = (begin + end) / 2;
        sort(a, b, c, begin, mid);
        sort(a, b, c, mid + 1, end);
        merge(a, b, c, begin, end);
    }

    public static void merge(int[] a, int[] b, int[] c, int begin, int end) {
        int mid = (begin + end) / 2;
        int i = begin;
        int j = mid + 1;
        int k = begin;
        while (i <= mid && j <= end) {
            if (a[c[i]] < a[c[j]]) {
                b[k] = c[j];
                j++;
            } else {
                b[k] = c[i];
                i++;
            }
            k++;
        }
        if (j <= end) {
            while (j <= end) {
                b[k] = c[j];
                k++;
                j++;
            }
        }
        if (i <= mid) {
            while (i <= mid) {
                b[k] = c[i];
                i++;
                k++;
            }
        }

        i = begin;
        while (i <= end) {
            c[i] = b[i];
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
            for (; buffer[offset] < 0x30; ++offset) {
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
            //            return number;
            return number * s;
        }

    }
}
