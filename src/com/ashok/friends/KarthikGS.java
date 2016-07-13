package com.ashok.friends;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Problem Name: Karthik GS
 * Link: Office
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class KarthikGS {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    private final static long[] bases =
            {Long.valueOf("1000000000000"), 1000000000, 1000000, 1000, 100};

    private final static String[] numbers =
            {"T", "B", "M", "K", "Hundreds"};

    private static final NumberFormat formatter = new DecimalFormat("#0.0");

    public static void main(String[] args) throws IOException {
        KarthikGS a = new KarthikGS();
        try {
            a.solve();
        } catch (IOException ioe) {
            // do nothing
        }
        out.close();
    }

    private void solve() throws IOException {
        while (true) {
            out.println(convertNumberInUI(in.readLong()));
            out.flush();
        }
    }

    private static String convertNumberInUI(long number) {
        if (number < 1000)
            return String.valueOf(number);

        for (int i = 0; i < bases.length; i++) {
            if (bases[i] <= number) {
                return formatter.format(number * 1.0 / bases[i]) + " " + numbers[i];
            }
        }

        return String.valueOf(number);
    }
}
