package com.ashok.lang.algorithms;

import com.ashok.lang.math.ModularArithmatic;
import com.ashok.lang.math.Numbers;
import com.ashok.lang.math.Power;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Encryption {
    private static final int OFFSET = 173;
    private static final String SEPERATOR = ":";
    private final int p, q, k, m, len;
    private final int phiM, phiPhiM, inverseK;

    public Encryption(int p, int q, int k) {
        this.p = p;
        this.q = q;
        this.k = k;
        m = p * q;
        len = Numbers.digitCounts(m);
        phiM = ModularArithmatic.totient(m);
        phiPhiM = ModularArithmatic.totient(phiM);
        inverseK = (int) ModularArithmatic.inverseModulo(k, phiM);
    }

    public String encrypt(String secret) {
        String normalizedString = normalize(secret.toCharArray());
        int[] codeBreaks = partition(normalizedString.toCharArray(), len);
        long[] finalNums = exponentiate(codeBreaks);
        return formString(finalNums, SEPERATOR);
    }

    public String decrypt(String encryptedSecret) {
        String[] partitions = encryptedSecret.split(SEPERATOR);
        long[] codeBreaks = toLongArray(partitions);
        long[] decodedCodeBreaks = decode(codeBreaks);
        String decodedString = formString(decodedCodeBreaks);
        return deNormalize(decodedString.toCharArray());
    }

    private String deNormalize(char[] ar) {
        int[] characters = partition(ar, 2);
        StringBuilder sb = new StringBuilder();
        for (int e : characters)
            sb.append((char) (e - OFFSET));

        return sb.toString();
    }

    private static long[] toLongArray(String[] ar) {
        long[] res = new long[ar.length];
        int index = 0;

        for (String e : ar)
            res[index++] = Long.valueOf(e);

        return res;
    }

    private long[] decode(long[] codeBreaks) {
        long[] res = new long[codeBreaks.length];
        for (int i = 0; i < codeBreaks.length; i++)
            res[i] = Power.pow(codeBreaks[i], inverseK, m);

        return res;
    }

    private long[] exponentiate(int[] ar) {
        long[] res = new long[ar.length];
        for (int i = 0; i < ar.length; i++)
            res[i] = Power.pow(ar[i], k, m);

        return res;
    }

    private static String formString(long[] ar, String seperator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ar.length; i++) {
            sb.append(ar[i]);
            if (i != ar.length - 1)
                sb.append(seperator);
        }
        return sb.toString();
    }

    private static String formString(long[] ar) {
        StringBuilder sb = new StringBuilder();
        for (long e : ar)
            sb.append(e);

        return sb.toString();
    }

    private static int[] partition(char[] ar, int len) {
        LinkedList<Integer> integers = new LinkedList<>();
        int index = 0;
        while (index < ar.length) {
            String num = new String(Arrays.copyOfRange(ar, index, Math.min(ar.length, index + len)));
            integers.add(Integer.valueOf(num));
            index += len;
        }

        int[] numbers = new int[integers.size()];
        index = 0;
        for (int e : integers)
            numbers[index++] = e;

        return numbers;
    }

    private static String normalize(char[] ar) {
        StringBuilder sb = new StringBuilder(ar.length);
        for (char ch : ar)
            sb.append(OFFSET + ch);

        return sb.toString();
    }
}
