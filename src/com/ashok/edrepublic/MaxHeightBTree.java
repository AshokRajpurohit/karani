package com.ashok.edrepublic;


/**
 * @author: Ashok Rajpurohit
 *
 */
public class MaxHeightBTree {
    private MaxHeightBTree() {
        super();
    }

    /**
     *
     * @param k balance factor of binary tree
     * @param n number of nodes in binary tree
     * @return maximum height possible for the binary tree with
     * balance factor k and number of nodes n.
     */
    public static int solve(int k, int n) {
        if (n <= k)
            return n;
        Node head = new Node(1);
        Node tail = head;
        for (int i = 1; i <= k; i++) {
            tail.next = new Node(i + 1);
            tail = tail.next;
        }

        int i = 0;
        for (i = k; tail.data <= n; i++) {
            tail.next = new Node(1 + tail.data + head.data);
            head = head.next;
            tail = tail.next;
        }
        return i;
    }

    public static int solve1(int k, int n) {
        if (n <= k)
            return n;
        int[] ar = new int[k + 1];
        for (int i = 0; i <= k; i++)
            ar[i] = i + 1;

        int head = 0, tail = k, h = k;
        for (; ar[tail] <= n; h++) {
            ar[head] = 1 + ar[tail] + ar[head];
            head++;
            tail++;
            if (head > k)
                head = 0;
            if (tail > k)
                tail = 0;
        }
        return h;
    }


    private final static class Node {
        int data;
        Node next;

        Node(int n) {
            data = n;
        }
    }
}
