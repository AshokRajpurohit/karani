package com.ashok.topcoder.srm;


import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Contest ID: SRM 680 DIV 2
 * Link: https://community.topcoder.com/stat?c=round_overview&rd=16650
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class SRM680 {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        SRM680 a = new SRM680();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        BearChairs bear = new BearChairs();
        while (true) {
            int n = in.readInt();
            out.println(bear.findPositions(in.readIntArray(n), in.readInt()));
            out.flush();
        }
    }

    public class BearChairs {
        public int[] findPositions(int[] atLeast, int d) {
            LinkedList<Integer> list = new LinkedList<Integer>();
            int[] res = new int[atLeast.length];

            res[0] = atLeast[0];
            list.add(atLeast[0]);

            for (int i = 1; i < atLeast.length; i++) {
                int pos = findPos(list, atLeast[i], d);
                insert(list, pos);
                res[i] = pos;
            }

            return res;
        }

        private int findPos(LinkedList<Integer> list, int atLeast, int d) {
            Iterator<Integer> iter = list.iterator();
            int pos;
            int cur = 0, prev = Integer.MIN_VALUE / 2;

            while (iter.hasNext()) {
                cur = iter.next();
                if (cur < atLeast) {
                    prev = cur;
                    continue;
                }

                if (cur >= atLeast + d && cur - prev >= 2 * d) {
                    int a = prev + d, b = cur - d;
                    if (a < atLeast) {
                        return atLeast;
                    }

                    pos = prev + d;
                    return pos;
                }
                prev = cur;
            }

            pos = Math.max(atLeast, cur + d);
            return pos;
        }

        private void insert(LinkedList<Integer> list, int n) {
            if (n < list.getFirst()) {
                list.addFirst(n);
                return;
            }

            if (n > list.getLast()) {
                list.addLast(n);
                return;
            }

            int cur = 0, i = 0;
            Iterator<Integer> iter = list.iterator();

            while (iter.hasNext()) {
                cur = iter.next();
                if (cur > n) {
                    list.add(i, n);
                    return;
                }
                i++;
            }
        }
    }

    public class BearPair {
        public int bigDistance(String s) {
            int res = 0;
            for (int i = 1; i < s.length(); i++) {
                if (s.charAt(i) != s.charAt(0))
                    res = i;
            }

            if (res == 0)
                return -1;

            for (int i = 0; i < s.length() - 1; i++) {
                if (s.charAt(i) != s.charAt(s.length() - 1))
                    res = Math.max(res, s.length() - i - 1);
            }

            return res;
        }
    }
}
