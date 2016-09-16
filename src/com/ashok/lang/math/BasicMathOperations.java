package com.ashok.lang.math;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public interface BasicMathOperations<K> {
    public K add(K a, K b);

    public K subtract(K a, K b);

    public K multiply(K a, K b);

    public K divide(K a, K b);

    public K remainder(K a, K b);

    public K power(K a, K b);
}
