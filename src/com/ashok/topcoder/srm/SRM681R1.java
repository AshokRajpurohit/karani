package com.ashok.topcoder.srm;


import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Contest ID: SRM 681 Round I
 * Link: https://community.topcoder.com/stat?c=round_overview&er=5&rd=16651
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class SRM681R1 {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        SRM681R1 a = new SRM681R1();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        ExplodingRobots obj = new ExplodingRobots();
        while (true) {
            out.println(obj.canExplode(in.readInt(), in.readInt(),
                                       in.readInt(), in.readInt(), in.read()));
            out.flush();
        }
    }

    class CoinFlipsDiv2 {
        public int countCoins(String state) {
            if (state.length() == 1)
                return 0;

            int count = 0;
            for (int i = 1; i < state.length() - 1; i++) {
                if (state.charAt(i) != state.charAt(i - 1) ||
                    state.charAt(i + 1) != state.charAt(i))
                    count++;
            }

            if (state.charAt(state.length() - 1) !=
                state.charAt(state.length() - 2))
                count++;

            if (state.charAt(0) != state.charAt(1))
                count++;

            return count;
        }
    }

    class ExplodingRobots {
        public String canExplode(int x1, int y1, int x2, int y2,
                                 String instructions) {
            String safe = "Safe", unsafe = "Explosion";

            if (check(x1, y1, x2, y2, instructions))
                return safe;

            return unsafe;
        }

        private boolean check(int x, int y, int a, int b,
                              String instructions) {
            int ftop = y, fbot = y, fleft = x, fright = x;
            int stop = b, sbot = b, sleft = a, sright = a;

            for (int i = 0; i < instructions.length(); i++) {
                if (instructions.charAt(i) == 'U') {
                    ftop++;
                    stop++;
                } else if (instructions.charAt(i) == 'D') {
                    fbot--;
                    sbot--;
                } else if (instructions.charAt(i) == 'L') {
                    fleft--;
                    sleft--;
                } else {
                    fright++;
                    sright++;
                }
            }

            return !overlap(ftop, fbot, fleft, fright, stop, sbot, sleft,
                           sright);
        }

        private boolean overlap(int ftop, int fbot, int fleft, int fright,
                                int stop, int sbot, int sleft, int sright) {
            if (ftop < sbot || stop < fbot || fright < sleft || sright < fleft)
                return false;

            return true;
        }
    }
}
