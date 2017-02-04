package com.ashok.friends;

import com.ashok.lang.dsa.RandomStrings;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.util.*;


/**
 * Problem Name: Ankit Soni
 * Link: Office E-MAIL ID
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AnkitSoni {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        while (true) {
            out.println(TopTal.task2(in.readInt(), in.read(), in.read(), in.read()));
            out.flush();
            int n = in.readInt(), k = in.readInt();
            int[] ar = in.readIntArray(k);
            long time = System.currentTimeMillis();
            out.println(uneaterLeaves(n, k, ar));
            out.println("time: " + (System.currentTimeMillis() - time));
            out.flush();
        }

//        AnkitSoni a = new AnkitSoni();
//        a.solve();
//        out.close();
    }

    private static int uneaterLeaves(int n, int k, int[] ar) {
        int[] factors = getFactors(ar);
        int res = n;

        for (int i = 0; i < factors.length; i++)
            res -= recursion(n, factors, 1, i);

        return res;
    }

    private static int recursion(int n, int[] factors, int soFar, int index) {
        if (index == factors.length || soFar > n)
            return 0;

        soFar *= factors[index];
        int res = n / soFar;

        for (int i = index + 1; i < factors.length; i++)
            res -= recursion(n, factors, soFar, i);

        return res;
    }

    private static int[] getFactors(int[] ar) {
        Arrays.sort(ar);
        LinkedList<Integer> list = new LinkedList<>();

        for (int i = 0; i < ar.length; i++) {
            if (ar[i] != 0) {
                list.add(ar[i]);

                for (int j = i + 1; j < ar.length; j++)
                    if (ar[j] % ar[i] == 0)
                        ar[j] = 0;
            }
        }

        int[] res = new int[list.size()];
        int index = 0;

        for (int e : list)
            res[index++] = e;

        return res;
    }

    private static int cupcakes(int n, int c, int m) {
        int cakes = n / c;
        int wrappers = cakes;

        while (wrappers >= m) {
            cakes += wrappers / m;
            wrappers = (wrappers / m) + (wrappers % m);
        }

        return cakes;
    }

    private void solve() throws IOException {
        RandomStrings random = new RandomStrings();
        while (true) {
            int n = in.readInt();
            int[] ar = Generators.generateRandomIntegerArray(n);
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int e : ar)
                arrayList.add(e);

            long time = System.currentTimeMillis();
            arrayList.toArray();
            out.println("toArray method time: " + (System.currentTimeMillis() - time));

            time = System.currentTimeMillis();
            listToArray(arrayList);
            out.println("custom method time: " + (System.currentTimeMillis() - time));

            out.flush();
        }
    }

    private static void listToArray(List<Integer> list) {
        int[] ar = new int[list.size()];
        int index = 0;

        for (int e : list)
            ar[index++] = e;
    }

    private static int reach(int[][] matrix, int sr, int sc, int er, int ec) {
        if (sr == er && sc == ec)
            return 0;

        return 4;
    }

    public static String bomber(char[] ar) {
        if (ar.length < 3)
            return String.valueOf(ar);

        int[] stack = new int[ar.length];
        char[] stackAr = new char[ar.length];
        stack[0] = 1;
        stackAr[0] = ar[0];
        int si = 0;

        for (int i = 1; i < ar.length; i++) {
            if (ar[i] == stackAr[si])
                stack[si]++;
            else {
                while (si > 0 && stack[si] >= 3)
                    si--;

                si++;
                stack[si] = 1;
                stackAr[si] = ar[i];
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= si; i++) {
            if (stack[i] >= 3)
                continue;

            for (int j = 0; j < stack[i]; j++)
                sb.append(stackAr[i]);
        }

        return sb.toString();
    }

    class Pair implements Comparable<Pair> {
        char key;
        int value;

        Pair(char k, int v) {
            key = k;
            value = v;
        }

        public int compareTo(Pair o) {
            return this.key - o.key;
        }
    }

    /**
     * HackerEarth Hiring Challenge Problem.
     */
    final static class MinimizeSum {
        private static int[][][] excludeArray, includeArray;
        private static int[] sums;

        private static void populate(int[] ar) {
            int n = ar.length;
            excludeArray = new int[n][n][];
            includeArray = new int[n][n][];

            for (int i = 0; i < n; i++)
                for (int j = i; j < n; j++) {
                    int width = j - i + 1;
                    excludeArray[i][j] = new int[n - width];
                    includeArray[i][j] = new int[width];

                    for (int k = 0; k < i; k++)
                        excludeArray[i][j][k] = ar[k];

                    for (int k = j + 1; k < n; k++)
                        excludeArray[i][j][k - width] = ar[k];

                    for (int k = i; k <= j; k++)
                        includeArray[i][j][k - i] = ar[k];

                    Arrays.sort(excludeArray[i][j]);
                    Arrays.sort(includeArray[i][j]);
                }

            sums = new int[ar.length];
            sums[0] = ar[0];

            for (int i = 1; i < ar.length; i++)
                sums[i] = ar[i] + sums[i - 1];
        }

        public static int minSum(int[] ar, int n, int k) {
            if (allNegative(ar))
                return sum(ar);

            int min = min(ar);
            if (min >= 0)
                return min;

            int pos = 0, neg = 0;
            for (int e : ar) {
                if (e == 0)
                    continue;

                if (e > 0)
                    pos++;
                else neg++;
            }

            if (k >= pos || k >= neg)
                return negativeSum(ar);

            return process(ar, k, negativeSum(ar));
        }

        private static int process(int[] ar, int k, int minLimit) {
            populate(ar);
            int minSum = ar[0];

            for (int i = 0; i < ar.length && minSum > minLimit; i++)
                for (int j = 0; j < ar.length; j++) {
                    int[] include = includeArray[i][j], exclude =
                            excludeArray[i][j];

                    if (include == null || exclude == null)
                        continue;

                    int sum = sums[j] - (i == 0 ? 0 : sums[i - 1]);

                    for (int x = include.length - 1, y = 0; x >= 0 && y <
                            exclude.length && y < k; x--, y++) {
                        if (include[x] <= exclude[y])
                            break;

                        sum += exclude[y] - include[x];
                    }

                    minSum = Math.min(minSum, sum);
                    if (minSum == minLimit)
                        return minLimit;
                }

            return minSum;
        }

        private static int negativeSum(int[] ar) {
            int sum = 0;

            for (int e : ar)
                if (e < 0)
                    sum += e;

            return sum;
        }

        private static int[] countNegatives(int[] ar) {
            int[] res = new int[ar.length];
            if (ar[0] <= 0)
                res[0] = 1;

            for (int i = 1; i < ar.length; i++)
                res[i] = ar[i] <= 0 ? res[i - 1] + 1 : res[i - 1];

            return res;
        }

        private static int min(int[] ar) {
            int min = Integer.MAX_VALUE;

            for (int e : ar)
                if (min > e) min = e;

            return min;
        }

        private static boolean allNegative(int[] ar) {
            for (int e : ar)
                if (e > 0)
                    return false;

            return true;
        }

        private static int sum(int[] ar) {
            int sum = 0;
            for (int e : ar)
                sum += e;

            return sum;
        }
    }

    final static class Task2 {
        public int[] solution(int[] A, int[] B) {
            int[] res = new int[A.length + B.length];
            int i = 0, j = 0, k = 0;

            for (i = A.length - 1, j = B.length - 1, k = res.length - 1; i >= 0 && j >= 0; i--, j--, k--) {
                add(res, A[i], B[j], k);
            }

            return res;
        }

        private int[] convert(int[] ar) {
            int length = ar.length;
            int i = 0, j = 0;

            while (i < ar.length && ar[i] == 0) {
                i++;
                length--;
            }

            int[] res = new int[length];
            for (i = ar.length - length, j = 0; i < ar.length; i++)
                res[j] = ar[i];

            return res;
        }

        private void add(int[] res, int v1, int v2, int index) {
            if (v1 == 1 && v2 == 1) {
                add(res, v1, v2, index - 2);
            }

            if (v1 == 0 && v2 == 0)
                return;

            if (res[index] == 1) {
                res[index] = 0;

                add(res, 1, 1, index - 2);
            }
        }
    }

    final static class TopTal {
        private static String[] weekDays =
                {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"},
                months = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
                        "October", "November", "December"};

        private static HashMap<String, Integer> map = new HashMap<>();
        private static int[] monthOffset = {0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5},
                daysInMonths = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        static {
            for (int i = 0; i < weekDays.length; i++)
                map.put(weekDays[i], i);

            for (int i = 0; i < months.length; i++)
                map.put(months[i], i);
        }

        public static int task2(int year, String startMonth, String endMonth, String firstDayOfYear) {
            boolean leapYear = isLeapYear(year);
            int startMonthIndex = map.get(startMonth), endMonthIndex = map.get(endMonth);
            int dayIndex = 1, firstDayIndex = map.get(firstDayOfYear);
            int days = daysInMonths(startMonthIndex, leapYear) + 1 - firstDayInMonth(dayIndex, monthOffset(leapYear, startMonthIndex, firstDayIndex));

            for (int month = startMonthIndex + 1; month < endMonthIndex; month++)
                days += daysInMonths(month, leapYear);

            days += lastDayInMonth(0, monthOffset(leapYear, endMonthIndex, firstDayIndex), daysInMonths(endMonthIndex, leapYear));
            if (startMonthIndex == endMonthIndex)
                days -= daysInMonths(startMonthIndex, leapYear);

            return days / 7;
        }

        private static int daysInMonths(int monthIndex, boolean leapYear) {
            if (leapYear && monthIndex == 1)
                return 29;

            return daysInMonths[monthIndex];
        }

        private static boolean isLeapYear(int year) {
            return (year & 4) == 0;
        }

        public static int monthOffset(boolean leapYear, int monthIndex, int firstDayInYear) {
            int offset = monthOffset[monthIndex] + firstDayInYear;

            if (leapYear && monthIndex > 2)
                offset++;

            return offset % 7;
        }

        public static int lastDayInMonth(int dayIndex, int monthOffset, int monthDays) {
            int date = 28 + firstDayInMonth(dayIndex, monthOffset);

            if (date <= monthDays)
                return date;

            return date - 7;
        }

        public static int firstDayInMonth(int dayIndex, int monthOffset) {
            if (dayIndex >= monthOffset)
                return dayIndex - monthOffset + 1;

            return dayIndex + 8 - monthOffset;
        }
    }
}
