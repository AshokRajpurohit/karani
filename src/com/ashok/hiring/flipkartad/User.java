package com.ashok.hiring.flipkartad;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class User {
    Set<Advertisement> advertisementsExposed = new HashSet<>();
    public static Map<Integer, User> users = new ConcurrentHashMap<>();
    static AtomicInteger sequence = new AtomicInteger(0);
    final int id = sequence.incrementAndGet();
    final String name;
    final Gender gender;
    final int age;

    static {
        new User("Ashok", 'M', 32);
        new User("Sandhya", 'F', 52);
        new User("Sid", 'M', 2);
    }

    User(String name, char gender, int age) {
        this.name = name;
        this.gender = Gender.getGender(gender);
        this.age = age;
        users.put(id, this);
    }

    public int hashCode() {
        return id;
    }

}
