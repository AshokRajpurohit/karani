package com.ashok.friends.harsh;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

public class CircularJump {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;

            int n = in.readInt(), source = in.readInt() - 1, target = in.readInt() - 1;
            RoundTable roundTable = new RoundTable(n);

            for (int i = 0; i < n; i++)
                roundTable.addChair(new Chair(i, in.readInt()));

            out.println(roundTable.getMinimumJumps(source, target));
        }
    }

    final static class RoundTable {
        final int size;
        final Chair[] chairs;

        RoundTable(int size) {
            this.size = size;
            chairs = new Chair[size];
        }

        void addChair(Chair chair) {
            chairs[chair.id] = chair;
        }

        int getMinimumJumps(int from, int to) {
            if (from == to)
                return 0;

            process();
            LinkedList<Chair> queue = new LinkedList<>();
            boolean[] map = new boolean[size];
            Chair target = chairs[to], source = chairs[from];
            queue.addLast(target);
            queue.addLast(INVALID_CHAIR);
            map[to] = true;

            int level = 0;
            while (queue.size() > 1) {
                level++;
                Chair top = queue.removeFirst();

                while (top != INVALID_CHAIR) {
                    for (Chair chair : top.sourceChairs) {
                        if (chair == source)
                            return level;

                        if (map[chair.id])
                            continue;

                        map[chair.id] = true;
                        queue.addLast(chair);
                    }

                    top = queue.removeFirst();
                }

                queue.addLast(INVALID_CHAIR);
            }

            return -1;
        }

        void process() {
            for (Chair chair : chairs) {
                chairs[(chair.id + chair.jump) % size].sourceChairs.addLast(chair);
                chairs[(size + (chair.id - chair.jump) % size) % size].sourceChairs.addLast(chair);
            }
        }
    }

    private static final Chair INVALID_CHAIR = new Chair(-1, -1);

    final static class Chair {
        final int id, jump;
        final LinkedList<Chair> sourceChairs = new LinkedList<>();

        Chair(int id, int jump) {
            this.id = id;
            this.jump = jump;
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
    }
}