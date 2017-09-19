/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.sept;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Sereja and Commands
 * Link: https://www.codechef.com/SEPT17/problems/SEACO
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class SerejaAndCommands {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int SINGLE = 1, BULK = 2, MOD = 1000000007;
    private static long[] powerArray = new long[1000000];

    static {
        powerArray[0] = 1;
        for (int i = 1; i < powerArray.length; i++)
            powerArray[i] = (powerArray[i - 1] << 1) % MOD;
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

            int n = in.readInt(), q = in.readInt();
            Command[] commands = new Command[q];

            for (int i = 0; i < q; i++)
                commands[i] = new Command(in.readInt(), in.readInt() - 1, in.readInt() - 1);

            long[] ar = process(commands, n);
            out.println(toString(ar));
        }
    }

    private static long[] process(Command[] commands, int size) {
        long[] ar = new long[size];
        process(commands);
        for (Command command : commands) {
            if (command.type == SINGLE) {
                ar[command.start] += command.data;
                if (command.end < size - 1)
                    ar[command.end + 1] += MOD - command.data;
            }
        }

        long value = 0;
        for (int i = 0; i < size; i++) {
            value = (value + ar[i]) % MOD;
            ar[i] = value;
        }

        return ar;
    }

    private static void process(Command[] commands) {
        int len = commands.length;
        long value = 0;
        for (int i = len - 1; i >= 0; i--) {
            Command command = commands[i];
            value = (value + command.data) % MOD;
            command.data = value + 1;
            if (command.type == BULK) {
                Command end = commands[command.end];
                end.data += command.data;

                if (command.start > 0) {
                    Command start = commands[command.start - 1];
                    start.data += MOD - command.data;
                }
            }
        }
    }

    private static String toString(long[] ar) {
        int len = ar.length;
        StringBuilder sb = new StringBuilder(len << 3);
        for (long e : ar)
            sb.append(e).append(' ');

        return sb.toString();
    }

    final static class Command {
        final int type, start, end;
        long data = 0;

        Command(int type, int start, int end) {
            this.type = type;
            this.start = start;
            this.end = end;
        }

        public String toString() {
            return start + "->" + end + " type: " + type + ": " + data;
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
    }
}