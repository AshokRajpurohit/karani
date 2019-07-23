package com.ashok.hiring.walmart;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public enum CartStatus {
    ACTIVE {
        @Override
        void putOrder(Cart cart) {
            cart.status = ORDERED;
        }

        @Override
        void cancleOrder(Cart cart) {
            cart.status = DISCARDED;
        }
    }, ORDERED {
        @Override
        void cancleOrder(Cart cart) {
            cart.status = DISCARDED;
        }
    }, DISCARDED {
        @Override
        void putOrder(Cart cart) {
            cart.status = ORDERED;
        }
    };

    void putOrder(Cart cart) {
        // default operation, do not update state when not necessary.
    }

    void cancleOrder(Cart cart) {
        // default operation, do not update state when not necessary.
    }
}
