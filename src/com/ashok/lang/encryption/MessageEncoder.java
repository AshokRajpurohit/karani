package com.ashok.lang.encryption;

import java.util.List;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
@FunctionalInterface
public interface MessageEncoder<T> {
    List<T> encode(T message);
}
