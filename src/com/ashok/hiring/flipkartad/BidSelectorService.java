package com.ashok.hiring.flipkartad;

import java.util.Collection;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class BidSelectorService {
    public static Bid selectBid(Collection<Bid> bids, BidSelector selector) {
        Bid bid = selector.selectBid(bids);
        return bid;
    }
}
