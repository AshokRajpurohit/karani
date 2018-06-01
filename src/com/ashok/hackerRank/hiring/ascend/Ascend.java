/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerRank.hiring.ascend;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Stack;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Ascend {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        while (true) {
            int n = in.readInt(), g = in.readInt();
            int[] or = in.readIntArray(in.readInt()), dest = in.readIntArray(in.readInt());
            out.print(TravellingFun.connectedCities(n, g, or, dest));
//            out.println(ZombieClusters.zombieCluster(in.readLineArray(in.readInt())));
            out.flush();
        }
    }

    final static class Braces {
        private static final BraceType[] BRACE_MAP = new BraceType[256];
        private static final char[] COMPLEMENTARY_BRACE = new char[256];
        private static final String YES = "YES", NO = "NO";

        static {
            char[] openBraces = "({[".toCharArray(), closedBraces = ")}]".toCharArray();
            for (char openBrace : openBraces)
                BRACE_MAP[openBrace] = BraceType.OPEN;

            for (char closedBrace : closedBraces)
                BRACE_MAP[closedBrace] = BraceType.CLOSE;

            for (int i = 0; i < 3; i++) {
                COMPLEMENTARY_BRACE[openBraces[i]] = closedBraces[i];
                COMPLEMENTARY_BRACE[closedBraces[i]] = openBraces[i];
            }
        }

        static String[] braces(String[] values) {
            int n = values.length;
            String[] res = new String[n];
            for (int i = 0; i < n; i++)
                res[i] = braces(values[i].toCharArray());

            return res;
        }

        private static String braces(char[] braces) {
            Stack<Character> stack = new Stack<>();
            for (char brace : braces) {
                if (BRACE_MAP[brace] == BraceType.OPEN)
                    stack.push(brace);
                else {
                    if (stack.isEmpty())
                        return NO;

                    char top = stack.pop();
                    if (COMPLEMENTARY_BRACE[top] != brace)
                        return NO;
                }
            }

            return stack.isEmpty() ? YES : NO;
        }
    }

    final static class ZombieClusters {
        final static int CONNECTED = '1';

        static int zombieCluster(String[] zombies) {
            int n = zombies.length;
            int[] groups = new int[n];
            int groupId = 1;
            char[][] zombieConnections = toCharArrays(zombies);
            for (int i = 0; i < n; i++) {
                if (groups[i] != 0)
                    continue;

                groups[i] = groupId;
                populateGroups(groups, zombieConnections, i, groupId);
                groupId++;
            }

            return groupId - 1;
        }

        private static void populateGroups(int[] groups, char[][] connections, int zombieId, int groupId) {
            int zombie = 0;
            for (char connection : connections[zombieId]) {
                if (connection == CONNECTED && groups[zombie] == 0) {
                    groups[zombie] = groupId;
                    populateGroups(groups, connections, zombie, groupId);
                }

                zombie++;
            }
        }

        private static char[][] toCharArrays(String[] ar) {
            int n = ar.length;
            char[][] chars = new char[n][];
            for (int i = 0; i < n; i++)
                chars[i] = ar[i].toCharArray();

            return chars;
        }
    }

    final static class TravellingFun {
        static int[] connectedCities(int n, int g, int[] originCities, int[] destinationCities) {
            int len = originCities.length;
            int[] ar = new int[len];
            for (int i = 0; i < len; i++)
                ar[i] = connected(originCities[i], destinationCities[i], g) ? 1 : 0;

            return ar;
        }

        private static boolean connected(int a, int b, int threshold) {
            if (threshold == 1 && Math.min(a, b) == 1)
                return false;

            if (threshold == 0)
                return true;

            if (Math.min(a, b) < threshold)
                return false;

            return gcd(a, b) >= threshold;
        }

        private static int gcd(int a, int b) {
            if (a % b == 0)
                return b;

            return gcd(b, a % b);
        }
    }

    enum BraceType {
        OPEN, CLOSE
    }
}
