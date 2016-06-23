package com.ashok.friends;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Noor {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        Noor a = new Noor();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        gameWithLetters();
    }

    public static void gameWithLetters() throws IOException {
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            String s = in.read();

            int[] win = hash(s);
            int winner = 0;
            String winTeam = "";
            long max = 0;

            for (int i = 1; i <= n; i++) {
                String team = in.read();
                int[] teamAr = hash(team);
                long score = score(win, teamAr);

                if (score > max) {
                    winTeam = team;
                    max = score;
                    winner = i;
                } else if (score == max) {
                    if (winTeam == null || winTeam.length() > team.length()) {
                        winTeam = team;
                        max = score;
                        winner = i;
                    }
                }
            }
            out.println(winner);
        }
    }

    private static long score(int[] ref, int[] team) {
        long res = 0;
        for (int i = 'a'; i <= 'z'; i++)
            res += ref[i] * team[i];

        return res;
    }

    private static int[] hash(String s) {
        int[] res = new int[256];
        for (int i = 0; i < s.length(); i++)
            res[s.charAt(i)]++;

        return res;
    }
}
