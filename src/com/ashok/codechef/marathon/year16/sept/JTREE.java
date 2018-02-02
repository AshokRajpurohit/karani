package com.ashok.codechef.marathon.year16.sept;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Problem Name: JosephLand
 * Link: https://www.codechef.com/SEPT16/problems/JTREE
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class JTREE {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final long max = 1L * Integer.MAX_VALUE * 100000;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), m = in.readInt();
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++)
            nodes[i] = new Node(i);

        for (int i = 1; i < n; i++) {
            int from = in.readInt() - 1, to = in.readInt() - 1;
            nodes[from].next = nodes[to];
        }

        nodes[0].cost = 0;
        nodes[0].nextLower = nodes[0];
        nodes[0].distance = 0;

        for (int i = 0; i < m; i++) {
            int node = in.readInt() - 1, count = in.readInt(),
                    cost = in.readInt();

            nodes[node].tickets.add(new Ticket(count, cost));
        }

        int friends = in.readInt();
        StringBuilder sb = new StringBuilder(friends << 2);
        for (int i = 0; i < friends; i++) {
            int city = in.readInt() - 1;
            process(nodes[city]);

            sb.append(nodes[city].cost).append('\n');
        }

        out.print(sb);
    }

    private static void process(Node node) {
        if (node.cost != max)
            return;

        formatTickets(node);
        formatDistance(node);
        process(node.next);
        Node next = node.next;
        node.cost = node.tickets.getFirst().cost + next.cost;

        for (Ticket ticket : node.tickets) {
            if (ticket.count >= node.distance) {
                node.cost = Math.min(node.cost, ticket.cost);
                break;
            }

            while (node.distance - next.distance <= ticket.count) {
                node.cost = Math.min(node.cost, ticket.cost + next.cost);
                next = next.nextLower;
            }
        }

        next = node.next;
        while (next.index != 0 && next.cost >= node.cost)
            next = next.nextLower;

        node.nextLower = next;
    }

    private static void formatDistance(Node node) {
        if (node.distance != -1)
            return;

        formatDistance(node.next);
        node.distance = node.next.distance + 1;
    }

    private static void formatTickets(Node node) {
        if (node.tickets.size() < 2)
            return;

        Collections.sort(node.tickets, TICKET_COMPARATOR);
        LinkedList<Ticket> tickets = new LinkedList<>();
        tickets.add(node.tickets.removeFirst());

        for (Ticket ticket : node.tickets) {
            if (ticket.count <= tickets.getLast().count)
                continue;

            tickets.addLast(ticket);
        }

        node.tickets = tickets;
    }

    final static class Node {
        int index, distance = -1;
        long cost = max;
        LinkedList<Ticket> tickets = new LinkedList<>();
        Node nextLower, next;

        Node(int index) {
            this.index = index;
        }
    }

    private final static Comparator TICKET_COMPARATOR = new TicketComparator();

    final static class TicketComparator implements Comparator<Ticket> {

        @Override
        public int compare(Ticket o1, Ticket o2) {
            if (o1.cost == o2.cost)
                return o2.count - o1.count;

            return o1.cost - o2.cost;
        }
    }

    final static class Ticket {
        int cost, count;

        Ticket(int count, int cost) {
            this.cost = cost;
            this.count = count;
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
