package com.ashok.lang.algorithms;

import com.ashok.lang.math.BasicMathOperations;
import com.ashok.lang.math.Power;

import java.util.LinkedList;
import java.util.List;

/**
 * The {@code Expressions} class is to evaluate different mathematical
 * expressions.
 * <p>
 * + is Addition of two Numbers
 * - is Subtraction of second number from first one.
 * * is Multiplication of two Numbers
 * / is Division of first Number by second.
 * % is remainder when first number is divided by second.
 * ^ is the power of first number raised by second.
 * <p>
 * <p>
 * Expression can be like
 * <p>
 * 92-36*77+59+88/8*44/76*55
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Expressions<K> {
    private int[] precedence;
    public static final BasicMathOperations<Long> basicMathOperations = new
            Operations();

    private BasicMathOperations<K> operations;

    public Expressions(BasicMathOperations<K> basicMathOperations) {
        operations = basicMathOperations;
        precedence = new int[256];

        precedence['%'] = precedence['*'] = precedence['/'] = 1;
        precedence['^'] = 2;
//        this(basicMathOperations, new char[]{'+', '-', '%', '*', '/', '^'});
    }

    public Expressions(BasicMathOperations<K> basicMathOperations, char[] operatorPrecedence) {
        operations = basicMathOperations;
        precedence = new int[256];
        int i = 0;

        for (char c : operatorPrecedence) {
            precedence[c] = i++;
        }
    }

    public K evaluate(List<K> params, List<Character> operators) {
        if (params.size() != operators.size() + 1)
            throw new RuntimeException("Invalid Expression");

        K res;
        LinkedList<K> resultStack = new LinkedList<>();
        LinkedList<Character> operatorStack = new LinkedList<>();
        resultStack.add(params.remove(0));
        resultStack.addFirst(params.remove(0));
        operatorStack.add(operators.remove(0));

        while (operatorStack.size() != 0 && params.size() != 0) {
            if (operatorStack.size() == 0) {
                operatorStack.add(operators.remove(0));
                resultStack.add(params.remove(0));
                continue;
            }

            char op = operators.get(0);

            if (precedence[op] > precedence[operatorStack.getFirst()]) {
                operatorStack.addFirst(operators.remove(0));
                resultStack.addFirst(params.remove(0));
                continue;
            }

//            if (precedence[op] == precedence[operatorStack.getFirst()]) {
//                K value = performOperation(resultStack.removeFirst(), params
//                        .remove(0), op);
//
//                resultStack.addFirst(value);
//                continue;
//            }

            K value = performOperation(resultStack.removeFirst(), resultStack
                    .removeFirst(), operatorStack.removeFirst());

            resultStack.addFirst(value);
        }

        return resultStack.getFirst();
    }

    private K performOperation(K a, K b, char op) {
        switch (op) {
            case '+':
                return operations.add(a, b);
            case '-':
                return operations.subtract(a, b);
            case '*':
                return operations.multiply(a, b);
            case '/':
                return operations.divide(a, b);
            case '%':
                return operations.remainder(a, b);
            case '^':
                return operations.power(a, b);
            default:
                throw new RuntimeException("Invalid Operation: " + op);
        }
    }

    public static long evaluate(String expression) {
        long res = 0;
        String[] additions = expression.split("[+]");
        for (String e : additions)
            res = basicMathOperations.add(res, subtraction(e, -1));

        return res;
    }

    public static long evaluate(String expression, int mod) {
        if (mod < 2)
            return 0;

        long res = 0;
        String[] additions = expression.split("[+]");
        for (String e : additions)
            res = basicMathOperations.add(res, subtraction(e, mod));

        if (mod != -1)
            return res % mod;

        return res;
    }

    private static long subtraction(String s, int mod) {
        String[] minus = s.split("[-]");
        long res = multiply(minus[0], mod);

        for (int i = 1; i < minus.length; i++)
            res = basicMathOperations.subtract(res, multiply(minus[i], mod));

        return res;
    }

    private static long multiply(String s, int mod) {
        long res = 1;
        String[] multi = s.split("[*]");

        for (String e : multi) {
            res = basicMathOperations.multiply(res, divide(e));

            if (mod != -1)
                res %= mod;
        }

        return res;
    }

    private static long divide(String s) {
        long res = 1;

        String[] divisions = s.split("[/]");
        res = Long.parseLong(divisions[0]);

        for (int i = 1; i < divisions.length; i++)
            res = basicMathOperations.divide(res, Long.parseLong
                    (divisions[i]));

        return res;
    }

    final static class Operations implements BasicMathOperations<Long> {
        @Override
        public Long add(Long a, Long b) {
            return a + b;
        }

        @Override
        public Long subtract(Long a, Long b) {
            return a - b;
        }

        @Override
        public Long multiply(Long a, Long b) {
            return a * b;
        }

        @Override
        public Long divide(Long a, Long b) {
            return a / b;
        }

        @Override
        public Long remainder(Long a, Long b) {
            return a % b;
        }

        @Override
        public Long power(Long a, Long b) {
            return Power.pow(a, b);
        }
    }
}
