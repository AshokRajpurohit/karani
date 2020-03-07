/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.ankit;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ClearTax {
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
        while (true) {
            String fileName = in.read();
            InputReader ir = new InputReader(fileName);
            for (CodeEstimator codeEstimator : CodeEstimators.values()) {
                out.println(codeEstimator.estimateLines(new BufferedInputStream(new FileInputStream(fileName))));
            }
            out.flush();
        }
    }

    interface CodeEstimator {
        int estimateLines(InputStream is);
    }

    enum CodeEstimators implements CodeEstimator {
        BLANK_LINES {
            @Override
            public int estimateLines(InputStream is) {
                return 0;
            }
        },

        SINGLE_LINE_COMMENTS {
            @Override
            public int estimateLines(InputStream is) {
                return 0;
            }
        },

        CODE_LINES {
            @Override
            public int estimateLines(InputStream is) {
                return 0;
            }
        };
    }
}
