package com.ashok.lang.dsa;

import java.util.Iterator;

public class Queue<T> implements Iterable {
    private Node<T> head, tail;
    private int size = 0;

    public Queue() {
        super();
    }

    private void initiate(T n) {
        size = 1;
        head = new Node<T>(n);
        tail = head;
    }

    public void push(T n) {
        if (size == 0) {
            initiate(n);
            return;
        }

        size++;
        tail.next = new Node<T>(n);
        tail = tail.next;
    }

    public T pop() {
        T temp = head.data;
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

    public T get() {
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

    class queueIter<T> implements Iterator {
        Queue<T> q;

        queueIter(Queue<T> queue) {
            q = queue;
        }

        public boolean hasNext() {
            return q.size != 0;
        }

        public Object next() {
            return q.pop();
        }
    }
}
