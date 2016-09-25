package com.ashok.hackerearth.marathon.sept16;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Fredo and Large Numbers
 * Link: https://www.hackerearth.com/september-circuits/algorithm/fredo-and-large-numbers/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class FredoAndLargeNumbers {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        FredoNumber[] fredoNumbers = new FredoNumber[n];

        for (int i = 0; i < n; i++)
            fredoNumbers[i] = new FredoNumber(i, in.readLong());

        Arrays.sort(fredoNumbers);
        for (int i = n - 2; i >= 0; i--) {
            if (fredoNumbers[i].number == fredoNumbers[i + 1].number)
                fredoNumbers[i].frequency += fredoNumbers[i + 1].frequency;
        }

        for (int i = 1; i < n; i++) {
            if (fredoNumbers[i].number == fredoNumbers[i - 1].number)
                fredoNumbers[i].frequency = fredoNumbers[i - 1].frequency;
        }

        sort(fredoNumbers);
        int maxFreq = 1;
        for (FredoNumber fredoNumber : fredoNumbers)
            maxFreq = Math.max(maxFreq, fredoNumber.frequency);

        int[] frequencyIndexMap = new int[maxFreq + 1];
        Arrays.fill(frequencyIndexMap, -1);

        for (FredoNumber fredoNumber : fredoNumbers) {
            int f = fredoNumber.frequency;
            if (frequencyIndexMap[f] == -1) {
                frequencyIndexMap[f] = fredoNumber.index;
            }
        }

        int min = frequencyIndexMap[maxFreq];
        for (int i = maxFreq - 1; i >= 0; i--) {
            if (frequencyIndexMap[i] == -1) {
                frequencyIndexMap[i] = min;
            } else
                min = Math.min(min, frequencyIndexMap[i]);
        }

        int[] minFrequencyMap = new int[maxFreq + 1];
        minFrequencyMap[maxFreq] = frequencyIndexMap[maxFreq];

        for (int i = maxFreq - 1; i >= 0; i--) {
            minFrequencyMap[i] = Math.min(frequencyIndexMap[i], minFrequencyMap[i + 1]);
        }

        int q = in.readInt();
        StringBuilder sb = new StringBuilder(q << 2);

        while (q > 0) {
            q--;
            int type = in.readInt();
            long freq = in.readLong();
            if (freq == 0 || freq > maxFreq) {
                sb.append(0).append('\n');
                continue;
            }

            int index = type == 0 ? minFrequencyMap[(int) freq] : frequencyIndexMap[(int) freq];
            if (type == 1 && fredoNumbers[index].frequency != freq) {
                sb.append(0).append('\n');
                continue;
            }

            sb.append(fredoNumbers[index].number).append('\n');
        }

        out.print(sb);
    }

    private static void sort(FredoNumber[] fredoNumbers) {
        FredoNumber[] map = new FredoNumber[fredoNumbers.length];

        for (FredoNumber fredoNumber : fredoNumbers)
            map[fredoNumber.index] = fredoNumber;

        for (int i = 0; i < fredoNumbers.length; i++)
            fredoNumbers[i] = map[i];
    }

    final static class FredoNumber implements Comparable<FredoNumber> {
        long number;
        int index, frequency = 1;

        FredoNumber(int index, long number) {
            this.index = index;
            this.number = number;
        }

        @Override
        public int compareTo(FredoNumber o) {
            if (number == o.number)
                return index - o.index;

            return Long.compare(number, o.number);
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

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }
    }
}
