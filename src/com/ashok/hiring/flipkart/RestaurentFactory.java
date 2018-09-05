package com.ashok.hiring.flipkart;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class RestaurentFactory {
    private static AtomicLong idSequence = new AtomicLong(1);
    private static final Map<String, Set<Restaurent>> RESTAURENTS_BY_NAME = new ConcurrentHashMap<>();
    private static final Map<String, Set<Restaurent>> RESTAURENTS_BY_ADDRESS = new ConcurrentHashMap<>();
    private static final Map<Long, Restaurent> RESTAURENTS_BY_ID = new ConcurrentHashMap<>();

    public static synchronized Restaurent newRestaurent(RestaurentModel model) {
        Restaurent restaurent = new Restaurent(idSequence.getAndIncrement(), model.name, model.address, model.capacity);
        RESTAURENTS_BY_ID.put(restaurent.id, restaurent);
        putIfAbsent(RESTAURENTS_BY_NAME, restaurent.name, restaurent);
        putIfAbsent(RESTAURENTS_BY_ADDRESS, restaurent.address, restaurent);
        RESTAURENTS_BY_NAME.get(restaurent.name).add(restaurent);
        return restaurent;
    }

    private static <Key, Value> void putIfAbsent(Map<Key, Set<Value>> map, Key key, Value value) {
        if (!map.containsKey(key)) map.put(key, new HashSet<>());
        map.get(key).add(value);
    }

    private static Set<Restaurent> findByName(String name) {
        return RESTAURENTS_BY_NAME.get(name);
    }

    private static Set<Restaurent> findByAddress(String address) {
        return RESTAURENTS_BY_ADDRESS.get(address);
    }

    private static Restaurent findById(long id) {
        return RESTAURENTS_BY_ID.get(id);
    }

}
