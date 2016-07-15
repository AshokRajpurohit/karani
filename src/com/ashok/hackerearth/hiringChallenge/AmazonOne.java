package com.ashok.hackerearth.hiringChallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem Name: Amazon Hiring Challenge
 * Link: https://www.hackerearth.com/amazon-hiring-challenge-1
 * Date: 10th July 2016
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AmazonOne {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int mod = 1000000007;

    public static void main(String[] args) throws IOException {
        AmazonOne a = new AmazonOne();
        a.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        VasyaParty.solve();
    }

    final static class VasyaParty {
        public static void solve() throws IOException {
            int n = in.readInt(), m = in.readInt();
            int[] knowledge = in.readIntArray(n);
            if (m == 0) {
                out.println(1);
                return;
            }

            LinkedList<Integer>[] relationList = (LinkedList<Integer>[])
                    Array.newInstance((new LinkedList<Integer>()).getClass(), n);

            for (int i = 0; i < m; i++) {
                populate(in.readInt() - 1, in.readInt() - 1, relationList);
            }

            int[] group = new int[n];
            Arrays.fill(group, -1);
            int groupId = 0;

            LinkedList<Integer>[] groupMembers = (LinkedList<Integer>[])
                    Array.newInstance((new LinkedList<Integer>()).getClass(), n);

            fillGroups(group, groupMembers, relationList);

            long res = 1L;
            for (int i = 0; i < n; i++) {
                if (groupMembers[i] == null)
                    break;

                res = res * getMaxCount(groupMembers[i], knowledge) % mod;
            }

            out.println(res);
        }

        private static int getMaxCount(LinkedList<Integer> groupMembers,
                                       int[] ar) {
            int max = -1, count = 0;
            for (int e : groupMembers)
                max = Math.max(max, ar[e]);

            for (int e : groupMembers)
                if (ar[e] == max)
                    count++;

            return count;
        }

        private static void fillGroups(int[] group, LinkedList<Integer>[]
                groupMembers, LinkedList<Integer>[] relationList) {
            int groupId = 0;
            int n = group.length;

            for (int i = 0; i < n; i++) {
                if (group[i] != -1)
                    continue;

                LinkedList<Integer> queue = new LinkedList<>();
                queue.add(i);
                group[i] = groupId;
                ensure(groupMembers, groupId);
                groupMembers[groupId].add(i);

                while (queue.size() != 0) {
                    int top = queue.pollFirst();

                    if (relationList[top] == null)
                        break;

                    for (int e : relationList[top]) {
                        if (group[e] != -1)
                            continue;

                        group[e] = groupId;
                        queue.addLast(e);
                        groupMembers[groupId].add(e);
                    }
                }

                groupId++;
            }
        }

        private static void populate(int u, int v, LinkedList<Integer>[]
                relationList) {
            ensure(relationList, u);
            ensure(relationList, v);
            relationList[u].add(v);
            relationList[v].add(u);
        }

        private static void ensure(LinkedList<Integer>[] list, int index) {
            if (list[index] == null)
                list[index] = new LinkedList<Integer>();
        }
    }

    final static class MillyChocolates {
        public static void solve() throws IOException {
            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            int q = in.readInt();

            int sum = 0;
            for (int e : ar)
                sum += e;

            // No need to use log(n) searching when we can get in O(1).
            int[] indices = new int[sum + 1];
            int index = 1;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < ar[i]; j++) {
                    indices[index++] = i;
                }
            }

            StringBuilder sb = new StringBuilder(q << 3);
            while (q > 0) {
                q--;
                sb.append(indices[in.readInt()] + 1).append('\n');
            }

            out.print(sb);
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
    }
}
