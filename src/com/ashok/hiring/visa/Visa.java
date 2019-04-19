/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.visa;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Minimum Moves
 * Link: Private HackerRank Test.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Visa {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    static final Integer i1 = 1;
    final Integer i2 = 2;
    Integer i3 = 3;

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        while (true) {
            int n = in.readInt();
            out.flush();
        }
    }

    private static List<Integer> toList(int[] ar) {
        List<Integer> list = new LinkedList<>();
        for (int e : ar) list.add(e);
        return list;
    }

    public static int minimumMoves(List<Integer> a, List<Integer> m) {
        int moves = 0;
        Iterator<Integer> ai = a.iterator(), mi = m.iterator();
        while (ai.hasNext()) moves += digitDistance(ai.next(), mi.next());
        return moves;
    }

    private static int digitDistance(int a, int b) {
        int distance = 0;
        while (a >= 10 && a != b) {
            int ta = a / 10, tb = b / 10;
            distance += Math.abs(a - b - 10 * (ta - tb));
            a = ta;
            b = tb;
        }

        return distance + Math.abs(a - b);
    }

    final static class Account {
        private volatile int balance = 0;
        final static String INSUFFICIENT_BALANCE = " (Insufficient Balance)";

        synchronized String withdraw(int money) {
            if (money > balance)
                return withdrawMessage(money, INSUFFICIENT_BALANCE);

            balance -= money;
            return withdrawMessage(money, "");
        }

        synchronized String deposite(int money) {
            balance += money;
            return depositeMessage(money);
        }

        int getBalance() {
            return balance;
        }

        private static String depositeMessage(int money) {
            return "Depositing $" + money;
        }

        private static String withdrawMessage(int money, String message) {
            return "Withdrawing $" + money + message;
        }
    }

    final static class Transaction {
        final Account account;
        final List<String> transactions = new LinkedList<>();
        Object lock = new Object();

        Transaction(Account account) {
            this.account = account;
        }

        public void deposite(int money) {
            synchronized (lock) {
                transactions.add(account.deposite(money));
            }
        }

        public void withdraw(int money) {
            synchronized (lock) {
                transactions.add(account.withdraw(money));
            }
        }

        public List<String> getTransaction() {
            return Collections.unmodifiableList(transactions);
        }
    }
}
