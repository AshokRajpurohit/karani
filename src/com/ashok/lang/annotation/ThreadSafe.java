package com.ashok.lang.annotation;

import java.lang.annotation.*;

/**
 * A Class annotated @ThreadSafe is one that handles concurrency
 * itself, programmer don't need to synchronize methods externally.
 *
 * @see java.util.concurrent.ConcurrentHashMap
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ThreadSafe {
}
