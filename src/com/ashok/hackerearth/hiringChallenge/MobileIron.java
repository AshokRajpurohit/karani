package com.ashok.hackerearth.hiringChallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Problem Name: MobileIron SDET Hiring Challenge
 * Link: https://www.hackerearth.com/mobileiron-sdet-hiring-challenge/problems
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MobileIron {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        ByAndSell.solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        while (true) {
            out.println(in.readInt());
            out.flush();
        }
    }

    final static class ByAndSell {
        static void solve() throws IOException {
            int n = in.readInt();
            String[] items = new String[n];
            int[] prices = new int[n];

            for (int i = 0; i < n; i++) {
                items[i] = in.read();
                prices[i] = in.readInt();
            }

            int uniqueItems = 0;
            int index = 0;
            TST tst = new TST(items[0], index);
            Item[] itemArray = new Item[n];
            itemArray[index] = new Item(index, prices[index]);

            for (int i = 1; i < n; i++) {
                tst.add(items[i], ++index);
                itemArray[index] = new Item(index, prices[index]);
            }

            Arrays.sort(itemArray);
            int[] indices = new int[n];
            int[] arrayForRange = new int[n];

            RangeQueryUpdate rq = new RangeQueryUpdate(arrayForRange);

            for (int i = 0; i < n; i++) {
                indices[itemArray[i].index] = i;
            }

            int q = in.readInt();
            StringBuilder sb = new StringBuilder(q << 2);
            int addQuery = '+', removeQuery = '-', ask = '?';
            while (q > 0) {
                q--;
                int type = in.read().charAt(0);
                if (type == ask) {
                    int num = in.readInt();
                    int v = find(itemArray, num);

                    if (v == itemArray.length - 1) {
                        sb.append(0).append('\n');
                    } else {
                        long res = rq.query(v + 1, arrayForRange.length - 1);
                        sb.append(res).append('\n');
                    }
                } else {
                    String item = in.read();
                    int vi = tst.find(item);
                    rq.update(indices[vi], type == addQuery ? 1 : -1);
                }
            }

            out.print(sb);
        }
    }

    private static int find(Item[] ar, int v) {
        int start = 0, end = ar.length, mid = (start + end) >>> 1;

        while (start != mid) {
            if (ar[mid].price > v)
                end = mid;
            else
                start = mid;

            mid = (start + end) >>> 1;
        }

        if (ar[end].price == v)
            return end;

        return start;
    }

    final static class Item implements Comparable<Item> {
        int index, price;

        Item(int index, int price) {
            this.index = index;
            this.price = price;
        }

        @Override
        public int compareTo(Item o) {
            return this.price - o.price;
        }
    }

    /**
     * @link https://github.com/AshokRajpurohit/karani/blob/master/src/com/ashok/lang/dsa/RangeQueryUpdate.java
     */
    final static class RangeQueryUpdate {
        private Node root;

        public RangeQueryUpdate(int[] ar) {
            construct(ar);
        }

        public long query(int L, int R) {
            if (R < L)
                return query(R, L);

            if (R > root.r || L < 0)
                throw new IndexOutOfBoundsException(L + ", " + R);

            return query(root, L, R);
        }

        private long query(Node node, int L, int R) {
            if (node.l == L && node.r == R)
                return node.data;

            int mid = (node.l + node.r) >>> 1;
            if (L > mid)
                return query(node.right, L, R);

            if (R <= mid)
                return query(node.left, L, R);

            return operation(query(node.left, L, mid),
                    query(node.right, mid + 1, R));
        }

        public void update(int i, int data) {
            if (i > root.r || i < 0)
                throw new IndexOutOfBoundsException(i + "");

            update(root, i, data);
        }

        public void update(int L, int R, int data) {
            for (int i = L; i <= R; i++)
                update(root, i, data);
        }

        private void update(Node root, int i, int data) {
            if (root.l == root.r) {
                root.data = updateOperation(root.data, data);
                return;
            }

            int mid = (root.l + root.r) >>> 1;
            if (i > mid)
                update(root.right, i, data);
            else
                update(root.left, i, data);

            root.data = operation(root.left.data, root.right.data);
        }

        private void construct(int[] ar) {
            root = new Node(0, ar.length - 1);
            int mid = (ar.length - 1) >>> 1;
            root.left = construct(ar, 0, mid);
            root.right = construct(ar, mid + 1, ar.length - 1);
            root.data = operation(root.left.data, root.right.data);
        }

        private Node construct(int[] ar, int l, int r) {
            if (l == r)
                return new Node(l, l, ar[l]);

            Node temp = new Node(l, r);
            int mid = (l + r) >>> 1;
            temp.left = construct(ar, l, mid);
            temp.right = construct(ar, mid + 1, r);
            temp.data = operation(temp.left.data, temp.right.data);
            return temp;
        }

        /**
         * Write your own operation while using it. It can be Math.min, max or add
         *
         * @param a
         * @param b
         * @return
         */
        private static long operation(long a, long b) {
            return a + b;
        }

        /**
         * Update function, for the existing data a and update query data b,
         * returns the new value for existing data. This can be replacement,
         * addition, subtraction, multiplication or anything desired.
         *
         * @param a
         * @param b
         * @return new value for the node.
         */
        private static long updateOperation(long a, long b) {
            return a + b;
        }

        final static class Node {
            Node left, right;
            int l, r;
            long data;

            Node(int m, int n) {
                l = m;
                r = n;
            }

            Node(int m, int n, int d) {
                l = m;
                r = n;
                data = d;
            }
        }
    }


    /**
     * This is implemantion of Ternary Search Tree (TST) Data Structure.
     *
     * @link https://github.com/AshokRajpurohit/karani/blob/master/src/com/ashok/lang/dsa/TST.java
     */
    final static class TST {
        private TST left, right, equal;
        private boolean end = false;
        private Character ch;
        private int count = 0;
        private int index = 0;

        public TST(String s, int index) {
            this(s, 0, index);
        }

        /**
         * initializes the instance.
         *
         * @param s     String s you want to initialize the instance.
         * @param pos   start pos in string s to be inserted in instance.
         * @param index
         */
        private TST(String s, int pos, int index) {
            if (pos == s.length())
                return;

            if (ch == null) {
                ch = s.charAt(pos);
                if (pos == s.length() - 1) {
                    end = true;
                    count++;
                } else
                    equal = new TST(s, pos + 1, 0);
            } else {
                if (ch < s.charAt(pos)) {
                    if (right == null)
                        right = new TST(s, pos, 0);
                    else
                        right.add(s, pos, 0);
                } else if (ch == s.charAt(pos)) {
                    if (pos == s.length() - 1) {
                        end = true;
                        count++;
                    } else if (equal == null) {
                        equal = new TST(s, pos + 1, 0);
                    } else {
                        equal.add(s, pos + 1, 0);
                    }
                } else {
                    if (left == null)
                        left = new TST(s, pos, 0);
                    else
                        left.add(s, pos, 0);
                }
            }
        }

        public int find(String s) {
            return find(s, 0);
        }

        private int find(String s, int pos) {
            if (pos == s.length())
                return index;

            if (s.charAt(pos) == this.ch) {
                pos++;
                if (pos == s.length())
                    return index;
                if (equal == null)
                    return -1;
                return equal.find(s, pos);
            }

            if (s.charAt(pos) > this.ch) {
                if (right == null)
                    return -1;
                return right.find(s, pos);
            }

            if (left == null)
                return -1;

            return left.find(s, pos);
        }

        public void setIndex(String s, int index) {
            setIndex(s, 0, index);
        }

        private void setIndex(String s, int pos, int index) {
            if (pos == s.length()) {
                this.index = index;
                return;
            }

            if (s.charAt(pos) == this.ch) {
                pos++;
                if (pos == s.length()) {
                    this.index = index;
                    return;
                }

                if (equal == null)
                    throw new RuntimeException("le kanjar");

                equal.setIndex(s, pos, index);
                return;
            }

            if (s.charAt(pos) > this.ch) {
                if (right == null)
                    throw new RuntimeException("le kanjar");

                right.setIndex(s, pos, index);
                return;
            }

            if (left == null)
                throw new RuntimeException("le kanjar");

            left.setIndex(s, pos, index);
        }

        /**
         * adds the given string to the TST structure.
         *
         * @param s
         */
        public void add(String s, int index) {
            this.add(s, 0, index);
        }

        /**
         * to add new string into existing TST instance.
         *
         * @param s     String s to be added to instance.
         * @param pos   start char pos in String s.
         * @param index
         */
        private void add(String s, int pos, int index) {
            if (s.charAt(pos) == this.ch) {
                if (pos == s.length() - 1) {
                    this.end = true;
                    count++;
                    this.index = index;
                    return;
                } else {
                    if (equal == null)
                        equal = new TST(s, pos + 1, 0);
                    else
                        equal.add(s, pos + 1, 0);
                    return;
                }
            } else if (s.charAt(pos) < this.ch) {
                if (left == null) {
                    left = new TST(s, pos, 0);
                    return;
                } else
                    left.add(s, pos, 0);
            } else {
                if (right == null) {
                    right = new TST(s, pos, 0);
                    return;
                } else {
                    right.add(s, pos, 0);
                    return;
                }
            }
            return;
        }

        public String toString() {
            return print();
        }

        /**
         * appends all the strings to sb lexicographical order.
         *
         * @param sb StringBuilder to which all the strings to be appended.
         */
        public void print(StringBuilder sb) {
            StringBuilder sbuf = new StringBuilder();
            print(sb, sbuf);
        }

        /**
         * returns all the strings in lexicographical order.
         *
         * @return all the strings in lexicographical order seperated by new line character.
         */
        public String print() {
            StringBuilder sb = new StringBuilder();
            StringBuilder sbuf = new StringBuilder();
            print(sb, sbuf);
            return sb.toString();
        }

        /**
         * print function adds all the strings in sorted order to StringBuilder sb.
         *
         * @param sb   StringBuilder to which all the strings to be appended.
         * @param sbuf used to get strings.
         */
        private void print(StringBuilder sb, StringBuilder sbuf) {
            int clen = sbuf.length();
            if (left != null) {
                left.print(sb, sbuf);
                sbuf.delete(clen, sbuf.length());
            }
            sbuf.append(ch);
            for (int i = 0; i < this.count; i++) {
                sb.append(sbuf).append('\n');
            }

            if (equal != null)
                equal.print(sb, sbuf);

            sbuf.deleteCharAt(clen);

            if (right != null) {
                right.print(sb, sbuf);
            }
        }

        public String[] toArrays() {
            ArrayList<String> al = new ArrayList<String>();
            StringBuilder sbuf = new StringBuilder();
            getList(al, sbuf);
            String[] res = new String[2];
            return al.toArray(res);
        }

        public ArrayList<String> toList() {
            ArrayList<String> al = new ArrayList<String>();
            StringBuilder sbuf = new StringBuilder();
            getList(al, sbuf);
            return al;
        }

        private void getList(ArrayList<String> al, StringBuilder sbuf) {
            int clen = sbuf.length();
            if (left != null) {
                left.getList(al, sbuf);
                sbuf.delete(clen, sbuf.length());
            }
            sbuf.append(ch);
            for (int i = 0; i < this.count; i++) {
                al.add(sbuf.toString());
            }

            if (equal != null)
                equal.getList(al, sbuf);

            sbuf.deleteCharAt(clen);

            if (right != null) {
                right.getList(al, sbuf);
            }
        }

        public void remove(String s) {
            remove(s, 0);
            TST temp = this;
            int i = 0;
            while (i < s.length() && temp != null) {
                if (temp.ch == s.charAt(i)) {
                    if (i == s.length() - 1) {
                        if (temp.count > 0)
                            temp.count--;
                        else {
                            temp.left = null;
                            temp.equal = null;
                            temp.right = null;
                        }
                        return;
                    }
                    temp = temp.equal;
                    i++;
                } else if (temp.ch < s.charAt(i))
                    temp = temp.right;
                else
                    temp = temp.left;
            }
        }

        private void remove(String s, int index) {
            if (index == s.length())
                return;
            this.find(s);
        }

        public void addEf(String s) {
            TST temp = this;
            int i = 0;
            while (i < s.length()) {
                if (temp.ch == s.charAt(i)) {
                    if (i == s.length() - 1) {
                        temp.count++;
                        return;
                    }
                    if (temp.equal == null) {
                        temp.equal = new TST(s, i + 1, 0);
                        return;
                    }
                } else if (s.charAt(i) < temp.ch) {
                    if (temp.left == null) {
                        temp.left = new TST(s, i, 0);
                        return;
                    } else
                        temp = temp.left;
                } else {
                    if (temp.right == null) {
                        temp.right = new TST(s, i, 0);
                        return;
                    } else
                        temp = temp.right;
                }
            }
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

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = readLong();

            return ar;
        }

        public String read() throws IOException {
            StringBuilder sb = new StringBuilder();
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
            for (; offset < bufferSize; ++offset) {
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

        public String readLine() throws IOException {
            StringBuilder sb = new StringBuilder();
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
            for (; offset < bufferSize; ++offset) {
                if (buffer[offset] == '\n' || buffer[offset] == '\r')
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
