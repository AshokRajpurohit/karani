package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;

/**
 * @author Ashok Rajpurohit ashok1113@gmail.com
 * problem Link: Idea Device | Decrypt Check
 */

public class IdeaDeviceDecryptCheck {

    private static PrintWriter out;
    private static InputStream in;
    private static boolean[] validTwo = new boolean[100];
    private static boolean[] validThree = new boolean[1000];
    private static boolean[] prime = new boolean[100];
    private static Random random = new Random();
    private static int[] validFive =
            {24835, 24135, 24813, 24811, 24135, 24113, 24111, 23111, 21111, 11111};

    static {
        for (int i = 2; i < 100; i++)
            prime[i] = true;

        for (int i = 2; i < 10; i++) {
            for (int j = i + 1; j < 100; j++)
                if (prime[j])
                    prime[j] = j % i != 0;
        }

        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                for (int k = 0; k < 10; k++)
                    validThree[i * 100 + j * 10 + k] = prime[i + j + k];

        for (int i = 4; i < 100; i++)
            validTwo[i] = (i % 2 == 0) || (i % 3 == 0);
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        IdeaDeviceDecryptCheck a = new IdeaDeviceDecryptCheck();
        a.solve();
        out.close();
    }

    private static void process(StringBuilder sb, int n) {
        if ((n & 1) == 0)
            inValidThree(sb);
        else
            validThree(sb);

        if ((n & 2) == 0)
            inValidTwo(sb);
        else
            validTwo(sb);

        if ((n & 4) == 0)
            inValidFive(sb);
        else
            validFive(sb);
        sb.append('\n');
    }

    private static void inValidFive(StringBuilder sb) {
        boolean res = true;
        int n = random.nextInt(100000);
        while (res) {
            n = random.nextInt(100000);
            for (int i = 0; i < 10; i++)
                if (validFive[i] == n)
                    res = false;
        }
        sb.append(n);
    }

    private static void validFive(StringBuilder sb) {
        sb.append(validFive[random.nextInt(10)]);
    }

    static void inValidTwo(StringBuilder sb) {
        int n = random.nextInt(100);
        while (validThree[n])
            n = random.nextInt(100);
        sb.append(n);
    }

    private static void validTwo(StringBuilder sb) {
        int n = random.nextInt(100);
        while (!validThree[n])
            n = random.nextInt(100);
        sb.append(n);
    }

    private static void inValidThree(StringBuilder sb) {
        int n = random.nextInt(1000);
        while (validThree[n])
            n = random.nextInt(1000);
        sb.append(n);
    }

    private static void validThree(StringBuilder sb) {
        int n = random.nextInt(1000);
        while (!validThree[n])
            n = random.nextInt(1000);
        sb.append(n);
    }

    public void solve() throws IOException {
        int t = 100;
        StringBuilder sb = new StringBuilder(11 * t + 4);
        sb.append(t).append('\n');
        for (int i = 0; i < 100; i++)
            process(sb, i);
        out.print(sb);
    }
}
