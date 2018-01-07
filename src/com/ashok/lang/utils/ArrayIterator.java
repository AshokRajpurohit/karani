package com.ashok.lang.utils;

import java.util.Iterator;

/**
 * This class {@code ArrayIterator} implements {@link Iterator} for
 * arrays. This is helpful when you want to iterate over array but not in straighforward
 * for each loop as in the most cases. Generally an array index parameter is used to know
 * which element is next and then we take one by one incrementing the index parameter each
 * time. To reduce this kind of effort, this class implements iterator and the user can
 * directly call {@link #next()} and/or {@link #hasNext()} to get the next element whenever
 * needed.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 * @see com.ashok.hackerRank.hiring.BlackRock#buildTree(String)
 */
public class ArrayIterator<T> implements Iterator<T> {
    private T[] ar;
    private int index = 0;
    final int length;

    ArrayIterator(T[] ar) {
        this(ar, 0, ar.length);
    }

    ArrayIterator(T[] ar, int start) {
        this(ar, start, ar.length);
    }

    ArrayIterator(T[] ar, int start, int end) {
        this.ar = ar;
        index = Math.max(start, 0);
        length = (Math.min(ar.length, end));
        if (length <= index)
            throw new RuntimeException("invalid array range: " + start + ", " + end);
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        return ar[index++];
    }
}
