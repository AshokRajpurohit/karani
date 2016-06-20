package com.ashok.lang.dsa;

/**
 * This class is to support reverse function multiple times on subarray.
 * It's not complete yet. Not able to figure out how to swap to ranges
 * efficiently.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class RangeQueryReverse {
    private Node root;

    final static class Node {
        Node left, right;
        int start, end;
        boolean reverse = false;

        Node(int i, int j) {
            start = i;
            end = j;
            if (i == j)
                return;

            int mid = (i + j) >>> 1;
            left = new Node(i, mid);
            right = new Node(mid + 1, j);
        }

        void reverse(int l, int r) {
            if (start == l && end == r) {
                reverse = !reverse;
                return;
            }

            int mid = (start + end) >>> 1;
            if (reverse) {
                left.reverse();
                right.reverse();
                reverse = false;
                Node temp = left;
                left = right;
                right = temp;
            }

            if (r <= mid) {
                left.normalize(start, mid);
                left.reverse(l, r);
                return;
            }

            if (l > mid) {
                right.normalize(mid + 1, end);
                right.reverse(l, r);
                return;
            }

            left.reverse(l, mid);
            right.reverse(mid + 1, r);
            swap(start, end, l, r);
        }

        void swap(int s, int e, int l, int r) {
            // le kanjar
            // koi clue nhin mil rha kanjar.
        }

        void normalize(int l, int r) {
            start = l;
            end = r;
        }

        void reverse() {
            reverse = !reverse;
        }
    }
}
