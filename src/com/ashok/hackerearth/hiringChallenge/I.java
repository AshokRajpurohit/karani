package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link: McAfee Challenge | Scheduling War
 * <p>
 * Prateek and Chintu are working on different projects both with equal priority.
 * They both need to run some batches of processes. A batch has processes which
 * need some systems to run them irrespective of the number of process running
 * on each dependent system. If a batch runs then the dependent systems are
 * occupied by its processes. No system can run processes from different
 * projects and thus a system can process only chintu's processes or prateek's
 * processes. Their manager being a stylo creep has allowed prateek to run his
 * batches. Chintu felt offended and complained the CTO directly due to which
 * the manager came up with a condition that if chintu can increase the number
 * of processes running in total by replacing some or all of prateeks processes
 * then only he can run his batches. Chintu wants to maximize the total
 * processes running in order to show the manager his skills. Help him complete
 * his task.
 * <p>
 * Note:
 * <p>
 * A system can run any number of process from multiple batches at the same time
 * but only of Chintu or of Prateek.
 * <p>
 * Prateek's processes are running on some or all systems. Now, chintu has to run
 * his batches of processes inorder to increase the number of running processes
 * across all systems. A batch of chintu either runs all its processes or doesn't
 * run any of them.
 * <p>
 * If Chintu has replaced a system with his own processes then another batch of
 * him can run its processes on that system.
 * <p>
 * A batch needs 's' systems and runs 'p' processes overall, in any manner.
 */

public class I {

    private static PrintWriter out;
    private static InputStream in;
    // pra is prateek and prachi is for prateek and chintu.
    private static int[] pra, prachi, batch_proc;
    private static int[][] chintu_batch;
    private static int score = 0, max = 0;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        I a = new I();
        a.solve();
        out.close();
    }

    private static void chintu_solve(int batch_index) {
        if (batch_index == batch_proc.length) {
//            for (int i = 0; i < batch_proc.length; i++) {
//                if (prachi[i] == 0) {
//                    score = score + pra[i];
//                }
//            }
//            max = max > score ? max : score;
            return;
        }
        score = score + batch_proc[batch_index];
        // let's use batch batch_index
        for (int i = 0; i < chintu_batch[batch_index].length; i++) {
            if (prachi[chintu_batch[batch_index][i]] == 1) {
                chintu_batch[batch_index][i] = -chintu_batch[batch_index][i];
            } else {
                prachi[chintu_batch[batch_index][i]] = 1;
                score = score - pra[chintu_batch[batch_index][i]];
            }
//            System.out.println(batch_index + "," + score);
        }
        max = max > score ? max : score;
        chintu_solve(batch_index + 1);
        // let's remove the batch used.
        score = score - batch_proc[batch_index];

        for (int i = 0; i < chintu_batch[batch_index].length; i++) {
            if (chintu_batch[batch_index][i] < 0) {
                chintu_batch[batch_index][i] = -chintu_batch[batch_index][i];
            } else {
                prachi[chintu_batch[batch_index][i]] = 0;
                score = score + pra[chintu_batch[batch_index][i]];
            }
        }
        chintu_solve(batch_index + 1);
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        pra = new int[n];
        prachi = new int[n];
        for (int i = 0; i < n; i++) {
            pra[i] = in.readInt();
            score = score + pra[i];
        }
//        max = score;
        int b = in.readInt();
        batch_proc = new int[b];
        chintu_batch = new int[b][];
        for (int i = 0; i < b; i++) {
            int pi = in.readInt();
            chintu_batch[i] = new int[pi];
            for (int j = 0; j < pi; j++) {
                chintu_batch[i][j] = in.readInt() - 1;
            }
            batch_proc[i] = in.readInt();
        }
        chintu_solve(0);
        out.println(max - score);

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

    }
}
