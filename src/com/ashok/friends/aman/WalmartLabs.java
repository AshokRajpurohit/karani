package com.ashok.friends.aman;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Walmart Labs July 30 Test
 * Link: email link
 */
public class WalmartLabs {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        String number = in.read();
        int swaps = in.readInt();

        out.println(MakeItLarge.solve(number.toCharArray(), swaps));
    }

    /**
     * Its your life make it large
     * Given a large number N, convert it into a larger number as much as possible
     * by swapping adjacent digits in the number.
     * <p>
     * You will be given a limit on the number of swaps that can be done, provide an algorithm
     * that can convert it to the largest number possible with the limited swaps available.
     * <p>
     * Constraints: 0<=N<=1000000000000
     * <p>
     * Sample Input
     * 2519372893
     * 6
     * <p>
     * Sample Output
     * 9532172893
     * <p>
     * Explanation
     * Original: 2519372893 Step 1: 2591372893 Step 2: 2951372893 Step 3: 9251372893
     * Step 4: 9521372893 Step 5: 9523172893 Step 6: 9532172893
     */
    final static class MakeItLarge {
        final static char invalid = 'a'; // this is not a digit.
        final static long[] factorials = new long[15];

        static {
            factorials[0] = 1;

            for (int i = 1; i < factorials.length; i++)
                factorials[i] = i * factorials[i - 1];
        }

        public static String solve(char[] number, int maxSwaps) {
            if (largestPermutation(number))
                return String.valueOf(number);

            if (maxSwaps(number.length) <= maxSwaps)
                return reverseSort(number);

            StringBuilder res = new StringBuilder(number.length);
            for (int i = 0; i < number.length; i++) {
                if (number[i] != '9')
                    break;

                res.append(number[i]);
                number[i] = invalid;
            }

            for (int i = 0; i < number.length && maxSwaps > 0; i++) {
                if (number[i] != invalid) {
                    while (true) { // let's do it untill we are unable to find it's replacement.
                        int j = i + 1, k = i;
                        int temp = maxSwaps;

                        while (temp > 0 && j < number.length) {
                            if (number[j] != invalid) {
                                if (number[j] > number[k])
                                    k = j;

                                temp--;
                            }

                            j++;
                        }

                        if (k > i) { // we found a replacement for character at index i.
                            res.append(number[k]);
                            maxSwaps -= distance(number, i, k);
                            number[k] = invalid;
                            continue;
                        }

                        res.append(number[i]);
                        number[i] = invalid;

                        break;
                    }
                }
            }

            for (char ch : number)
                if (ch != invalid)
                    res.append(ch);

            return res.toString();
        }

        private static int maxSwaps(int length) {
            if (length < 3)
                return length - 1;

            return length * (length - 1) / 2;
        }

        private static int distance(char[] number, int i, int j) {
            i++;
            int distance = 0;

            while (i <= j) {
                if (number[i] != invalid)
                    distance++;

                i++;
            }

            return distance;
        }

        private static boolean largestPermutation(char[] ar) {
            for (int i = 1; i < ar.length; i++)
                if (ar[i] > ar[i - 1])
                    return false;

            return true;
        }

        private static String reverseSort(char[] ar) {
            Arrays.sort(ar);

            for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
                char t = ar[i];
                ar[i] = ar[j];
                ar[j] = t;
            }

            return String.valueOf(ar);
        }
    }

    /**
     * Compatibility difference among two friends
     * Two friends Kohli and Dhoni want to test their friendship to check how compatible they are.
     * To find the compatibilty difference amongst them they approached a programmer.
     * Programmer gave them a list of n movies numbered 1,2,3....n and asked both of them to rank the movies.
     * <p>
     * Assuming you are that programer,design an algorithm to find compatibility difference between them.
     * <p>
     * Definition of compatibilty difference:
     * <p>
     * Compatibility difference is the number of mis-matches in the relative rankings of the same movie
     * givem by them i.e. if Kohli ranks Movie3 before Movie2 and Dhoni ranks Movie2 before Movie3 then
     * its a relative ranking mis-match
     * <p>
     * Output the total number of compatibility difference.
     * <p>
     * Constraints: 0<N<1000
     * <p>
     * Sample Input
     * 5
     * 3 1 2 4 5
     * 3 2 4 1 5
     * <p>
     * Sample Output
     * 2
     * <p>
     * Explanation
     * Movies are 1,2,3,4,5. Kohli ranks them 3,1,2,4,5,
     * Dhoni ranks them 3,2,4,1,5. Compatibility differene is 2 because
     * Kohli ranks movie 1 before 2,4 but Dhoni ranks it after.
     */
    final static class CompatibilityDifference {
        public static int solve(int[] dhoni, int[] kohli) {
            // as test data is small, let's use naive way.
            int difference = 0;
            int[] map = new int[dhoni.length + 1];

            for (int i = 0; i < dhoni.length; i++)
                map[dhoni[i]] = i;

            for (int i = 0; i < kohli.length; i++) {
                for (int j = i + 1; j < kohli.length; j++) {
                    if (map[kohli[i]] > map[kohli[j]]) // dhoni ranked opposite to kohli, oh that's a difference.
                        difference++;
                }
            }

            return difference;
        }
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

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
