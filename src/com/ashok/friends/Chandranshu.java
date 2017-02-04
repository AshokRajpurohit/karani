package com.ashok.friends;

import com.ashok.friends.harsh.Harsh;
import com.ashok.lang.dsa.GenericHeap;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * The {@code Chandranshu} class is to solve problems for Chandranshu.
 * <p>
 * {@link Harsh}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Chandranshu {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        Chandranshu a = new Chandranshu();
        try {
            a.solve();
        } catch (IOException ioe) {
            // do nothing
        }
        out.close();
    }

    private void solve() throws IOException {
        while (true) {
            int size = in.readInt(), minValue = in.readInt(), maxValue = in.readInt();
            long elements = in.readLong();
            int[] ar = Generators.generateRandomIntegerArray(size, minValue, maxValue);
            int[] copy = Arrays.copyOf(ar, ar.length);
            out.print(ar);
            out.println(Fiberlink.maximumAmount(ar, elements) + "\t" + bruteForce(copy, elements));
            out.flush();
        }
    }

    public static long bruteForce(int[] ar, long k) {
        GenericHeap<Integer> heap = new GenericHeap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o2, o1);
            }
        }, (int) k);


        for (int e : ar)
            heap.offer(e);

        long sum = 0;
        while (k > 0) {
            k--;
            int top = heap.poll();
            sum += top;
            heap.offer(top - 1);
        }

        return sum;
    }

    final static class Fiberlink {
        public static long maximumAmount(int[] a, long k) {
            Arrays.sort(a);
            int count = 0;
            long sum = 0;
            int i = 0;

            for (i = a.length - 2; i >= 0; i--) {
                int soFar = a.length - i - 1;
                long n = soFar * (a[i + 1] - a[i]);

                if (n > k - count)
                    n = k - count;

                if (k - count < soFar)
                    return sum + (k - count) * a[i + 1];

                long tempCount = n / soFar;

                count += tempCount * soFar;
                sum += soFar * sum(a[i + 1] - tempCount + 1, a[i + 1]);

                a[i + 1] -= tempCount;

                if (count == k)
                    return sum;
            }

            return sum + (k - count) * a[1];
        }

        static long sum(long start, long end) {
            long sum = (end - start + 1) * (start + end);
            return sum >>> 1;
        }
    }
}
