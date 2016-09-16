package com.ashok.codechef.marathon.year16.sept;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * Problem Name: Chef and Friends
 * Link: https://www.codechef.com/SEPT16/problems/CHFNFRN
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class CHFNFRN {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final String yes = "YES\n", no = "NO\n";

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            int n = in.readInt(), m = in.readInt();

            if (n <= 2) {
                sb.append(yes);
                for (int i = 0; i < m; i++) {
                    int a = in.readInt(), b = in.readInt();
                }
                continue;
            }

            if (m == 0) {
                sb.append(no);
                continue;
            }

            boolean[][] map = new boolean[n][n];
            int edges = 0;
            for (int i = 0; i < m; i++) {
                int a = in.readInt() - 1, b = in.readInt() - 1;

                if (a != b) {
                    if (map[a][b])
                        continue;

                    map[a][b] = true;
                    map[b][a] = true;
                    edges++;
                }
            }

            sb.append(process(n, map, edges) ? yes : no);
        }

        out.print(sb);
    }

    private static boolean impossible(boolean[][] map, int edges, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (map[i][j])
                    continue;

                for (int k = j + 1; k < n; k++) {
                    if (!map[i][k] && !map[j][k])
                        return true;
                }
            }
        }

        return false;
    }

    private static boolean process(int n, boolean[][] map, int edges) {
        if (edges == 0)
            return false;

        if (n == 3)
            return true;

        if (edges >= n * (n - 1) / 2 - 1)
            return true;

        int m = n >>> 1;
        int min = m * (m - 1) / 2;

        m = n - m;
        min += m * (m - 1) / 2;

        if (edges < min)
            return false;

        if (impossible(map, edges, n))
            return false;

        int first = 0, second = 0;
        for (int i = 0; i < n && second == 0; i++)
            for (int j = i + 1; j < n && second == 0; j++) {
                if (!map[i][j]) {
                    first = i;
                    second = j;
                }
            }

        if (first == second)
            return true;

        for (int i = 0; i < n; i++) {
            if (i != first && i != second && !map[i][first] && !map[i][second])
                return false;
        }

        LinkedList<Integer> firstGroup = new LinkedList<>(),
                secondGroup = new LinkedList<>();

        boolean[] common = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (map[i][first] && map[i][second])
                common[i] = true;
        }

        firstGroup.add(first);
        for (int i = 0; i < n; i++) {
            if (!common[i] && map[first][i]) {
                for (int e : firstGroup) {
                    if (map[e][i])
                        continue;

                    return false;
                }

                firstGroup.add(i);
            }
        }

        secondGroup.add(second);
        for (int i = 0; i < n; i++) {
            if (!common[i] && map[second][i]) {

                for (int e : secondGroup) {
                    if (map[e][i])
                        continue;

                    return false;
                }

                secondGroup.add(i);
            }
        }

        LinkedList<Integer> commonList = new LinkedList<>();
        for (int i = 0; i < n; i++)
            if (common[i])
                commonList.add(i);

        if (!validateGroup(firstGroup, map))
            return false;

        if (!validateGroup(secondGroup, map))
            return false;

        if (!function(firstGroup, secondGroup, commonList, map))
            return false;

        if (validateGroup(commonList, map))
            return true;

        return recursive(commonList, map);
    }

    private static boolean recursive(LinkedList<Integer> list, boolean[][] map) {
        if (list.size() <= 2)
            return true;

        int first = 0, second = 0;
        LinkedList<Integer> firstGroup = new LinkedList<>(),
                secondGroup = new LinkedList<>(),
                common = new LinkedList<>();

        for (int e : list) {
            for (int f : list) {
                if (e != f && !map[e][f]) {
                    first = e;
                    second = f;
                    break;
                }
            }

            if (first != second)
                break;
        }

        if (first == second)
            return true;

        for (int e : list) {
            if (e == first || e == second)
                continue;

            if (map[e][first] && map[e][second])
                common.add(e);
            else if (map[e][first])
                firstGroup.add(e);
            else if (map[e][second])
                secondGroup.add(e);
        }

        if (!validateGroup(firstGroup, map))
            return false;

        if (!validateGroup(secondGroup, map))
            return false;

        if (!function(firstGroup, secondGroup, common, map))
            return false;

        if (validateGroup(common, map))
            return true;

        return recursive(common, map);
    }

    private static boolean function(LinkedList<Integer> first, LinkedList<Integer> second,
                                    LinkedList<Integer> common, boolean[][] map) {
        boolean cont = true;
        while (cont) {
            int size = common.size();
            cont = false;

            while (size > 0) {
                size--;

                int e = common.removeFirst();
                boolean toBeGroupedOne = true;
                for (int f : first)
                    if (!map[f][e]) {
                        toBeGroupedOne = false;
                        break;
                    }


                boolean toBeGroupedTwo = true;
                for (int f : second)
                    if (!map[f][e]) {
                        toBeGroupedTwo = false;
                        break;
                    }

                if (!toBeGroupedOne && !toBeGroupedTwo) {
                    return false;
                }

                if (toBeGroupedOne && toBeGroupedTwo) {
                    common.addLast(e);
                    continue;
                }

                cont = true;
                if (toBeGroupedOne) {
                    first.add(e);
                    continue;
                }

                if (toBeGroupedTwo) {
                    second.add(e);
                }
            }
        }

        return true;
    }


    private static boolean validateGroup(LinkedList<Integer> list, boolean[][] map) {
        if (list.size() < 2)
            return true;

        for (int e : list) {
            for (int f : list) {
                if (e != f && !map[e][f])
                    return false;
            }
        }

        return true;
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
