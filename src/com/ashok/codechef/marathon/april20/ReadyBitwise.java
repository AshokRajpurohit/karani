/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.april20;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Problem Name: Ready Bitwise
 * Link: https://www.codechef.com/APRIL20A/problems/REBIT
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ReadyBitwise {
    private static final int MOD = 998244353;
    private static final char OPEN = '(', CLOSE = ')', WILD = '#';
    private static char[] ops = new char[]{'&', '|', '^'};

    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            String exp = in.read();
            out.println(process(exp));
        }
    }

    private static Expression process(String expr) {
        if (expr.length() == 1) return Expression.SINGLE_EXPRESSION;
        char[] chars = expr.toCharArray();
        Deque<Expression> expressStack = new LinkedList<>();
        Deque<Character> charStack = new LinkedList<>();

        for (char ch : chars) {
            if (ch == OPEN) continue;

            if (ch == CLOSE && !charStack.isEmpty()) {
                char op = charStack.pop();
                Expression first = expressStack.pop(), second = expressStack.pop();
                ExpressionEvaluator evaluator = Evaluators.getEvaluator(op);
                Expression expression = evaluator.evaluate(first, second);
                expressStack.push(expression);
                continue;
            }

            if (ch == WILD) {
                expressStack.push(Expression.SINGLE_EXPRESSION);
            } else {
                charStack.push(ch);
            }
        }

        return expressStack.pop();
    }

    enum Evaluators implements ExpressionEvaluator {
        AND(new AndEvaluator()),
        OR(new OrEvaluator()),
        XOR(new XorEvaluator());

        private final ExpressionEvaluator evaluator;

        Evaluators(final ExpressionEvaluator evaluator) {
            this.evaluator = evaluator;
        }

        static ExpressionEvaluator getEvaluator(char ch) {
            switch (ch) {
                case '&':
                    return AND;
                case '|':
                    return OR;
                case '^':
                    return XOR;
                default:
                    throw new RuntimeException("something is wrong");
            }
        }

        @Override
        public Expression evaluate(Expression e1, Expression e2) {
            return evaluator.evaluate(e1, e2);
        }
    }

    interface ExpressionEvaluator {
        Expression evaluate(Expression e1, Expression e2);
    }

    private static class Expression {
        private static final Expression SINGLE_EXPRESSION =
                new Expression(748683265, 748683265, 748683265, 748683265);
        final long p0, p1, pa, pA;

        private Expression(long p0, long p1, long pa, long pA) {
            this.p0 = p0;
            this.p1 = p1;
            this.pa = pa;
            this.pA = pA;
        }

        public String toString() {
            return p0 + " " + p1 + " " + pa + " " + pA;
        }
    }

    final static class AndEvaluator implements ExpressionEvaluator {

        @Override
        public Expression evaluate(Expression e1, Expression e2) {
            long p0 = e1.p0 + e2.p0 - e1.p0 * e2.p0 + e1.pa * e2.pA + e1.pA * e2.pa;
            long p1 = e1.p1 * e2.p1 % MOD;
            long pa = e1.p1 * e2.pa + e1.pa * e2.p1 + e1.pa * e2.pa;
            long pA = e1.p1 * e2.pA + e1.pA * e2.p1 + e1.pA * e2.pA;
            p0 %= MOD;
            if (p0 < 0) p0 += MOD;
            p1 %= MOD;
            pa %= MOD;
            pA %= MOD;
            return new Expression(p0, p1, pa, pA);
        }
    }

    final static class OrEvaluator implements ExpressionEvaluator {

        @Override
        public Expression evaluate(Expression e1, Expression e2) {
            long p0 = e1.p0 * e2.p0 % MOD;
            long p1 = (e1.p1 + e2.p1 - e1.p1 * e2.p1 + e1.pa * e2.pA + e1.pA * e2.pa) % MOD;
            long pa = (e1.pa * e2.pa + e1.p0 * e2.pa + e1.pa * e2.p0) % MOD;
            long pA = (e1.pA * e2.pA + e1.p0 * e2.pA + e1.pA * e2.p0) % MOD;
            if (p1 < 0) p1 += MOD;

            return new Expression(p0, p1, pa, pA);
        }
    }

    final static class XorEvaluator implements ExpressionEvaluator {

        @Override
        public Expression evaluate(Expression e1, Expression e2) {
            long p0 = (e1.p0 * e2.p0 + e1.p1 * e2.p1 + e1.pa * e2.pa + e1.pA * e2.pA) % MOD;
            long p1 = (e1.p0 * e2.p1 + e1.p1 * e2.p0 + e1.pa * e2.pA + e1.pA * e2.pa) % MOD;
            long pa = (e1.p0 * e2.pa + e1.pa * e2.p0 + e1.p1 * e2.pA + e1.pA * e2.p1) % MOD;
            long pA = (e1.p0 * e2.pA + e1.pA * e2.p0 + e1.p1 * e2.pa + e1.pa * e2.p1) % MOD;
            return new Expression(p0, p1, pa, pA);
        }
    }

    final static class InputReader {
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;
        InputStream in;

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