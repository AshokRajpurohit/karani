/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.techgig.deloitte;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.util.*;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Deloitte {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        play();
        test();
        RectanglePerimeter.solve();
        CardCombinations.solve();
        BehindEnemyLines.solve();
    }

    private static void play() throws IOException {
        while (true) {
            int x1 = in.readInt(), y1 = in.readInt(), x2 = in.readInt(), y2 = in.readInt();
            int ankit = ChessProblem.getStepCount(x1, y1, x2, y2),
                    ashok = ChessBoard.getStepCount(x1, y1, x2, y2);
        }
    }

    private static void test() throws IOException {
        while (true) {
            int n = in.readInt();
            int[] knx = Generators.generateRandomIntegerArray(n, 1, 8),
                    kny = Generators.generateRandomIntegerArray(n, 1, 8),
                    kgx = Generators.generateRandomIntegerArray(n, 1, 8),
                    kgy = Generators.generateRandomIntegerArray(n, 1, 8);

            int testFailed = 0;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                int actual = ChessProblem.getStepCount(knx[i], kny[i], kgx[i], kgy[i]),
                        expected = ChessBoard.getStepCount(knx[i], kny[i], kgx[i], kgy[i]);

                if (actual == expected)
                    continue;

                testFailed++;
                sb.append(knx[i] + " " + kny[i] + " " + kgx[i] + " " + kgy[i]).append('\n');
                sb.append("Expected: " + expected + ", Actual: " + actual).append('\n').append('\n');
            }

            out.println("Failed: " + testFailed + " out of " + n);
            out.println(sb);
            out.flush();
        }
    }

    final static class LaunchingSatellites {
        private static void solve() throws IOException {
            while (true) {

                out.flush();
            }
        }

        private static Point getPoint(String param) {
            String[] coordinates = param.split("#");
            return new Point(Integer.valueOf(coordinates[0]), Integer.valueOf(coordinates[1]));
        }

        final static class Point implements Comparable<Point> {
            final int x, y;

            Point(int x, int y) {
                this.x = x;
                this.y = y;
            }

            @Override
            public int compareTo(Point point) {
                if (x == point.x)
                    return y - point.y;

                return x - point.x;
            }

            public String toString() {
                return x + ", " + y;
            }
        }
    }

    final static class BehindEnemyLines {
        static boolean[] validChars = new boolean[256];

        static {
            Arrays.fill(validChars, 'a', 'z' + 1, true);
            Arrays.fill(validChars, 'A', 'Z' + 1, true);
        }

        private static void solve() throws IOException {
            while (true) {
                out.println(appearanceCount(0, 0, in.next(), in.read()));
                out.flush();
            }
        }

        public static int appearanceCount(int patternLength, int messageLength, String patternString, String message) {
            //Write code here
            Pattern pattern = new Pattern(patternString.toCharArray());
            PatternMatcher matcher = new PatternMatcher(pattern);
            char[] charArray = getValidCharArray(message);
            int count = 0;

            for (int i = 0; i < pattern.validCharCount && i < charArray.length; i++)
                matcher.push(charArray[i]);

            if (matcher.isMatch())
                count++;

            for (int i = 0, j = pattern.validCharCount; i < charArray.length && j < charArray.length; i++, j++) {
                matcher.pop(charArray[i]);
                matcher.push(charArray[j]);

                if (matcher.isMatch())
                    count++;
            }

            return count;
        }

        private static char[] getValidCharArray(String string) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < string.length(); i++)
                if (validChars[string.charAt(i)])
                    sb.append(string.charAt(i));

            return sb.toString().toCharArray();
        }

        final static class PatternMatcher {
            final int[] charCounter = new int[256];
            final Pattern pattern;
            private int matchCount = 0;

            PatternMatcher(Pattern pattern) {
                this.pattern = pattern;
            }

            void push(char ch) {
                if (pattern.getCount(ch) == 0)
                    return;

                charCounter[ch]++;
                if (charCounter[ch] <= pattern.getCount(ch))
                    matchCount++;
            }

            void pop(char ch) {
                if (pattern.getCount(ch) == 0)
                    return;

                charCounter[ch]--;
                if (charCounter[ch] < pattern.getCount(ch))
                    matchCount--;
            }

            boolean isMatch() {
                return matchCount == pattern.validCharCount;
            }
        }

        final static class Pattern {
            private final int[] countMap = new int[256];
            final int validCharCount;

            Pattern(char[] pattern) {
                for (char ch : pattern) {
                    if (validChars[ch])
                        countMap[ch]++;
                }

                int validCount = 0;
                for (int e : countMap)
                    validCount += e;

                validCharCount = validCount;
            }

            int getCount(char ch) {
                return countMap[ch];
            }
        }
    }

    final static class Ankit {
        static Map<DataKey, Integer> values = new HashMap<>();

        public static int openingRightDoor(int inputLength, char[] inputString) {
            if (inputString.length == 1 || isPalindrome(inputString, 0, inputLength - 1))
                return 0;

            values = new HashMap<>();
            return getMinValue(new DataKey(0, inputLength - 1), inputString);
        }

        public static int getMinValue(DataKey dataKey, char[] input) {
            if (values.containsKey(dataKey)) {
                return values.get(dataKey);
            }

            int a = dataKey.a;
            int b = dataKey.b;
            int value = Integer.MAX_VALUE;
            if (a >= b) {
                return 0;
            }
            if (isPalindrome(input, a, b)) {
                values.put(dataKey, 0);
                return 0;
            }

            if (input[a] == input[b])
                value = getMinValue(new DataKey(a + 1, b - 1), input);
            else
                value = Math.min(getMinValue(new DataKey(a + 1, b), input), getMinValue(new DataKey(a, b - 1), input)) + 1;

            values.put(dataKey, value);
            return value;
        }

        private static boolean isPalindrome(char[] input, int i, int j) {
            while (i < j) {
                if (input[i] != input[j]) {
                    return false;
                }
                i++;
                j--;
            }

            return true;
        }

        static class DataKey {
            public final int a;
            public final int b;

            @Override
            public int hashCode() {
                return a * b;
            }

            public DataKey(int a, int b) {
                this.a = a;
                this.b = b;
            }

            @Override
            public boolean equals(Object obj) {
                DataKey dbKey2 = (DataKey) obj;
                return dbKey2.a == a && dbKey2.b == b;
            }

            @Override
            public String toString() {
                return "[a=" + a + ",b=" + b + "]";
            }
        }
    }

    final static class JailBreakBhopal {
        private static void solve() throws IOException {
            while (true) {
                int x = in.readInt(), y = in.readInt(), n = in.readInt();

                out.println(GetJumpCount(x, y, in.readIntArray(n)));
                out.flush();
            }
        }

        public static int GetJumpCount(int jump, int slip, int[] wallHeights) {
            int count = 0;

            for (int e : wallHeights)
                count += calculateJumpCount(jump, slip, e);

            return count;
        }

        private static int calculateJumpCount(int jump, int slip, int height) {
            if (height <= jump)
                return (height + jump - 1) / jump;

            int wallToScale = height - jump;
            int effectiveJump = jump - slip;

            return (wallToScale + effectiveJump - 1) / effectiveJump + 1;
        }
    }

    /**
     * Problem Name: Card Combination
     * Level: Medium
     * Contest: Code Gladiator.
     *
     * @author Ankit Kumar
     */
    final static class CardCombinations {

        static Set<String> inputStrings = new TreeSet<>();
        static Map<Integer, Set<String>> operationsString = new HashMap<>();

        // card values.
        final static char ONE = '1';
        final static char ZERO = '0';
        final static char XOR = ONE ^ ZERO; // to flip the cards.

        public static void solve() throws IOException {
            while (true) {
                out.println(combinationOfCards(in.readInt(), in.readInt(), in.read(), in.read()));
                out.flush();
            }
        }

        public static String combinationOfCards(int input1, int input2, String input3, String input4) {
            return printCardCombinations(input1, input2, input3, input4);
        }

        public static String printCardCombinations(int inputLength, int chances, String input1, String input2) {
            char[] input = createInputArray(inputLength);
            preprocessInputString(input);
            int[] options = new int[2];
            getOptionsArray(chances, options);
            StringBuilder stringBuilder = new StringBuilder();
            String[] input1Arr = input1.split(",");
            String[] input2Arr = input2.split(",");
            Set<String> possibleOutputs = operationsString.get(options[0]);
            possibleOutputs.addAll(operationsString.get(options[1]));
            Set<String> finalOutputs = new TreeSet<>();
            for (String possibleOutput : possibleOutputs) {
                if (doesFulfillCriteria(possibleOutput, input1Arr, input2Arr)) {
                    finalOutputs.add(possibleOutput);
                }
            }

            int size = finalOutputs.size();
            int k = 0;
            for (String finalOutput : finalOutputs) {
                stringBuilder.append(finalOutput);
                if (k != size - 1) {
                    stringBuilder.append("#");
                }
                k++;
            }
            return stringBuilder.toString();
        }

        private static void getOptionsArray(int chances, int[] options) {
            if (chances > 2) {
                if (chances % 2 == 0) {
                    options[0] = 2;
                    options[1] = 4;
                } else {
                    options[0] = 1;
                    options[1] = 3;
                }
            } else if (chances == 1) {
                options[0] = options[1] = 1;
            } else if (chances == 2) {
                options[0] = options[1] = 2;
            }
        }

        private static boolean doesFulfillCriteria(String possibleOutput, String[] input1Arr, String[] input2Arr) {
            return doesFulfillCriteria(possibleOutput, input1Arr, ONE)
                    && doesFulfillCriteria(possibleOutput, input2Arr, ZERO);
        }

        private static boolean doesFulfillCriteria(String possibleOutput, String[] inputArr, char matchingChar) {
            if (inputArr.length == 1 && inputArr[0].equals("-1")) {
                return true;
            }
            for (String indexes : inputArr) {
                int index = Integer.parseInt(indexes) - 1;
                if (possibleOutput.charAt(index) != matchingChar) {
                    return false;
                }
            }
            return true;
        }

        private static char[] createInputArray(int inputLength) {
            char[] input = new char[inputLength];
            for (int i = 0; i < input.length; i++) {
                input[i] = ONE;
            }
            return input;
        }

        private static void preprocessInputString(char[] input) {
            operationsString.clear();
            inputStrings.clear();
            inputStrings.add(new String(input));
            // allCombinations.add(new String(input));
            int chances = 1;
            while (chances <= 4) {
                Set<String> outputStrings = new TreeSet<>();
                for (String inputString : inputStrings) {
                    Set<String> allOperations = performAllOperations(inputString);
                    outputStrings.addAll(allOperations);
                }
                inputStrings.clear();
                inputStrings.addAll(outputStrings);
                operationsString.put(chances++, new TreeSet<>(inputStrings));
            }
        }

        private static Set<String> performAllOperations(String input) {
            Set<String> outputStrings = new TreeSet<>();

            char[] charArray = input.toCharArray();
            outputStrings.add(new String(flipAllCards(charArray)));
            outputStrings.add(new String(flipEvenCards(charArray)));
            outputStrings.add(new String(flipOddCards(charArray)));
            outputStrings.add(new String(flip3KPlus1Cards(charArray)));
            return outputStrings;
        }

        private static char[] flipAllCards(char[] input) {
            char[] inputCopy = Arrays.copyOf(input, input.length);
            flipCards(inputCopy, 0, 1);
            return inputCopy;
        }

        private static char[] flipOddCards(char[] input) {
            char[] inputCopy = Arrays.copyOf(input, input.length);
            flipCards(inputCopy, 0, 2);
            return inputCopy;
        }

        private static char[] flipEvenCards(char[] input) {
            char[] inputCopy = Arrays.copyOf(input, input.length);
            flipCards(inputCopy, 1, 2);
            return inputCopy;
        }

        private static char[] flip3KPlus1Cards(char[] input) {
            char[] inputCopy = Arrays.copyOf(input, input.length);
            flipCards(inputCopy, 0, 3);
            return inputCopy;
        }

        private static void flipCards(char[] cards, int start, int diff) {
            for (int i = start; i < cards.length; i += diff)
                flipCard(cards, i);
        }

        private static void flipCard(char[] input, int i) {
            input[i] ^= XOR;
        }
    }

    /**
     * Problem: 1 Chess Board.
     * Contest: Semi Final Round
     */
    final static class ChessBoard {

        /**
         * Your webcam warnings are irritating. Although I am not moving, but it's showing
         * warning every few seconds. Please fix it.
         * The warning is: Webcam detects right hand movement. Obviously bro, as I am coding there
         * are right hand movements. I am surprised why it didn't show, it detects left hand
         * movement. I am at home and not the only member.
         * Oh yaar, these notifictations are irritating.
         * ab main khuzli bhi nhin kar skta, kya hai ye.
         * Warnings wale pe bhi click karo to warning aati hai: you are not allowed to move away!
         * kya hai ye?
         * <p>
         * Karte raho right hand detect. Looksyou enjoy right hand movement.
         */

        private static Position[][] positionMap;
        private static final Position INVALID_POSITION = new Position(-1, -1);
        private static int[][] processStatusMap;
        private static int[][] minimumMoveMap;
        private static Position target;
        private static final int IN_PROGRESS = 1, PROCESSED = 2, NEW = 0;

        private static void init() {
            positionMap = new Position[8][8];
            processStatusMap = new int[8][8];
            minimumMoveMap = new int[8][8];
        }

        public static int getStepCount(int knightx, int knighty, int kingx, int kingy) {
            init();
            target = getPosition(kingx - 1, kingy - 1);
            Position current = getPosition(knightx - 1, knighty - 1);
            markProcessed(target);
            process(current);
            return getMoveCounts(current);
        }

        private static void process(Position position) {
            if (invalidPosition(position) || processing(position))
                return;

            // System.out.println("processing for" + position);
            markInProgress(position);

            int min = 16; // it is actually 11 as knight can move to any position in less than 12 moves.

            for (Position next : nextMoves(position)) {
                process(next);
                min = Math.min(min, getMoveCounts(next));
            }

            min++;
            setMoveCounts(position, min);
            markProcessed(position);
            // System.out.println("processed " + position + " and the count is: " + min);
        }

        private static Position[] nextMoves(Position current) {
            Position[] positions = new Position[8];

            positions[0] = moveLD(current);
            positions[1] = moveLU(current);
            positions[2] = moveUL(current);
            positions[3] = moveUR(current);
            positions[4] = moveRU(current);
            positions[5] = moveRD(current);
            positions[6] = moveDR(current);
            positions[7] = moveDL(current);

            return positions;
        }

        private static Position moveLD(Position knight) {
            return getPosition(knight.row - 1, knight.col - 2);
        }

        private static Position moveLU(Position knight) {
            return getPosition(knight.row + 1, knight.col - 2);
        }

        private static Position moveUL(Position knight) {
            return getPosition(knight.row + 2, knight.col - 1);
        }

        private static Position moveUR(Position knight) {
            return getPosition(knight.row + 2, knight.col + 1);
        }

        private static Position moveRU(Position knight) {
            return getPosition(knight.row + 1, knight.col + 2);
        }

        private static Position moveRD(Position knight) {
            return getPosition(knight.row - 1, knight.col + 2);
        }

        private static Position moveDR(Position knight) {
            return getPosition(knight.row - 2, knight.col + 1);
        }

        private static Position moveDL(Position knight) {
            return getPosition(knight.row - 2, knight.col - 1);
        }

        private static void markInProgress(Position position) {
            processStatusMap[position.row][position.col] = IN_PROGRESS;
        }

        private static void markProcessed(Position position) {
            processStatusMap[position.row][position.col] = PROCESSED;
        }

        private static boolean processing(Position position) {
            return processStatusMap[position.row][position.col] != NEW;
        }

        private static boolean isProcessed(Position position) {
            return processStatusMap[position.row][position.col] == PROCESSED;
        }

        private static int getMoveCounts(Position position) {
            if (invalidPosition(position) || !isProcessed(position))
                return 16;

            return minimumMoveMap[position.row][position.col];
        }

        private static void setMoveCounts(Position position, int moves) {
            minimumMoveMap[position.row][position.col] = moves;
        }

        private static boolean invalidPosition(Position position) {
            return position == INVALID_POSITION;
        }

        private static boolean invalidPosition(int x, int y) {
            return x < 0 || y < 0 || x > 7 || y > 7;
        }

        private static Position getPosition(int x, int y) {
            if (invalidPosition(x, y)) {
                return INVALID_POSITION;
            }

            if (positionMap[x][y] == null) {
                positionMap[x][y] = new Position(x, y);
            }

            return positionMap[x][y];
        }

        final static class Position {
            final int row, col;

            Position(int x, int y) {
                row = x;
                col = y;
            }

            @Override
            public String toString() {
                return "(" + row + ", " + col + ")";
            }
        }
    }

    /**
     * Problem: 2 Minimum Perimeter Rectangles
     * Contest: Semi Final Round
     */
    final static class RectanglePerimeter {
        private static int[][] matrix, sumMatrix;
        private static int length, width;
        private static Point[][] pointMap;
        private static final Point INVALID_POINT = new Point(-1, -1);

        private static void solve() throws IOException {
            while (true) {
                int wid = in.readInt(), len = in.readInt(), hubCount = in.readInt(), count = in.readInt();
//                int[][] ar = in.readIntTable(in.readInt(), in.readInt());
                int[][] ar = generateArray(hubCount, len, wid);
                long time = System.currentTimeMillis();
                out.println(homesteadThatDefinesANewLivingPlanet(wid, len, hubCount, count, ar));
                out.println("total time: " + (System.currentTimeMillis() - time));
                out.flush();
            }
        }

        private static int[][] generateArray(int n, int r, int c) {
            int[] rows = Generators.generateRandomIntegerArray(n, 1, r),
                    cols = Generators.generateRandomIntegerArray(n, 1, c);

            int[][] ar = new int[n][2];
            for (int i = 0; i < n; i++) {
                ar[i][0] = cols[i];
                ar[i][1] = rows[i];
            }

            return ar;
        }

        public static int homesteadThatDefinesANewLivingPlanet(int wid, int len, int hubCount, int hubsInSection, int[][] hubPositionArray) {
            length = len;
            width = wid;
            matrix = new int[length][width];
            sumMatrix = new int[length][width];
            pointMap = new Point[length][width];

            initialize(hubPositionArray);
            return process(hubsInSection);
        }

        private static int process(int minCount) {
            Set<Rectangle> rectangleSet = new HashSet<>();
            for (int sr = 0; sr < length; sr++) {
                for (int sc = 0; sc < width; sc++) {
//                    rectangleSet.addAll(getRectangles(getPoint(sr, sc), minCount));
                    int ecLimit = width;
                    for (int er = sr; er < length; er++) {
                        for (int ec = sc; ec < ecLimit; ec++) {
                            if (getHubCounts(sr, sc, er, ec) >= minCount) {
                                Rectangle rectangleToAdd = new Rectangle(sr, sc, er, ec);
                                rectangleSet.add(new Rectangle(sr, sc, er, ec));
                                ecLimit = ec;
                                break;
                            }
                        }
                    }
                }
            }

            if (rectangleSet.size() < 2)
                return 0;

            out.println(rectangleSet.size());

            return getPerimeter(toArray(rectangleSet));
        }

        private static int getPerimeter(Rectangle[] rectangles) {
            Arrays.sort(rectangles);
            int min = Integer.MAX_VALUE;

            Rectangle first = null, second = null;

            for (int i = 0; i < rectangles.length; i++) {
                for (int j = i + 1; j < rectangles.length; j++) {
                    if (!rectangles[i].intersect(rectangles[j])) {
                        int perimeter = rectangles[i].getPerimeter() + rectangles[j].getPerimeter();
                        if (min > perimeter) {
                            min = perimeter;
                            first = rectangles[i];
                            second = rectangles[j];
                        } else
                            break;
                    }
                }
            }

            return min == Integer.MAX_VALUE ? 0 : min;
        }

        private static List<Rectangle> getRectangles(Point point, int count) {
            List<Rectangle> rectangles = new LinkedList<>();
            Point ref = INVALID_POINT;

            for (int er = point.x; er < length; er++) {
                if (getHubCounts(point.x, point.y, er, width - 1) >= count) {
                    ref = getPoint(er, width - 1);
                    break;
                }
            }

            if (ref == INVALID_POINT)
                return rectangles;

            int er = point.x, ec = width - 1, sr = point.x, sc = point.y;
            while (er < length) {
                if (getHubCounts(sr, sc, er, ec) < count) {
                    er++;
                    continue;
                }

                while (ec >= sc && getHubCounts(sr, sc, er, ec) >= count)
                    ec--;

                ec++;
                rectangles.add(new Rectangle(point, getPoint(er, ec)));
                er++;
            }

            return rectangles;
        }

        private static Rectangle[] toArray(Set<Rectangle> set) {
            Rectangle[] rectangles = new Rectangle[set.size()];

            int index = 0;
            for (Rectangle rectangle : set) {
                // System.out.println(rectangle + ": " + rectangle.getPerimeter());
                rectangles[index++] = rectangle;
            }

            return rectangles;
        }

        private static int getHubCounts(int r, int c) {
            if (invalidIndex(r, c))
                return 0;

            return sumMatrix[r][c];
        }

        private static int getHubCounts(int sr, int sc, int er, int ec) {
            int count = getHubCounts(er, ec) + getHubCounts(sr - 1, sc - 1) - getHubCounts(sr - 1, ec) - getHubCounts(er, sc - 1);
            return count;
        }

        private static boolean invalidIndex(int r, int c) {
            return r < 0 || c < 0 || r >= length || c >= width;
        }

        private static void initialize(int[][] hubPositionArray) {
            // System.out.println("initialize");
            for (int[] hubs : hubPositionArray) {
                int r = hubs[1] - 1, c = hubs[0] - 1;

                if (r >= length || c >= width)
                    continue;

                matrix[hubs[1] - 1][hubs[0] - 1]++;
            }

            for (int i = 0; i < length; i++) {
                int[] row = matrix[i], sumRow = sumMatrix[i];
                sumRow[0] = row[0];

                for (int j = 1; j < width; j++) {
                    sumRow[j] = sumRow[j - 1] + row[j];
                }
            }

            for (int i = 1; i < length; i++) {
                for (int j = 0; j < width; j++) {
                    sumMatrix[i][j] += sumMatrix[i - 1][j];
                }
            }
        }

        private static Point getPoint(int x, int y) {
            if (x < 0 || y < 0 || x >= length || y >= width)
                return INVALID_POINT;

            if (pointMap[x][y] == null)
                pointMap[x][y] = new Point(x, y);

            return pointMap[x][y];
        }

        final static class Rectangle implements Comparable<Rectangle> {
            final Point start, end; // should be extreme points

            Rectangle(int sr, int sc, int er, int ec) {
                start = getPoint(Math.min(sr, er), Math.min(sc, ec));
                end = getPoint(Math.max(sr, er), Math.max(sc, ec));
            }

            Rectangle(Point start, Point end) {
                this(start.x, start.y, end.x, end.y);
            }

            int getPerimeter() {
                return (end.x + end.y + 2 - start.x - start.y) * 2;
            }

            boolean intersect(Rectangle rectangle) {
                if (this.isValid() && rectangle.isValid())
                    return end.isRightUp(rectangle.start) && rectangle.end.isRightUp(start);

                return true;
            }

            @Override
            public boolean equals(Object object) {
                if (this == object)
                    return true;

                Rectangle rectangle = (Rectangle) object;
                return rectangle.start == start && rectangle.end == end;
            }

            @Override
            public int hashCode() {
                return Long.hashCode(1L * start.hashCode() * end.hashCode());
            }

            public boolean isValid() {
                return start != INVALID_POINT && end != INVALID_POINT;
            }

            @Override
            public String toString() {
                return start + " -> " + end;
            }

            @Override
            public int compareTo(Rectangle o) {
                return getPerimeter() - o.getPerimeter();
            }
        }

        final static class Point {
            final int x, y;

            Point(int x, int y) {
                this.x = x;
                this.y = y;
            }

            boolean isRightUp(Point point) {
                return x >= point.x && y >= point.y;
            }

            @Override
            public String toString() {
                return "(" + x + ", " + y + ")";
            }

            @Override
            public int hashCode() {
                return x * y;
            }
        }
    }

    final static public class ChessProblem {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            int x1 = scanner.nextInt();
            int y1 = scanner.nextInt();
//            Point point1 = new Point(x1, y1);
            int x2 = scanner.nextInt();
            int y2 = scanner.nextInt();
//            Point point2 = new Point(x2, y2);
//            System.out.println(findMinDistance(point1, point2));
            System.out.println(getStepCount(x1, y1, x2, y2));
            scanner.close();
        }

        public static int getStepCount(int x1, int y1, int x2, int y2) {
            Point pointA = new Point(x1, y1), pointB = new Point(x2, y2);
            return findMinDistance(pointA, pointB);
        }

        private static int findMinDistance(Point pointA, Point pointB) {
            Map<Point, Integer> visitedPoints = new HashMap<>();
            visitedPoints.put(pointA, 0);
            int distance = 1;
            while (distance < 7) {
                Set<Point> points = visitedPoints.keySet();
                Map<Point, Integer> newPointsMap = new HashMap<>();
                for (Point point : points) {
                    if (visitedPoints.get(point) != distance - 1) {
                        continue;
                    }
                    List<Point> nextPoints = getNextPossiblePoints(point);
                    if (nextPoints == null || nextPoints.size() == 0) {
                        continue;
                    }
                    for (Point point2 : nextPoints) {
                        if (!visitedPoints.containsKey(point2)) {
                            newPointsMap.put(point2, distance);
                        }
                    }
                }
                if (!newPointsMap.isEmpty()) {
                    Set<Point> newPoints = newPointsMap.keySet();
                    for (Point point2 : newPoints) {
                        visitedPoints.put(point2, distance);
                    }
                }

                distance++;
            }
//            System.out.println(visitedPoints);
            return visitedPoints.get(pointB);
        }

        private static boolean isCoordinatesValid(int x, int y) {
            int maxX = 8, maxY = 8, minX = 1, minY = 1;
            if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
                return true;
            }
            return false;
        }

        private static List<Point> getNextPossiblePoints(Point point) {
            List<Point> possiblePoints = new LinkedList<>();
            int x = point.x;
            int y = point.y;
            if (isCoordinatesValid(x + 2, y + 1)) {
                possiblePoints.add(new Point(x + 2, y + 1));
            }
            if (isCoordinatesValid(x + 2, y - 1)) {
                possiblePoints.add(new Point(x + 2, y - 1));
            }
            if (isCoordinatesValid(x - 2, y + 1)) {
                possiblePoints.add(new Point(x - 2, y + 1));
            }
            if (isCoordinatesValid(x - 2, y - 1)) {
                possiblePoints.add(new Point(x - 2, y - 1));
            }
            if (isCoordinatesValid(x + 1, y + 2)) {
                possiblePoints.add(new Point(x + 1, y + 2));
            }
            if (isCoordinatesValid(x + 1, y - 2)) {
                possiblePoints.add(new Point(x + 1, y - 2));
            }
            if (isCoordinatesValid(x - 1, y + 2)) {
                possiblePoints.add(new Point(x - 1, y + 2));
            }
            if (isCoordinatesValid(x - 1, y - 2)) {
                possiblePoints.add(new Point(x - 1, y - 2));
            }
            return possiblePoints;
        }

        static class Point {

            int x;
            int y;

            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }

            @Override
            public String toString() {
                return "(" + x + "," + y + ")";
            }

            @Override
            public int hashCode() {
                return x * y;
            }

            @Override
            public boolean equals(Object obj) {
                Point point2 = (Point) obj;
                if (point2.x == this.x && point2.y == this.y) {
                    return true;
                }
                return false;
            }
        }
    }
}
