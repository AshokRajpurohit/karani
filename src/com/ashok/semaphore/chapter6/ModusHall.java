package com.ashok.semaphore.chapter6;

import com.ashok.lang.concurrency.Turnstile;

import java.util.concurrent.Semaphore;

/**
 * Problem Statement: Please see the e-book.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class ModusHall {
    private int heathens = 0, prudes = 0;
    private Status status = Status.NEUTRAL;
    private final Semaphore mutex = new Semaphore(1), heathenQueue = new Semaphore(0), prudeQueue = new Semaphore(0);
    private final Turnstile heathenTurn = new Turnstile(1), prudeTurn = new Turnstile(1);

    public void heathen() throws InterruptedException {
        enterHeathen();

        // cross the field.

        exitHeathen();
    }

    public void prude() throws InterruptedException {
        enterPrude();

        // code to cross field.

        exitPrude();
    }

    public void enterHeathen() throws InterruptedException {
        heathenTurn.passThrough();

        mutex.acquire();
        heathens++;

        switch (status) {
            case NEUTRAL:
                status = Status.HEATHENS;
                mutex.release();
                break;
            case PRUDES:
                if (heathens > prudes) {
                    status = Status.TRANS_HEATHEN;
                    prudeTurn.acquire();
                }
                mutex.release();
                heathenQueue.acquire();
                break;
            case TRANS_HEATHEN:
                mutex.release();
                heathenQueue.acquire();
                break;
            default:
                mutex.release();
        }
    }

    public void exitHeathen() throws InterruptedException {
        mutex.acquire();
        heathens--;

        if (heathens == 0) {
            if (status == Status.TRANS_PRUDE) {
                prudeTurn.release();
            } else if (prudes > 0) {
                prudeQueue.release(prudes);
                status = Status.PRUDES;
            } else
                status = Status.NEUTRAL;
        }

        if (status == Status.HEATHENS && prudes > heathens) {
            status = Status.TRANS_PRUDE;
            heathenTurn.acquire();
        }

        mutex.release();
    }

    public void enterPrude() throws InterruptedException {
        prudeTurn.passThrough();
        mutex.acquire();
        prudes++;

        switch (status) {
            case NEUTRAL:
                status = Status.PRUDES;
                mutex.release();
                break;
            case HEATHENS:
                if (heathens < prudes) {
                    status = Status.TRANS_PRUDE;
                    heathenTurn.acquire();
                }
                mutex.release();
                prudeQueue.acquire();
                break;
            case TRANS_PRUDE:
                mutex.release();
                prudeQueue.acquire();
                break;
            default:
                mutex.release();
        }
    }

    public void exitPrude() throws InterruptedException {
        mutex.acquire();
        prudes--;

        if (prudes == 0) {
            if (status == Status.TRANS_HEATHEN) {
                heathenTurn.release();
            } else if (heathens > 0) {
                heathenQueue.release(heathens);
                status = Status.HEATHENS;
            } else
                status = Status.NEUTRAL;
        }

        if (status == Status.PRUDES && prudes < heathens) {
            status = Status.TRANS_HEATHEN;
            prudeTurn.acquire();
        }

        mutex.release();
    }


    enum Status {
        NEUTRAL, HEATHENS, PRUDES, TRANS_HEATHEN, TRANS_PRUDE
    }

}
