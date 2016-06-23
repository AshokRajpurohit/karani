package com.ashok.codechef.marathon.year15.SEP15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Random;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Nikitosh and xor
 * https://www.codechef.com/SEPT15/problems/REBXOR
 */

class REBXOR_2 {

    private static PrintWriter out;
    private static InputStream in;
    private static int mapper = (1 << 30);
    private static int[] map = new int[31];

    static {
        int mapper = REBXOR_2.mapper;
        for (int i = 0; i < 31; i++) {
            map[i] = mapper;
            mapper = mapper >>> 1;
        }
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        REBXOR_2 a = new REBXOR_2();
        a.solve();
        out.close();
    }

    private static int[] gen_rand(int size, int mod) {
        Random random = new Random();
        int[] ar = new int[size];
        for (int i = 0; i < size; i++)
            ar[i] = Math.abs(random.nextInt()) % mod;

        return ar;
    }

    private static int[] gen_rand(int size) {
        Random random = new Random();
        int[] ar = new int[size];
        for (int i = 0; i < size; i++)
            ar[i] = Math.abs(random.nextInt());

        return ar;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] ar = in.readIntArray(n);
        System.out.println(solve(ar));
        while (true) {
            n = in.readInt();
            long t = 0;
            for (int i = 0; i < n; i++) {
                int[] tar = gen_rand(in.readInt());
                long t1 = System.currentTimeMillis();
                solve(tar);
                t += System.currentTimeMillis() - t1;
            }
            System.out.println(t / n);
        }
    }

    private static int process(int[] ar) {
        if (ar.length == 2)
            return ar[0] + ar[1];

        int[] sumLeft = new int[ar.length];
        int[] sumRight = new int[ar.length];

        maxSumLeft(ar, sumLeft);
        maxSumRight(ar, sumRight);

        int res = sumLeft[0] + sumRight[1];
        for (int i = 2; i < ar.length; i++)
            res = Math.max(res, sumLeft[i - 1] + sumRight[i]);

        return res;
    }

    private static int solve(int[] ar) {
        Trie root = new Trie(0);
        int[] left = new int[ar.length];
        int[] right = new int[ar.length];
        int res = ar[0], curr = ar[0];
        left[0] = ar[0];
        add(root, ar[0]);
        for (int i = 1; i < ar.length; i++) {
            curr = curr ^ ar[i];
            add(root, curr);
            left[i] = Math.max(left[i - 1], query(root, curr));
        }

        root = new Trie(0);
        res = ar[ar.length - 1];
        curr = res;
        right[ar.length - 1] = ar[ar.length - 1];
        add(root, res);
        for (int i = ar.length - 2; i >= 0; i--) {
            curr = curr ^ ar[i];
            add(root, curr);
            right[i] = Math.max(right[i + 1], query(root, curr));
        }

        res = 0;
        for (int i = 1; i < ar.length; i++)
            res = Math.max(res, left[i - 1] + right[i]);

        return res;
    }

    private static void add(Trie root, int n) {
        int mapper = 0;
        //        Trie parent = root, child = null;
        while (mapper < 31) {
            if ((n & map[mapper]) == 0) {
                if (root.zero == null) {
                    root.zero = new Trie(n, ++mapper);
                    return;
                }
                root = root.zero;
                ++mapper;
            } else {
                if (root.one == null) {
                    root.one = new Trie(n, ++mapper);
                    return;
                }
                root = root.one;
                ++mapper;
            }
        }
    }

    private static int query(Trie root, int n) {
        int mapper = 0;
        int res = 0;
        while (mapper < 31) {
            if ((n & map[mapper]) == 0) {
                if (root.one != null) {
                    res += map[mapper];
                    root = root.one;
                    ++mapper;
                } else {
                    root = root.zero;
                    ++mapper;
                }
            } else {
                if (root.zero != null) {
                    res += map[mapper];
                    root = root.zero;
                    ++mapper;
                } else {
                    root = root.one;
                    ++mapper;
                }
            }
        }
        return res;
    }

    private static int brute(int[] ar) {
        int[] left = new int[ar.length];
        int[] right = new int[ar.length];

        left[0] = ar[0];
        for (int i = 0; i < ar.length; i++) {
            int temp = 0, now = 0;
            for (int j = i; j >= 0; j--) {
                now = now ^ ar[j];
                temp = Math.max(temp, now);
            }
            left[i] = temp;
        }

        right[ar.length - 1] = ar[ar.length - 1];
        for (int i = ar.length - 1; i >= 0; i--) {
            int temp = 0, now = 0;
            for (int j = i; j < ar.length; j++) {
                now = now ^ ar[j];
                temp = Math.max(temp, now);
            }
            right[i] = temp;
        }

        int res = 0;
        for (int i = 1; i < ar.length; i++) {
            res = Math.max(res, left[i - 1] + right[i]);
        }
        return res;
    }

    private static void maxSumLeft(int[] ar, int[] sum) {
        int maxEndingHere = ar[0], maxSoFar = ar[0];
        sum[0] = ar[0];
        for (int i = 1; i < ar.length; i++) {
            maxEndingHere = Math.max(ar[i], maxEndingHere ^ ar[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
            sum[i] = maxSoFar;
        }
    }

    private static void maxSumRight(int[] ar, int[] sum) {
        int maxEndingHere = ar[ar.length - 1], maxSoFar = ar[ar.length - 1];
        sum[ar.length - 1] = ar[ar.length - 1];

        for (int i = ar.length - 2; i >= 0; i--) {
            maxEndingHere = Math.max(ar[i], maxEndingHere ^ ar[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
            sum[i] = maxSoFar;
        }
    }

    final static class Trie {
        Trie zero, one;

        Trie() {
        }

        Trie(int n) {
            int mapper = 0;
            Trie root = this;
            while (mapper < 31) {
                if ((n & map[mapper]) == 0) {
                    root.zero = new Trie();
                    root = root.zero;
                    ++mapper;
                } else {
                    root.one = new Trie();
                    root = root.one;
                    ++mapper;
                }
            }
        }

        Trie(int n, int mapper) {
            Trie root = this;
            while (mapper < 31) {
                if ((n & map[mapper]) == 0) {
                    root.zero = new Trie();
                    root = root.zero;
                    ++mapper;
                } else {
                    root.one = new Trie();
                    root = root.one;
                    ++mapper;
                }
            }
        }

        void add(int n) {
            int mapper = 0;
            if ((n & map[mapper]) == 0) {
                if (zero == null) {
                    zero = new Trie(n, ++mapper);
                    return;
                } else {
                    zero.add(n, ++mapper);
                    return;
                }
            }

            if (one == null)
                one = new Trie(n, ++mapper);
            else
                one.add(n, ++mapper);
        }

        void add(int n, int mapper) {
            if (mapper == 0)
                return;

            if ((n & map[mapper]) == 0) {
                if (zero == null) {
                    zero = new Trie(n, ++mapper);
                    return;
                } else {
                    zero.add(n, ++mapper);
                    return;
                }
            }

            if (one == null)
                one = new Trie(n, ++mapper);
            else
                one.add(n, ++mapper);
        }

        int query(int n) {
            int mapper = 0;
            if ((n & map[mapper]) == 0) {
                if (one != null)
                    return map[mapper] + one.query(n, mapper + 1);
                return zero.query(n, ++mapper);
            }

            if (zero != null)
                return map[mapper] + zero.query(n, mapper + 1);

            return one.query(n, mapper + 1);
        }

        int query(int n, int mapper) {
            if (mapper == 31)
                return 0;

            if ((n & map[mapper]) == 0) {
                if (one != null)
                    return map[mapper] + one.query(n, mapper + 1);

                return zero.query(n, mapper + 1);
            }

            if (zero != null)
                return map[mapper] + zero.query(n, mapper + 1);

            return one.query(n, mapper + 1);
        }
    }

    /**
     * This class was created originaly to use TST to solve the problem,
     * but soon I realized TST is not the solution here. as we need two different
     * child nodes for each node (not for the end/leaf node). Currently I am using
     * Trie (see above).
     */
    final static class Node {
        int bit = 0;
        Node equal, unequal;

        Node(int n) {
            this(n, mapper);
        }

        Node(int n, int mapper) {
            //            System.out.println(n + "\t" + mapper);
            bit = n & mapper;
            if (mapper <= 1)
                return;
            equal = new Node(n, mapper >>> 1);
        }

        void add(int n) {
            add(n, mapper);
        }

        void add(int n, int mapper) {
            if (mapper == 0)
                return;
            if (bit == (n & mapper)) {
                if (equal == null)
                    equal = new Node(n, mapper >>> 1);
                else
                    equal.add(n, mapper >>> 1);

                return;
            }

            if (unequal == null)
                unequal = new Node(n, mapper);
            else
                unequal.add(n, mapper);
        }

        int query(int n) {
            return query(n, mapper);
        }

        static int query(Node root, int n) {
            int mapper = (1 << 30);
            int res = 0;
            while (mapper > 0) {
                if ((n & mapper) == root.bit) {
                    if (root.unequal != null) {
                        res += mapper;
                        root = root.unequal;
                    } else if (root.equal != null) {
                        root = root.equal;
                        mapper = mapper >>> 1;
                    } else
                        return res + (n & (mapper - 1));
                } else {
                    res += mapper;
                    if (root.equal == null)
                        return res + (n & (mapper - 1));
                    else {
                        root = root.equal;
                        mapper = mapper >>> 1;
                    }
                }
            }
            return res;
        }

        int query(int n, int mapper) {
            if (mapper == 0)
                return 0;

            if (n == 0) {
                int res = mapper;
                if (equal != null)
                    res += equal.query(n);

                if (unequal != null)
                    return Math.max(res, unequal.query(n));

                return res;
            }

            if ((n & mapper) == bit) {
                //                System.out.println(bit + "\t" + mapper + "\t" + n);
                if (unequal != null) {
                    //                    System.out.println(bit + " **");
                    return mapper + unequal.query(n, mapper);
                } else if (equal != null) {
                    //                    System.out.println("le");
                    return equal.query(n, mapper >>> 1);
                }

                return n & (mapper - 1);
            }

            return mapper + (equal == null ? 0 : equal.query(n, mapper >>> 1));
        }
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
