package com.ashok.lang.dsa;

/**
 * This is the fast multiplication implementation using binary operations
 * and additions.
 * @author Ashok Rajpurohit
 */
public class MultiFast {
    private MultiFast() {
        super();
    }

    public static long fastMulti(int x, int y) {
        if (x == 0 || y == 0)
            return 0;

        if (x == 1)
            return y;

        if (y == 1)
            return x;

        long res = 0;
        boolean sign = (x > 0 && y > 0) || (x < 0 && y < 0);
        x = x > 0 ? x : -x;
        long xt = x;
        y = y > 0 ? y : -y;
        long yt = 1;
        while (yt < y) {
            yt = yt << 1;
            xt = xt << 1;
        }

        while (yt > y) {
            yt = yt >>> 1;
            xt = xt >>> 1;
        }

        while (yt > 0) {
            if ((y & yt) != 0) {
                res += xt;
            }
            yt = yt >>> 1;
            xt = xt >>> 1;
        }

        return sign ? res : -res;
    }
}
