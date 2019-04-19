package com.ashok.hiring.flipkartad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Advertisers {
    private static final AtomicInteger sequence = new AtomicInteger(1);
    public static final List<Advertiser> ADVERTISERS = new ArrayList<>();

    static {
        String[] advs = new String[]{"flipkart", "amazon", "done"};
        Arrays.stream(advs).forEach(t -> createAdvertiser(t));
    }

    public static Advertiser createAdvertiser(String name) {
        synchronized (ADVERTISERS) {
            Advertiser advertiser = new Advertiser(sequence.getAndIncrement(), name);
            ADVERTISERS.add(advertiser);
            return advertiser;
        }
    }

    public static Advertiser getAdvertiser(int id) {
        return ADVERTISERS.get(id);
    }
}
