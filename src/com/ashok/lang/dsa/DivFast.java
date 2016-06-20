package com.ashok.lang.dsa;

/**
 * This class implements a faster method of division.
 * for a 32 bit number the maximum number of comparision (worst case scenario) is 31+31 = 62 times.
 * and maximum number of subtraction and additions are 31 each.
 * @author Main Rajpurohit
 */

public class DivFast {
    private DivFast() {
        super();
    }

    /**
     *
     * @param N
     * @param D
     * this method calculates division and reminder and save it to the parameter of the class.
     */
    public static int div(int N, int D) {
        int result = 0;
        int rtemp = 1;
        int sign = (((N >= 0) && (D >= 0)) || ((N < 0) && (D < 0))) ? 1 : 0;
        N = N >= 0 ? N : -N;
        D = D >= 0 ? D : -D;
        int dtemp = D;


        while ((N >= dtemp) && (dtemp < 1073741824)) {
            dtemp = dtemp << 1;
            rtemp = rtemp << 1;
        }

        if (N < dtemp) {
            dtemp = dtemp >> 1;
            rtemp = rtemp >> 1;
        }

        while (rtemp != 0) {
            if (N >= dtemp) {
                N = N - dtemp;
                result = result + rtemp;
            }
            dtemp = dtemp >> 1;
            rtemp = rtemp >> 1;
        }

        if (sign == 0) {
            result = -result;
            N = -N;
        }

        return result;
    }

    public static int mod(int N, int D) {
        int result = 0;
        int rtemp = 1;
        int sign = (((N >= 0) && (D >= 0)) || ((N < 0) && (D < 0))) ? 1 : 0;
        N = N >= 0 ? N : -N;
        D = D >= 0 ? D : -D;
        int dtemp = D;


        while ((N >= dtemp) && (dtemp < 1073741824)) {
            dtemp = dtemp << 1;
            rtemp = rtemp << 1;
        }

        if (N < dtemp) {
            dtemp = dtemp >> 1;
            rtemp = rtemp >> 1;
        }

        while (rtemp != 0) {
            if (N >= dtemp) {
                N = N - dtemp;
                result = result + rtemp;
            }
            dtemp = dtemp >> 1;
            rtemp = rtemp >> 1;
        }

        if (sign == 0) {
            result = -result;
            N = -N;
        }

        return N;
    }
}
