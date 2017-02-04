package com.ashok.techgig;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Here2 {
    final static int MAX_COST = Integer.MAX_VALUE;
    final static Group DEFAULT = new Group();
    final static Cabin INVALID = new Cabin(-1, -1, 0);

    private static void reset() {
        Group.counter = 1; // the only thing we need to reset for
        // every call.
    }

    public static int minimumpossiblecost(String cityString) {
        long time = System.currentTimeMillis();
        reset();
        City city = new City(format(cityString));
        System.out.println(System.currentTimeMillis() - time);
        System.out.println("Counter is: " + Group.counter);

        if (Group.counter < 3)
            return 0;

        System.out.println("Group size is: " + Group.getGroup(1).cabins.size());
        time = System.currentTimeMillis();
        int res = compute(city);

        System.out.println(System.currentTimeMillis() - time);

        return res;
    }

    private static int compute(City city) {
        int res = 0, cost = merge(city);
        while (cost > 0) {
            res += cost;
            cost = merge(city);
        }

        return res;
    }

    private static int merge(City city) {
        Group first = Group.getGroup(1), second = Group.getGroup(2);
        int minDistance = MAX_COST;

        for (int i = 1; i < Group.counter; i++)
            for (int j = i + 1; j < Group.counter; j++) {
                Group a = Group.getGroup(i), b = Group.getGroup(j);
                if (a.getOriginGroup() == b.getOriginGroup())
                    continue;

                int distance = city.getDistanceAndMerge(a, b, false);
                if (distance < minDistance) {
                    first = a;
                    second = b;
                }
            }


        return city.getDistanceAndMerge(first, second, true);
    }

    private final static class City {
        private Cabin[][] cabins;
        final int length, width;

        City(int[][] city) {
            length = city.length;
            width = city[0].length;

            cabins = new Cabin[length][width];
            for (int i = 0; i < length; i++)
                for (int j = 0; j < width; j++)
                    cabins[i][j] = new Cabin(i, j, city[i][j]);

            populateGroup();
        }

        private void populateGroup() {
            for (Cabin[] row : cabins)
                for (Cabin cabin : row)
                    updateGroup(cabin);
        }

        private void updateGroup(Cabin cabin) {
            if (cabin.group != DEFAULT || !cabin.passable())
                return;

            Group group = Group.newInstance();
            group.add(cabin);

            for (Cabin neighbor : cabinsInNeighborhood(cabin))
                updateGroup(group, neighbor);
        }

        private void updateGroup(Group group, Cabin cabin) {
            if (cabin == INVALID || cabin.group != DEFAULT || !cabin.passable())
                return;

            group.add(cabin);
            for (Cabin neighbor : cabinsInNeighborhood(cabin))
                updateGroup(group, neighbor);
        }

        public int getDistanceAndMerge(Group first, Group second, boolean merge) {
            if (first.getOriginGroup() == second.getOriginGroup())
                return 0;

//            System.out.println("Entering getDistanceAndMerge for " + first + ", " + second + ", " + merge);

            int cost = MAX_COST;
            boolean[][] checkMap = new boolean[length][width];
            int[][] costMap = new int[length][width];
            LinkedList<Cabin> cabinsCovered = cabinsInNeighborhood(first);

            for (int[] e : costMap)
                Arrays.fill(e, MAX_COST);

            for (Cabin cabin : cabinsCovered) {
                costMap[cabin.row][cabin.col] = cabin.cost;
                checkMap[cabin.row][cabin.col] = true;
            }

            Cabin connectingPoint = INVALID;

            while (!cabinsCovered.isEmpty()) {
//                System.out.println("cabins in queue are: " + cabinsCovered);
                Cabin cabin = cabinsCovered.removeFirst();
                int cabinCost = costMap[cabin.row][cabin.col];
                if (second.contains(cabin)) {
                    if (cost > cabinCost) {
                        cost = cabinCost;
                        connectingPoint = cabin;
                    }
                } else {
                    for (Cabin neighbor : cabinsInNeighborhood(cabin)) {
                        if (neighbor == INVALID)
                            continue;

                        if (neighbor.group == DEFAULT || neighbor.group == second) {
                            int neighborCost = neighbor.group == DEFAULT ? neighbor.cost : 0;
                            if (!checkMap[neighbor.row][neighbor.col])
                                cabinsCovered.addLast(neighbor);

                            checkMap[neighbor.row][neighbor.col] = true;
                            costMap[neighbor.row][neighbor.col] =
                                    Math.min(costMap[neighbor.row][neighbor.col], cabinCost + neighborCost);
                        }
                    }
                }
            }

            if (cost == MAX_COST)
                throw new RuntimeException("le kanjar \n" + this);

            if (merge)
                mergeGroups(first, second, connectingPoint, costMap);

//            System.out.println("Exiting getDistanceAndMerge for " + first + ", " + second + ", " + merge);
            return cost;
        }

        private void mergeGroups(Group first, Group second, Cabin touchPoint, int[][] costMap) {
            if (touchPoint == INVALID)
                return;

            System.out.println("Entering mergeGroups method for " + first + ", " + second + ", " + touchPoint);
            while (!first.contains(touchPoint)) {
                second.add(touchPoint);

                int cost = MAX_COST;
                for (Cabin cabin : cabinsInNeighborhood(touchPoint)) {
                    if (first.contains(cabin)) {
                        touchPoint = cabin;
                        break;
                    }

                    if (cabin == INVALID || (costMap[cabin.row][cabin.col] >= cost && cabin.group != DEFAULT))
                        continue;

                    cost = costMap[cabin.row][cabin.col];
                    touchPoint = cabin;
                }
            }

            first.merge(second);
            System.out.println("Exiting mergeGroups method for " + first + ", " + second + ", " + touchPoint);
        }

        private boolean areNeighbors(Group group, Cabin cabin) {
            if (cabin == INVALID || group.contains(cabin))
                return false;

            for (Cabin neighbor : cabinsInNeighborhood(cabin))
                if (group.contains(cabin))
                    return true;

            return false;
        }

        /**
         * We know for a cabin there are eight neighbors, so we can use
         * an array of size 8.
         *
         * @param cabin
         * @return
         */
        private Cabin[] cabinsInNeighborhood(Cabin cabin) {
            Cabin[] cabins = new Cabin[8];
            int index = 0;

            cabins[index++] = left(cabin);
            cabins[index++] = up(left(cabin));
            cabins[index++] = up(cabin);
            cabins[index++] = right(up(cabin));
            cabins[index++] = right(cabin);
            cabins[index++] = down(right(cabin));
            cabins[index++] = down(cabin);
            cabins[index++] = left(down(cabin));

            return cabins;
        }

        /**
         * We are not sure how many neighbors are there, so let's use
         * list instead of array in previous function.
         *
         * @param group Group
         * @return list of cabins which are near to group.
         */
        private LinkedList<Cabin> cabinsInNeighborhood(Group group) {
//            System.out.println("Entering cabinsInNeighborhood for " + group + ", " + group.cabins);
            LinkedList<Cabin> neighbors = new LinkedList<>();

            boolean[][] checked = new boolean[length][width];

            /*for (Cabin cabin : group.cabins)
                checked[cabin.row][cabin.col] = true;*/

            for (Cabin cabin : group.cabins) {
                for (Cabin neighbor : cabinsInNeighborhood(cabin)) {
                    if (group.contains(neighbor) || neighbor == INVALID
                            || checked[neighbor.row][neighbor.col])
                        continue;

                    checked[neighbor.row][neighbor.col] = true;
                    neighbors.add(neighbor);
                }
            }

//            System.out.println("Exiting cabinsInNeighborhood for " + group);

            return neighbors;
        }

        Cabin left(Cabin cabin) {
            if (cabin == INVALID || cabin.col == 0)
                return INVALID;

            return cabins[cabin.row][cabin.col - 1];
        }

        Cabin up(Cabin cabin) {
            if (cabin == INVALID || cabin.row == 0)
                return INVALID;

            return cabins[cabin.row - 1][cabin.col];
        }

        Cabin right(Cabin cabin) {
            if (cabin == INVALID || cabin.col == width - 1)
                return INVALID;

            return cabins[cabin.row][cabin.col + 1];
        }

        Cabin down(Cabin cabin) {
            if (cabin == INVALID || cabin.row == length - 1)
                return INVALID;

            return cabins[cabin.row + 1][cabin.col];
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();

            for (Cabin[] rows : cabins) {
                for (Cabin cabin : rows) {
                    sb.append("[" + cabin + "]\t");
                }

                sb.deleteCharAt(sb.length() - 1);
                sb.append('\n');
            }

            return sb.toString();
        }
    }

    private final static class Cabin {
        final int row, col;
        Group group = DEFAULT;
        final int cost;

        public Cabin(int r, int c, int cost) {
            row = r;
            col = c;
            this.cost = cost;
        }

        public boolean passable() {
            return cost < 0;
        }

        public String toString() {
            if (this == INVALID)
                return "INVALID";

            return "[" + row + ", " + col + ", " + cost + ", " + group + "]";
        }
    }

    private final static class Group {
        private static Group[] groups = new Group[10000];
        private static int counter = 0;
        private LinkedList<Cabin>
                cabins = new LinkedList<>(),
                neighbours = new LinkedList<>();

        final int id;
        private Group parent = null;

        private Group() {
            id = counter++;
            groups[id] = this;
        }

        public boolean contains(Cabin cabin) {
            return cabin.group == this;
        }

        public void add(Cabin cabin) {
            cabin.group = this;
            cabins.add(cabin);
        }

        public void merge(Group group) {
            if (parent == null) {
                group.parent = this;
                for (Cabin cabin : group.cabins)
                    add(cabin);

                group.cabins.clear();
                group.neighbours.clear();
                neighbours.clear();
            } else
                parent.merge(group);
        }

        public Group getParent() {
            return parent;
        }

        public Group getOriginGroup() {
            if (parent == null)
                return this;

            return parent.getOriginGroup();
        }

        public static Group newInstance() {
            return new Group();
        }

        public static Group getGroup(int id) {
            return groups[id];
        }

        public String toString() {
            if (this == DEFAULT)
                return "DEFAULT";

            return id + ", " + cabins.size();
        }
    }

    /**
     * Transforming the string input into two dimensional array of
     * integers for the code to begin.
     *
     * @param input
     * @return
     */
    private static int[][] format(String input) {
        String[] rows = input.split("#");
        int[][] res = new int[rows.length][];

        for (int i = 0; i < rows.length; i++) {
            String[] values = rows[i].split("@");

            res[i] = new int[values.length];
            int index = 0;
            for (String value : values) {
                res[i][index++] = Integer.valueOf(value);
            }
        }

        return res;
    }
}
