package com.ashok.projecteuler.problems;


import java.util.Arrays;

public class P054 {
    private static int[] cardValue = new int[256], count = new int[256];
    private static boolean[] temp = new boolean[256];
    private static String royal = "TJQKA", types = "CDHS";

    static {
        for (int i = '2'; i <= '9'; i++)
            cardValue[i] = i - '0';

        cardValue['T'] = 10;
        cardValue['J'] = 11;
        cardValue['Q'] = 12;
        cardValue['K'] = 13;
        cardValue['A'] = 14;
    }

    public static int solve(String[] playerA, String[] playerB) {
        int winner = royalFlush(playerA, playerB);
        if (winner != 0)
            return winner;

        winner = straightFlush(playerA, playerB);
        if (winner != 0)
            return winner;

        winner = fourKind(playerA, playerB);
        if (winner != 0)
            return winner;

        winner = fullHouse(playerA, playerB);
        if (winner != 0)
            return winner;

        winner = flush(playerA, playerB);
        if (winner != 0)
            return winner;

        winner = straight(playerA, playerB);
        if (winner != 0)
            return winner;

        winner = threeKind(playerA, playerB);
        if (winner != 0)
            return winner;

        winner = twoPairs(playerA, playerB);
        if (winner != 0)
            return winner;

        winner = onePair(playerA, playerB);
        if (winner != 0)
            return winner;

        return highCard(playerA, playerB);
    }

    private static int royalFlush(String[] playerA, String[] playerB) {
        boolean a = royalFlush(playerA), b = royalFlush(playerB);

        if (a && !b)
            return -1;

        if (!a && b)
            return 1;

        return 0;
    }

    private static boolean royalFlush(String[] player) {
        // let's check whether all cards are from same suit or not.
        boolean yes = true;
        for (int i = 1; i < 5 && yes; i++)
            if (player[i].charAt(1) != player[i - 1].charAt(1))
                yes = false;

        if (!yes)
            return false;

        // let's find which cards from royal are available
        for (String s : player)
            temp[s.charAt(0)] = true;

        // checking whether all the cards from royal are available.
        boolean res = true;
        for (int i = 0; i < royal.length() && res; i++)
            res = temp[royal.charAt(i)];

        // let's reset the temp array which we are going to use very frequent.
        for (int i = 0; i < 5; i++)
            temp[player[i].charAt(0)] = false;

        return res;
    }

    private static int straightFlush(String[] playerA, String[] playerB) {
        int a = straightFlush(playerA), b = straightFlush(playerB);

        if (a > b)
            return -1;

        if (a < b)
            return 1;

        return 0;
    }

    private static int straightFlush(String[] player) {
        if (flush(player))
            return straight(player);

        return 0;
    }

    private static int fourKind(String[] playerA, String[] playerB) {
        int a = fourKind(playerA), b = fourKind(playerB);

        if (a > b)
            return -1;

        if (a < b)
            return 1;

        return 0;
    }

    private static int fourKind(String[] player) {
        // let's fill the count array first.
        for (String s : player)
            count[s.charAt(0)]++;

        int result = 0;

        for (String s : player)
            if (count[s.charAt(0)] > 3)
                result = cardValue[s.charAt(0)];

        // cleanup
        for (String s : player)
            count[s.charAt(0)] = 0;

        return result;
    }

    private static int fullHouse(String[] playerA, String[] playerB) {
        boolean a = fullHouse(playerA), b = fullHouse(playerB);

        if (a && !b)
            return -1;

        if (!a && b)
            return 1;

        return 0;
    }

    private static boolean fullHouse(String[] player) {
        int v = threeKind(player);
        if (v == 0)
            return false;

        for (String s : player)
            if (cardValue[s.charAt(0)] != v)
                count[s.charAt(0)]++;

        boolean result = false;
        for (String s : player)
            result |= count[s.charAt(0)] > 1;

        for (String s : player)
            count[s.charAt(0)] = 0;

        return result;
    }

    private static int flush(String[] playerA, String[] playerB) {
        boolean a = flush(playerA), b = flush(playerB);

        if (a && !b)
            return -1;

        if (!a && b)
            return 1;

        return 0;
    }

    private static boolean flush(String[] player) {
        // let's use the count array.
        for (String s : player)
            count[s.charAt(1)]++;

        boolean result = count[player[0].charAt(1)] == 5;

        // cleanup
        for (String s : player)
            count[s.charAt(1)] = 0;

        return result;
    }

    private static int straight(String[] playerA, String[] playerB) {
        int a = straight(playerA), b = straight(playerB);

        if (a > b)
            return -1;

        if (a < b)
            return 1;

        return 0;
    }

    private static int straight(String[] player) {
        int minCard = 14, maxCard = 2;

        for (String s : player) {
            int card = cardValue[s.charAt(0)];
            temp[card] = true;

            minCard = Math.min(card, minCard);
            maxCard = Math.max(card, maxCard);
        }

        boolean res = true;
        if (maxCard - minCard != 4)
            res = false;

        for (int i = minCard; i <= maxCard; i++)
            res = res && temp[i];

        for (int i = minCard; i <= maxCard; i++)
            temp[i] = false;

        if (res)
            return maxCard;

        return 0;
    }

    private static int threeKind(String[] playerA, String[] playerB) {
        int a = threeKind(playerA), b = threeKind(playerB);

        if (a > b)
            return -1;

        if (a < b)
            return 1;

        return 0;
    }

    private static int threeKind(String[] player) {
        //let's use the count array.
        for (String s : player)
            count[s.charAt(0)]++;

        int result = 0;
        for (String s : player)
            if (count[s.charAt(0)] > 2)
                result = cardValue[s.charAt(0)];

        // cleanup
        for (String s : player)
            count[s.charAt(0)] = 0;

        return result;
    }

    private static int twoPairs(String[] playerA, String[] playerB) {
        boolean a = twoPairs(playerA), b = twoPairs(playerB);

        if (a && !b)
            return -1;

        if (!a && b)
            return 1;

        return 0;
    }

    private static boolean twoPairs(String[] player) {
        for (String s : player)
            count[s.charAt(0)]++;

        boolean one = false, two = false;
        for (int i = 0; i < 5 && !one; i++) {
            one = count[player[i].charAt(0)] > 1;
            count[player[i].charAt(0)] = 0;
        }

        for (int i = 0; i < 5 && !two; i++) {
            two = count[player[i].charAt(0)] > 1;
            count[player[i].charAt(0)] = 0;
        }

        for (String s : player)
            count[s.charAt(0)] = 0;

        return one && two;
    }

    private static int onePair(String[] playerA, String[] playerB) {
        int a = onePair(playerA), b = onePair(playerB);

        if (a > b)
            return -1;

        if (a < b)
            return 1;

        return 0;
    }

    private static int onePair(String[] player) {
        for (String s : player)
            count[s.charAt(0)]++;

        int result = 0;
        for (String s : player)
            if (count[s.charAt(0)] > 1)
                result = Math.max(result, cardValue[s.charAt(0)]);

        for (String s : player)
            count[s.charAt(0)] = 0;

        return result;
    }

    private static int highCard(String[] playerA, String[] playerB) {
        int[] ar = new int[5], br = new int[5];

        for (int i = 0; i < 5; i++) {
            ar[i] = cardValue[playerA[i].charAt(0)];
            br[i] = cardValue[playerB[i].charAt(0)];
        }

        Arrays.sort(ar);
        Arrays.sort(br);

        for (int i = 4; i >= 0; i--) {
            if (ar[i] == br[i])
                continue;

            if (ar[i] > br[i])
                return -1;

            return 1;
        }

        return 0;
    }

    private static int highCard(String[] player) {
        int maxCard = 2;

        for (String s : player) {
            int value = cardValue[s.charAt(0)];
            maxCard = Math.max(maxCard, value);
        }

        return maxCard;
    }
}
