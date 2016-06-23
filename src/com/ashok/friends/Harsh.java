package com.ashok.friends;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * This class is to solve Harshvardhan's problems (programming only ;) )
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Harsh {
    private static Output out;
    private static InputReader in;
    private static final String path =
        "D:\\GitHub\\Language\\Language\\Common\\src\\Code\\Main\\";

    public static void main(String[] args) throws IOException {
        in = new InputReader(path + "input.txt");
        out = new Output(path + "output.txt");

        Harsh a = new Harsh();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {

        StringBuilder output = new StringBuilder();

        try {
            while (true)
                output.append(process(in.readLine())).append('\n');
        } catch (Exception e) {
            // do nothing
        } finally {
            out.print(output);
        }

        //        while (true) {
        //            out.println(pattern(in.read(), in.read()));
        //            out.flush();
        //        }
    }

    private static boolean pattern(String a, String b) {
        if (a.length() > b.length())
            return false;

        int[] map = new int[256], copy = new int[256];

        for (int i = 0; i < a.length(); i++)
            map[a.charAt(i)]++;

        for (int j = 0; j < b.length(); j++)
            copy[b.charAt(j)]++;

        for (int i = 0; i < a.length(); i++)
            if (map[a.charAt(i)] > copy[a.charAt(i)])
                return false;

        return true;
    }

    private static int days(int x, int y, int n) {
        if (x >= n)
            return 1;

        int velocity = x - y;
        int target = n - y - 1;

        return 1 + target / velocity;
    }

    private static String process(String s) {
        StringBuilder sb = new StringBuilder(s.length());

        int comment = s.indexOf("//");

        for (int i = 0; i < comment; i++) {
            if (s.charAt(i) == '+')
                sb.append('*');
            else
                sb.append(s.charAt(i));
        }

        int i = Math.max(0, comment);
        for (; i < s.length(); i++)
            sb.append(s.charAt(i));

        return sb.toString();
    }
}
