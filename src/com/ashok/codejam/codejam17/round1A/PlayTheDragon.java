/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.codejam17.round1A;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Problem Name: Problem C. Play the Dragon
 * Link: https://code.google.com/codejam/contest/5304486/dashboard#s=p2
 * <p>
 * Half cooked code.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class PlayTheDragon {
    private static Output out;// = new Output();
    private static InputReader in;// = new InputReader();
    private static final String CASE = "Case #";
    private static final String path = "C:\\Projects\\others\\karani\\src\\com\\ashok\\codejam\\codejam17\\round1A\\";
    private static Map<Combat, Integer> combatCountMap = new HashMap<>();
    private static String IMPOSSIBLE = "IMPOSSIBLE";

    public static void main(String[] args) throws IOException {
        in = new InputReader(path + "PlayTheDragon.in");
        out = new Output(path + "PlayTheDragon.out");
        in = new InputReader();
        out = new Output();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        StringBuilder sb = new StringBuilder();
        int t = in.readInt();

        for (int i = 1; i <= t; i++) {
            int hd = in.readInt(), ad = in.readInt(), hk = in.readInt(), ak = in.readInt();
            Fighter.buff = in.readInt();
            Fighter.debuff = in.readInt();
            Fighter.defaultHealth = hd;

            Combat combat = new Combat(new Fighter(hd, ad), new Fighter(hd, ak));

            sb.append(CASE).append(i).append(": ").append(process(combat)).append('\n');
        }

        out.print(sb);
    }

    private static Object process(Combat combat) {
        int count = fight(combat);

        return count >= 0 ? count : IMPOSSIBLE;
    }

    private static int fight(Combat combat) {
        if (combatCountMap.containsKey(combat))
            return combatCountMap.get(combat);

        if (impossible(combat))
            return -1;


        int min = Integer.MAX_VALUE;
        Fighter dragon = combat.dragon, knight = combat.knight;

        if (knight.health <= dragon.attack) {
            min = 1;
        } else if (dragon.health <= knight.attack) {
            if (Fighter.debuff != 0) {
                int count = 0;
                while (dragon.health <= knight.attack) {
                    count++;
                }
                min = fight(new Combat(dragon, knight.reducePower()));
            }
        }

        return min;
    }

    private static int validCount(int a, int b) {
        if (a > b)
            return validCount(b, a);

        if (a == -1)
            return b;

        return a;
    }

    private static boolean impossible(Combat combat) {
        Fighter dragon = combat.dragon, knight = combat.knight;
        boolean result = true;

        result = result && dragon.attack < knight.health;
        result = result && dragon.health <= knight.attack - Fighter.debuff;
        result = result && Fighter.defaultHealth <= knight.attack;

        return result;
    }

    final static class Fighter {
        static int defaultHealth = 0, buff = 0, debuff = 0;
        final long health, attack;

        Fighter(long h, long a) {
            health = h;
            attack = a;
        }

        Fighter cure() {
            if (health == defaultHealth)
                return this;

            return new Fighter(defaultHealth, attack);
        }

        boolean needToBeCured() {
            return health < defaultHealth;
        }

        Fighter boostPower() {
            if (buff == 0)
                return this;

            return new Fighter(health, attack + buff);
        }

        Fighter reducePower() {
            if (debuff == 0)
                return this;

            return new Fighter(health, Math.max(0, attack - debuff));
        }

        public int hashCode() {
            return Long.hashCode(health + attack);
        }

        public boolean equals(Object o) {
            if (o == this)
                return true;

            if (o == null)
                return false;

            if (!(o instanceof Fighter))
                return false;

            Fighter fighter = (Fighter) o;
            return health == fighter.health && attack == fighter.attack;
        }

        public String toString() {
            return health + ", " + attack;
        }
    }

    final static class Combat {
        final Fighter dragon, knight;

        Combat(Fighter d, Fighter k) {
            dragon = d;
            knight = k;
        }

        public int hashCode() {
            return Integer.hashCode(dragon.hashCode() + knight.hashCode());
        }

        public boolean equals(Object o) {
            if (o == this)
                return true;

            if (o == null)
                return false;

            if (!(o instanceof Combat))
                return false;

            Combat combat = (Combat) o;
            return dragon.equals(combat.dragon) && knight.equals(combat.knight);
        }

        public String toString() {
            return dragon + " vs " + knight;
        }
    }
}
