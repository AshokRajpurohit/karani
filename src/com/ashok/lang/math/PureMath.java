package com.ashok.lang.math;

import com.ashok.lang.encryption.CharDecoder;
import com.ashok.lang.encryption.CharEncoder;
import com.ashok.lang.encryption.EncryptionUtil;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.LongUnaryOperator;
import java.util.stream.IntStream;

/**
 * Class to support learnings from Number Theory Books (Silverman and Alan Baker)
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class PureMath {

    public static void main(String[] args) throws IOException {
//        testEncoderDecoder();
        InputReader in = new InputReader();
        Output out = new Output();
        long m = 1000000007;
        out.print(Prime.primesInRange(m, m + 100));
        out.flush();
        int[] primes = Prime.generatePrimes(10000000);
        int[] sumOfTwoSquares = IntStream.range(1, 1001)
                .flatMap(i -> IntStream.range(i, 1001).map(j -> i * i + j * j))
                .distinct()
                .sorted()
                .toArray();

        IntPredicate primalityCheck = n -> Arrays.binarySearch(primes, n) >= 0;
        IntPredicate primitiveRootExistence = n -> smallestPrimitiveRoot(n) != 0;
        IntPredicate divisibilityBy4 = PureMath::divisibleBy4;
        IntPredicate sumOfSquare = n -> Arrays.binarySearch(sumOfTwoSquares, n) >= 0;
        IntPredicate phi4 = n -> divisibleBy4(ModularArithmatic.totient(n));
        Function<int[], int[]> twoSumFunction = ar -> IntStream.range(0, ar.length)
                .flatMap(i -> IntStream.range(i, ar.length)
                .map(j -> ar[i] + ar[j])
                ).distinct()
                .sorted().toArray();
        while (true) {
            int p = in.readInt();
            long time = System.currentTimeMillis();
            int[] squares = IntStream.range(1, p).map(i -> i * i).toArray();
            int[] triangularNumbers = IntStream.range(1, p - 1).map(i -> i * (i + 1) / 2).toArray();
            int[] triSums = twoSumFunction.apply(triangularNumbers);
            int[] squareSums = twoSumFunction.apply(squares);
            int[] matches = Arrays.stream(triSums).filter(v -> Arrays.binarySearch(squareSums, v) >= 0).toArray();
            out.print(squareSums);
            out.print(triSums);
            out.print(matches);
            out.println("execution time: " + (System.currentTimeMillis() - time));
            out.flush();
        }
    }

    /**
     * Returns the table of indices based on primitive root <i>g</i> modulo prime <i>p</i>. To get index of <i>a</i>
     * modulo p, array can be used like: indices[a]. the value at index 0 is -1 since the p is not achievable in any
     * way through successive multiplication with same number.
     *
     * @param p a prime
     * @param g a primitive root
     * @return table of indices
     */
    public static int[] indicesTable(int p, int g) {
        int[] indices = new int[p];
        for (int i = 1, j = g; i < p; i++) {
            indices[j] = i;
            j = j * i % p;
        }

        return indices;
    }

    public static int[] indicesTable(int p) {
        return indicesTable(p, smallestPrimitiveRoot(p));
    }

    public static int smallestPrimitiveRoot(int p) {
        if (p == 2) return 1;
        if (p == 3) return 2;
        int[] primes = Prime.generatePrimes((int) isqrt(p));
        return Arrays.stream(primes)
                .parallel()
                .filter(v -> isPrimitiveRoot(v, p))
                .findAny().orElse(-1);
    }

    public static int orderOfModulo(int a, int p) {
        long res = a;
        int order = 1;
        while (res != p - 1 && res != 1 && order < p) {
            res = res * a % p;
            order++;
        }

        return res == p - 1 ? order << 1 : order;
    }

    public static boolean isPrimitiveRoot(final int root, final int p) {
        if (ModularArithmatic.gcd(root, p) != 1) {
            return false;
        }

        int phi = ModularArithmatic.totient(p);
        if (Power.pow(root, phi, p) != 1) return false;
        if (Numbers.isEven(phi) && Power.pow(root, phi >> 1, p) != p - 1) return false;
        return orderOfModulo(root, p) == phi;
    }

    public static int countSquareSumSolutions(final int n) {
        int root = (int) isqrt(n);
        return (int) IntStream.range(0, root + 1)
                .mapToLong(a -> IntStream.range(a, root + 1)
                        .filter(b -> a * a + b * b == n)
                        .count())
                .sum();
    }

    public static boolean isTriangularNumber(long n) {
        n <<= 1;
        long sqrt = mathSqrt(n);
        return sqrt * (sqrt + 1) == n;
    }

    /**
     * n can be composite number.
     *
     * @param n
     * @return
     */
    public static long getSquareSumPart(final long n) {
        if (primalityCheckRabinMiller(n)) return squareSumPart(n);
        long factor = getFactor(n), factorSquare = factor * factor;
        long m = n;
        while (m % factorSquare == 0) m /= factorSquare;
        long multiplier = isqrt(n / m);
        m /= factor;
        long a = getSquareSumPart(m), b = isqrt(m - a * a);
        long u = squareSumPart(factor), v = isqrt(factor - u * u);
        long x = u * a + v * b, y = Math.abs(u * b - v * a);
        return Math.min(x, y) * multiplier;
    }

    /**
     * p is a prime number.
     *
     * @param p
     * @return
     */
    public static int squareSumPart(long p) {
        if (p == 2) return 1;
        int A = squareRoot5Mod8(p - 1, p);
        return squareSumPart(A, 1, p);
    }

    /**
     * returns x where x<sup>2</sup>+y<sup>2</sup> = p.
     * The given values A and B are for the equation
     * A<sup>2</sup>+B<sup>2</sup> congruent to 0 modulo p.
     * This procedure is called <b>Fermat</b>'s <i>Descent Procedure</i>
     *
     * @param A initial value for A
     * @param B initial value for procedure
     * @param p prime p which is sum of two squares
     * @return the smaller number whose square is one of the squares.
     */
    public static int squareSumPart(long A, long B, long p) {
        if ((p & 3) != 1) throw new ArithmeticException("p should be congruent to 1 modulo 4");
        long LA = A * A, LB = B * B;
        long M = (A * A + B * B) / p;
        while (M > 1) {
            long u = A % M, v = B % M;
            if (M < (u << 1)) u -= M;
            if (M < (v << 1)) v -= M;
//            u = Math.min(u, M - u);
//            v = Math.min(v, M - v);
            long a = Math.abs((u * A + v * B)) / M, b = (Math.abs(u * B - v * A)) / M;
            A = a;
            B = b;
            M = (A * A + B * B) / p;
        }

        return (int) Math.min(A, B);
    }

    public static int squareRoot5Mod8(long a, long p) {
        if (Numbers.isSquare(a)) return (int) isqrt(a);
        if ((p & 7) != 5) throw new RuntimeException("p should be congruent to 5 modulo 8: " + a + ", " + p);
        long root1 = Power.pow(a, (p + 3) >> 3, p);
        if (root1 * root1 % p == a) return (int) root1;
        long root2 = (a << 1) * Power.pow(a << 2, (p - 5) >> 3, p);
        root2 %= p;
        if (root2 * root2 % p == a) return (int) root2;
        throw new ArithmeticException("square root for a modulo p does not exists: " + a + ", " + p);
    }

    /**
     * computes Jacobi symbol (q/p) where q and p can be non-prime.
     *
     * @param q
     * @param p
     * @return true if q is quadratic residue of p.
     */
/*
    public static boolean quadraticReciprocity1(int q, int p) {
        if (q == 1 || q == p || Numbers.isSquare(q)) return true;
        if (q == p - 1) return (p & 3) == 1;
        boolean result = true;
        while (true) {
            while ((q & 3) == 0) q >>>= 2;
            if (Numbers.isEven(q)) result = result == ()
        }
        if (q > p) return quadraticReciprocity(q % p, p);
        if (Numbers.isEven(q)) {
            int zeroes = Integer.numberOfTrailingZeros(q);
            q >>= zeroes;
            boolean result = quadraticReciprocity(q, p);
            if (Numbers.isEven(zeroes)) return result;
            int mod8 = p & 7;
            return mod8 == 1 || mod8 == 7 ? result : !result;
        }
        boolean fourMod1 = (q & 3) == 1 || (p & 3) == 1;
        return fourMod1 == quadraticReciprocity(p % q, q);
    }
*/

    /**
     * computes Jacobi symbol (q/p) where q and p can be non-prime.
     *
     * @param q
     * @param p
     * @return true if q is quadratic residue of p.
     */
    public static boolean quadraticReciprocity(int q, int p) {
        if (q == 1 || q == p || Numbers.isSquare(q)) return true;
        if (q == p - 1) return (p & 3) == 1;
        if (q > p) return quadraticReciprocity(q % p, p);
        if (Numbers.isEven(q)) {
            int zeroes = Integer.numberOfTrailingZeros(q);
            q >>= zeroes;
            boolean result = quadraticReciprocity(q, p);
            if (Numbers.isEven(zeroes)) return result;
            int mod8 = p & 7;
            return mod8 == 1 || mod8 == 7 ? result : !result;
        }
        boolean fourMod1 = (q & 3) == 1 || (p & 3) == 1;
        return fourMod1 == quadraticReciprocity(p % q, q);
    }

    /**
     * computes Jacobi symbol (q/p) where q and p can be non-prime.
     *
     * @param q
     * @param p
     * @return true if q is quadratic residue of p.
     */
    public static boolean quadraticReciprocity(long q, long p) {
        if (q < 0 || p < 0) throw new ArithmeticException("negative value for p or q: " + p + ", " + q);
        if (q == 0) return true;
        if (q == 1 || q == p || Numbers.isSquare(q)) return true;
        if (q == p - 1) return (p & 3) == 1;
        if (q > p) return quadraticReciprocity(q % p, p);
        if (Numbers.isEven(q)) {
            int zeroes = Long.numberOfTrailingZeros(q);
            q >>= zeroes;
            boolean result = quadraticReciprocity(q, p);
            if (Numbers.isEven(zeroes)) return result;
            long mod8 = p & 7;
            return mod8 == 1 || mod8 == 7 ? result : !result;
        }
        boolean fourMod1 = (q & 3) == 1 || (p & 3) == 1;
        return fourMod1 == quadraticReciprocity(p % q, q);
    }

    /**
     * computes Jacobi symbol (q/p) where q and p can be non-prime.
     *
     * @param q
     * @param p
     * @return true if q is quadratic residue of p.
     */
    public static boolean quadraticReciprocity(BigInteger q, BigInteger p) {
        if (p.bitLength() < 60 && q.bitLength() < 60) {
            return quadraticReciprocity(q.longValue(), p.longValue());
        }
        if (q == BigInteger.ONE || q.equals(p)) return true;
        int pInt = p.intValue();
        if (q.equals(p.subtract(BigInteger.ONE))) return (pInt & 3) == 1;
        if (q.compareTo(p) == 1) return quadraticReciprocity(q.mod(p), p);
        int a = q.getLowestSetBit();
        if (a > 0) {
            q = q.shiftRight(a);
            boolean result = quadraticReciprocity(q, p);
            if (Numbers.isEven(a)) return result;
            long mod8 = pInt & 7;
            return (mod8 == 1 || mod8 == 7) == result;
        }
        int qInt = q.intValue();
        boolean fourMod1 = (qInt & 3) == 1 || (pInt & 3) == 1;
        return fourMod1 == quadraticReciprocity(p.mod(q), q);
    }

    public static void testEncoderDecoder() throws IOException {
        InputReader in = new InputReader();
        Output out = new Output();
        CharEncoder encoder = EncryptionUtil.getStandardEncoder();
        CharDecoder decoder = EncryptionUtil.getStandardDecoder();
        while (true) {
            char ch = in.nextChar();
            int coded = encoder.encode(ch);
            char decoded = decoder.decode(coded);
            out.println(ch + " " + coded + " " + decoded);
            out.flush();
        }
    }

    /**
     * returns the root for equation x^k ~ b (modulo m) where m is m1 * m1
     *
     * @param k  root number
     * @param b
     * @param m1 factor of m
     * @param m2 m factor
     * @return kth root of b modulo m1*m2.
     */
    public static long moduloRoot(long k, long b, int m1, int m2) {
        long phi = 1l * (m1 - 1) * (m2 - 1);
        if (ModularArithmatic.gcd(phi, k) != 1)
            throw new RuntimeException("no kth root possible for modulo " + m1 * m2);
        long pow = ModularArithmatic.inverseModulo(k, phi);
        return Power.pow(b, pow, m1 * m2);
    }

    private static boolean isPrimeProbably(long n) {
        if (n < 100) return Prime.primality((int) n);
        long[] rands = Generators.generateRandomLongArray(100, 2, n - 1);
        for (long rand : rands) {
            if (ModularArithmatic.gcd(rand, n) != 1) return false;
            if (Power.pow(rand, n - 1, n) != 1) return false;
        }

        return true;
    }

    /**
     * Returns true with high probability. But the compositness is with surity.
     * TODO: this function is returning false even for sure primes.
     *
     * @param n number need to check if is prime number
     * @return true if number is prime (probably) or composite (surely)
     */
    public static boolean primalityCheckRabinMiller(long n) {
        if (n < 1000) return Prime.primality(n);
        long m = n - 1;
        int trailingZeroes = Long.numberOfTrailingZeros(m);
        long twoPower = 1 << trailingZeroes;
        m >>>= trailingZeroes;
        int[] rands = Generators.generateRandomIntegerArray(100, 10, 1000);
        for (int e : rands) {
            long base = Power.pow(e, m);
            if (base == 1 || base == n - 1) continue;
            if (Power.pow(e, n - 1, n) != 1) return false;
            int r = 2;
            while (r <= twoPower) {
                base = Power.pow(e, m * r, n);
                if (base == -1) return true;
                r <<= 1;
            }
        }

        return false;
    }

    public static long mathSqrt(long n) {
        long res = (long) Math.sqrt(n);
        return res * res > n ? res - 1 : res;
    }

    private static long isqrtHardware(long x) {
        long m, y, b;
        m = 0x40000000L * 0x40000000;
        y = 0;
        while (m != 0) {
            b = y | m;
            y >>= 1;
            if (x >= b) {
                x -= b;
                y |= m;
            }
            m >>= 2;
        }

        return y;
    }

    private static int isqrtBinSearch(int x) {
        int a, b, m;
        a = 1;
        b = (x >>> 5) + 8;
        if (b > 65535) b = 65535;
        do {
            m = (a + b) >>> 1;
            if (m * m > x) b = m - 1;
            else a = m + 1;
        } while (b >= a);

        return a - 1;
    }

    private static long isqrtBinSearch(long x) {
        long a, b, m;
        a = 1;
        b = (x >>> 6) + 16;
        do {
            m = (a + b) >>> 1;
            if (m * m > x) b = m - 1;
            else a = m + 1;
        } while (b >= a);
        return a - 1;
    }

    private static long isqrt(long x) {
        long x1;
        long s, g0, g1;

        if (x <= 1) return x;
        s = 1;
        x1 = x - 1;
        if (x1 > 4294967295L) {
            s += 16;
            x1 >>>= 32;
        }
        if (x1 > 65535) {
            s += 8;
            x1 >>>= 16;
        }
        if (x1 > 255) {
            s += 4;
            x1 >>>= 8;
        }
        if (x1 > 15) {
            s += 2;
            x1 >>>= 4;
        }
        if (x1 > 3) {
            s += 1;
        }

        g0 = 1 << s;
        g1 = (g0 + (x >> s)) >> 1;

        while (g1 < g0) {
            g0 = g1;
            g1 = (g0 + x / g0) >>> 1;
        }

        return g0;
    }

    private static boolean divisibleBy4(long n) {
        return (n & 3) == 0;
    }

    public static long sumOfDivisors(int n) {
        return Prime.primeFactors(n).stream().mapToLong(p -> {
            int component = getFactorComponent(n, p);
            return (component * p - 1) / (p - 1);
        }).reduce((a, b) -> a * b).getAsLong();
    }

    public static long removeFactor(long n, long factor) {
        while (n % factor == 0) n /= factor;
        return n;
    }

    public static int removeFactor(int n, long factor) {
        while (n % factor == 0) n /= factor;
        return n;
    }

    /**
     * Returns the exponent for {@code factor} which appears as part of n's factorization.
     *
     * @param n
     * @param factor
     * @return
     */
    public static int getFactorExponent(long n, long factor) {
        int power = 0;
        long factorPower = factor;
        while (n % factorPower == 0) {
            power++;
            factorPower *= factor;
        }

        return power;
    }

    public static long getFactorComponent(long n, long factor) {
        if (n % factor != 0) return 1;
        long factorPower = factor;
        while (n % factorPower == 0) {
            factorPower *= factor;
        }

        return factorPower / factor;
    }

    public static int getFactorComponent(int n, int factor) {
        if (n % factor != 0) return 1;
        int factorPower = factor;
        while (n % factorPower == 0) {
            factorPower *= factor;
        }

        return factorPower / factor;
    }

    public static long getFactor(long n) {
        if (n < 1000) return Prime.primeFactors(n).get(0);
        long m = n;
        LongUnaryOperator g = x -> (x * x + 1) % m;
        LongUnaryOperator gd = g.andThen(g);

        long a = 2, b = a, d = 1;
        while (d == 1) {
//            a = g.applyAsLong(a);
//            b = gd.applyAsLong(b);
            a = a * (a + 1) % m;
            b = b * (b + 1) % m;
            b = b * (b + 1) % m;
            d = ModularArithmatic.gcd(n, a == b ? a << 1 : Math.abs(a - b));
        }

        return d;
    }

    /**
     * Factorizing using Pollard's rho algorithm. Wikipedia has enough information about this algorithm.
     *
     * @param n
     * @return
     */
    public static int[] factorize(long n) {
        List<Integer> factors = new LinkedList<>();
        while (n > 1) {
            int factor = (int) getFactor(n);
            while (n % factor == 0) {
                n /= factor;
                factors.add(factor);
            }
        }

        return factors.stream().mapToInt(v -> v).toArray();
    }
}
