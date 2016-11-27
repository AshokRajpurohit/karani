package com.ashok.lang.dsa;

/**
 * The {@code GroupOperator} class is for {@link GenericFenwickTree} class to implement
 * the group operation for the required problem.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public interface GroupOperator<T> {
    /**
     * The method performs operation between two elements, it can be
     * addition, multiplication, subtraction or any kind of operation.
     * <p>
     * This method can return a new instance or update the same #first object
     * and return the same.
     *
     * @param first
     * @param second
     * @return operation result performed on #first and #second.
     */
    T operation(T first, T second);

    /**
     * This method nullify the operation performed in {@code #operation}, like
     * subtraction for addition type operation, division for multiplication etc.
     * <p>
     * This method can return a new instance or update the same #first object
     * and return the same.
     *
     * @param first
     * @param second
     * @return
     */
    T inverseOperation(T first, T second);

    /**
     * Returns a new instance of T to save query results and return the same.
     *
     * @return a new instance of T.
     */
    T newInstance();
}
