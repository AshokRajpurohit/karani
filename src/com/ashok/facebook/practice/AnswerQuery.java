/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.facebook.practice;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AnswerQuery {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        int n = in.readInt(), q = in.readInt();
        Query[] queries = new Query[q];
        for (int i = 0; i < q; i++) queries[i] = new Query(in.readInt(), in.readInt());
        int[] res = process(n, queries);
        out.print(res);
    }

    private static int[] process(int n, Query[] queries) {
        TreeSet<Integer> trueSet = new TreeSet<>();
        List<Integer> res = new ArrayList<>();
        for (Query query: queries) {
            if (query.isRead()) {
                Integer index = trueSet.ceiling(query.value);
                if (index == null) index = -1;
                res.add(index);
            } else {
                trueSet.add(query.value);
            }
        }

        return res.stream().mapToInt(v -> v).toArray();
    }

    final static class Query {
        final int type, value;

        Query(final int type, final int value) {
            this.type = type;
            this.value = value;
        }

        boolean isRead() {
            return type == 2;
        }
    }
}
