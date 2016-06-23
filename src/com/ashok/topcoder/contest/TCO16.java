package com.ashok.topcoder.contest;


import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

import java.util.Arrays;

/**
 * Problem Name:
 * Contest ID:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class TCO16 {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        //        String input = "input_file.in", output = "output_file.out";
        //        FileInputStream fip = new FileInputStream(input);
        //        FileOutputStream fop = new FileOutputStream(output);
        //        in = new InputReader(fip);
        //        out = new Output(fop);

        TCO16 a = new TCO16();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        EllysTimeMachine e = new EllysTimeMachine();
        while (true) {
            out.println(e.getTime(in.read()));
            out.flush();
        }
    }

    class EllysTimeMachine {
        public String getTime(String time) {
            int h = hour(time), m = minute(time);

            return minuteToHour(m) + ":" + hourToMinute(h);
        }

        String hourToMinute(int hour) {
            int minute = hour * 5 % 60;

            if (minute >= 10)
                return "" + minute;

            return "0" + minute;
        }

        String minuteToHour(int minute) {
            int hour = minute / 5;
            if (hour == 0)
                return "12";

            if (hour >= 10)
                return "" + hour;

            return "0" + hour;
        }

        int hour(String time) {
            return time.charAt(0) * 10 + time.charAt(1) - 11 * '0';
        }

        int minute(String time) {
            return time.charAt(3) * 10 + time.charAt(4) - 11 * '0';
        }
    }
}
