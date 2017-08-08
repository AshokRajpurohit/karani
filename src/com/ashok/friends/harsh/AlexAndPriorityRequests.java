package com.ashok.friends.harsh;

import com.ashok.lang.math.Prime;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;

public class AlexAndPriorityRequests {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        while (true) {
            out.print(Prime.primesInRange(in.readInt(), in.readInt()));
            out.flush();
        }
    }

    final static class PriorityRequest {
        int time, priorityValue;

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

    final static class GenericHeap<E> {
        private final Comparator<? super E> comparator;
        public final int capacity;
        private int count = 0;
        private final Node<E>[] heap;
        private final HashMap<E, Node<E>> map = new HashMap<>();

        public GenericHeap(Comparator<? super E> comparator, int capacity) {
            this.comparator = comparator;
            this.capacity = capacity;
            heap = new Node[capacity];
        }

        public E poll() {
            E res = heap[0].value;
            count--;
            heap[0] = heap[count];
            reformatDown(0);

            return res;
        }

        public void refresh(int index) {
            E res = heap[index].value;
            remove(index);
            offer(res);
        }

        public void update(int index, E newValue) {
            heap[index].value = newValue;
            reformatDown(index);
            reformatUp(index);
        }

        public E remove(int index) {
            E res = heap[index].value;
            count--;
            map.remove(res);
            heap[index] = heap[count];
            reformatDown(index);
            return res;
        }

        public E peek() {
            return heap[0].value;
        }

        public boolean isFull() {
            return count == capacity;
        }

        /**
         * If the heap is already full then this will update the already existing
         * top element if necessary.
         *
         * @param e
         * @return
         */
        public boolean offer(E e) {
            if (count == capacity)
                return update(e);
            else
                add(e);

            return true;
        }

        public void addAll(E[] ar) {
            for (E e : ar)
                offer(e);
        }

        private void add(E e) {
            heap[count] = new Node<E>(e, count);
            map.put(e, heap[count]);
            reformatUp(count);
            count++;
        }

        private boolean update(E e) {
            if (comparator.compare(e, heap[0].value) <= 0)
                return false;

            heap[0] = new Node<>(e, 0);
            map.put(e, heap[0]);
            reformatDown(0);
            return true;
        }

        private void reformatUp(int index) {
            while (index != 0) {
                int parent = (index - 1) >>> 1;
                if (compare(heap[parent], heap[index]) > 0) {
                    swap(index, parent);
                    index = parent;
                }
            }
        }

        private void reformatDown(int index) {
            while (((index << 1) + 1) < count) {
                int c = getSmallerChild(index);
                if (compare(heap[index], heap[c]) > 0) {
                    swap(index, c);
                    index = c;
                }
            }
        }

        private int getParent(int index) {
            return (index - 1) >>> 1;
        }

        private int getSmallerChild(int index) {
            int c1 = (index << 1) + 1, c2 = c1 + 1;

            if (c2 == count)
                return c1;

            return compare(heap[c1], heap[c2]) <= 0 ? c1 : c2;
        }

        private void swap(int i, int j) {
            Node temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
            heap[i].index = i;
            heap[j].index = j;
        }

        private int compare(int a, int b) {
            return compare(heap[a], heap[b]);
        }

        private int compare(Node<E> a, Node<E> b) {
            return comparator.compare(a.value, b.value);
        }

        final static class Node<E> {
            int index = -1;
            E value;

            Node(E e) {
                value = e;
            }

            Node(E e, int index) {
                value = e;
                this.index = index;
            }
        }
    }
}