/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.april20;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Problem Name: Leaked Answers (Challenge)
 * Link: https://www.codechef.com/APRIL20A/problems/ANSLEAK
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class LeakedAnswers {
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
            process();
        }
    }

    private static void process() throws IOException {
        int n = in.readInt(), m = in.readInt(), k = in.readInt();
        int[][] answerTable = in.readIntTable(n, k);
        int[] answers = new int[n];
        for (int i = 0; i < n; i++) {
            int[] qas = answerTable[i];
            if (allEquals(qas)) answers[i] = qas[0];
        }

        int[] unanswered = IntStream.range(0, n).filter(i -> answers[i] == 0).toArray();
        int[][][] answerSheetGroups = IntStream.range(0, n) // array with question_id, option are the keys and the value is ids of Answer Sheets
                .mapToObj(qn -> {
                    return IntStream.range(0, m + 1).mapToObj(option -> {
                        return IntStream.range(0, k).filter(v -> answerTable[qn][v] == option).toArray();
                    }).toArray(t -> new int[t][]);
                }).toArray(t -> new int[t][][]);

        answerQuestions(answerSheetGroups, answerTable, answers);

        StringBuilder sb = new StringBuilder();
        for (int e : answers) {
            sb.append(e == 0 ? 1 : e).append(' ');
        }

        out.println(sb);
    }

    private static int unionSize(QuestionGroup a, QuestionGroup b) {
        if (a == b) return a.sheets.length;
        int[] ar = Stream.of(a.sheets, b.sheets)
                .flatMapToInt(sheets -> Arrays.stream(sheets))
                .toArray();

        Arrays.sort(ar);
        int count = 1;
        for (int i = 1; i < ar.length; i++) {
            if (ar[i] != ar[i - 1]) count++;
        }

        return count;
    }

    private static void answerQuestions(int[][][] answerSheetGroups, int[][] answerTable, int[] answers) {
        int n = answers.length;
        int options = answerSheetGroups[0].length - 1;
        int minScore = 0;
        int[] betterAnswer = answers;

        QuestionGroup[] questionGroups = IntStream.range(0, n)
                .filter(qn -> answers[qn] != 0)
                .parallel()
                .mapToObj(qn -> qn)
                .flatMap(qn -> {
                    int[][] qgs = answerSheetGroups[qn];
                    return IntStream.range(1, options + 1)
                            .filter(op -> qgs[op].length != 0)
                            .mapToObj(op -> new QuestionGroup(qn, op, qgs[op]));
                })
                .toArray(t -> new QuestionGroup[t]);

        for (int count = 0; count < 10; count++) {
            int[] ansClone = answers.clone();
            QuestionGroup first, second;

            randomizeArray(questionGroups);
            for (int i = 0; i < questionGroups.length; i++) {
                first = questionGroups[i];
                if (ansClone[first.questionId] != 0) continue;
                second = first;
                int size = first.sheets.length;
                for (int j = i + 1; j < questionGroups.length; j++) {
                    QuestionGroup temp = questionGroups[j];
                    if (ansClone[second.questionId] != 0 || temp.questionId == first.questionId) continue;
                    int unionSize = unionSize(first, temp);
                    if (unionSize > size) {
                        second = temp;
                        size = unionSize;
                    }
                }

                ansClone[first.questionId] = first.option;
                ansClone[second.questionId] = second.option;
            }

            for (QuestionGroup questionGroup : questionGroups) { // let's answer remaining questions.
                if (ansClone[questionGroup.questionId] == 0) {
                    ansClone[questionGroup.questionId] = questionGroup.option;
                }
            }

            int score = calculateMinScore(answerTable, ansClone);
            if (score > minScore) {
                betterAnswer = ansClone;
                minScore = score;
            }
        }

        for (int i = 0; i < n; i++) {
            answers[i] = betterAnswer[i];
        }
    }

    private static int calculateMinScore(int[][] answerTable, int[] answers) {
        int n = answers.length, k = answerTable[0].length;
        return IntStream.range(0, k).parallel().map(col -> {
            return (int) IntStream.range(0, n).filter(r -> answers[r] == answerTable[r][col]).count();
        }).min().getAsInt();
    }

    private static void randomizeArray(Object[] objects) {
        Random random = new Random();
        for (int i = 0; i < objects.length; i++) {
            int j = random.nextInt(objects.length);
            while (j == i)
                j = random.nextInt(objects.length);

            swap(objects, i, j);
        }
    }

    private static void swap(Object[] objects, int i, int j) {
        Object temp = objects[i];
        objects[i] = objects[j];
        objects[j] = temp;
    }

    private static boolean allEquals(int[] ar) {
        int ref = ar[0];
        for (int e : ar) {
            if (e != ref) return false;
        }

        return true;
    }

    final static class QuestionGroup {
        final int questionId, option;
        final int[] sheets;

        QuestionGroup(final int questionId, final int option, int[] sheets) {
            this.questionId = questionId;
            this.option = option;
            this.sheets = sheets;
        }
    }

    final static class InputReader {
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;
        InputStream in;

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

        public int[][] readIntTable(int m, int n) throws IOException {
            int[][] res = new int[m][];
            for (int i = 0; i < m; i++)
                res[i] = readIntArray(n);

            return res;
        }
    }
}