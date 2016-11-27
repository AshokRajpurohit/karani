package com.ashok.lang.dsa;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class GenericFenwickTree<T> {
    private final GroupOperator<T> operator;
    private final T[] array;
    private int size = 0;
    private final int capacity;

    public GenericFenwickTree(int capacity, GroupOperator<T> operator) {
        this.capacity = capacity;
        this.operator = operator;
        array = (T[]) new Object[capacity];
    }

    public GenericFenwickTree(T[] ar, GroupOperator<T> operator) {
        this(ar.length, operator);

        for (T value : ar)
            add(value);
    }

    public void add(T value) {
        array[size++] = value;

        if (size == capacity)
            populate();
    }

    private void populate() {
        for (int i = 0; i < capacity; i++)
            updateTree(i);
    }

    public void update(int index, T value) {
        array[index] = operator.operation(array[index], value);
        updateTree(index, value);
    }

    public void replace(int index, T newValue) {
        T oldValue = query(index, index);
        T updateValue = operator.inverseOperation(newValue, oldValue);
        update(index, updateValue);
    }

    /**
     * Returns the grouped value for elements from start to end index, both inclusive.
     *
     * @param start
     * @param end
     * @return
     */
    public T query(int start, int end) {
        T result = operator.newInstance();
        while (end >= start) {
            result = operator.operation(result, array[end]);
            end = end & (end + 1);
            end--;
        }

        start--;
        while (start > end) {
            result = operator.inverseOperation(result, array[start]);
            start = start & (start + 1);
            start--;
        }

        return result;
    }

    private void updateTree(int index) {
        int nextIndex = index | (index + 1);

        if (nextIndex >= capacity)
            return;

        array[nextIndex] = operator.operation(array[nextIndex], array[index]);
    }

    private void updateTree(int index, T value) {
        int nextIndex = index | (index + 1);

        if (nextIndex >= capacity)
            return;

        array[nextIndex] = operator.operation(array[nextIndex], value);
        updateTree(nextIndex, value);
    }
}
