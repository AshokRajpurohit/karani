package com.ashok.topcoder.srm;


import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;

/**
 * Problem Name:
 * Contest ID:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class SRM679 {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        SRM679 a = new SRM679();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        ListeningSongs songs = new ListeningSongs();
        while (true) {
            int n = in.readInt();
            int count =
                songs.listen(in.readIntArray(n), in.readIntArray(n), in.readInt(),
                             in.readInt());
            out.println(count);
            out.flush();
        }
    }

    public class ListeningSongs {
        public int listen(int[] durations1, int[] durations2, int minutes,
                          int T) {
            if (T >= durations1.length)
                return -1;

            Arrays.sort(durations1);
            Arrays.sort(durations2);

            int count = 0, time = 0, total = minutes * 60;
            for (int i = 0; i < T; i++) {
                time += durations1[i] + durations2[i];

                if (time <= total)
                    count += 2;
            }

            int i = T, j = T;
            while (time <= total && i < durations1.length &&
                   j < durations1.length) {
                if (durations1[i] < durations2[j]) {
                    time += durations1[i];
                    i++;
                } else {
                    time += durations2[j];
                    j++;
                }

                if (time <= total)
                    count++;
            }

            while (time <= total && i < durations1.length) {
                time += durations1[i];
                i++;
                if (time <= total)
                    count++;
            }

            while (time <= total && j < durations1.length) {
                time += durations1[j];
                j++;
                if (time <= total)
                    count++;
            }

            if (count < T)
                return -1;

            return count;
        }
    }
}
