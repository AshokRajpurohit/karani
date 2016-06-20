package com.ashok.lang.dsa;

import java.util.Iterator;

/**
 * Node class for list nodes.
 * @author Ashok Rajpurohit ashok1113@gmail.com
 */
class Node<T> implements Iterator {
    T data;
    Node<T> next;

    Node(T data) {
        this.data = data;
    }

    Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    public boolean hasNext() {
        return next != null;
    }

    public T next() {
        Node<T> temp = next;
        next = next.next;
        return temp.data;
    }

    public T peep() {
        return next.data;
    }

    /**
     * This method will remove the next item.
     */
    public void remove() {
        next = next.next;
    }

    public String toString() {
        return data.toString();
    }
}
