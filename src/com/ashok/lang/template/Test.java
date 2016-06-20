package com.ashok.lang.template;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Test {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        //        in = new ExcelReader(path + "output.txt");
//        out = new Output(path + "output.txt");

        String path =
                "D:\\Java Projects\\karani\\src\\com\\ashok\\lang\\main\\";
        in = new InputReader();
        out = new Output(path + "output.txt", true);

        Test a = new Test();
        try {
            a.solve();
        } catch (IOException ioe) {
            // do nothing
        }
        out.close();
    }

    private void solve() throws IOException {
        while (true) {
            out.append(formatName(in.readLine())).append('\t');
//            out.flush();
            out.append("ashok").append('\n');
            out.flush();
        }
    }

    private static String formatName(String name) {
        StringBuilder sb = new StringBuilder();
        char[] ar = name.toCharArray();

        if (ar[0] >= 'a' && ar[0] <= 'z')
            sb.append((char) (ar[0] - 'a' + 'A'));
        else
            sb.append(ar[0]);

        for (int i = 1; i < ar.length; ) {
            if (ar[i] == ' ') {
                sb.append(ar[i]);
                i++;
                if (i == ar.length)
                    break;

                if (ar[i] >= 'a' && ar[i] <= 'z')
                    sb.append((char) (ar[i] - 'a' + 'A'));
                else
                    sb.append(ar[i]);

                i++;
            } else
                sb.append(ar[i++]);
        }

        return sb.toString();
    }
}
