package com.ashok.friends.tridip;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Test: Microsoft Hiring Test
 * Candidate: Tridip Chakraborty
 * Link: E-Mail link.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Microsoft {

    static class SinglyLinkedListNode {
        public int data;
        public SinglyLinkedListNode next;

        public SinglyLinkedListNode(int nodeData) {
            this.data = nodeData;
            this.next = null;
        }
    }

    static class SinglyLinkedList {
        public SinglyLinkedListNode head;
        public SinglyLinkedListNode tail;

        public SinglyLinkedList() {
            this.head = null;
            this.tail = null;
        }

        public void insertNode(int nodeData) {
            SinglyLinkedListNode node = new SinglyLinkedListNode(nodeData);

            if (this.head == null) {
                this.head = node;
            } else {
                this.tail.next = node;
            }

            this.tail = node;
        }
    }

    final static Comparator<SinglyLinkedListNode> LIST_NODE_COMPARATOR = (a, b) -> a.data - b.data;

    /**
     * This is the method to implement in the test.
     *
     * @param k
     * @param list
     * @return
     */
    static SinglyLinkedListNode sort(int k, SinglyLinkedListNode list) {
        if (k < 2) return list;
        // Write your code here.
        SinglyLinkedListNode[] nodes = toArray(list);
        k = Math.min(k, nodes.length);
        int start = 0, end = k, len = nodes.length;
        while (start <= len) {
            end = Math.min(end, len);
            Arrays.sort(nodes, start, end, LIST_NODE_COMPARATOR);
            start += k;
            end += k;
        }

        for (int i = 0; i < len - 1; i++)
            nodes[i].next = nodes[i + 1];

        nodes[len - 1].next = null;
        return nodes[0];
    }

    private static SinglyLinkedListNode[] toArray(SinglyLinkedListNode list) {
        int size = getSize(list);
        SinglyLinkedListNode[] ar = new SinglyLinkedListNode[size];
        for (int i = 0; i < size; i++, list = list.next)
            ar[i] = list;

        return ar;
    }

    private static int getSize(SinglyLinkedListNode list) {
        int count = 0;
        SinglyLinkedListNode iterator = list;
        while (iterator != null) {
            iterator = iterator.next;
            count++;
        }

        return count;
    }

    /**
     * method for the second problem to be implemented.
     *
     * @param n
     * @param r
     * @return
     */
    static long calculateCombinations(int n, int r) {
        // recursive way;
        if (r == 0)
            return 1;

        return calculateCombinations(n - 1, r - 1) * n / r;
    }

    /**
     * Iterative way
     */
    private static long ncr(int n, int r) {
        r = Math.min(r, n - r);
        long result = 1L;
        n = n - r + 1;

        for (int i = 1; i <= r; i++, n++)
            result = result * n / i;

        return result;
    }
}
