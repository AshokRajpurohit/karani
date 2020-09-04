package com.ashok.lang.math;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class SquareRoot {
    public static String sqrt(long n, int precision) {
        ContinuedFraction continuedFraction = new SquareRootContinuedFraction(n);
        LinkedList<Fraction> fractionList = new LinkedList<>();
        BigInteger base = new BigInteger(String.valueOf(n));

        while (true) {
            fractionList.addLast(continuedFraction.toFraction());
            BigFraction f = ContinuedFraction.evaluate(fractionList);
            if (f.denominator.toString().length() > precision) {
                return divide(f.numerator, f.denominator, precision);
            }

            continuedFraction = continuedFraction.nextFraction();
        }
    }

    public static String divide(BigInteger num, BigInteger den, int precision) {
        char[] ar = new char[precision];
        Arrays.fill(ar, '0');
        char[] res = new BigInteger(num.toString() + String.valueOf(ar)).divide(den).toString().toCharArray();
        char[] result = new char[res.length + 1];
        System.arraycopy(res, 0, result, 0, res.length - precision);
        result[res.length - precision] = '.';
        System.arraycopy(res, res.length - precision, result, result.length - precision, precision);
        return String.valueOf(res);
    }
}
