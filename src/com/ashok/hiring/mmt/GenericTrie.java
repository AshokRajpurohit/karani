package com.ashok.hiring.mmt;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class GenericTrie<T> {
    TrieNode<T> root = new TrieNode<T>('a');

    final class TrieNode<T> {
        private TrieNode<T>[] map = (TrieNode<T>[]) Array.newInstance(TrieNode.class, 26);
        final char ch;
        final List<T> objects = new LinkedList<T>();

        TrieNode(char ch) {
            this.ch = ch;
        }

        void add(char[] ar, int index, T o) {
            if (index == ar.length) {
                objects.add(o);
                return;
            }

            if (map[getIndex(ar[index])] != null) {
                TrieNode<T> node = new TrieNode<T>(ar[index]);
                node.add(ar, index++, o);
                map[getIndex(ar[index])] = node;
            }
        }

        private int getIndex(char ch) {
            if (ch >= 'a') ch -= 'a';
            if (ch >= 'A') ch -= 'A';
            return ch;
        }
    }
}
