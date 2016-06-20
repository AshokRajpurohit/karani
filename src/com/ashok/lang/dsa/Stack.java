package com.ashok.lang.dsa;

import java.util.Iterator;

/**
 * simple Stack Data Structure implementation.
 *
 * @param <T>
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class Stack<T> implements Iterable {
    private Node<T> top = null;
    private int size = 0;

    public Stack() {
        // let's do nothing
    }

    public void push(T data) {
        size++;
        Node<T> temp = new Node<T>(data, top);
        top = temp;
    }

    public T pop() {
        size--;
        Node<T> temp = top;
        top = top.next;
        return temp.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Iterator iterator() {
        return new Node<T>(null, top);
    }
}
