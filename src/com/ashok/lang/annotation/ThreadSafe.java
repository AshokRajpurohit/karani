package com.ashok.lang.annotation;

import java.lang.annotation.*;

/**
 * A Class annotated @ThreadSafe is one that handles concurrency
 * itself, programmer don't need to synchronize methods externally.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * @see java.util.concurrent.ConcurrentHashMap
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ThreadSafe {
}
