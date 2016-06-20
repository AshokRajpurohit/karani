package com.ashok.friends;

import java.util.Arrays;
import java.util.Random;

public class DevKashyap {
    private DevKashyap() {
        super();
    }

    private static boolean[] digit = new boolean[256];
    private static int[] value = new int[256];

    static {
        for (int i = '0'; i <= '9'; i++)
            digit[i] = true;

        for (int i = '0'; i <= '9'; i++)
            value[i] = i - '0';
    }

    public static int solution(String s) {
        int i = 0;
        while (i < s.length() && s.charAt(i) == '0')
            i++;

        if (i == s.length())
            return 0;

        i++;
        int res = 1;
        while (i < s.length()) {
            res++;
            if (s.charAt(i) == '1')
                res++;

            i++;
        }

        return res;
    }

    public static long combinationString(String s, int patternSize) {
        if (s.length() == 1)
            return 1;

        int mod = 1000000007;

        long a = 1, b = 1;
        for (int i = 1; i < s.length(); i++) {
            long c = b;
            int num = charsToNumber(s.charAt(i - 1), s.charAt(i));
            if (num < patternSize)
                c += a;

            a = b;
            b = c % mod;
        }

        return b;
    }

    private static int charsToNumber(char a, char b) {
        return a * 10 - 11 * '0' + b;
    }

    public static int left1right0(int[] ar) {
        int total = 0;
        for (int i = 0; i < ar.length; i++)
            if (ar[i] == 0)
                total++;

        int one = 0, zero = total;
        for (int i = 0; i < ar.length; i++) {
            if (ar[i] == 0)
                zero--;

            if (one == zero)
                return i;

            if (ar[i] == 1)
                one++;
        }

        return -1;
    }

    public static int maxRepeating(int[] ar, int k) {
        int n = ar.length;

        for (int i = n - 1; i >= 0; i--) {
            while (ar[i] != i && ar[ar[i]] != ar[i]) {
                swap(ar, i, ar[i]);
            }
        }

        for (int i = 0; i < k; i++)
            ar[i] = 1;

        for (int i = k; i < n; i++)
            ar[ar[i]]++;

        int max = 0, num = 0;
        for (int i = 0; i < k; i++) {
            if (ar[i] > max) {
                max = ar[i];
                num = i;
            }
        }

        return num;
    }

    private static void swap(int[] ar, int a, int b) {
        int temp = ar[a];
        ar[a] = ar[b];
        ar[b] = temp;
    }

    public static int majorityElement(int[] arr) {
        //int arr = {3,1,4,1,3,1,1,2}  an element is in majority if it is present more than 50% of arr length
        int majorityIndex = 1;
        int count = 1;

        for (int i = 0; i < arr.length; i++) {
            if (count == 0) {
                majorityIndex = i;
                count = 1;
            }
            if (arr[majorityIndex] == arr[i]) {

                count++;
            } else {
                count--;
            }
        }
        if (count > (arr.length / 2)) {
            return majorityIndex;
        } else {
            return -1;
        }
    }

    /**
     * Two for loops are used in this function to calculate each element's
     * frequency in the array. To reduce the complexity we are not calculating
     * element's frequency which is already calculated using boolean array
     * check. Worst case complexity is order of n * n. average complexity is
     * O(n). {n/2 + n/4 + n/8 + ... = n}
     *
     * @param ar
     * @return
     */
    public static int majorityElementOne(int[] ar) {
        int n = ar.length;
        boolean[] check = new boolean[n];

        for (int i = 0; i <= n >> 1; i++) {
            if (!check[i]) {
                int v = ar[i];
                int c = 0;

                for (int j = i; j < n; j++)
                    if (ar[j] == v) {
                        c++;
                        check[j] = true;
                    }

                if (c * 2 > n)
                    return v;
            }
        }

        return -1;
    }

    /**
     * Elements are first sorted in order to calculate the frequency.
     * For sorted elements the frequency calculation is done in single for loop.
     * Complexity is O(n*lgn)
     *
     * @param ar
     * @return
     */
    public static int majorityElementTwo(int[] ar) {
        int n = ar.length;
        Arrays.sort(ar);

        int lim = ar.length >> 1;
        int count = 1;
        for (int i = 1; i < n; i++) {
            if (ar[i] == ar[i - 1])
                count++;
            else {
                if (count > lim)
                    return ar[i - 1];

                count = 1;
            }
        }

        return -1;
    }

    /**
     * Selecting random un-checked element and calculating it's frequency.
     * Complexity O(n).
     *
     * @param ar
     * @return
     */
    public static int majorityElementThree(int[] ar) {
        int n = ar.length;
        boolean[] check = new boolean[n];
        int checkCount = 0;
        int lim = n >>> 1;
        Random random = new Random();

        while (checkCount != n) {
            int i = random.nextInt(n);
            while (check[i])
                i = random.nextInt(n);

            int v = ar[i], c = 0;
            for (int j = 0; j < n; j++)
                if (ar[j] == v) {
                    c++;
                    check[j] = true;
                }

            if (c > lim)
                return v;
        }

        return -1;
    }

    public static int pattern(String s) {
        int n = s.length();
        int[] one = new int[s.length()], eight = new int[s.length()], zero =
            new int[s.length()], seven = new int[s.length()];

        one[0] = s.charAt(0) == '1' ? 1 : 0;
        zero[0] = s.charAt(0) == '0' ? 1 : 0;

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == '1')
                one[i] = one[i - 1] + 1;
            else
                one[i] = one[i - 1];

            if (s.charAt(i) == '0')
                zero[i] = zero[i - 1] + 1;
            else
                zero[i] = zero[i - 1];
        }

        eight[n - 1] = s.charAt(n - 1) == '8' ? 1 : 0;
        seven[n - 1] = s.charAt(n - 1) == '7' ? 1 : 0;

        for (int i = n - 2; i >= 0; i--) {
            if (s.charAt(i) == '8')
                eight[i] = eight[i + 1] + 1;
            else
                eight[i] = eight[i + 1];

            if (s.charAt(i) == '7')
                seven[i] = seven[i + 1] + 1;
            else
                seven[i] = seven[i + 1];
        }

        int[] one8 = new int[n];
        for (int i = 0; i < n; i++)
            one8[i] = one[i] + eight[i];

        int[] maxOne8 = new int[n];
        maxOne8[0] = 0;
        for (int i = 1; i < n; i++) {
            maxOne8[i] = Math.max(maxOne8[i - 1], one8[i]);
        }

        int[] zero7 = new int[n];
        for (int i = 0; i < n; i++)
            zero7[i] = zero[i] + seven[i];

        int[] maxZero7 = new int[n];
        maxZero7[n - 1] = 0;
        for (int i = n - 2; i >= 0; i--) {
            maxZero7[i] = Math.max(maxZero7[i + 1], zero7[i]);
        }

        int max = 0;
        for (int i = 1; i < n - 1; i++) {
            int t18 = maxOne8[i] - eight[i + 1];
            int t07 = maxZero7[i] - zero[i - 1];

            max = Math.max(t18 + t07, max);
        }

        return max;
    }

    public static long getSuminString(String s) {
        long res = 0, temp = 0;
        for (int i = 0; i < s.length(); i++) {
            if (digit[s.charAt(i)])
                temp = temp * 10 + value[s.charAt(i)];
            else {
                res += temp;
                temp = 0;
            }
        }
        return res + temp;
    }

    public static boolean isAnagram(String a, String b) {
        if (a.length() != b.length())
            return false;

        int[] map = new int[256];
        for (int i = 0; i < a.length(); i++) {
            map[a.charAt(i)]++;
            map[b.charAt(i)]--;
        }

        for (int i = 0; i < 256; i++)
            if (map[i] != 0)
                return false;

        return true;
    }

    public static String consecutiveNumbers(int[] ar) {
        if (ar.length == 1) {
            return ar[0] + "";
        }

        StringBuilder sb = new StringBuilder();

        int startIndex = 0;
        for (int i = 1; i < ar.length; i++) {
            if (ar[i] == ar[i - 1] + 1)
                continue;

            int endIndex = i - 1;
            if (startIndex == endIndex) {
                sb.append(ar[startIndex]).append(',');
            } else {
                sb.append(ar[startIndex]).append('-').append(ar[endIndex]).append(',');
            }

            startIndex = i;
        }

        int endIndex = ar.length - 1;
        if (startIndex == endIndex) {
            sb.append(ar[startIndex]).append(',');
        } else {
            sb.append(ar[startIndex]).append('-').append(ar[endIndex]).append(',');
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String consecutiveNumbersDev(int[] arr) {
        int startIndex = 0, endIndex = 0;
        boolean flag = true;
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= arr.length; i++) {
            // System.out.print(i+"**");

            if ((i < arr.length) && (arr[i] - arr[i - 1] == 1)) {
                if (flag) {
                    startIndex = i - 1;
                    flag = false;
                }
                continue;
            }

            if (!flag) {
                sb.append(arr[startIndex] + "-" + arr[i - 1] + ",");
                flag = true;
            } else
                sb.append(arr[i - 1] + ",");
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String reverseSum(String a, String b) {
        int temp = '0' + '0';
        StringBuilder sb = new StringBuilder();
        int min = a.length() > b.length() ? b.length() : a.length();
        int carry = 0;
        for (int i = 0; i < min; i++) {
            int sum = a.charAt(i) + b.charAt(i) - temp + carry;
            carry = sum / 10;
            sb.append(sum % 10);
        }

        for (int i = min; i < a.length(); i++) {
            int sum = a.charAt(i) + carry - '0';
            carry = sum / 10;
            sb.append(sum % 10);
        }

        for (int i = min; i < b.length(); i++) {
            int sum = b.charAt(i) + carry - '0';
            carry = sum / 10;
            sb.append(sum % 10);
        }
        if (carry > 0)
            sb.append(carry);

        //        StringBuilder res = new StringBuilder();
        int i = 0;
        while (i < sb.length() && sb.charAt(i) == '0')
            i++;

        return sb.substring(i);

        //        return sb.toString();
    }

    /**
     * This function reverse each word without changing their order.
     * for example the given string is "test the  bud" the returned string is
     * "tset eht  dub"
     *
     * @param s String to be formated
     * @return returns the reversed words string.
     */

    public static String reverseWords(String s) {
        int i = 0, j = 0;
        StringBuilder sb = new StringBuilder();

        while (j < s.length() && s.charAt(j) == ' ') {
            sb.append(' ');
            j++;
        }

        i = j;

        while (j < s.length() && s.charAt(j) != ' ')
            j++;

        while (i < s.length()) {
            for (int k = j - 1; k >= i; k--)
                sb.append(s.charAt(k));

            while (j < s.length() && s.charAt(j) == ' ') {
                j++;
                sb.append(' ');
            }

            i = j;
            while (j < s.length() && s.charAt(j) != ' ')
                j++;
        }

        return sb.toString();
    }

    public static String reverseWordsSingleSpace(String s) {
        int i = 0, j = 0;
        StringBuilder sb = new StringBuilder();

        while (j < s.length() && s.charAt(j) == ' ')
            j++;

        i = j;

        while (j < s.length() && s.charAt(j) != ' ')
            j++;

        while (i < s.length()) {
            for (int k = j - 1; k >= i; k--)
                sb.append(s.charAt(k));

            while (j < s.length() && s.charAt(j) == ' ') {
                j++;
            }

            sb.append(' ');
            i = j;

            while (j < s.length() && s.charAt(j) != ' ')
                j++;
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static int friendCircles(String[] friends) {
        int n = friends.length;
        boolean[] check = new boolean[n];
        int groups = 0;

        for (int i = 0; i < n; i++) {
            if (!check[i]) {
                mark(i, friends, check);
                groups++;
            }
        }

        return groups;
    }

    private static void mark(int node, String[] ar, boolean[] check) {
        if (check[node] || node == ar.length)
            return;

        check[node] = true;

        for (int i = node + 1; i < ar.length; i++)
            if (ar[node].charAt(i) == 'Y')
                mark(i, ar, check);
    }
}
