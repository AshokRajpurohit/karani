package com.ashok.lang.main;

import com.ashok.lang.algorithms.Expressions;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.math.BasicMathOperations;
import com.ashok.lang.math.Power;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class Main {

    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private static final String path =
            "D:\\GitHub\\Language\\Language\\Common\\src\\Code\\Main\\";

    private static String line =
            "---------------------------------------------------------------------";

    public static void main(String[] args) throws IOException, Exception {
        String input = "input_file.in", output = "output.txt";

        Main a = new Main();
        a.solve();
        out.close();

        Collection<Integer> c;
    }

    public void solve() throws IOException, Exception {
        int t = in.readInt();

        while (true) {
            int n = in.readInt(), mod = in.readInt();
            LongOperations longOperations = new LongOperations(mod);
            Expressions<Long> expressions = new Expressions<Long>(longOperations);
            List<Long> numbers = new LinkedList<>();
            List<Character> operators = new LinkedList<>();

            populate(numbers, new char[]{'+', '-', '*', '/'}, operators, mod
                    - 1, n);

            String expression = formExpression(numbers, operators);
            out.println(expression);

            long time = System.currentTimeMillis();
            out.println(Expressions.evaluate(expression, mod));
            out.println((System.currentTimeMillis() - time) + " ms");
            out.flush();

            time = System.currentTimeMillis();
            out.println(expressions.evaluate(numbers, operators));
            out.println((System.currentTimeMillis() - time) + " ms");
            out.flush();
        }
    }

    private static void populate(List<Long> numbers, char[] operators,
                                 List<Character> operatorList, int mod, int length) {
        Random random = new Random();

        for (int i = 0; i < length; i++)
            numbers.add(Math.abs(random.nextLong()) % mod + 1);

        for (int i = 1; i < length; i++)
            operatorList.add(operators[random.nextInt(operators.length)]);
    }

    private static String formExpression(List<Long> numbers, List<Character>
            operators) {
        StringBuilder sb = new StringBuilder(numbers.size() * 9);

        Iterator<Long> numIter = numbers.iterator();
        Iterator<Character> opeIter = operators.iterator();

        while (opeIter.hasNext())
            sb.append(numIter.next()).append(opeIter.next());

        sb.append(numIter.next());
        return sb.toString();
    }

    final static class LongOperations implements BasicMathOperations<Long> {
        private int mod = 1000000007;

        public LongOperations(int mod) {
            this.mod = mod;
        }

        public LongOperations() {

        }

        @Override
        public Long add(Long a, Long b) {
            return (a + b) % mod;
        }

        @Override
        public Long subtract(Long a, Long b) {
            return a - b;
        }

        @Override
        public Long multiply(Long a, Long b) {
            return a * b % mod;
        }

        @Override
        public Long divide(Long a, Long b) {
            return a * Power.inverseModulo(b, mod) % mod;
        }

        @Override
        public Long remainder(Long a, Long b) {
            return a % b;
        }

        @Override
        public Long power(Long a, Long b) {
            return Power.pow(a, b, mod);
        }
    }

    private static byte[] toBytes(String s) {
        return s.getBytes();
    }

    private static byte[] decryptPassword(byte[] result) throws
            GeneralSecurityException {
        byte constant = result[0];
        if (constant != 5) {
            throw new IllegalArgumentException();
        }

        byte[] secretKey = new byte[8];
        System.arraycopy(result, 1, secretKey, 0, 8);

        byte[] encryptedPassword = new byte[result.length - 9];
        System.arraycopy(result, 9, encryptedPassword, 0, encryptedPassword.length);

        byte[] iv = new byte[8];
        for (int i = 0; i < iv.length; i++) {
            iv[i] = 0;
        }

        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(secretKey, "DES"), new IvParameterSpec(iv));
        return cipher.doFinal(encryptedPassword);
    }

    enum Property {
        ASHOK("Ashok"), KK("Krishna Kumar");
        final String name;

        Property(String name) {
            this.name = name;
        }
    }
}
