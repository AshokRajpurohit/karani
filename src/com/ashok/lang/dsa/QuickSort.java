package com.ashok.lang.dsa;

import java.util.Random;

public class QuickSort {
    private QuickSort() {
        super();
    }

    public static void sort(int[] ar) {
        sort_Mo3(ar, 0, ar.length - 1);
    }

    public static void sortRandom(int[] ar) {
        sort(ar, 0, ar.length - 1, new Random());
    }

    public static void sort_Mo3Random(int[] ar) {
        sort_Mo3Random(ar, 0, ar.length - 1, new Random());
    }

    public static void sort(int[] ar, int start, int end) {
        if (start >= end)
            return;

        int pivot = pivot_Coremen(ar, start, end);
        sort(ar, start, pivot - 1);
        sort(ar, pivot + 1, end);
    }

    public static void sort_Mid(int[] ar) {
        sort_Mid(ar, 0, ar.length - 1);
    }

    private static void sort_Mid(int[] ar, int start, int end) {
        if (start >= end)
            return;

        int pivot = pivot_Mid(ar, start, end);
        sort_Mid(ar, start, pivot - 1);
        sort_Mid(ar, pivot + 1, end);
    }

    private static void sort_Mo3(int[] ar, int start, int end) {
        if (start >= end)
            return;

        int pivot = pivot_Mo3(ar, start, end);
        sort_Mo3(ar, start, pivot - 1);
        sort_Mo3(ar, pivot + 1, end);
    }

    public static void sort_Mo3Random(int[] ar, int start, int end,
                                      Random random) {
        if (start >= end)
            return;

        int pivot = pivot_Mo3Random(ar, start, end, random);
        sort_Mo3Random(ar, start, pivot - 1, random);
        sort_Mo3Random(ar, pivot + 1, end, random);
    }

    private static void sort(int[] ar, int start, int end, Random random) {
        if (start >= end)
            return;

        int p = coremen_Random(ar, start, end, random);
        sort(ar, start, p - 1, random);
        sort(ar, p + 1, end, random);
    }

    private static int pivote(int[] ar, int start, int end, Random random) {
        if (start == end)
            return start;

        int p = start + Math.abs(random.nextInt() % (end - start + 1));

        int i = start, j = end;
        while (i < j) {
            while (i < j && ar[i] <= ar[p])
                i++;

            if (j > i && ar[j] > ar[p])
                j--;

            if (i < j) {
                swap(ar, i, j);
                i++;
                j--;
            }
        }

        return i;
    }

    public static void hoare(int[] ar, int start, int end) {
        if (start >= end)
            return;

        int pivote = pivot_Hoare(ar, start, end);
        hoare(ar, start, pivote - 1);
        hoare(ar, pivote + 1, end);
    }

    /**
     * Pivot index function implementation as explained in "Introduction to
     * Algorithms by Coremen et al."
     *
     * @param ar array to be sorted
     * @param start start index of the subarray
     * @param end end index of the subarray
     * @return returns the index of the pivote after putting in correct place.
     */
    private static int pivot_Coremen(int[] ar, int start, int end) {
        if (start == end)
            return start;

        int pivot = ar[end];
        int i = start;
        for (int j = start; j < end; j++) {
            if (ar[j] <= pivot) {
                swap(ar, i, j);
                i++;
            }
        }
        swap(ar, i, end);
        return i;
    }

    private static int coremen_Random(int[] ar, int start, int end,
                                      Random random) {
        int pivot = start + Math.abs(random.nextInt(end - start + 1));
        swap(ar, pivot, end);
        pivot = ar[end];
        int i = start;
        for (int j = start; j < end; j++) {
            if (ar[j] <= pivot) {
                swap(ar, i, j);
                i++;
            }
        }
        swap(ar, i, end);
        return i;
    }

    /**
     * This is the implementation of Hoare partition scheme. for more info
     * please check wiki article. This method is not in use. It is advised not
     * to use this method as when the input array is already sorted the
     * running complexity is order of n^2.
     *
     * @param ar
     * @param start
     * @param end
     * @return
     */
    private static int pivot_Hoare(int[] ar, int start, int end) {
        if (start >= end)
            return start;

        int pivot = ar[(start + end) >>> 1];
        int i = start, j = end;
        while (true) {
            while (i <= end && ar[i] <= pivot)
                i++;

            while (j >= start && ar[j] > pivot)
                j--;

            if (i < j)
                swap(ar, i, j);
            else
                return j;
        }
    }

    private static int hoare_Random(int[] ar, int start, int end,
                                    Random random) {
        int pivot = ar[Math.abs(random.nextInt(end + 1 - start))];
        int i = start, j = end;

        while (true) {
            while (j > start && ar[j] > pivot)
                j--;

            while (i < end && ar[i] < pivot)
                i++;

            if (i < j)
                swap(ar, i, j);
            else
                return j;
        }
    }

    private static int pivot_Mid(int[] ar, int start, int end) {
        if (start == end)
            return start;

        int mid = (start + end) >>> 1;
        int pivot = ar[mid];
        swap(ar, end, mid);
        int i = start;
        for (int j = start; j < end; j++) {
            if (ar[j] <= pivot) {
                swap(ar, i, j);
                i++;
            }
        }
        swap(ar, i, end);
        return i;
    }

    private static int pivot_Mo3(int[] ar, int start, int end) {
        if (start == end)
            return start;

        if (end - start < 40)
            return pivot_Mid(ar, start, end);

        int l = start;
        int n = end;
        int m = (l + n) >>> 1;
        int len = end + 1 - start;
        // Big arrays, pseudomedian of 9
        int s = len / 8;
        l = medianOfThree(ar, l, l + s, l + 2 * s);
        m = medianOfThree(ar, m - s, m, m + s);
        n = medianOfThree(ar, n - 2 * s, n - s, n);
        m = medianOfThree(ar, l, m, n); // Mid-size, med of 3

        //        int mid = medianOfThree(ar, start, end);
        int pivot = ar[m];
        swap(ar, end, m);
        int i = start;
        for (int j = start; j < end; j++) {
            if (ar[j] <= pivot) {
                swap(ar, i, j);
                i++;
            }
        }
        swap(ar, i, end);
        return i;
    }

    private static int pivot_Mo3Random(int[] ar, int start, int end,
                                       Random random) {
        if (start == end)
            return start;

        if (end - start < 100)
            return pivot_Mid(ar, start, end);

        int l = start;
        int n = end;
        int m = (l + n) >>> 1;
        int len = end + 1 - start;
        // Big arrays, pseudomedian of 9
        int s = len / 8;
        l =
  medianOfThree(ar, l, l + random.nextInt(s), l + random.nextInt(2 * s));
        m = medianOfThree(ar, m - random.nextInt(s), m, m + random.nextInt(s));
        n =
  medianOfThree(ar, n - 1 - random.nextInt(2 * s), n - 1 - random.nextInt(s),
                n);
        m = medianOfThree(ar, l, m, n); // Mid-size, med of 3

        //        int mid = medianOfThree(ar, start, end);
        int pivot = ar[m];
        swap(ar, end, m);
        int i = start;
        for (int j = start; j < end; j++) {
            if (ar[j] <= pivot) {
                swap(ar, i, j);
                i++;
            }
        }
        swap(ar, i, end);
        return i;
    }

    public static boolean isSorted(int[] ar) {
        if (ar.length == 1)
            return true;

        for (int i = 1; i < ar.length; i++)
            if (ar[i] < ar[i - 1])
                return false;

        return true;
    }

    private static boolean isSorted(int[] ar, int start, int end) {
        if (start >= end)
            return true;

        for (int i = start + 1; i <= end; i++)
            if (ar[i] < ar[i - 1])
                return false;

        return true;
    }

    private static void swap(int[] ar, int i, int j) {
        int temp = ar[i];
        ar[i] = ar[j];
        ar[j] = temp;
    }

    public static int kthMinElement(int[] ar, int k) {
        return kthElement(ar, k - 1, 0, ar.length - 1);
    }

    public static int kthMaxElement(int[] ar, int k) {
        return kthElement(ar, ar.length - k, 0, ar.length - 1);
    }

    private static int kthElement(int[] ar, int k, int start, int end) {
        int pivot = pivot_Mo3(ar, start, end);
        if (pivot == k)
            return ar[pivot];

        if (pivot > k)
            return kthElement(ar, k, start, pivot - 1);

        return kthElement(ar, k, pivot + 1, end);
    }

    public static int[] maxElements(int[] ar, int k) {
        return maxElements(ar, ar.length - k, 0, ar.length - 1);
    }

    private static int[] maxElements(int[] ar, int k, int start, int end) {
        int pivot = pivot_Mid(ar, start, end);
        if (pivot == k)
            return copy(ar, pivot);

        if (k < pivot)
            return maxElements(ar, k, start, pivot - 1);

        return maxElements(ar, k, pivot + 1, end);
    }

    private static int medianOfThree(int[] ar, int a, int b, int c) {
        return (ar[a] < ar[b] ? (ar[b] < ar[c] ? b : ar[a] < ar[c] ? c : a) :
                (ar[b] > ar[c] ? b : ar[a] > ar[c] ? c : a));
    }

    private static int medianOfThree(int[] ar, int start, int end) {
        return medianOfThree(ar, start, end, (start + end) >>> 1);
    }

    private static int[] copy(int[] ar, int start) {
        int[] res = new int[ar.length - start];
        for (int i = start; i < ar.length; i++)
            res[i - start] = ar[i];

        return res;
    }

    public static void twoWaySedgewick(int[] ar) {
        twoWaySedgewick(ar, 0, ar.length - 1);
    }

    private static void twoWaySedgewick(int[] ar, int start, int end) {
        if (start >= end || isSorted(ar, start, end))
            return;

        //        if (isSorted(ar, start, end))
        //            return;

        if (end - start < 3) {
            goTrivial(ar, start, end);
            return;
        }

        int m = medianOfThree(ar, start, end);
        swap(ar, start, m);

        int i = start, j = end;
        while (i < j) {
            while (i < end && ar[i] <= ar[start])
                i++;

            while (j > start && ar[j] > ar[start])
                j--;

            if (i < j)
                swap(ar, i, j);
        }

        swap(ar, j, start);

        twoWaySedgewick(ar, start, j - 1);
        twoWaySedgewick(ar, j + 1, end);
        //        System.out.println(start + "\t" + i + "\t" + j + "\t" + end);
    }

    private static void goTrivial(int[] ar, int start, int end) {
        if (ar[start] > ar[start + 1])
            swap(ar, start, start + 1);

        if (ar[start + 1] > ar[end])
            swap(ar, start + 1, end);

        if (ar[start] > ar[start + 1])
            swap(ar, start, start + 1);
    }

    public static void Dijkstra3Way(int[] ar) {
        Dijkstra3Way(ar, 0, ar.length - 1);
    }

    private static void Dijkstra3Way(int[] ar, int start, int end) {
        if (start >= end || isSorted(ar, start, end))
            return;

        int m = medianOfThree(ar, start, end);
        swap(ar, start, m);

        int lt = start, gt = end, i = start, v = ar[start];
        while (i <= gt) {
            if (ar[i] == v)
                i++;
            else if (ar[i] < v) {
                swap(ar, i, lt);
                i++;
                lt++;
            } else {
                swap(ar, i, gt);
                gt--;
            }
        }

        Dijkstra3Way(ar, start, lt - 1);
        Dijkstra3Way(ar, i, end);
    }

    /**
     ******** work in progress ********
     ******** don't try to tun this *******
     * @param ar
     */

    public static void BentleyMcIlroy3Way(int[] ar) {
        BentleyMcIlroy3Way(ar, 0, ar.length - 1);
    }

    private static void BentleyMcIlroy3Way(int[] ar, int start, int end) {
        if (start >= end)
            return;

        if (end == start + 1) {
            if (ar[start] > ar[end])
                swap(ar, start, end);
            return;
        }

        if (isSorted(ar, start, end))
            return;

        int p = start + 1, q = end, i = start + 1, j = end;
        int v = ar[start];

        while (i <= j) {
            while (i < end && ar[i] < v)
                i++;

            while (j > start && ar[j] > v)
                j--;


            swap(ar, i, j);

            if (ar[i] == v) {
                swap(ar, i, p);
                p++;
                i++;
                //                    if (i < p)
                //                        i = p;
            }

            if (ar[j] == v) {
                swap(ar, j, q);
                q--;
                j--;
                //                if (j > q)
                //                    j = q;
            }

        }

        p--;
        q++;

        while (p >= start && j >= start) {
            swap(ar, p, j);
            j--;
            p--;
        }

        while (q <= end && i <= end) {
            swap(ar, q, i);
            q++;
            i++;
        }
        //        System.out.println(v + ":");
        //        print(ar);

        BentleyMcIlroy3Way(ar, start, j);
        BentleyMcIlroy3Way(ar, i, end);
    }

    public static void DualPivot(int[] ar) {
        DualPivot(ar, 0, ar.length - 1);
    }

    private static void DualPivot(int[] ar, int start, int end) {
        if (start >= end || isSorted(ar, start, end))
            return;

        if (end - start < 3) {
            goTrivial(ar, start, end);
            return;
        }

        int lt = start + 1, i = start + 1, gt = end - 1;
        while (i <= gt) {
            if (ar[i] < ar[start]) {
                swap(ar, i, lt);
                i++;
                lt++;
            } else if (ar[i] > ar[end]) {
                swap(ar, i, gt);
                gt--;
            } else
                i++;
        }
        lt--;
        swap(ar, start, lt);
        gt++;
        swap(ar, gt, end);

        DualPivot(ar, start, lt - 1);
        DualPivot(ar, gt + 1, end);
        DualPivot(ar, lt + 1, gt - 1);
    }
}
