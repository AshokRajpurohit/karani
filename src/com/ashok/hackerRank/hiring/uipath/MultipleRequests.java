/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerRank.hiring.uipath;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MultipleRequests {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        play();
        MultipleRequests a = new MultipleRequests();
        try {
//            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        String fileName = in.read();
        InputReader fileReader = new InputReader(fileName);
        PrintWriter fileWriter = new PrintWriter("req_" + fileName);
        Pattern p = Pattern.compile("\\[(.*?)\\]");
        Map<String, Counter> dateCounterMap = new HashMap<>();
        try {
            while (fileReader.hasNext()) {
                String line = fileReader.readLine();
                Matcher matcher = p.matcher(line);
                String dateString = matcher.group(1).split(" ")[0];
                dateCounterMap.putIfAbsent(dateString, new Counter());
                dateCounterMap.get(dateString).increment();
            }

            dateCounterMap.entrySet()
                    .stream()
                    .filter((entry) -> entry.getValue().count > 1)
                    .forEach((entry) -> fileWriter.println(entry.getKey()));
        } finally {
            fileReader.close();
            fileWriter.close();
        }

    }

    private static void play() throws IOException {
        Pattern p = Pattern.compile("\\[(.*?)\\]");
        Map<String, Counter> dateCounterMap = new HashMap<>();
        int n = in.readInt();
        while (n-- > 0) {
            String line = in.readLine();
            Matcher matcher = p.matcher(line);
            while (matcher.find()) {
                String dateString = matcher.group(1).split(" ")[0];
                dateCounterMap.putIfAbsent(dateString, new Counter());
                dateCounterMap.get(dateString).increment();
            }
        }

        dateCounterMap.entrySet()
                .stream()
                .filter((entry) -> entry.getValue().count > 1)
                .forEach((entry) -> out.println(entry.getKey()));
    }

    final static class Counter {
        int count = 0;

        void increment() {
            count++;
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

        public InputReader(String file) throws FileNotFoundException {
            in = new FileInputStream(file);
        }

        public void close() throws IOException {
            in.close();
        }

        public boolean hasNext() throws IOException {
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            return bufferSize != -1;
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
