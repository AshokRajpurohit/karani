/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.dec;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Penalty Shoot-out
 * Link: https://www.codechef.com/DEC17/problems/CPLAY
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class PenaltyShootOut {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int SCORE = '1', MISS = '0';
    private static final String TEAM_A = "TEAM-A", TEAM_B = "TEAM-B", TIE = "TIE";

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            while (true) {
                process(sb, in.read(20).toCharArray());
            }
        } catch (Exception e) {
            // do nothing.
        } finally {
            out.print(sb);
        }

    }

    private static void process(StringBuilder sb, char[] shots) {
        int teamA = 0, teamB = 0, remainingA = 5, remainingB = 5;
        int index = 0;
        while (index < 10) {
            teamA += getScore(shots[index++]);
            if (win(teamA - teamB, --remainingA, remainingB))
                break;

            teamB += getScore(shots[index++]);
            if (win(teamA - teamB, remainingA, --remainingB))
                break;
        }

        if (teamA == teamB) { // move to sudden death step
            while (index < 20) {
                teamA += getScore(shots[index++]);
                teamB += getScore(shots[index++]);

                if (teamA != teamB)
                    break;
            }
        }

        if (teamA > teamB)
            sb.append(TEAM_A).append(' ').append(index);
        else if (teamA < teamB)
            sb.append(TEAM_B).append(' ').append(index);
        else
            sb.append(TIE);

        sb.append('\n');
    }

    private static int getScore(char shot) {
        return shot == MISS ? 0 : 1;
    }

    private static boolean win(int diff, int shotsA, int shotsB) {
        return diff + shotsA < 0 || diff - shotsB > 0;
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