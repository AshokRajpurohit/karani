package com.ashok.codechef.marathon.year16.FEB16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.lang.reflect.Array;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem: Chef and Job and Rest
 * https://www.codechef.com/FEB16/problems/CHEFJR
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class CHEFJR {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CHEFJR a = new CHEFJR();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt(), m = in.readInt(), k = in.readInt();
        int[] rest = in.readIntArray(n);

        Book[] books = new Book[m];
        for (int i = 0; i < m; i++)
            books[i] = new Book(in.readInt(), in.readInt(), in.readInt());

        LinkedList<Integer> ref = new LinkedList<Integer>();
        LinkedList<Integer>[] list =
            (LinkedList<Integer>[])Array.newInstance(ref.getClass(), m);

        for (int i = 0; i < k; i++) {
            int a = in.readInt() - 1, b = in.readInt() - 1;
            if (list[a] == null)
                list[a] = new LinkedList<Integer>();

            list[a].add(b);
        }

        int[] order = topologicalSort(m, list);
        LinkedList<ReadBook> refBook = new LinkedList<ReadBook>();
        LinkedList<ReadBook>[] readBooks =
            (LinkedList<ReadBook>[])Array.newInstance(refBook.getClass(), n);

        for (int i = 0, j = 0; i < n && j < order.length; ) {
            Book book = books[order[j]];
            if (book.oneSitting) {
                while (i < n && rest[i] < book.pages)
                    i++;

                if (i < n) {
                    if (readBooks[i] == null)
                        readBooks[i] = new LinkedList<ReadBook>();

                    readBooks[i].add(new ReadBook(order[j], book.pages));
                    book.pages = 0;
                }
                j++;
            } else {
                while (i < n && book.pages > rest[i]) {
                    if (rest[i] == 0) {
                        i++;
                        continue;
                    }

                    if (readBooks[i] == null)
                        readBooks[i] = new LinkedList<ReadBook>();

                    readBooks[i].add(new ReadBook(order[j], rest[i]));
                    book.pages -= rest[i];
                    rest[i] = 0;
                }

                if (i < n && book.pages != 0) {
                    if (readBooks[i] == null)
                        readBooks[i] = new LinkedList<ReadBook>();

                    readBooks[i].add(new ReadBook(order[j], book.pages));
                    rest[i] -= book.pages;
                    book.pages = 0;
                    i++;
                    j++;
                }
            }

            if (i == n) {
                while (j < order.length) {
                    book = books[order[j]];
                    book.pages = 0;
                    j++;
                }
            }
        }

        for (int j = 0; j < order.length; j++)
            books[order[j]].pages = 0;

        Rest[] rests = new Rest[n];
        for (int i = 0; i < n; i++)
            rests[i] = new Rest(i, rest[i]);

        Arrays.sort(rests);
        PairBook[] pairBooks = new PairBook[m];
        for (int i = 0; i < m; i++)
            pairBooks[i] = new PairBook(i, books[i]);

        Arrays.sort(pairBooks);

        // let's first fit one sitting books only.
        for (int i = 0, j = 0; i < n && j < m; ) {
            Book book = pairBooks[j].book;
            if (book.pages == 0 || !book.oneSitting) {
                j++;
                continue;
            }

            while (i < n && rests[i].rest < book.pages)
                i++;

            if (i < n) {
                int r = rests[i].index;
                if (readBooks[r] == null)
                    readBooks[r] = new LinkedList<ReadBook>();

                readBooks[r].add(new ReadBook(pairBooks[j].index, book.pages));
                rests[i].rest -= book.pages;
                book.pages = 0;
                j++;
            }
        }

        // let's fit the flexible books now.
        for (int i = 0, j = 0; i < n && j < m; ) {
            if (rests[i].rest == 0) {
                i++;
                continue;
            }
            Book book = pairBooks[j].book;
            if (book.pages == 0 || book.oneSitting) {
                j++;
                continue;
            }

            while (i < n && book.pages > rests[i].rest) {
                if (rests[i].rest == 0) {
                    i++;
                    continue;
                }

                if (readBooks[i] == null)
                    readBooks[i] = new LinkedList<ReadBook>();

                readBooks[i].add(new ReadBook(pairBooks[j].index,
                                              rests[i].rest));
                book.pages -= rests[i].rest;
                rests[i].rest = 0;
            }

            if (i < n && book.pages != 0) {
                if (readBooks[i] == null)
                    readBooks[i] = new LinkedList<ReadBook>();

                readBooks[i].add(new ReadBook(pairBooks[j].index, book.pages));
                rests[i].rest -= book.pages;
                book.pages = 0;
                i++;
                j++;
            }
        }

        StringBuilder sb = new StringBuilder(n << 3);
        for (int i = 0; i < n; i++) {
            if (readBooks[i] == null) {
                sb.append("0\n");
                continue;
            }

            sb.append(readBooks[i].size()).append(' ');
            for (ReadBook book : readBooks[i]) {
                sb.append(book.book +
                          1).append(' ').append(book.time).append(' ');
            }
            sb.append('\n');
        }

        out.print(sb);
    }

    private static void print(int[] ar) {
        StringBuilder sb = new StringBuilder(ar.length << 1);

        for (int e : ar)
            sb.append(e).append(' ');

        out.println(sb);
    }

    private static void print(boolean[] ar) {
        StringBuilder sb = new StringBuilder(ar.length << 1);

        for (boolean o : ar)
            sb.append(o).append(' ');

        out.println(sb);
    }

    public int[] topologicalSort(int nodes, LinkedList<Integer>[] list) {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        boolean[] root = new boolean[nodes];
        int[] level = new int[nodes];
        for (int i = 0; i < nodes; i++) {
            if (list[i] != null && list[i].size() > 0) {
                root[i] = true;
            }
        }

        for (int i = 0; i < nodes; i++) {
            if (list[i] != null) {
                for (Object o : list[i])
                    root[(Integer)o] = false;
            }
        }

        for (int i = 0; i < nodes; i++)
            if (root[i]) {
                queue.add(i);
            }

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
                    continue;
                }

                level[temp] = count;
                if (list[temp] == null)
                    continue;

                for (Object o : list[temp]) {
                    queue.add((Integer)o);
                }
            }
        }

        count = 0;
        boolean[] check = new boolean[nodes];
        for (int i = 0; i < nodes; i++) {
            if (list[i] != null) {
                check[i] = true;

                for (Object o : list[i]) {
                    check[(Integer)o] = true;
                }
            }
        }

        for (int i = 0; i < nodes; i++) {
            if (check[i])
                count++;
        }

        LinkedList[] topos = new LinkedList[count + 1];
        for (int i = 0; i < nodes; i++) {
            if (!check[i])
                continue;

            if (topos[level[i]] == null)
                topos[level[i]] = new LinkedList<Integer>();

            topos[level[i]].add(i);
        }

        int[] res = new int[count];
        for (int i = 0, j = 1; j <= count; j++) {
            if (topos[j] != null) {
                for (Object o : topos[j]) {
                    res[i] = (Integer)o;
                    i++;
                }
            }
        }

        return res;
    }

    final static class Rest implements Comparable<Rest> {
        int index, rest;

        Rest(int n, int r) {
            index = n;
            rest = r;
        }

        public int compareTo(Rest o) {
            return rest - o.rest;
        }
    }

    final static class PairBook implements Comparable<PairBook> {
        int index;
        Book book;

        PairBook(int n, Book b) {
            index = n;
            book = b;
        }

        public int compareTo(PairBook o) {
            return this.book.pages - o.book.pages;
        }
    }

    final static class Book {
        boolean oneSitting;
        int pages, rating;

        Book(int must, int page, int rating) {
            oneSitting = must == 0;
            pages = page;
            this.rating = rating;
        }
    }

    final static class ReadBook {
        int book, time;

        ReadBook(int b, int t) {
            book = b;
            time = t;
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
