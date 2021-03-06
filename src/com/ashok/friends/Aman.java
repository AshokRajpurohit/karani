package com.ashok.friends;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.util.Arrays;

/**
 * this class is to solve Aman Kashyap's problems.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class Aman {

    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        Aman a = new Aman();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        while (true) {
            int count = 0;
            int size = in.readInt(), test = in.readInt();
            long time = System.currentTimeMillis();

            while (test > 0) {
                test--;
                int[] ar = Generators.generateRandomIntegerArray(size);
                int[] br = Generators.generateRandomIntegerArray(size);
                seggregateArrays(ar, br);

                Arrays.sort(ar);
                Arrays.sort(br);

                if (ar[size - 1] > br[0])
                    count++;
            }

            out.println("time: " + (System.currentTimeMillis() - time));
            out.println(count);
            out.flush();
        }
    }

    /**
     * Populates first array with smaller elements and second with larger elements
     * without using third array.
     *
     * @param a first array
     * @param b second array
     */
    private static void seggregateArrays(int[] a, int[] b) {
        Arrays.sort(a);
        Arrays.sort(b);

        int ai = a.length - 1, bi = 0;

        while (ai != -1 && bi != b.length) {
            if (a[ai] > b[bi]) {
                int temp = a[ai];
                a[ai] = b[bi];
                b[bi] = temp;
                bi++;
            }

            ai--;
        }
    }

    private static long maximumAmount(int[] a, long k) {
        Arrays.sort(a);
        int n = a.length;

        if (k == 1)
            return a[n - 1];

        if (k == sum(a))
            return sellAll(a);

        int start = n - 1;
        long res = 0;

        while (k > 0) {
            int lower = start > 0 ? a[start - 1] : 0;
            long total = (a[start] - lower) * (n - start);

            if (total <= k) {
                k -= total;
                res += (n - start) * calculate(lower + 1, a[start]);
            } else {
                long flips = k / (n - start);
                long newLower = a[start] - flips;

                res += (n - start) * calculate(newLower + 1, a[start]);
                k -= (n - start) * (a[start] - newLower);
                res += newLower * k;
                k = 0;
            }

            start--;
        }

        return res;
    }

    private static long sum(int[] a) {
        long res = 0;

        for (int e : a)
            res += e;

        return res;
    }

    private static long sellAll(int[] ar) {
        long res = 0;

        for (int e : ar)
            res += calculate(e);

        return res;
    }

    private static long calculate(long a, long b) {
        return calculate(b) - calculate(a - 1);
    }

    private static long calculate(long n) {
        return (n * (n + 1)) >>> 1;
    }

    /**
     * This functions maintains two type of states for each user.
     * One he is using computer or not. second he is in cafe or not.
     * If he is in cafe and not using any computer than if he leaves
     * the availability of computers is still the same. If he leaves
     * nobody cares (at least I don't as he is not releaving computer.
     *
     * @param computers number of computers in the cafe
     * @param customers customer string (come and go data)
     * @return number of customers without service.
     */
    private static int function(int computers, String customers) {
        boolean[] using = new boolean[256];
        boolean[] entry = new boolean[256];

        int count = 0;
        for (int i = 0; i < customers.length(); i++) {
            if (using[customers.charAt(i)]) {
                computers++;
                using[customers.charAt(i)] = false;
                entry[customers.charAt(i)] = false;
            } else if (computers == 0 && !entry[customers.charAt(i)]) {
                count++;
                entry[customers.charAt(i)] = true;
            } else if (entry[customers.charAt(i)]) {
                entry[customers.charAt(i)] = false;
            } else {
                computers--;
                entry[customers.charAt(i)] = true;
                using[customers.charAt(i)] = true;
            }
        }
        return count;
    }

    /**
     * will write later.
     *
     * @param s
     * @param n
     * @return
     */
    private static char compressor(String s, int n) {
        if (n == 0)
            return s.charAt(0);

        int index = 0;
        for (int i = 0; i < s.length(); ) {
            char ch = s.charAt(i);
            i++;
            int count = 0;
            while (i < s.length() && s.charAt(i) >= '0' &&
                    s.charAt(i) <= '9') {
                count = (count << 3) + (count << 1) + s.charAt(i) - '0';
                i++;
            }

            index += count;
            if (index >= n)
                return ch;
        }
        return 'L';
    }

    final static class Node {
        Node next;
        int data;

        Node(int n) {
            data = n;
        }
    }

    public Node mergeList(Node headOne, Node headTwo) {
        int count = 0;
        Node temp = headOne;
        while (temp != null) {
            temp = temp.next;
            count++;
        }

        temp = headTwo;
        while (temp != null) {
            temp = temp.next;
            count++;
        }

        Node[] ar = new Node[count];
        int i = 0;
        temp = headOne;
        while (temp != null) {
            ar[i] = temp;
            temp = temp.next;
            i++;
        }

        temp = headTwo;
        while (temp != null) {
            ar[i] = temp;
            i++;
            temp = temp.next;
        }

        sort(ar);
        Node head = ar[0];
        temp = head;
        for (int j = 1; j < ar.length; ) {
            while (j < ar.length && ar[j].data == temp.data)
                j++;
            if (j < ar.length) {
                temp.next = ar[j];
                j++;
                temp = temp.next;
            }
        }
        temp.next = null;

        return head;
    }

    private static void sort(Node[] a) {
        Node[] b = new Node[a.length];
        sort(a, b, 0, a.length - 1);
    }

    private static void sort(Node[] a, Node[] b, int begin, int end) {
        if (begin == end) {
            return;
        }

        int mid = (begin + end) / 2;
        sort(a, b, begin, mid);
        sort(a, b, mid + 1, end);
        merge(a, b, begin, end);
    }

    private static void merge(Node[] a, Node[] b, int begin, int end) {
        int mid = (begin + end) / 2;
        int i = begin;
        int j = mid + 1;
        int k = begin;
        while (i <= mid && j <= end) {
            if (a[i].data > a[j].data) {
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

    /**
     * splits the array in two parts where average of first part is equal to
     * average of second part. If no such split possible it will make the
     * second part as null.
     *
     * @param ar The array to be split.
     * @return array of two arrays (sub arrays of ar)
     */
    public static int[][] splitArray(int[] ar) {
        int[][] result = new int[2][];

        double[] left = new double[ar.length];
        double[] right = new double[ar.length];
        left[0] = ar[0];
        right[ar.length - 1] = ar[ar.length - 1];

        for (int i = 1; i < ar.length; i++)
            left[i] = left[i - 1] + ar[i];

        for (int i = ar.length - 2; i >= 0; i--)
            right[i] = right[i + 1] + ar[i];

        for (int i = 0; i < ar.length; i++)
            left[i] = left[i] / (i + 1);

        for (int i = ar.length - 1; i >= 0; i--)
            right[i] = right[i] / (ar.length - i);

        int index = 0;
        for (index = 0;
             index < ar.length - 1 && left[index] != right[index + 1]; index++)
            ;


        int[] first = new int[index + 1];
        for (int i = 0; i <= index; i++)
            first[i] = ar[i];

        int[] second = new int[ar.length - index - 1];
        for (int i = index + 1; i < ar.length; i++)
            second[i - index - 1] = ar[i];

        result[0] = first;
        result[1] = second;
        return result;
    }
}
