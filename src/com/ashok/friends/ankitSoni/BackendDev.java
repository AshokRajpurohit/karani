/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.ankitSoni;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class BackendDev {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        BackendDev a = new BackendDev();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            int n = in.readInt(), m = in.readInt();
            int[] ar = in.readIntArray(m);
            m = in.readInt();
            int[] br = in.readIntArray(m);
            out.println(Shopping.budgetShopping(n, ar, br));
            out.flush();
        }
    }

    private static int gcd(int a, int b) {
        if (b == 0)
            return a;

        return gcd(b, a % b);
    }

    final static class Shopping {
        private static Bundle[] bundles;
        private static Bundle[] minCostBundles;
        private static int maxNotebooks = 0;

        private static int budgetShopping(int n, int[] bundleQuantities, int[] bundleCosts) {
            int bundleSize = bundleCosts.length;
            bundles = new Bundle[bundleSize];

            for (int i = 0; i < bundleSize; i++)
                bundles[i] = new Bundle(bundleQuantities[i], bundleCosts[i]);

            Arrays.sort(bundles);
            normalize();
            populateMinCostBundles();
            return process(n);
        }

        private static void normalize() {
            LinkedList<Bundle> bundleList = new LinkedList<>();
            bundleList.add(bundles[0]);

            for (Bundle bundle : bundles) {
                Bundle last = bundleList.getLast();
                if (bundle.contains(last))
                    continue;

                bundleList.add(bundle);
            }

            bundles = toArray(bundleList);
        }

        private static void populateMinCostBundles() {
            int len = bundles.length;
            minCostBundles = new Bundle[len];
            Bundle minCostBundle = bundles[len - 1];
            minCostBundles[len - 1] = minCostBundle;

            for (int i = len - 2; i >= 0; i--) {
                if (bundles[i].cost() <= minCostBundle.cost())
                    minCostBundle = bundles[i];

                minCostBundles[i] = minCostBundle;
            }
        }

        private static Bundle[] toArray(LinkedList<Bundle> bundleList) {
            Bundle[] bundleArray = new Bundle[bundleList.size()];
            int index = 0;

            for (Bundle bundle : bundleList)
                bundleArray[index++] = bundle;

            return bundleArray;
        }

        private static int process(int budget) {
            process(budget, 0, 0);
            return maxNotebooks;
        }

        private static void process(int budget, int index, int noteBooksSoFar) {
            updateNotebooks(noteBooksSoFar);
            if (budget < 0 || index >= bundles.length)
                return;

            if (budget < minCostBundles[index].cost()) {
                updateNotebooks(noteBooksSoFar);
                return;
            }

            if (budget == minCostBundles[index].cost()) {
                updateNotebooks(noteBooksSoFar + minCostBundles[index].notebooks());
                return;
            }

            int cost = bundles[index].cost();
            int notebooks = bundles[index].notebooks();
            while (budget >= 0) {
                process(budget, index + 1, noteBooksSoFar);
                budget -= cost;
                noteBooksSoFar += notebooks;
            }
        }

        private static void updateNotebooks(int value) {
            maxNotebooks = Math.max(maxNotebooks, value);
        }

        final static class Bundle implements Comparable<Bundle> {
            final int baseCost, baseNotebooks, multiplier;
            final double notebookPerDollar;

            Bundle(int noteBooks, int cost) {
                multiplier = gcd(noteBooks, cost);
                baseCost = cost / multiplier;
                baseNotebooks = noteBooks / multiplier;
                notebookPerDollar = 1.0 * noteBooks / cost;
            }

            private int cost() {
                return baseCost * multiplier;
            }

            private int notebooks() {
                return baseNotebooks * multiplier;
            }

            public boolean contains(Bundle bundle) {
                return (notebookPerDollar == bundle.notebookPerDollar)
                        && (multiplier % bundle.multiplier == 0);
            }

            @Override
            public int compareTo(Bundle bundle) {
                if (notebookPerDollar == bundle.notebookPerDollar)
                    return multiplier - bundle.multiplier;

                return Double.compare(bundle.notebookPerDollar, notebookPerDollar);
            }
        }
    }
}
