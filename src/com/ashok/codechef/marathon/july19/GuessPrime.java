/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.july19;

import com.sun.tools.javac.util.Assert;
import com.sun.tools.javac.util.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Problem Name: Guess the Prime!
 * Link: https://www.codechef.com/JULY19A/problems/GUESSPRM
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class GuessPrime {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int LIMIT_SQRT = (int) Math.sqrt(2000000000), LIMIT = LIMIT_SQRT * LIMIT_SQRT;
    private static int[] primes = generatePrimes(LIMIT_SQRT);

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        InterativeSystem system = new DefaultInteractiveSystem();
        while (t > 0 && run(system)) {
            t--;
        }
    }

    private static boolean run(InterativeSystem system) throws IOException {
        int query = 178;
        int response = system.submitQuery(query);
        if (response == query * query) { // the answer is larger than query * query
            query = 31623;
            response = system.submitQuery(query);
            List<Integer> factors = collectFactors(1L * query * query - response);
            int limit = query;
            int answer = factors.stream().filter(f -> f > limit).findFirst().get();
            return system.checkAnswer(answer);
        }
        List<Integer> factors = collectFactors(1L * query * query - response); // the answer is one of the prime factors.
        factors = removeSmallFactors(factors, response);
        if (factors.size() == 1) return system.checkAnswer(factors.get(0));
        QueryGeneratorAndValidator queryGeneratorAndValidator = new QueryGeneratorAndValidator(factors);
        int query2 = queryGeneratorAndValidator.getValueForQuery();
        int response2 = system.submitQuery(query2);
        return system.checkAnswer(queryGeneratorAndValidator.findFactorForResponse(response2));
    }

    private static List<Integer> collectFactors(long num) {
        int root = (int) Math.sqrt(num);
        List<Integer> factors = new LinkedList<>();
        for (int e : primes) {
            if (e > root || num == 1) break;
            if (num % e != 0) continue;
            factors.add(e);
            num = removeFactor(num, e);
        }

        if (num != 1 && num < LIMIT) factors.add((int) num);
        return factors;
    }

    private static List<Integer> removeSmallFactors(List<Integer> factors, int limit) {
        return factors.stream().filter(factor -> factor > limit).collect(Collectors.toList());
    }

    /**
     * calculates a raised to power b and remainder to mod
     *
     * @param a
     * @param b
     * @param mod
     * @return
     */
    private static long pow(long a, long b, long mod) {
        if (b == 0)
            return 1;

        a = a % mod;
        if (a < 0)
            a += mod;

        if (a == 1 || a == 0 || b == 1)
            return a;

        long r = Long.highestOneBit(b), res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res * a) % mod;
            }
        }

        if (res < 0) res += mod;
        return res;
    }

    private static long inverseModulo(long n, long p) {
        return pow(n, p - 2, p);
    }

    private static long removeFactor(long n, int factor) {
        while (n % factor == 0)
            n /= factor;

        return n;
    }

    /**
     * This function generates prime numbers upto given integer n and
     * returns the array of primes upto n (inclusive).
     *
     * @param n prime numbers upto integer n
     * @return
     */
    private static int[] generatePrimes(int n) {
        boolean[] ar = new boolean[n + 1];
        int root = (int) Math.sqrt(n);

        for (int i = 2; i <= root; i++) {
            while (ar[i])
                i++;
            for (int j = i * 2; j <= n; j += i) {
                if (!ar[j]) {
                    ar[j] = j % i == 0;
                }
            }
        }

        int count = 0;
        for (int i = 2; i <= n; i++)
            if (!ar[i])
                count++;

        int[] ret = new int[count];

        for (int i = 2, j = 0; i <= n; i++) {
            if (!ar[i]) {
                ret[j] = i;
                j++;
            }
        }
        return ret;
    }

    final static class QueryGeneratorAndValidator {
        private final List<FactorInfo> factorInfos = new LinkedList<>();
        private int queryValue = -1;

        private QueryGeneratorAndValidator(List<Integer> factors) {
            factors.forEach(factor -> factorInfos.add(new FactorInfo(factor)));
            factorInfos.get(0).squareResidue = 0;
            factorInfos.get(0).residue = 0;
            initialize();
        }

        private void initialize() {
            List<SquareResidueProvider> squareResidueProviders = factorInfos.stream()
                    .skip(1)
                    .map(factorInfo -> new SquareResidueProvider(factorInfo.factor, factorInfos.size()))
                    .collect(Collectors.toList());

            PriorityQueue<SquareResidueProvider> residueProviderHeap = new PriorityQueue<>(Comparator.comparingInt(p -> p.prime));
            residueProviderHeap.addAll(squareResidueProviders);
            while (!residueProviderHeap.isEmpty()) {
                SquareResidueProvider top = residueProviderHeap.poll();
                int squareValue = top.topSquareResidue();
                if (factorInfos.stream().filter(f -> f.squareResidue == squareValue).count() != 0) {
                    top.remove();
                    residueProviderHeap.add(top);
                    continue;
                }

                FactorInfo factorInfo = factorInfos.stream().filter(f -> f.factor == top.prime).findFirst().get();
                factorInfo.squareResidue = top.topSquareResidue();
                factorInfo.residue = top.topResidue();
            }

            Assert.check(factorInfos.size() == factorInfos.stream().map(f -> f.squareResidue).collect(Collectors.toSet()).size());
        }

        int getValueForQuery() {
            if (queryValue != -1) return queryValue;
            long multi = factorInfos.stream().mapToLong(factor -> factor.factor).reduce((a, b) -> a * b).getAsLong();
            long result = factorInfos.stream()
                    .skip(1)
                    .mapToLong(factorInfo -> {
                        long mj = multi / factorInfo.factor;
                        long x = inverseModulo(mj, factorInfo.factor) * factorInfo.residue % factorInfo.factor;
                        return x * mj % multi;
                    })
                    .sum();

            queryValue = (int) (result % multi);
            return queryValue;
        }

        int findFactorForResponse(int response) {
            long factorCounts = factorInfos
                    .stream()
                    .filter(factorInfo -> factorInfo.squareResidue == response)
                    .count();

            if (factorCounts != 1) throw new RuntimeException("problem");
            return factorInfos
                    .stream()
                    .filter(factorInfo -> factorInfo.squareResidue == response)
                    .findFirst()
                    .get()
                    .factor;
        }
    }

    final static class SquareResidueProvider {
        final int prime;
        // mapping of squaered residue and residue for a given prime number
        List<Pair<Integer, Integer>> squreResiduePairs = new LinkedList<>();

        SquareResidueProvider(int prime, int count) {
            this.prime = prime;
            initialize(count);
        }

        private void initialize(int count) {
            count = Math.min(count, prime);
            for (int i = 1; i <= count; i++) {
                squreResiduePairs.add(new Pair<>(i * i % prime, i));
            }

            Collections.sort(squreResiduePairs, Comparator.comparingInt(p -> p.fst));
        }

        private int topSquareResidue() {
            return squreResiduePairs.get(0).fst;
        }

        private int topResidue() {
            return squreResiduePairs.get(0).snd;
        }

        private int remove() {
            return squreResiduePairs.remove(0).fst;
        }
    }

    final static class FactorInfo {
        final int factor;
        int residue = -1, squareResidue = -1;

        FactorInfo(int factor) {
            this.factor = factor;
        }
    }

    interface InterativeSystem {
        boolean checkAnswer(int answer) throws IOException;

        int submitQuery(int n) throws IOException;
    }

    final static class MockInteractiveSystem implements InterativeSystem {
        private final int result;

        MockInteractiveSystem(final int result) {
            this.result = result;
        }

        @Override
        public boolean checkAnswer(int answer) throws IOException {
//            out.println("2 " + answer);
//            out.println(answer == result ? "Yes" : "No");
//            out.flush();
            if (answer != result) throw new RuntimeException("expected: " + result + ", actual: " + answer);
            return answer == result;
        }

        @Override
        public int submitQuery(int n) throws IOException {
//            out.println("1 " + n);
            long res = 1L * n * n % result;
//            out.println("query result: " + res);
//            out.flush();
            return (int) res;
        }
    }

    final static class DefaultInteractiveSystem implements InterativeSystem {

        @Override
        public boolean checkAnswer(int answer) throws IOException {
            out.println("2 " + answer);
            out.flush();
            return in.read().equals("Yes");
        }

        @Override
        public int submitQuery(int x) throws IOException {
            out.println("1 " + x);
            out.flush();
            return in.readInt();
        }
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

        public int readInt() throws IOException {
            int number = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            if (bufferSize == -1)
                throw new IOException("No new bytes");
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                number = (number << 3) + (number << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            return number * s;
        }

        public String read() throws IOException {
            StringBuilder sb = new StringBuilder();
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                         '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                        buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }
    }
}