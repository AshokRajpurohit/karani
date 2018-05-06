/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Deepak Das
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Deepak {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        Deepak a = new Deepak();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            DummyImpl dummy = new DummyImpl();
            out.println(dummy.give());
            out.println(dummy.give());
            out.println(dummy.give());
            out.println(dummy.give());
            dummy.set(in.readInt());
            out.println(dummy.give());
            out.println(dummy.give());
            out.println(dummy.give());

            ConsumerClass consumerClass = new ConsumerClass();
            Dummy d = consumerClass.give();
            out.println(d.give());
            out.println(d.give());
            out.println(d.give());
            out.println(d.give());
            out.flush();
        }
    }

    private static List<Integer> giveList() {
        return new LinkedList<>();
    }

    interface Dummy {
        int give();
    }

    final static class DummyImpl implements Dummy{
        int value = 0;

        @Override
        public int give() {
            return value++;
        }

        public void reset() {
            value = 0;
        }

        public void set(int newValue) {
            value = newValue;
        }
    }

    final static class ConsumerClass {
        DummyImpl dummy = new DummyImpl();

        ConsumerClass() {
            dummy.set(10);
        }
        public Dummy give() {
            return dummy;
        }
    }
}
