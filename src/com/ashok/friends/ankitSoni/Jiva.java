package com.ashok.friends.ankitSoni;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class Jiva {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve2();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        Time base = new Time(in.read());
        int n = in.readInt();
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            n--;
            sb.append(base.subtract(new Time(in.read()))).append('\n');
        }
        out.print(sb);
    }

    private static void solve2() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            out.println(process(in.readLine(), in.read(), in.read().charAt(0), in.readInt()));
        }
    }

    private static String process(String s, String s2, int c, int index) {
        if (index + s2.length() > s.length()) {
            return "Goodbye Watson.";
        }

        String s1 = s.substring(index);
        int position = s1.indexOf(s2);
        int no = 'N';
        if (position == -1)
            return "Goodbye Watson";

        if (c == no)
            return String.valueOf(position + index);

        boolean good = true;
        if (index > 0 && position == 0)
            good = s.charAt(index - 1) == ' ';

        if (!good)
            return "Goodbye Watson.";

        if (position + s2.length() < s1.length())
            good = s1.charAt(position + s2.length()) == ' ';

        if (!good)
            return "Goodbye Watson.";

        return String.valueOf(position + index);
    }

    private static String process2(String s, String s2, int c, int index) {
        if (index + s2.length() > s.length()) {
            return "Goodbye Watson.";
        }

        String s1 = s.substring(index);
        int position = s1.indexOf(s2);
        int no = 'N';
        if (position == -1)
            return "Goodbye Watson";

        if (c == no)
            return String.valueOf(position + index);

        String[] ar = s1.split(" ");
        for (String e: ar)
            if (e.equals(s2))
                return String.valueOf(position + index);

        return "Goodbye Watson";
    }

    final static class Time {
        final int hours, minutes, seconds;

        Time(String timeString) {
            String[] timeAr = timeString.split(":");
            hours = Integer.valueOf(timeAr[0]);
            minutes = Integer.valueOf(timeAr[1]);
            seconds = Integer.valueOf(timeAr[2]);
        }

        Time(int h, int m, int s) {
            if (s < 0) {
                s += 60;
                m--;
            }

            if (m < 0) {
                m += 60;
                h--;
            }

            if (h < 0)
                h += 24;

            hours = h;
            minutes = m;
            seconds = s;
        }

        Time subtract(Time t) {
            return new Time(hours - t.hours, minutes - t.minutes, seconds - t.seconds);
        }

        @Override
        public String toString() {
            if (hours != 0) {
                if (hours == 1)
                    return "1 hour ago";
                else
                    return hours + " hours ago";
            }

            if (minutes != 0) {
                if (minutes == 1)
                    return "1 minute ago";
                else
                    return minutes + " minutes ago";
            }

            if (seconds == 0)
                return "now";

            if (seconds == 1)
                return "1 second ago";

            return seconds + " seconds ago";
        }
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

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
    }
}