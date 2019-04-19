package com.ashok.hiring.flipkartad;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Advertisements {
    public static final AtomicInteger sequence = new AtomicInteger(1);
    public static List<Advertisement> advertisements = new ArrayList<>();

    static {
        createAdvertisement(100);
        createAdvertisement(50);
        createAdvertisement(25);
        createAdvertisement(145);
        createAdvertisement(135);
    }

    public static Advertisement createAdvertisement(int cost) {
        synchronized (advertisements) {
            Advertisement ad = new Advertisement(sequence.getAndIncrement(), cost);
            advertisements.add(ad);
            return ad;
        }
    }
}
