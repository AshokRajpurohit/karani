package com.ashok.hiring.flipkartad;

import java.util.Collection;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public interface BidSelector {
    Bid selectBid(Collection<Bid> bids);
}
