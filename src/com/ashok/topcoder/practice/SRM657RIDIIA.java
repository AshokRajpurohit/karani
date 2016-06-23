package com.ashok.topcoder.practice;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link:
 *  http://community.topcoder.com/stat?c=problem_statement&pm=13773&rd=16417
 */

public class SRM657RIDIIA {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        SRM657RIDIIA a = new SRM657RIDIIA();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        EightRooks er = new EightRooks();
        String[] s =
        {"........",
         "........",
         "........",
         "........",
         "........",
         "........",
         "........",
         "........"};
        out.println(er.isCorrect(s));
    }

    class EightRooks {
        private String yes = "Correct", no = "Incorrect";

        public String isCorrect(String[] board) {
            boolean[] row = new boolean[8];
            boolean[] col = new boolean[8];
            int count = 0;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i].charAt(j) == 'R') {
                        count++;
                        if (row[i] || col[j])
                            return no;
                        row[i] = true;
                        col[j] = true;
                    }
                }
            }
            return count == 8 ? yes : no;
        }
    }
}
