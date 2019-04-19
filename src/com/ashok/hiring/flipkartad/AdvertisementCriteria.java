package com.ashok.hiring.flipkartad;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class AdvertisementCriteria {
    public static final AtomicInteger sequence = new AtomicInteger(1);
    final Advertisement advertisement;
    final int id = sequence.getAndIncrement();
    public volatile AtomicInteger budget = new AtomicInteger(0);

    public final int ageFrom, ageTo;
    public final Gender gender;

    AdvertisementCriteria(Advertisement advertisement, int ageFrom, int ageTo, char gender) {
        this.advertisement = advertisement;
        this.ageFrom = ageFrom;
        this.ageTo = ageTo;
        this.gender = Gender.getGender(gender);
    }
}
