/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.july19;

import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * Problem Name: Codechef War
 * Link: https://www.codechef.com/JULY19A/problems/CHFWAR
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class CodechefWar {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        test();
        solve();
        in.close();
        out.close();
    }

    private static void test() throws IOException {
        while (true) {
            int n = in.readInt(), power = in.readInt();
            int[] ar = Generators.generateRandomIntegerArray(n - 1, in.readInt(), in.readInt());
            out.println(process(ar, power));
            out.flush();
        }
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] swords = in.readIntArray(n - 1);
            int firePower = in.readInt();
            out.println(process(swords, firePower));
        }
    }

    private static Response process(int[] swords, int firePower) {
        ShieldStrengthCalculator strengthCalculator = getShieldStrengthCalculator(swords);
        return strengthCalculator.getShieldStrengthForPower(firePower);
    }

    private static ShieldStrengthCalculator getShieldStrengthCalculator(int[] swords) {
        return new DefaultShieldStrengthCalculator(swords);
    }

    private static boolean isEven(int n) {
        return (n & 1) == 0;
    }

    private static int calculateNextRef(int refIndex) {
        return (refIndex + 1) >>> 1;
    }

    private static final Comparator<Response>
            RESPONSE_COMPARATOR = (a, b) -> a.hits == b.hits ? a.position - b.position : a.hits - b.hits;

    final static class Response {
        private static final Response DEFAULT_RESPONSE = new Response(ResponseType.IMPOSSIBLE);
        final ResponseType type;
        final int position, hits;

        Response(int position, int hits) {
            this.type = ResponseType.POSSIBLE;
            this.position = position;
            this.hits = hits;
        }

        Response(ResponseType type) {
            this.type = type;
            position = -1;
            hits = -1;
        }

        @Override
        public String toString() {
            if (type == ResponseType.IMPOSSIBLE) return type.toString();
            return type + "\n" + position + " " + hits;
        }
    }

    enum ResponseType {
        POSSIBLE("possible"), IMPOSSIBLE("impossible");

        final String param;

        ResponseType(String param) {
            this.param = param;
        }

        @Override
        public String toString() {
            return param;
        }
    }

    @FunctionalInterface
    interface ShieldStrengthCalculator {
        Response getShieldStrengthForPower(int firePower);
    }

    final static class DefaultShieldStrengthCalculator implements ShieldStrengthCalculator {
        final int[] swords;
        final int size;

        DefaultShieldStrengthCalculator(final int[] swords) {
            this.swords = swords.clone();
            size = swords.length;
        }

        @Override
        public Response getShieldStrengthForPower(int firePower) {
            Response r1 = IntStream
                    .range(1, size)
                    .mapToObj(i -> calculateShieldStrength(firePower, i))
                    .filter(r -> r != Response.DEFAULT_RESPONSE)
                    .min(RESPONSE_COMPARATOR)
                    .orElse(Response.DEFAULT_RESPONSE);

            Response r2 = lastManShieldStrength(firePower);
            if (r1 == Response.DEFAULT_RESPONSE) return r2;
            if (r2 == Response.DEFAULT_RESPONSE) return r1;
            return RESPONSE_COMPARATOR.compare(r1, r2) <= 0 ? r1 : r2;
        }

        private Response lastManShieldStrength(int firePower) {
            if (nextSwordPower(size) > firePower) return Response.DEFAULT_RESPONSE;
            IndexConvertors convertors = new IndexConvertors();
            int ref = size, hits = 0;
            while (ref > 1) {
                if (isHit(ref)) {
                    int hitterIndex = convertors.getOriginalIndex(ref - 1, ref);
                    hits += swords[hitterIndex];
                }

                convertors.addPreIndexConvertor(IndexConvertorFactory.createPreIndexConvertor());
                ref = calculateNextRef(ref);
            }

            hits += firePower;
            return new Response(size + 1, hits);
        }

        private boolean isHit(int index) {
            return !isEven(index);
        }

        private Response calculateShieldStrength(final int power, final int referenceIndex) {
            int refIndex = referenceIndex;
            if (nextSwordPower(refIndex) > power) return Response.DEFAULT_RESPONSE;
            IndexConvertors convertors = new IndexConvertors();
            int hits = 0, endMans = size - refIndex;
            while (refIndex > 0) {
                if (isHit(refIndex)) {
                    int hitterIndex = convertors.getOriginalIndex(refIndex - 1, refIndex);
                    hits += swords[hitterIndex];
                }

                if (firstManGetsKilled(endMans)) {
                    convertors.updateIndexConvertorIfFirstKilled(refIndex);
                } else {
                    convertors.addIndexConvertor(refIndex);
                }

                refIndex = calculateNextRef(refIndex);
                if (firstManGetsKilled(endMans)) refIndex--;
                endMans = (endMans + 1) >>> 1;
            }

            while (endMans > 1) {
                if (firstManGetsKilled(endMans)) {
                    int hitter = convertors.getOriginalIndex(endMans, refIndex);
                    if (hitter > referenceIndex) hitter--;
                    hits += swords[hitter];
                }
                convertors.addPostIndexConvertor(IndexConvertorFactory.createPostIndexConvertor());
                endMans = (endMans + 1) >>> 1;
            }

            hits += power;
            return new Response(referenceIndex + 1, hits);
        }

        private boolean firstManGetsKilled(int manCount) {
            return !isEven(manCount);
        }

        private int nextSwordPower(int index) {
            if (index == size) return swords[0];
            return swords[index];
        }
    }

    final static class IndexConvertors {
        private IndexConvertor preIndexConvertor = DEFAULT_INDEX_CONVERTOR,
                postIndexConvertor = DEFAULT_INDEX_CONVERTOR;

        void addPreIndexConvertor(IndexConvertor convertor) {
            preIndexConvertor = convertor.compose(preIndexConvertor);
        }

        void addPostIndexConvertor(IndexConvertor convertor) {
            postIndexConvertor = convertor.compose(postIndexConvertor);
        }

        void addIndexConvertor(int refIndex) {
            addPreIndexConvertor(IndexConvertorFactory.createPreIndexConvertor());
            addPostIndexConvertor(IndexConvertorFactory.createPostIndexConvertor(refIndex, calculateNextRef(refIndex)));
        }

        void updateIndexConvertorIfFirstKilled(int refIndex) {
            addPreIndexConvertor(IndexConvertorFactory.createPreIndexConvertorIfFirstKilled());
            addPostIndexConvertor(IndexConvertorFactory.createPostIndexConvertor(refIndex, calculateNextRef(refIndex) - 1));
        }

        int getOriginalIndex(int index, int reference) {
            IndexConvertor convertor = index <= reference ? preIndexConvertor : postIndexConvertor;
            return convertor.getOldIndex(index);
        }
    }

    final static class IndexConvertorFactory {
        static IndexConvertor createPreIndexConvertor() {
            return (index) -> index << 1; // index starts from zero and the first guy survives.
        }

        static IndexConvertor createPreIndexConvertorIfFirstKilled() {
            return (index) -> (index + 1) << 1; // index starts from zero and the first guy survives.
        }

        static IndexConvertor createPostIndexConvertor() {
            // same as ref + index * 2, here the index is relative to ref and the response is also relative.
            // the actual index can be found only by adding this to original reference index.
            return (index) -> (index << 1) - 1;
        }

        static IndexConvertor createPostIndexConvertor(final int refIndex, final int newRefIndex) {
            return (index) -> {
                int relativeIndex = index - newRefIndex - 1;
                return refIndex + 1 + (relativeIndex << 1);
            };
        }
    }

    private static final IndexConvertor DEFAULT_INDEX_CONVERTOR = (index) -> index;

    @FunctionalInterface
    interface IndexConvertor {
        int getOldIndex(int newIndex);

        /**
         * Use this index convertor as the next level index convertor.
         *
         * @param convertor the next level (deep) convertor
         * @return the most recent convertor which can translate the recent index to original index.
         */
        default IndexConvertor compose(IndexConvertor convertor) {
            return (index) -> convertor.getOldIndex(getOldIndex(index));
        }
    }

    final static class UnmodifiableIndexConvertor implements IndexConvertor {
        private final IndexConvertor convertor;

        UnmodifiableIndexConvertor(final IndexConvertor convertor) {
            this.convertor = convertor;
        }

        @Override
        public int getOldIndex(int newIndex) {
            return convertor.getOldIndex(newIndex);
        }

        @Override
        public IndexConvertor compose(IndexConvertor convertor) {
            throw new UnsupportedOperationException();
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