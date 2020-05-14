package com.ashok.lang.encryption;

import com.ashok.lang.math.Power;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class ElGammalEncoder implements MessageEncoder<Long> {
    private final long g; // g is a primitive root.
    private final long a, p; // a is g raised to power k and p is prime
    private final Random random = new Random();

    public ElGammalEncoder(final long g, final long a, final long p) {
        this.g = g;
        this.a = a;
        this.p = p;
    }

    @Override
    public List<Long> encode(Long message) {
        int r = Math.abs(random.nextInt()) + 2;
        long e1 = Power.pow(g, r, p);
        long e2 = message * Power.pow(a, r, p) % p;
        return Arrays.asList(e1, e2);
    }
}
