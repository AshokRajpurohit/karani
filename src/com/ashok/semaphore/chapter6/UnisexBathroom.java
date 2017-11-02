package com.ashok.semaphore.chapter6;

import com.ashok.lang.concurrency.Turnstile;
import com.ashok.semaphore.chapter4.LightSwitch;

import java.util.concurrent.Semaphore;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class UnisexBathroom {
    private final Semaphore empty = new Semaphore(1);
    private final LightSwitch maleSwitch = new LightSwitch(), femaleSwitch = new LightSwitch();
    private final Semaphore maleMultiplex, femaleMultiplex;
    private final Turnstile turnstile = new Turnstile();

    public UnisexBathroom(int maxPersonsInBathroom) {
        maleMultiplex = new Semaphore(maxPersonsInBathroom);
        femaleMultiplex = new Semaphore(maxPersonsInBathroom);
    }

    public void enterMale() throws InterruptedException {
        turnstile.acquire();
        maleSwitch.lock(empty);
        turnstile.release();

        maleMultiplex.acquire();
        // bathroom code.
        maleMultiplex.release();

        maleSwitch.unlock(empty);
    }

    public void enterFemale() throws InterruptedException {
        turnstile.acquire();
        femaleSwitch.lock(empty);
        turnstile.release();

        femaleMultiplex.acquire();
        // bathroom code for female, like face washing, looking in mirror for hours, etc.
        femaleMultiplex.release();

        femaleSwitch.unlock(empty);
    }
}
