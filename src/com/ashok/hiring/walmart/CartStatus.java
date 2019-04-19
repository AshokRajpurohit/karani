package com.ashok.hiring.walmart;

import sun.plugin.dom.exception.InvalidStateException;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public enum CartStatus {
    ACTIVE {
        @Override
        CartStatus setStatus(Cart cart, CartStatus newStatus) {
            if (this == newStatus)
                throw new InvalidStateException("state can't be changed from " + this + " to " + newStatus);

            return newStatus;
        }
    }, ORDERED {
        @Override
        CartStatus setStatus(Cart cart, CartStatus newStatus) {
            if (this == newStatus || this == ACTIVE)
                throw new InvalidStateException("state can't be changed from " + this + " to " + newStatus);

            return newStatus;
        }
    }, DISCARDED {
        @Override
        CartStatus setStatus(Cart cart, CartStatus newStatus) {
            if (this == newStatus)
                throw new InvalidStateException("state can't be changed from " + this + " to " + newStatus);

            return newStatus;
        }
    };

    abstract CartStatus setStatus(Cart cart, CartStatus status);
}
