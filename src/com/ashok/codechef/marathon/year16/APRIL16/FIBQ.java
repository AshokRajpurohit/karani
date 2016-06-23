package com.ashok.codechef.marathon.year16.APRIL16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Fibonacci Queries
 * https://www.codechef.com/APRIL16/problems/FIBQ
 *
 *
 * We will use matrix exponentiation to get nth fibonacci term.
 * Let's say the base matrix is M.
 * Then nth fibonacci term will be (0,0)th term in matrix (M raised to power n - 1).
 *
 * Let's say the ith element in the array is Ai.
 * Let's say sum of elements from jth subset is Sj.
 *
 * Let say we have two elements A1 and A2.
 * So S1 = A1, S2 = A2, S3 = A1 + A2.
 *
 * We need to find the value of F(S1) + F(S2) + F(S3)
 * i. e. pow(M, S1 - 1) + pow(M, S2 - 1) + pow(M, S3 - 1) ..... (1)
 * or Sum(pow(M, Si - 1)).
 *
 * If you remember in Combinatorics and Binomial Theorem:
 * (1 + a) * (1 + b) * (1 + c) * ... * (1 + x) =
 *  1 + Sum(multiplication of elements in Subset)...... (2)
 *
 *  if you see our equation (1) the multiplication is converted into
 *  sum of powers i. e. (sum of elements in subset).
 *
 *  So let's convert (1) into simpler form like (2)
 *  (I + pow(M, A1)) * (I + pow(M, A2)) * .... * (I + pow(M, Ai)) ..... (3)
 *
 *  Now if you notice there are two things extra that we would want to remove.
 *  first the extra I (1 in case of binomial) and one power of M extra.
 *  so if our answer is ans then (3) is M * (I + ans).
 *
 *  to remove I we can just subtract I from (3) and to remove M we need to
 *  multiply it with inverse matrix of M, i.e. M'.
 *
 *  So the final matrix containg the answer is
 *  M' * ((I + pow(M, A1)) **** (I + pow(M, Ai)) ***(I + pow(M, An)) - I)....(4)
 *
 *  the final answer is element at (0,0) in the matrix returned by (4).
 *
 * Links:
 * https://github.com/AshokRajpurohit/JavaAlgorithms/blob/master/Language/Common/src/Code/DSA/RangeQueryUpdate.java
 * https://github.com/AshokRajpurohit/JavaAlgorithms/blob/master/Language/Common/src/Code/Algorithms/Matrix.java
 * https://github.com/AshokRajpurohit/JavaAlgorithms/blob/master/Language/Common/src/Problems/Fibonacci.java
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class FIBQ {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007;
    private static Matrix base, identity, inverse;
    private static int[] ar;
    private static Matrix[] basePower = new Matrix[40];

    static {
        long[][] ar = new long[2][];

        ar[0] = new long[] { 1, 1 };
        ar[1] = new long[] { 1, 0 };

        base = new Matrix(ar);

        ar[0][1] = 0;
        ar[1][0] = 0;
        ar[1][1] = 1;

        identity = new Matrix(ar);

        ar[0][0] = 0;
        ar[0][1] = 1;
        ar[1][0] = 1;
        ar[1][1] = mod - 1;

        inverse = new Matrix(ar);

        basePower[0] = base;
        basePower[1] = base;

        Matrix temp = base.clone();

        for (int i = 2; i < basePower.length; i++) {
            basePower[i] = basePower[i - 1].clone();
            Matrix.square(basePower[i], temp);
        }
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        FIBQ a = new FIBQ();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt(), m = in.readInt();

        ar = in.readIntArray(n);
        RangeQueryUpdate rq = new RangeQueryUpdate(ar);
        StringBuilder sb = new StringBuilder(m << 3);
        int c = 19, q = 33;

        while (m > 0) {
            m--;
            int type = in.readInt(), a = in.readInt(), b = in.readInt();

            if (type == c)
                rq.update(a - 1, b);
            else
                sb.append(rq.query(a - 1, b - 1)).append('\n');
        }

        out.print(sb);
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
    }

    final static class Matrix {
        private long[][] matrix;
        private int n = 2, m = 2; // for fibonacci series 2x2 matrix we need.

        private Matrix() {
            /**
             * this constructor is only for internal purpose to create dummy
             * matrix to store the result temporary.
             */
        }

        public Matrix(long[][] ar) {
            matrix = new long[n][m];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    matrix[i][j] = ar[i][j];
        }

        /**
         * Multiplies matrix a and b, stores the result in new matrix.
         * @param a
         * @param b
         * @return returns a x b matrix
         */
        private static Matrix multiply(Matrix a, Matrix b) {
            Matrix result = new Matrix();
            long[][] ar = new long[a.n][b.m];
            for (int i = 0; i < a.n; i++) {
                for (int j = 0; j < b.m; j++) {
                    for (int k = 0; k < a.m; k++)
                        ar[i][j] += a.matrix[i][k] * b.matrix[k][j] % mod;
                }
            }
            result.matrix = ar;
            result.mod(mod);
            return result;
        }

        private void add(Matrix m) {
            for (int i = 0; i < this.n; i++)
                for (int j = 0; j < this.m; j++)
                    this.matrix[i][j] += m.matrix[i][j];
        }

        private void subtract(Matrix m) {
            for (int i = 0; i < this.n; i++)
                for (int j = 0; j < this.m; j++)
                    this.matrix[i][j] -= m.matrix[i][j];
        }

        /**
         * Multiplies two matrices a and b and stores the result into third matrix
         * @param a
         * @param b
         * @param result
         */
        private static void multiply(Matrix a, Matrix b, Matrix result) {
            result.reset();
            for (int i = 0; i < a.n; i++) {
                for (int j = 0; j < b.m; j++) {
                    for (int k = 0; k < a.m; k++)
                        result.matrix[i][j] += a.matrix[i][k] * b.matrix[k][j];
                }
            }
        }

        public static Matrix pow(int n) {
            if (n < 2)
                return base.clone();

            Matrix result = identity.clone();

            int i = 0;
            Matrix temp = base.clone();

            while (n > 0) {
                i++;
                if ((n & 1) == 1) {
                    Matrix.multiply(basePower[i], result, temp);
                    result.copy(temp);
                    result.mod(mod);
                }

                n = n >>> 1;
            }

            return result;
        }

        /**
         * Copy the contents of matrix temp into this matrix.
         * @param temp Temporary Matrix.
         */
        private void copy(Matrix temp) {
            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    matrix[i][j] = temp.matrix[i][j];
        }

        /**
         * Squaring the matrix a, storing the result temporarily in matrix b.
         * finally the result is stored in matrix a.
         * @param a matrix to be squared
         * @param b temporary result storage
         */
        private static void square(Matrix a, Matrix b) {
            b.reset();
            for (int i = 0; i < a.n; i++)
                for (int j = 0; j < a.n; j++) {
                    for (int k = 0; k < a.n; k++)
                        b.matrix[i][j] += a.matrix[i][k] * a.matrix[k][j];
                }

            for (int i = 0; i < a.n; i++)
                for (int j = 0; j < a.n; j++)
                    a.matrix[i][j] = b.matrix[i][j] % mod;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;

            if (!(o instanceof Matrix))
                return false;

            Matrix x = (Matrix)o;
            if (x.n != this.n || x.m != this.m)
                return false;

            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    if (this.matrix[i][j] != x.matrix[i][j])
                        return false;

            return true;
        }

        /**
         * Squares the matrix and stores the result in the same matrix.
         * @throws Exception if the Matrix is not square matrix.
         */
        public void square() {
            Matrix temp = clone();
            square(this, temp);
        }

        /**
         * Create and Returns a new copy of this object.
         * @return
         */
        public Matrix clone() {
            return new Matrix(this.matrix);
        }

        public long get(int i, int j) {
            return matrix[i][j];
        }

        public void mod(long mod) {
            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    matrix[i][j] %= mod;
        }

        /**
         * resets the all values in matrix to zero.
         */
        private void reset() {
            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    matrix[i][j] = 0;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(n * m << 1);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++)
                    sb.append(matrix[i][j]).append(' ');
                sb.append('\n');
            }

            return sb.toString();
        }
    }

    final static class RangeQueryUpdate {
        private Node root;

        public RangeQueryUpdate(int[] ar) {
            construct(ar);
        }

        public long query(int L, int R) {
            Matrix m = query(root, L, R).clone();
            m.subtract(identity);
            m = Matrix.multiply(inverse, m);

            return m.matrix[0][0];
        }

        private Matrix query(Node node, int L, int R) {
            if (node.l == L && node.r == R)
                return node.cell;

            int mid = (node.l + node.r) >>> 1;
            if (L > mid)
                return query(node.right, L, R);

            if (R <= mid)
                return query(node.left, L, R);

            return Matrix.multiply(query(node.left, L, mid),
                                   query(node.right, mid + 1, R));
        }

        public void update(int i, int data) {
            update(root, i, data);
        }

        private void update(Node root, int i, int data) {
            if (root.l == root.r) {
                root.cell = Matrix.pow(data);
                root.cell.add(identity);
                return;
            }

            int mid = (root.l + root.r) >>> 1;
            if (i > mid)
                update(root.right, i, data);
            else
                update(root.left, i, data);

            root.cell = Matrix.multiply(root.left.cell, root.right.cell);
        }

        private void construct(int[] ar) {
            root = new Node(0, ar.length - 1);
            int mid = (ar.length - 1) >>> 1;
            root.left = construct(ar, 0, mid);
            root.right = construct(ar, mid + 1, ar.length - 1);
            root.cell = Matrix.multiply(root.left.cell, root.right.cell);
        }

        private Node construct(int[] ar, int l, int r) {
            if (l == r)
                return new Node(l, l, Matrix.pow(ar[l]));

            Node temp = new Node(l, r);
            int mid = (l + r) >>> 1;
            temp.left = construct(ar, l, mid);
            temp.right = construct(ar, mid + 1, r);
            temp.cell = Matrix.multiply(temp.left.cell, temp.right.cell);
            return temp;
        }

        class Node {
            Node left, right;
            int l, r;
            Matrix cell;

            Node(int m, int n) {
                l = m;
                r = n;
            }

            Node(int m, int n, Matrix d) {
                l = m;
                r = n;
                cell = d;
                cell.add(identity);
            }
        }
    }
}
