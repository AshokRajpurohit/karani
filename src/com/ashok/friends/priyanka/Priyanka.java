/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.priyanka;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.*;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Priyanka {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        Priyanka a = new Priyanka();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        HashMap<Key, Value> hashMap = new HashMap<>();
        hashMap.values().stream()
                .filter((t) -> (t.value & 1) == 0)
                .sorted((a, b) -> a.value - b.value)
                .forEach((t) -> out.println(t));

        hashMap.entrySet().stream()
                .sorted((a, b) -> a.getValue().value - b.getValue().value)
                .forEach((t) -> out.println("key: " + t.getKey() + " value " + t.getValue()));

        Set<Map.Entry<Key, Value>> set = hashMap.entrySet();
        List<Map.Entry<Key, Value>> setList = new LinkedList<>(set);
        Collections.sort(setList, new Comparator<Map.Entry<Key, Value>>() {
            @Override
            public int compare(Map.Entry<Key, Value> o1, Map.Entry<Key, Value> o2) {
//                return o1.getValue().compareTo(o2.getValue());
                return o1.getValue().value - o2.getValue().value;
            }
        });

        for (Map.Entry entry : setList) {
            out.println(entry.getKey());
        }

        List<Value> valueList = new LinkedList<>(hashMap.values());
        Collections.sort(valueList, new Comparator<Value>() {
            @Override
            public int compare(Value o1, Value o2) {
                return o1.value - o2.value;
            }
        });
        for (Map.Entry<Key, Value> entry : hashMap.entrySet()) {
            out.println(entry.getKey().key + ", " + entry.getValue().value);
        }


        Collections.sort(valueList, (a, b) -> a.value - b.value);
        TreeMap<Key, Pair> map = new TreeMap<>(keyComparaotor);

        for (Map.Entry entry : map.entrySet()) {
            out.println(entry.getValue());
        }

    }

    final static Comparator<Key> keyComparaotor = new Comparator<Key>() {
        @Override
        public int compare(Key o1, Key o2) {
            return o1.key - o2.key;
        }
    };

    final static class CustomMap {
        void put() {

        }

    }

    /*final static class KeyComparator implements Comparator<Key> {
        public int compare(Key k1, Key k2) {
            return k1.key - k2.key;
        }
    }*/

    final static class Key {
        int key = 0;

        public String toString() {
            return "" + key;
        }
    }

    final static class Value implements Comparable<Value> {
        int value = 0;

        @Override
        public int compareTo(Value o) {
            return this.value - o.value;
        }

        public String toString() {
            return "" + value;
        }
    }

    final static class Pair {
        Key key;
        Value value;

        public int hashCode() {
            return key.hashCode();
        }

        public boolean equals(Object o) {
            Pair pair = (Pair) o;
            return key == pair.key;
        }
    }
}
