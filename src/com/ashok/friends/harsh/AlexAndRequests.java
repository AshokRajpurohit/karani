package com.ashok.friends.harsh;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class AlexAndRequests {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final String YES = "YES", NO = "NO";

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), q = in.readInt();
        AlexFirm firm = new AlexFirm(n);
        StringBuilder sb = new StringBuilder(q << 2);
        while (q > 0) {
            q--;

            sb.append(firm.assignRequest(in.readInt() - 1) ? YES : NO).append('\n');
        }

        out.print(sb);
    }

    final static class AlexFirm {
        final int size;
        final AllocationSystem[] allocationSystems;

        AlexFirm(int size) {
            this.size = size;
            allocationSystems = new AllocationSystem[size];
            for (int i = 0; i < size; i++)
                allocationSystems[i] = new AllocationSystem(i);
        }

        boolean assignRequest(int priority) {
            AllocationSystem system = INVALID_ALLOCATION_SYSTEM;
            for (int i = Math.min(priority - 1, size - 1); i >= 0; i--) {
                if (allocationSystems[i].requestPriority < priority) {
                    allocationSystems[i].addRequest(priority);
                    return true;
                }
            }

            return false;
        }
    }

    private static final AllocationSystem INVALID_ALLOCATION_SYSTEM = new AllocationSystem(-1);

    final static class AllocationSystem {
        final int id;
        int requestPriority = -1;

        AllocationSystem(int id) {
            this.id = id;
        }

        void addRequest(int priority) {
            requestPriority = priority;
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