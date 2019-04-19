package com.ashok.hiring.flipkartad;

import java.util.Collection;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public enum BidSelectingStrategy implements BidSelector {
    HIGHEST {
        @Override
        public Bid selectBid(Collection<Bid> bids) {
            return bids.stream().max((a, b) -> a.bidPrice - b.bidPrice).get();
        }
    },

    SECOND_HIGHEST {
        @Override
        public Bid selectBid(Collection<Bid> bids) {
            Bid highest = HIGHEST.selectBid(bids);
            return bids.stream().filter((t) -> t != highest).max((a, b) -> a.bidPrice - b.bidPrice).get();
        }
    };
}
