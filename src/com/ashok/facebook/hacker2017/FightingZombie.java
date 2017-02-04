/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.facebook.hacker2017;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Problem Name: Fighting the Zombie
 * Link: https://www.facebook.com/hackercup/problem/326053454264498/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class FightingZombie {
    private static Output out, log;
    private static InputReader in;
    private static final String CASE = "Case #";
    private static Map<Request, Double> cache;


    public static void main(String[] args) throws IOException {
        String path = "C:\\Projects\\others\\karani\\src\\com\\ashok\\facebook\\hacker2017\\";
        in = new InputReader(path + "FightingZombie.in");
        out = new Output(path + "FightingZombie.out");
        log = new Output(path + "FightingZombie.log");

        solve();

        in.close();
        out.close();
        log.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 4);

        for (int i = 1; i <= t; i++) {
            int h = in.readInt(), s = in.readInt();
            String[] spells = in.readStringArray(s);

            append(sb, i, process(h, spells));
        }

        out.print(sb);
    }

    private static double process(int h, String[] spells) {
        log("Let's start for h: ", h);
        log(spells);
        Spell[] spellAr = getSpells(spells);
        int offset = getOffsetSum(spellAr);
        h -= offset;

        if (h <= 0)
            return 1.0;

        if (getMax(spellAr) < h)
            return 0.0;

        int[] dices = getDices(spellAr);
        return probability(dices, h);
    }

    private static int getOffsetSum(Spell[] spells) {
        int sum = 0;

        for (Spell spell : spells)
            sum += spell.offset + spell.count;

        return sum;
    }

    private static double probability(int[] faces, int target) {
        int sum = sum(faces);
        cache = new HashMap<>();
        return probability(faces, sum, target, 0);
    }

    private static double probability(int[] faces, int sum, int target, int index) {
        if (target < 0)
            return 1.0;

        if (target > sum || index >= faces.length)
            return 0.0;

        // it doesn't make difference if we want to select k out of n or n - k out of n.
        target = Math.min(target, sum - target);

        Request params = new Request(sum, target, index);
        if (cache.containsKey(params))
            return cache.get(params);

        if (target == 1) {
            double probability = 1.0;

            while (index < faces.length) {
                probability /= (faces[index] + 1);
                index++;
            }

            probability = 1 - probability;
            cache.put(params, probability);
            log(params, ", value: ", probability);
            return probability;
        }

        if (target == 0) {
            double probability = 1.0;

            while (index < faces.length) {
                probability /= (faces[index] + 1);
                index++;
            }

            cache.put(params, probability);
            log(params, ", value: ", probability);
            return probability;
        }

        sum -= faces[index];

        if (sum <= 0) {
            int possibles = faces[index] - target + 1;

            double probability = Math.max(0.0, possibles) / (faces[index] + 1);
            cache.put(params, probability);
            log(params, ", value: ", probability);
            return probability;
        }

        target++;
        double probability = 0.0;
        for (int i = 0; i <= faces[index]; i++) {
            target--;
            probability += probability(faces, sum, target, index + 1);
        }

        probability /= (faces[index] + 1);
        cache.put(params, probability);

        log(params, ", value: ", probability);
        return probability;
    }

    private static int sum(int[] ar) {
        int sum = 0;

        for (int e : ar)
            sum += e;

        return sum;
    }

    private static Spell[] getSpells(String[] strings) {
        Spell[] ar = new Spell[strings.length];

        for (int i = 0; i < ar.length; i++)
            ar[i] = new Spell(strings[i]);

        return ar;
    }

    private static int[] getDices(Spell[] spells) {
        int size = 0;

        for (Spell spell : spells)
            size += spell.count;

        int[] ar = new int[size];
        int index = 0;

        for (Spell spell : spells) {
            for (int i = 0; i < spell.count; i++)
                ar[index++] = spell.faces - 1;
        }

        Arrays.sort(ar);
        reverse(ar);

        return ar;
    }

    private static void reverse(int[] ar) {
        for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
            int t = ar[i];
            ar[i] = ar[j];
            ar[j] = t;
        }
    }

    private static int getMin(Spell[] spells) {
        int min = 0;

        for (Spell spell : spells)
            min += 1 + spell.offset;

        return min;
    }

    private static int getMax(Spell[] spells) {
        int max = 0;

        for (Spell spell : spells)
            max += spell.faces - 1; // normalized value

        return max;
    }

    private static void append(StringBuilder sb, int test, double result) {
        sb.append(CASE).append(test).append(": ").append(result).append('\n');
    }

    private static void log(Object object) {
        log.println(object);
        log.flush();
    }

    private static void log(Object... objects) {
        log.print(objects);
        log.println();
        log.flush();
    }

    final static class Spell {
        final int count, faces, offset;

        Spell(String str) {
            String[] ar = str.split("d");
            count = Integer.valueOf(ar[0]);

            str = ar[1];
            if (str.contains("+")) {
                ar = str.split("[+]");
                faces = Integer.valueOf(ar[0]);
                offset = Integer.valueOf(ar[1]);
            } else if (str.contains("-")) {
                ar = str.split("-");
                faces = Integer.valueOf(ar[0]);
                offset = -Integer.valueOf(ar[1]);
            } else {
                faces = Integer.valueOf(str);
                offset = 0;
            }
        }

        public String toString() {
            return count + ", " + faces + ", " + offset;
        }
    }

    final static class Request {
        final int sum, target, index;

        Request(int s, int t, int i) {
            sum = s;
            target = t;
            index = i;
        }

        public int hashCode() {
            return sum * 1000 + target * 79 + index;
        }

        public boolean equals(Object object) {
            if (object == this)
                return true;

            if (!(object instanceof Request))
                return false;

            Request request = (Request) object;

            return sum == request.sum && target == request.target && index == request.index;
        }

        public String toString() {
            return "sum: " + sum + ", target: " + target + ", index: " + index;
        }
    }
}
