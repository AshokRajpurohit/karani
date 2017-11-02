package com.ashok.lang.dsa;

/**
 * This class is to generate random objects. This is complementary to {@link RandomStrings}.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public interface RandomObjects<T> {
    /**
     * Returns new random object of type <i>T</i>
     *
     * @return
     */
    T next();
}
