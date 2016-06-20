package com.ashok.codechef.marathon.year16.APRIL16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.lang.reflect.Array;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Problem: Amazing Experiment
 * https://www.codechef.com/APRIL16/problems/AMAEXPER
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class AMAEXPER {

    private static PrintWriter out;
    private static InputStream in;
    private static Node[] treeNodes;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        AMAEXPER a = new AMAEXPER();
        a.solve();
        out.close();
    }

    private static int[] gen_rand(int size, int mod) {
        Random random = new Random();
        int[] ar = new int[size];
        for (int i = 0; i < size; i++)
            ar[i] = random.nextInt(mod);

        return ar;
    }

    private static int[] gen_rand(int size, int start, int end) {
        int mod = end + 1 - start;
        int[] res = gen_rand(size, mod);

        for (int i = 0; i < size; i++)
            res[i] += start;

        return res;
    }


    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        boolean cont = false;
        StringBuilder sb = new StringBuilder();

        while (t > 0) {
            t--;
            int n = in.readInt();
            treeNodes = new Node[n + 1];
            int[] from = new int[n - 1], to = new int[n - 1], weight =
                new int[n - 1];

            //            int[] from = gen_rand(n - 1, 1, n), to =
            //                gen_rand(n - 1, 1, n), weight = gen_rand(n - 1, 1, 10000);

            for (int i = 0; i < n - 1; i++) {
                from[i] = in.readInt();
                to[i] = in.readInt();
                weight[i] = in.readInt();
            }

            //            setInputs(from, to, weight);
            //            print(from);
            //            print(to);
            //            print(weight);

            format(from, to, weight);

            //            if (cont)
            //            process();
            letsDoItAgain();

            printCost(sb);


            //            if (cont)
            //                continue;

            //            out.print(sb);
            //            print(treeNodes);
            //            out.flush();
        }
        out.print(sb);
    }

    private static void setInputs(int[] from, int[] to, int[] w) {
        int n = from.length + 1;

        for (int i = 0; i < n - 1; i++) {
            from[i] = i + 1;
            to[i] = i + 2;
            w[i] = 5;
        }
    }

    private static void print(int[] ar) {
        StringBuilder sb = new StringBuilder(ar.length << 2);

        for (int e : ar)
            sb.append(e).append(' ');

        out.println(sb);
    }

    private static void printCost(StringBuilder sb) {
        for (int i = 1; i < treeNodes.length; i++)
            sb.append(treeNodes[i].cost).append('\n');
    }

    private static void process() {
        Node root = treeNodes[1];

        if (root == null || root.childs == null)
            return;

        process(root);
    }

    private static void letsDoItAgain() {
        LinkedList<Integer> q = new LinkedList<Integer>();

        for (int i = 1; i < treeNodes.length; i++)
            if (treeNodes[i].childs.size() == 0)
                q.add(i);

        q.add(-1);

        while (q.size() > 1) {
            int temp = q.removeFirst();
            if (temp == 1)
                continue;

            if (temp != -1) {
                int p = treeNodes[temp].parent;

                setFar(treeNodes[p],
                       treeNodes[temp].far2 + treeNodes[temp].weight);

                if (treeNodes[p].cost == 0)
                    treeNodes[p].cost = getCost(treeNodes[temp]);
                else
                    treeNodes[p].cost =
                            Math.min(treeNodes[p].cost, getCost(treeNodes[temp]));
                q.add(p);
            } else
                q.add(-1);
        }
    }

    private static long getFarChild(Node node) {
        return node.far2;
    }

    private static void setFar(Node node, long v) {
        if (v < node.far1)
            return;

        if (v > node.far2) {
            node.far1 = node.far2;
            node.far2 = v;
        } else
            node.far1 = v;
    }

    private static void copy(boolean[] a, boolean[] b) {
        for (int i = 0; i < a.length; i++)
            b[i] = a[i];
    }

    private static void process(Node root) {
        if (root.childs.size() == 0)
            return;

        long max = 0;
        for (int child : root.childs) {
            process(treeNodes[child]);

            max = Math.max(max, getCost(treeNodes[child]));
        }

        root.cost = max;
    }

    private static long getCost(Node node) {
        return node.far1 + node.far2;
        //        return node.cost + node.weight;
    }

    private static void print(Node[] nodes) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < nodes.length; i++)
            sb.append(nodes[i]);

        out.print(sb);
    }

    private static void swap(int[] a, int[] b, int i) {
        if (a[i] < b[i])
            return;

        int t = a[i];
        a[i] = b[i];
        b[i] = t;
    }

    private static void format(int[] from, int[] to, int[] weight) {
        int n = to.length + 1;
        for (int i = 1; i <= n; i++)
            treeNodes[i] = new Node(i);

        LinkedList<Pair>[] list = populate(from, to, weight);

        Node root = treeNodes[1];
        int count = 1;
        boolean[] check = new boolean[n + 1];
        check[1] = true;

        Queue queue = new Queue();
        //        LinkedList<Integer> q = new LinkedList<Integer>();
        //        q.addLast(1);
        //        q.addLast(-1);
        queue.push(1);
        queue.push(-1);

        if (list[1] == null)
            return;

        //        for (Pair pair : list[1]) {
        //            root.childs.add(pair.a);
        //            treeNodes[pair.a].weight = pair.b;
        //            treeNodes[pair.a].parent = 1;
        //
        //            queue.push(pair.a);
        //        }
        //
        //        queue.push(-1);

        //        if (q.size() > 0)
        //            return;

        while ( /**q.size() > 1 && */queue.size() > 1) {
            boolean cont = true;

            while (cont) {
                int temp = queue.pop();
                cont = temp != -1;

                if (cont) {
                    //                    check[temp] = true;

                    if (list[temp] != null) {
                        for (Pair pair : list[temp]) {
                            if (!check[pair.a]) {
                                //                                treeNodes[temp].childs =
                                //                                        ensure(treeNodes[temp].childs);
                                treeNodes[temp].childs.add(pair.a);
                                treeNodes[pair.a].parent = temp;
                                treeNodes[pair.a].weight = pair.b;
                                //                                q.add(pair.a);
                                queue.push(pair.a);

                                check[pair.a] = true;
                            }
                        }
                    }
                } else {
                    queue.push(-1);
                }
            }

            //            queue.push(-1);
            //            q.add(-1);
        }
    }

    private static LinkedList<Pair>[] populate(int[] from, int[] to, int[] w) {
        LinkedList<Pair> temp = new LinkedList<Pair>();
        LinkedList<Pair>[] list =
            (LinkedList<Pair>[])Array.newInstance(temp.getClass(),
                                                  to.length + 2);

        for (int i = 0; i < to.length; i++) {
            int a = from[i], b = to[i];

            list[a] = ensure(list[a]);
            list[b] = ensure(list[b]);

            list[a].add(new Pair(b, w[i]));
            list[b].add(new Pair(a, w[i]));
        }

        return list;
    }

    private static <T> LinkedList<T> ensure(LinkedList<T> list) {
        if (list == null)
            return new LinkedList<T>();

        return list;
    }

    private static Pair find(LinkedList<Pair> list, int a) {
        for (Pair pair : list)
            if (pair.a == a)
                return pair;

        return null;
    }

    final static class Pair {
        int a, b;

        Pair(int x, int y) {
            a = x;
            b = y;
        }

        public boolean equals(Object o) {
            if (this == o)
                return true;

            if (!(o instanceof Pair))
                return false;

            Pair pair = (Pair)o;

            return a == pair.a && b == pair.b;
        }
    }

    private final static class Node {
        int index, parent = 0;
        LinkedList<Integer> childs = new LinkedList<Integer>();
        int weight = 0;
        long far1 = 0, far2 = 0, cost = 0;

        Node(int v) {
            index = v;
        }

        void add(int node) {
            if (childs == null)
                childs = new LinkedList<Integer>();

            childs.add(node);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(index + " | ").append(" | ").append(weight).append("\n");

            if (childs.size() == 0)
                return sb.append("NO CHILDS\n\n").toString();

            for (Integer child : childs)
                sb.append(child).append(", ");

            sb.append("\n\n");
            return sb.toString();
        }
    }

    private final static class InputReader {
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

    private final static class Queue implements Iterable {
        private Node head, tail;
        private int size = 0;

        public Queue() {
            super();
        }

        private void initiate(int n) {
            size = 1;
            head = new Node(n);
            tail = head;
        }

        public void push(int n) {
            if (size == 0) {
                initiate(n);
                return;
            }

            size++;
            tail.next = new Node(n);
            tail = tail.next;
        }

        public int pop() {
            int temp = head.data;
            head = head.next;
            size--;
            return temp;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(size << 1);
            for (Object obj : this)
                sb.append(obj).append(' ');

            return sb.toString();
        }

        public void print() {
            Node temp = head;
            while (temp != null) {
                System.out.print(temp.data + "\t");
                temp = temp.next;
            }
            System.out.println();
        }

        public int get() {
            return head.data;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int count() {
            return size;
        }

        public Iterator iterator() {
            return new queueIter(this);
        }

        public int size() {
            return size;
        }

        final static class queueIter implements Iterator<Integer> {
            Queue q;

            queueIter(Queue queue) {
                q = queue;
            }

            public boolean hasNext() {
                return q.size != 0;
            }

            public Integer next() {
                return q.pop();
            }
        }

        class Node {
            int data;
            Node next;

            Node(int data) {
                this.data = data;
            }

            Node(int data, Node next) {
                this.data = data;
                this.next = next;
            }
        }
    }
}
