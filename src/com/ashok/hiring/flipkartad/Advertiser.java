package com.ashok.hiring.flipkartad;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Advertiser {
    public static final Advertiser DEFAULT = Advertisers.createAdvertiser("DEFAULT");
    final int id;
    final String name;

    Advertiser(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return "[" + id + " " + name;
    }
}
