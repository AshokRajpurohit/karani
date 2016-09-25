package com.ashok.topcoder.contest;

/**
 * Single Round Match 698 Sponsored by Google
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class SRM698 {
    public static class Initials {
        public static String getInitials(String s) {
            StringBuilder sb = new StringBuilder();

            sb.append(s.charAt(0));
            for (int i = 1; i < s.length(); i++) {
                if (s.charAt(i) != ' ')
                    continue;

                i++;
                sb.append(s.charAt(i));
            }

            return sb.toString();
        }
    }

    public static class RepeatStringEasy {
        static boolean[][] computed;
        public static int maximalLength(String s) {
            if (s.length() == 1)
                return 0;

            if (s.length() == 2)
                return s.charAt(0) == s.charAt(1) ? 2 : 0;

            if (square(s))
                return s.length();

            return process(s.toCharArray());
        }

        private static boolean square(String s) {
            if ((s.length() & 1) == 1)
                return false;

            int i = 0, j = s.length() >>> 1;
            while (j != s.length()) {
                if (s.charAt(i) != s.charAt(j))
                    return false;

                i++;
                j++;
            }

            return true;
        }

        private static int process(char[] ar) {
            int max = 0;

            for (int i = 0; i < ar.length - max; i++) {
                max = Math.max(process(ar, i), max);
            }

            return max;
        }

        private static int process(char[] ar, int index) {
            int max = 0, len = 0;

            for (int i = index + 1; i < ar.length - len; i++) {
                max = Math.max(max, process(ar, index, i));
                len = max >>> 1;
            }

            return max;
        }

        private static int process(char[] ar, int first, int second) {
            computed = new boolean[ar.length][ar.length];
            if (ar[first] != ar[second])
                return 0;

            int len = 2;
            len = process(ar, first + 1, second + 1, second, len);

            return len;
        }

        private static int process(char[] ar, int firstEnd, int secondEnd, int firstLimit, int length) {
            if (firstEnd == firstLimit || secondEnd == ar.length || computed[firstEnd][secondEnd])
                return length;

            computed[firstEnd][secondEnd] = true;

            if (ar[firstEnd] == ar[secondEnd])
                return process(ar, firstEnd + 1, secondEnd + 1, firstLimit, length + 2);

            return Math.max(process(ar, firstEnd + 1, secondEnd, firstLimit, length),
                    process(ar, firstEnd, secondEnd + 1, firstLimit, length));
        }
    }
}
