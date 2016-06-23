package com.ashok.codechef.marathon.year15.NOV15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem: Receipt Recovery
 * https://www.codechef.com/NOV15/problems/RECRECOV
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class RECRECOV {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        RECRECOV a = new RECRECOV();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            int n = in.readInt(), m = in.readInt();
            int[] from = new int[m], to = new int[m];

            for (int i = 0; i < m; i++) {
                from[i] = in.readInt();
                to[i] = in.readInt();
            }

            sb.append(goEasy(from, to, n)).append('\n');
        }
        out.print(sb);
    }

    private static int process(int[] from, int[] to, int n) {
        return goEasy(from, to, n);
        //        return 563289;
    }

    private static int goEasy(int[] from, int[] to, int n) {
        if (from.length == 1)
            return 1;

        int m = from.length, max = 0, count = from.length;

        //        LinkedList<Tuple> list = new LinkedList<Tuple>();
        //        for (int i = 0; i < m; i++)
        //            list.add(new Tuple(from[i], to[i]));

        while (count > 0) {
            int rc = 0, lc = 0;
            boolean[] root = new boolean[n + 1], leaf =
                new boolean[n + 1], uar = new boolean[n + 1];
            for (int i = 1; i <= n; i++) {
                root[i] = true;
                leaf[i] = true;
            }

            /*
            Iterator<Tuple> iter = list.iterator();
            while (iter.hasNext()) {
                Tuple temp = iter.next();
                int a = temp.from, b = temp.to;
                root[b] = false;
                leaf[a] = false;
                uar[a] = true;
                uar[b] = true;
            }
            */

            for (int i = 0; i < m; i++) {
                int a = from[i], b = to[i];
                root[b] = false;
                leaf[a] = false;
                uar[a] = true;
                uar[b] = true;
            }


            for (int i = 1; i <= n; i++) {
                if (uar[i] && root[i])
                    rc++;

                if (uar[i] && leaf[i])
                    lc++;
            }

            max = Math.max(max, Math.max(rc, lc));

            /*
            iter = list.iterator();
            while (iter.hasNext()) {
                Tuple temp = iter.next();
                if (root[temp.from] || leaf[temp.to])
                    iter.remove();
            }
            */


            for (int i = 0; i < m; i++) {
                if (root[from[i]] || leaf[to[i]]) {
                    count--;
                    from[i] = 0;
                    to[i] = 0;
                }
            }

        }

        return max;
        //        return 563289;
    }

    private static int listWay(int[] from, int[] to, int n) {
        LinkedList<Integer>[] list = new LinkedList[n];
        for (int i = 0; i < n; i++)
            list[i] = new LinkedList<Integer>();

        for (int i = 0; i < from.length; i++)
            list[from[i] - 1].add(to[i] - 1);

        return BFS(list);
        //        return 563289;
    }

    private static void indegree(boolean[][] ar) {
        int n = ar.length;
        int[] degree = new int[n], order = new int[n];
        LinkedList<Integer> queue = new LinkedList<Integer>();
        boolean[] uar = new boolean[n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (ar[i][j]) {
                    uar[i] = true;
                    uar[j] = true;
                }

        for (int i = 0; i < n; i++)
            if (uar[i])
                queue.add(i);

        int counter = 0;
        //        for (int i = 0; i < n; i++) {
        //            boolean root = true;
        //            for (int j = 0; j < n && root; j++) {
        //                if (ar[j][i])
        //                    root = false;
        //            }
        //
        //            if (root)
        //                queue.add(i);
        //        }

        while (!queue.isEmpty()) {
            int v = queue.removeFirst();
            order[v] = ++counter;
            for (int i = 0; i < n; i++)
                if (ar[v][i]) {
                    if (--degree[i] == 0)
                        queue.add(i);
                }
        }

        for (int k = 0; k < n; k++)
            if (uar[k])
                System.out.print((k + 1) + ", ");
        System.out.println("\nting");

        for (int i = 0; i < n; i++)
            if (uar[i])
                System.out.print(order[i] + ", ");
        System.out.println();

        for (int i = 0; i < n; i++)
            if (uar[i])
                System.out.print(degree[i] + ", ");
        System.out.println();
    }

    private static int BFS(boolean[][] ar) {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        int n = ar.length;
        int[] level = new int[n];
        boolean[] uar = new boolean[n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (ar[i][j]) {
                    uar[i] = true;
                    uar[j] = true;
                }

        for (int i = 0; i < n; i++) {
            boolean root = true;
            for (int j = 0; j < n && root; j++) {
                if (ar[j][i])
                    root = false;
            }

            if (root)
                queue.add(i);
        }

        int count = 0;
        queue.add(-1);
        while (queue.size() > 1) {
            count++;
            boolean cont = true;
            while (cont) {
                int temp = queue.removeFirst();
                if (temp == -1) {
                    cont = false;
                    queue.add(-1);
                }

                if (cont) {
                    level[temp] = count;
                    for (int i = 0; i < n; i++) {
                        if (ar[temp][i])
                            queue.add(i);
                    }
                }
            }
        }

        //        int res = 0;
        //        for (int i = 0; i < n; i++)
        //            if (uar[i] && level[i] == 1)
        //                res++;
        //
        //        if (res > 0)
        //            return res;

        int[] car = new int[count + 1];
        int res = 0;
        for (int i = 0; i < n; i++)
            if (uar[i])
                car[level[i]]++;

        for (int i = 0; i <= count; i++)
            res = Math.max(res, car[i]);

        if (res > 0)
            return res;

        count = 0;
        for (int i = 0; i < n; i++)
            if (uar[i])
                count++;

        Pair[] pairs = new Pair[count];
        for (int i = 0, j = 0; j < count; i++)
            if (uar[i])
                pairs[j++] = new Pair(i, level[i]);

        Arrays.sort(pairs);
        boolean[] vis = new boolean[n];
        //        System.out.println(pairs.length);
        count = 0;
        for (int i = 0; i < pairs.length; i++)
            if (!vis[pairs[i].num]) {
                count++;
                int k = pairs[i].num;
                vis[k] = true;
                //                System.out.println("mark i : " + k + "\tand order is " +
                //                                   count);
                for (int j = i + 1; j < pairs.length; j++)
                    if (ar[k][pairs[j].num] && !vis[pairs[j].num]) {
                        k = pairs[j].num;
                        vis[k] = true;
                        //                        System.out.println("mark k : " + k);
                    }

            }

        //        print(pairs);

        //                print(n);
        //                print(level);
        //                print(uar);

        return count;
    }

    private static int BFS(LinkedList<Integer>[] list) {
        int n = list.length;
        LinkedList<Integer> queue = new LinkedList<Integer>();
        boolean[] root = new boolean[n], uar = new boolean[n];
        int[] level = new int[n];

        for (int i = 0; i < n; i++)
            root[i] = true;

        for (int i = 0; i < n; i++) {
            for (Integer e : list[i])
                root[e] = false;
        }

        for (int i = 0; i < n; i++) {
            if (list[i].size() != 0) {
                //                uar[i] = true;
                for (Integer e : list[i])
                    uar[i] = true;
            }
        }

        for (int i = 0; i < n; i++)
            root[i] = root[i] && uar[i];

        for (int i = 0; i < n; i++)
            if (root[i])
                queue.add(i);

        queue.add(-1);
        int count = 0;
        while (queue.size() > 1) {
            count++;
            boolean cont = true;
            while (cont) {
                int temp = queue.removeFirst();
                if (temp == -1) {
                    cont = false;
                    queue.add(-1);
                } else if (list[temp].size() != 0) {
                    level[temp] = count;
                    for (Integer e : list[temp]) {
                        queue.remove(e);
                        queue.add(e);
                    }
                }
            }
        }

        int res = 0;
        for (int i = 0; i < n; i++)
            if (level[i] == 1)
                res++;

        if (res > 0)
            return res;

        if (res == 0)
            throw new RuntimeException("le le ab");

        count = 0;
        for (int i = 0; i < n; i++)
            if (uar[i])
                count++;


        Pair[] pairs = new Pair[count];
        for (int i = 0, j = 0; j < count; i++) {
            if (uar[i])
                pairs[j++] = new Pair(i, level[i]);
        }

        Arrays.sort(pairs);
        boolean[] vis = new boolean[n];
        count = 0;
        for (int i = 0; i < pairs.length; i++) {
            if (!vis[pairs[i].num]) {
                count++;
                vis[pairs[i].num] = true;
                int k = pairs[i].num;
                for (int j = i + 1; j < pairs.length; j++) {
                    if (!vis[pairs[j].num] && list[k].contains(pairs[j].num)) {
                        k = pairs[j].num;
                        vis[k] = true;
                    }
                }
            }
        }
        return count;
    }

    final static class Pair implements Comparable<Pair> {
        int num, level;

        Pair(int n, int l) {
            num = n;
            level = l;
        }

        public int compareTo(Pair o) {
            return level - o.level;
        }
    }

    final static class Tuple {
        int from, to;

        Tuple(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }

    private static void print(int n) {
        StringBuilder sb = new StringBuilder(n << 1);
        for (int i = 1; i <= n; i++)
            sb.append(i).append(' ');

        System.out.println(sb);
    }

    private static void print(int[] ar) {
        StringBuilder sb = new StringBuilder(ar.length << 1);
        for (int e : ar)
            sb.append(e).append(' ');

        System.out.println(sb);
    }

    private static void print(boolean[] ar) {
        StringBuilder sb = new StringBuilder(ar.length << 1);
        for (boolean e : ar)
            sb.append(e).append(' ');

        System.out.println(sb);
    }

    private static void print(Pair[] ar) {
        StringBuilder sn = new StringBuilder(), sl = new StringBuilder();
        for (Pair e : ar) {
            sn.append(e.num).append('\n');
            sl.append(e.level).append('\n');
        }

        System.out.println(sn + "\n" +
                sl);
    }

    private static int purvaj(boolean[][] map, int mem) {
        int n = map.length;
        for (int i = 0; i < n; i++)
            if (map[i][mem] && i != mem) {
                return purvaj(map, i);
            }

        return mem;
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

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
