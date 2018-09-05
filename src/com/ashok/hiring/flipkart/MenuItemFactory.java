package com.ashok.hiring.flipkart;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class MenuItemFactory {
    private static AtomicLong idSequence = new AtomicLong(1);
    private static final Map<String, Set<MenuItem>> ITEMS_BY_NAME = new ConcurrentHashMap<>();
    private static final Map<Long, MenuItem> ITEMS_BY_ID = new ConcurrentHashMap<>();


    public static synchronized MenuItem newMenuItem(Restaurent restaurent, String name, int price) {
        MenuItem item = new MenuItem(idSequence.getAndIncrement(), name, price, restaurent);
        ITEMS_BY_ID.put(item.id, item);
        putIfAbsent(ITEMS_BY_NAME, item.name, item);
        ITEMS_BY_NAME.get(item.name).add(item);
        return item;
    }

    private static <Key, Value> void putIfAbsent(Map<Key, Set<Value>> map, Key key, Value value) {
        if (!map.containsKey(key)) map.put(key, new HashSet<>());
        map.get(key).add(value);
    }
}
