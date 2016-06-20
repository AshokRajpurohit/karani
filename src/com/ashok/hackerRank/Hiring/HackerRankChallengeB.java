package com.ashok.hackerRank.hiring;


import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Problem: File Short
 * HackerRank Challenge
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class HackerRankChallengeB {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        //        File file = new File("input.txt");
        //        System.out.println(file.getAbsolutePath());
        //        FileInputStream fip = new FileInputStream("D:\\Competetion\\HackerRank\\Client\\src\\Hiring\\input.txt");
        //        in = fip;
        in = System.in;
        out = new PrintWriter(outputStream);

        //        String input = "input_file.in", output = "output_file.out";
        //        FileInputStream fip = new FileInputStream(input);
        //        FileOutputStream fop = new FileOutputStream(output);
        //        in = new InputReader(fip);
        //        out = new Output(fop);

        HackerRankChallengeB a = new HackerRankChallengeB();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        LinkedList<String> fileList = new LinkedList<String>();
        try {
            while (true) {
                fileList.add(in.read());
            }
        } catch (IOException ioe) {
            // do nothing
        }

        LinkedList<String> stringList = new LinkedList<String>();

        for (String str : fileList)
            populate(stringList, str);

        String[] ar = listToArray(stringList);
        Arrays.sort(ar, comparator);
        print(ar);
    }

    private static void print(String[] ar) {
        StringBuilder sb = new StringBuilder(ar.length << 1);
        for (String str : ar)
            if (formatString(str) != "")
                sb.append(str).append('\n');

        out.print(sb);
    }

    private static String formatString(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++)
            if (!Character.isWhitespace(s.charAt(i)))
                sb.append(s.charAt(i));

        if (sb.length() == 0)
            return "";

        return sb.toString();
    }

    private static String[] listToArray(LinkedList<String> list) {
        String[] ar = new String[list.size()];
        int i = 0;
        for (String str : list)
            ar[i++] = str;

        return ar;
    }

    private static void populate(LinkedList<String> strings,
                                 String file) throws IOException {
        in.close();
        FileInputStream fip = new FileInputStream(file);
        //        FileOutputStream fop = new FileOutputStream(output);
        in = fip;
        InputReader input = new InputReader();

        try {
            while (true)
                format(strings, input.readLine());
        } catch (IOException ioe) {
            // do nothing
        }
    }

    private static void format(LinkedList<String> list, String string) {
        String[] ar = string.split(" ");
        String min = ar[0], max = ar[0];

        for (int i = 1; i < ar.length; i++)
            if (ar[i].compareTo(min) <= 0)
                min = ar[i];
            else if (ar[i].compareTo(max) > 0)
                max = ar[i];

        list.add(min + max);
    }

    private static Comparator<String> comparator = new compare();

    final static class compare implements Comparator<String> {

        public int compare(String o1, String o2) {
            if (o1.length() != o2.length())
                return o1.length() - o2.length();

            return o1.compareTo(o2);
        }
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

        public int readInt() throws IOException {
            int number = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            if (bufferSize == -1)
                throw new IOException("No new bytes");
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                number = (number << 3) + (number << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            return number * s;
        }

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = readLong();

            return ar;
        }

        public String read() throws IOException {
            StringBuilder sb = new StringBuilder();
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                 '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                    buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }

        public String readLine() throws IOException {
            StringBuilder sb = new StringBuilder();
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                 '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize; ++offset) {
                if (buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                 '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (int i = 0; offset < bufferSize && i < n; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                    buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }
    }
}
