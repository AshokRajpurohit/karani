package com.ashok.lang.main;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.math.Numbers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class Main {

    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private static final String path =
            "D:\\GitHub\\Language\\Language\\Common\\src\\Code\\Main\\";

    private static String line =
            "---------------------------------------------------------------------";

    public static void main(String[] args) throws IOException, Exception {
        String input = "input_file.in", output = "output.txt";

        Main a = new Main();
        a.solve();
        out.close();
    }

    static interface MySecondEnum {
        public String toString();

        public String secondName();

        public String getTableName();
    }

    static enum Test implements MySecondEnum {
        A("Ashok"),
        B("Bak-Bak");

        String testParam;
        final static String tabeName = "My Own Table Foosball";

        Test(String name) {
            testParam = name;
        }

        public String toString() {
            return testParam;
        }

        public String secondName() {
            return "Rajpurohit";
        }

        public String getTableName() {
            return tabeName;
        }
    }

    enum Table2 implements MySecondEnum {
        id("id"),
        name("name"),
        pK("p_K");

        final String column;
        final static String table = "ClientConfiguration";

        Table2(String name) {
            column = name;
        }

        public String secondName() {
            return column;
        }

        public String getTableName() {
            return table;
        }
    }

    public void solve() throws IOException, Exception {
        for (Table2 t : Table2.values())
            System.out.println(formQuery(t));

        System.out.println(line);

        List<MySecondEnum> list = new LinkedList<MySecondEnum>();

        for (Table2 t : Table2.values())
            list.add(t);

        for (MySecondEnum t : list)
            System.out.println(formQuery(t));

        System.out.println(Table2.name instanceof MySecondEnum);


        System.out.println(line);
        System.out.println(formQuery(list));
        System.out.println(line);

        while (true) {
            int n = in.readInt();
            out.println(Numbers.digitCounts(n) + ", " + Numbers.firstDigit(n));
            out.flush();
        }
    }

    final static class implenter implements Iterator, Comparator {

        public boolean hasNext() {
            return false;
        }

        public Object next() {
            return null;
        }

        public String getName() {
            implenter im = new implenter();
            im.getClass().getName();

            return "ashok";
        }

        public int compare(Object o1, Object o2) {
            return 0;
        }
    }

    private static String formQuery(List<MySecondEnum> list) {
        StringBuilder sb = new StringBuilder();

        for (MySecondEnum mse : list) {
            sb.append("select ").append(mse.secondName()).append(" from ").append(mse.getTableName()).append('\n');
        }

        return sb.toString();
    }

    private static String formQuery(MySecondEnum column) {
        return "select " + column.secondName() + " from " +
                column.getTableName();
    }

    final static class LoggingExceptions {
        public static void test() {
            try {
                throw new LoggingException();
            } catch (LoggingException e) {
                System.err.println("Caught " + e);
            }

            try {
                throw new LoggingException();
            } catch (LoggingException e) {
                System.err.println("Caught Again " + e);
            }
        }
    }

    final static class LoggingException extends Exception {
        private static Logger logger = Logger.getLogger("Logging Exception");

        public LoggingException() {
            StringWriter trace = new StringWriter();
            printStackTrace(new PrintWriter(trace));
            logger.severe(trace.toString());
        }
    }
}
