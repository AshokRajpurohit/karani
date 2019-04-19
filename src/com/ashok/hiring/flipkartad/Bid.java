package com.ashok.hiring.flipkartad;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Bid {
    final Advertiser advertiser;
    final int bidPrice;

    Bid(Advertiser advertiser, int price) {
        this.advertiser = advertiser;
        bidPrice = price;
    }
}
