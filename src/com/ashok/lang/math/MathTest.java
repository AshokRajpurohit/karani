/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.lang.math;

import com.ashok.lang.encryption.EncryptionUtil;
import com.ashok.lang.encryption.MessageDecoder;
import com.ashok.lang.encryption.MessageEncoder;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class is for the book
 * 'A Friendly Introduction to Number Theory by Joseph H. Silverman'.
 * All the programs are to solve excersise problems.
 * Any generic program will be moved out of this class eventually.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MathTest {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        MathTest a = new MathTest();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        int start = 10000000, end = start + 2000;
        out.print(Prime.primesInRange(start, end));
        out.flush();
        out.println(PureMath.smallestPrimitiveRoot(1000000007));
        out.flush();
        Function<int[], long[][]> matrixArGenerator = ar -> IntStream.range(0, ar.length)
                .mapToObj(i -> {
                    long[] row = new long[ar.length];
                    row[ar[i]] = 1;
                    return row;
                }).toArray(t -> new long[t][]);

        Function<int[], Matrix> matrixFunction = ar -> new Matrix(matrixArGenerator.apply(ar));
        Predicate<int[]> hasZero = ar -> Arrays.stream(ar).anyMatch(e -> e == 0);
        IntFunction<int[]> rootIndexFunction = n -> {
            int[] ar = new int[n - 1];
            int r = 2;
            while (hasZero.test(ar)) {
                for (int i = 1, j = r; i < n; i++) {
                    ar[j - 1] = i;
                    j = j * r % n;
                }
                r++;
            }
            return ar;
        };

        ToIntFunction<Matrix> identityPowerFunction = m -> {
            if (m.isIdentityMatrix()) return 1;
            Matrix res = m.clone();
            int power = 1;
            while (!res.isIdentityMatrix()) {
                res = res.multiply(m);
                power++;
            }

            return power;
        };

        Function<List<Long>, String> toStringFunc = list -> list.stream().reduce("", (a, b) -> a + b, (a, b) -> a + b);
        EncryptionUtil.RSAPrivateKey key = new EncryptionUtil.RSAPrivateKey(10139, 10259, 10007);
        MessageEncoder<Long> encoder = EncryptionUtil.getRSAEncoder(key.publicKey);
        MessageDecoder<Long> decoder = EncryptionUtil.getRSADecoder(key);
        while (true) {
            String s = in.readLine();
            long time = System.currentTimeMillis();
            List<Long> encoded = EncryptionUtil.encodeMessage(s);
            String decoded = EncryptionUtil.decodeMessage(encoded);
            out.println("endcoded: ", encoded);
            out.println("decoded: ", decoded);
            List<List<Long>> encrypted = encoded.stream().map(m -> encoder.encode(Long.valueOf(m))).collect(Collectors.toList());
            out.println(encoded);
            out.println(encrypted);
            List<Long> decrypted = encrypted.stream().map(m -> decoder.decode(m)).collect(Collectors.toList());
            out.println(decrypted);
            out.println(toStringFunc.apply(decrypted));
            decoded = EncryptionUtil.decodeMessage(decrypted);
            out.println(decoded);
            out.println("time taken in millis is " + (System.currentTimeMillis() - time));
            out.flush();
        }
    }

    private static long process(int a, int b) {
        long res = 1L * a * a * a + 1L * b * b * b;

        if (Numbers.isSquare(res))
            return (long) Math.sqrt(res);

        return -1;
    }

    private static boolean primitive(long a, long b, long c) {
        if (a == b && a != 2)
            return false;

        long gcd = ModularArithmatic.gcd(a, b);

        return gcd == 1 || !Numbers.isSquare(gcd);
    }
}
