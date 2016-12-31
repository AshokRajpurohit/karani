package com.ashok.lang.dsa;

import java.util.Iterator;

/**
 * List class to support list related questions.
 *
 * @author Ashok Rajpurohit ashok1113@gmail.com
 */
public class List<T> implements Iterable<T> {
    private Node<T> head, tail;
    private int size = 0;

    public List() {
        // do nothing
    }

    public List(T t) {
        head = new Node<T>(t);
        tail = head;
        size = 1;
    }

    List(T[] ar) {
        if (ar.length == 0)
            throw new NullPointerException("Bhai Empty array hai");

        size = ar.length;
        head = new Node<T>(ar[0]);
        tail = head;
        for (int i = 1; i < ar.length; i++) {
            tail.next = new Node<T>(ar[i]);
            tail = tail.next;
        }
    }

    public void add(T t) {
        if (head == null) {
            size = 1;
            head = new Node<T>(t);
            tail = head;
            return;
        }
        size++;
        tail.next = new Node<T>(t);
        tail = tail.next;
    }

    public void delete(T t) {
        if (size == 0)
            return;

        if (head.data.equals(t)) {
            head = head.next;
            --size;
            return;
        }

        Node temp = head;
        while (temp.next != null && temp.next.data != t)
            temp = temp.next;

        if (temp.next != null)
            temp.next = temp.next.next;
    }

    public boolean contains(T t) {
        if (size == 0)
            return false;

        if (t == null)
            return true;

        Node temp = head;
        while (temp != null && !t.equals(temp.data))
            temp = temp.next;

        return temp != null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public T getHead() {
        return head.data;
    }

    public boolean remove(T t) {
        if (size == 0)
            return false;

        if (head.data.equals(t)) {
            head = head.next;
            --size;
            return true;
        }

        Node temp = head;
        while (temp.next != null && !t.equals(temp.data))
            temp = temp.next;

        if (temp.next == null)
            return false;

        temp = temp.next.next;
        --size;
        return true;
    }

    public T getLast() {
        return tail.data;
    }

    public Iterator<T> iterator() {
        return new Node<T>(null, head);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(size << 1);
        Node temp = head;
        while (temp != null) {
            sb.append(temp.data).append(' ');
            temp = temp.next;
        }

        return sb.toString();
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
}
