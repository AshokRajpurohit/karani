package com.ashok.codechef.marathon.year15.MARCH15;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.ArrayList;

/**
 * @author  Ashok Rajpurohit
 * problem Link: http://www.codechef.com/MARCH15/problems/RNG
 */

public class RNG {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 104857601;
    private static ArrayList<Integer> al;
    private static int[] C;
    private static int k;
    private static List head;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        RNG a = new RNG();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        StringBuilder sb = new StringBuilder();
        k = in.readInt();
        long n = in.readLong();
        C = new int[k];
        //        al = new ArrayList<Integer>(k+1);
        head = new List(in.readInt());
        List temp;

        for (int i = 1; i < k; i++) {
            temp = new List(in.readInt(), head);
            head = temp;
            //            al.add(in.readInt());
        }

        for (int i = 0; i < k; i++)
            C[i] = in.readInt();

        sb.append(solve(n)).append('\n');
        out.print(sb);
    }

    private static int solve(long n) {
        while (n > k) {
            n--;
            shuffle();
        }

        List temp = head;

        for (int i = 0; i < k - 1; i++) {
            temp = temp.next;
        }
        return temp.data;
    }

    private static void shuffle() {
        List temp = head;
        long res = 0;

        for (int i = 0; i < k; i++) {
            res = res + temp.data * C[i];
            res = res > mod ? res - mod : res;
            if (temp.next != null)
                temp = temp.next;
        }
        head = head.next;
        temp.next = new List((int)res);
        System.out.println(temp.data);
    }

    //    private static void shuffle() {
    //        long temp_A = 0;
    //        for(int i=0, j=k-1; i<k; i++,j--) {
    //            temp_A = temp_A + (long)C[j]*(long)al.get(i);
    //            temp_A = temp_A%mod;
    //        }
    //        al.remove(0);
    //        int res = (int)temp_A;
    //        al.add(res);
    //    }

    final static class List {
        List next;
        int data;

        List(int data) {
            this.data = data;
            this.next = null;
        }

        List(int data, List next) {
            this.data = data;
            this.next = next;
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
    }
}
