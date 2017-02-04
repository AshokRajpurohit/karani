/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.jan;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * Problem Name: Digits Cannot Separate
 * Link: https://www.codechef.com/JAN17/problems/DIGITSEP
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class DigitsCannotSeparate {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[] digits = new int[256];

    static {
        for (int i = '1', j = 1; i <= '9'; i++, j++)
            digits[i] = j;
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            String s = in.read(n);
            int m = in.readInt(), x = in.readInt(), y = in.readInt();
            out.println(process(s, m, x, y));
        }
    }

    private static long process(String s, int m, int x, int y) {
        Track track = new Track(s.toCharArray(), m, x, y);
        return track.operate();
    }

    private static boolean allZeroes(char[] track) {
        for (char ch : track)
            if (digits[ch] != 0)
                return false;

        return true;
    }

    private static long getNumber(char[] ar, int start, int length) {
        long num = 0;

        for (int i = 0, j = start; i < length; i++, j++)
            num = (num << 3) + (num << 1) + digits[ar[j]];

        return num;
    }

    /**
     * Adds one more digit to number {@code num} at the end (Lowest Significant Digit) and returns the new number.
     * This is like num = num * 10 + digit.
     *
     * @param num
     * @param digit
     * @return
     */
    private static long appendDigitToNumber(long num, char digit) {
        return (num << 3) + (num << 1) + digits[digit];
    }

    private static long gcd(long a, long b) {
        if (a == 0)
            return b;

        return gcd(b % a, a);
    }

    final static class Track {
        private final int maxDigits, minSeperators, maxSeperators, length;
        private long currentGCD = 1; // minimum gcd is 1.
        private final char[] notes;

        // stores gcd values (so far) for digit d and segment s at index (d, s).
        // If we come to the same point with same segment number and same gcdSoFar (with obviously different combination)
        // We can skip the calculation as the latter part of track is going to give us the same result.
        // This cache is initialized on need basis.
        private Set<Long>[][] cache;

        Track(char[] track, int m, int x, int y) {
            maxDigits = m;
            minSeperators = x;
            maxSeperators = y;
            notes = track;
            length = track.length;
        }

        private long operate() {
            if (allZeroes(notes))
                return 0;

            cache = new HashSet[length][maxSeperators + 2];

            if (maxDigits * (minSeperators + 1) == length)
                return useAllSeperators();

            int max = getMaxDigits(1, 0), min = getMinDigits(1, 0);
            long currentNumber = getNumber(notes, 0, min - 1);
            for (int i = min - 1; i < max; i++) {
                currentNumber = appendDigitToNumber(currentNumber, notes[i]);
                operate(i + 1, 1, currentNumber);
            }

            return currentGCD;
        }

        /**
         * Starts at index {@code noteIndex} to form a new segment and takes it's gcd with {@code gcdSoFar}.
         * If gcdSoFar is 1 or noteIndex and segment counts are such that no valid segments can be created, it stops
         * at that moment.
         * Recursively it calls the same method with new gcdSoFar (with current segment number) and updates track gcd.
         * <p>
         * Please note that the inputs for this method are {@code noteIndex, segements, gcdSoFar} and it doesn't care
         * about the previous combinations of digits (partition). So for the same set of parameters, it is going to
         * give us the same result. So we can cache the input parameters to check whether it has already been calculated.
         * <p>
         * We knonw gcd is always decresing (except when it is zero), so there are not many {@code gcdSoFar} for each
         * set of {@code noteIndex} and {@code segements}.
         *
         * @param noteIndex
         * @param segments
         * @param gcdSoFar
         */
        private void operate(int noteIndex, int segments, long gcdSoFar) {
            if (!possible(noteIndex, segments) || gcdSoFar == 1)
                return;

            if (gcdSoFar != 0 && gcdSoFar <= currentGCD)
                return;

            if (noteIndex == length) {
                if (segments <= minSeperators)
                    return;

                currentGCD = Math.max(currentGCD, gcdSoFar);
                return;
            }

            if (cached(noteIndex, segments + 1, gcdSoFar))
                return;

            addToCache(noteIndex, segments + 1, gcdSoFar);
            cache[noteIndex][segments + 1].add(gcdSoFar);
            int max = getMaxDigits(segments + 1, noteIndex), min = getMinDigits(segments + 1, noteIndex);

            long currentNumber = getNumber(notes, noteIndex, min - 1);
            for (int i = noteIndex + min - 1, j = min - 1; j < max && i < length; j++, i++) {
                currentNumber = appendDigitToNumber(currentNumber, notes[i]);
                long gcd = gcd(gcdSoFar, currentNumber);
                operate(i + 1, segments + 1, gcd);
            }
        }

        private boolean cached(int i, int j, long v) {
            return cache[i][j] != null && cache[i][j].contains(v);
        }

        private void addToCache(int i, int j, long v) {
            ensureInitialized(i, j);
            cache[i][j].add(v);
        }

        private void ensureInitialized(int i, int j) {
            if (cache[i][j] != null)
                return;

            cache[i][j] = new HashSet<>();
        }

        /**
         * Returns true if going forward in this combination is going to give us any valid result.
         * This is checked
         * <p>
         * 1. when we use minimum number of segments minimum digits (read One) and it does not exceed remaining digits.
         * 2. When we use all the remaining segments with maximum digits and it is not less than remaining digits.
         *
         * @param index     current digit index for new segment.
         * @param segements number of segments already created prior to current digit (@code index)
         * @return true if a valid partition is possible.
         */
        private boolean possible(int index, int segements) {
            int remainingLength = length - index, remainingSegements = maxSeperators + 1 - segements;
            return remainingLength <= maxDigits * remainingSegements && remainingLength >= minSeperators + 1 - segements;
        }

        /**
         * Maximum digits current segment can have, so that all the remaining segments can have at least one digit for
         * minimum number of segments.
         *
         * @param currentSegment
         * @param currentIndex
         * @return
         */
        private int getMaxDigits(int currentSegment, int currentIndex) {
            return Math.min(maxDigits, length - currentIndex - (minSeperators + 1 - currentSegment)); // when remaining tracks are of 1 length.
        }

        /**
         * Returns minimum number of digits, the current segment should have in order to have valid segments with size
         * not exceeding {@link #maxDigits} when we use all the seperators.
         *
         * @param currentSegment current segment to be cut off.
         * @param currentIndex   start index for current segment.
         * @return minimum number of digits current segments should have.
         */
        private int getMinDigits(int currentSegment, int currentIndex) {
            return Math.max(1, length - currentIndex - (maxSeperators + 1 - currentSegment) * maxDigits);
        }

        /**
         * This method is called when there is only one possible way to put seperators, e.g. when each segment is
         * of same length (maxDigits) and have to use all the seperators.
         *
         * @return
         */
        private long useAllSeperators() {
            int index = 0;
            long gcd = 0;

            while (index < length && gcd != 1) {
                gcd = gcd(gcd, getNumber(notes, index, maxDigits));
                index += maxDigits;
            }

            return gcd;
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

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
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
            for (int i = 0; offset < bufferSize && i < n; ++offset) {
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
