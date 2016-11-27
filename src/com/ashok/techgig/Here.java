package com.ashok.techgig;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Here {
    private final static Cabin INVALID_CABIN = new Cabin(-1, -1, 1);
    private final static int MAX_COST = 1000000000;

    public static int minimumpossiblecost(String cityString) {
        Group.counter = 0; // reset the counter.
        City city = new City(format(cityString));
        return city.compute();
    }

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

    final static class City {
        private Cabin[][] grid;
        final int len, wid;
        private int[][][] map; // this map is to be used by getDistance method only.

        City(int[][] ar) {
            len = ar.length;
            wid = ar[0].length;

            grid = new Cabin[len][wid];
            for (int i = 0; i < len; i++)
                for (int j = 0; j < wid; j++)
                    grid[i][j] = new Cabin(i, j, ar[i][j]);
        }

        int compute() {
            populateGroups();
            map = new int[len][wid][Group.counter];

            if (Group.counter < 2)
                return 0;

            return mergeGroups();
        }

        /**
         * Merges all groups into group with id 0.
         *
         * @return merge cost
         */
        private int mergeGroups() {
            int res = 0;
            int cost = merge();

            while (cost != 0) {
                res += cost;
                cost = merge();
            }

            return res;
        }

        /**
         * Find two nearest groups and merge them.
         * The distance is measured in cost terms.
         *
         * @return
         */
        private int merge() {
            int minDistance = MAX_COST;
            Group source = Group.getById(0), target = Group.getById(1);
            int distance = 0;

            for (int i = 0; i < Group.counter; i++) {
                for (int j = i + 1; j < Group.counter; j++) {
                    Group a = Group.getById(i), b = Group.getById(j);

                    if (a.actualGroup() != b.actualGroup()) {
                        distance = groupDistance(a, b, false);

                        if (distance < minDistance) {
                            source = Group.getById(i);
                            target = Group.getById(j);
                            minDistance = distance;
                        }
                    }
                }
            }

            int cost = mergeGroup(source, target);
            return cost;
        }

        /**
         * target group to be merged into source group and returns the cost
         * of it.
         *
         * @param source
         * @param target
         * @return merge cost
         */
        private int mergeGroup(Group source, Group target) {
            source = source.actualGroup();
            target = target.actualGroup();

            if (source == target)
                return 0;

            int cost = groupDistance(source, target, true);
            target.parentGroup = source;

            return cost;
        }

        /**
         * Calculates the minimum cost when two groups are merged.
         *
         * @param group1
         * @param group2
         * @param mark
         * @return
         */
        private int groupDistance(Group group1, Group group2, boolean mark) {
            Group source = group1.actualGroup(), target = group2.actualGroup();
            if (source == target)
                return 0;

            /**
             * We will merge Group with higher id into the Group having
             * smaller id.
             */
            if (source.id > target.id) {
                Group temp = source;
                source = target;
                target = temp;
            }

            int cost = MAX_COST;
            Cabin markCabin = INVALID_CABIN;

            for (Cabin[] cabins : grid)
                for (Cabin cabin : cabins) {
                    int distance = groupDistance(source, target, cabin, false);
                    if (cost > distance) {
                        cost = distance;
                        markCabin = cabin;
                    }
                }

            if (mark) {
//                source.add(markCabin);
                groupDistance(source, target, markCabin, true);
            }

            return cost;
        }

        /**
         * Extremely sorry for this ugly method.
         *
         * @param source
         * @param target
         * @param cabin
         * @param mark
         * @return
         */
        private int groupDistance(Group source, Group target, Cabin cabin, boolean mark) {
            if (mark) {
                boolean res = target.contains(cabin);
            }
            if (invalidCabin(cabin) || source.contains(cabin) || !connectedToGroup(source, cabin))
                return MAX_COST;

            if (target.contains(cabin)) // we have reached there.
                return 0;

            if (cabin.isGrouped())
                return MAX_COST;

            if (!mark && calculated(cabin, target)) // if already calculated
                return getCalculatedValue(cabin, target);

            source.add(cabin);
            int cost = MAX_COST;
            Cabin markCabin = INVALID_CABIN;

            int distance = MAX_COST;

            distance = groupDistance(source, target, left(cabin), false);
            if (distance < cost)
                markCabin = left(cabin);

            cost = Math.min(cost, distance);
            if (distance != 0)
                distance = groupDistance(source, target, up(left(cabin)), false);

            if (distance < cost)
                markCabin = up(left(cabin));

            cost = Math.min(cost, distance);
            if (distance != 0)
                distance = groupDistance(source, target, up(cabin), false);

            if (distance < cost)
                markCabin = up(cabin);

            cost = Math.min(cost, distance);
            if (distance != 0)
                distance = groupDistance(source, target, right(up(cabin)), false);

            if (distance < cost)
                markCabin = right(up(cabin));

            cost = Math.min(cost, distance);
            if (distance != 0)
                distance = groupDistance(source, target, right(cabin), false);

            if (distance < cost)
                markCabin = right(cabin);

            cost = Math.min(cost, distance);
            if (distance != 0)
                distance = groupDistance(source, target, down(right(cabin)), false);

            if (distance < cost)
                markCabin = down(right(cabin));

            cost = Math.min(cost, distance);
            if (distance != 0)
                distance = groupDistance(source, target, down(cabin), false);

            if (distance < cost)
                markCabin = down(cabin);

            cost = Math.min(cost, distance);
            if (distance != 0)
                distance = groupDistance(source, target, left(down(cabin)), false);

            if (distance < cost)
                markCabin = left(down(cabin));

            cost = Math.min(cost, distance);
            source.remove(cabin);
            cost += cabin.cost;
            setCalculatedValue(cabin, cost, target);

            if (mark) {
                source.add(cabin);
                groupDistance(source, target, markCabin, true);
            }

            return cost;
        }

        private void setCalculatedValue(Cabin cabin, int cost, Group group) {
            map[cabin.row][cabin.col][group.id] = cost;
        }

        private boolean calculated(Cabin cabin, Group group) {
            return group.contains(cabin) || map[cabin.row][cabin.col][group.id] != 0;
        }

        private int getCalculatedValue(Cabin cabin, Group group) {
            if (group.contains(cabin))
                return 0;

            return map[cabin.row][cabin.col][group.id];
        }

        private boolean connectedToGroup(Group group, Cabin cabin) {
            if (!invalidCabin(cabin)) {
                return group.contains(left(cabin)) ||
                        group.contains(left(up(cabin))) ||
                        group.contains(up(cabin)) ||
                        group.contains(right(up(cabin))) ||
                        group.contains(right(cabin)) ||
                        group.contains(down(right(cabin))) ||
                        group.contains(down(cabin)) ||
                        group.contains(left(down(cabin)));
            }

            return false;
        }

        private void populateGroups() {
            for (Cabin[] cabins : grid) {
                for (Cabin cabin : cabins)
                    markCabinGroup(cabin);
            }
        }

        private void markCabinGroup(Cabin cabin) {
            if (cabin.passable() && !cabin.isGrouped())
                markCabinGroup(cabin, new Group());
        }

        private void markCabinGroup(Cabin cabin, Group group) {
            if (!invalidCabin(cabin) && cabin.passable() && !cabin.isGrouped()) {
                cabin.groupId = group.id;
                group.count++;

                markCabinGroup(left(cabin), group);
                markCabinGroup(up(left(cabin)), group);
                markCabinGroup(up(cabin), group);
                markCabinGroup(right(up(cabin)), group);
                markCabinGroup(right(cabin), group);
                markCabinGroup(down(right(cabin)), group);
                markCabinGroup(down(cabin), group);
                markCabinGroup(left(down(cabin)), group);
            }
        }

        private boolean invalidCabin(Cabin cabin) {
            return cabin == INVALID_CABIN || cabin.row < 0 ||
                    cabin.col < 0 || cabin.row >= len || cabin.col >= wid;
        }

        private Cabin left(Cabin cabin) {
            if (cabin == INVALID_CABIN || cabin.col == 0)
                return INVALID_CABIN;

            return grid[cabin.row][cabin.col - 1];
        }

        private Cabin up(Cabin cabin) {
            if (cabin == INVALID_CABIN || cabin.row == 0)
                return INVALID_CABIN;

            return grid[cabin.row - 1][cabin.col];
        }

        private Cabin right(Cabin cabin) {
            if (cabin == INVALID_CABIN || cabin.col == wid - 1)
                return INVALID_CABIN;

            return grid[cabin.row][cabin.col + 1];
        }

        private Cabin down(Cabin cabin) {
            if (cabin == INVALID_CABIN || cabin.row == len - 1)
                return INVALID_CABIN;

            return grid[cabin.row + 1][cabin.col];
        }
    }

    final static class Group {
        static int counter = 0;
        static Group[] map = new Group[100000];
        int id, count = 0; // member count
        Group parentGroup = null; // when Groups b is merged into a then
        // a is parentGroup of b. groupId for passable cabins will never be changed.

        Group() {
            id = counter++;
            map[id] = this;
        }

        /**
         * Return the actual group this group belongs to or the
         * larger group, this group is part of and at the same time
         * updating the actual group if multiple there are hierarchies.
         *
         * @return The Larger Enclosing Group.
         */
        public Group actualGroup() {
            if (parentGroup == null)
                return this;

            parentGroup = parentGroup.actualGroup();
            return parentGroup;
        }

        void add(Cabin cabin) {
            if (cabin == INVALID_CABIN)
                return;

            count++;
            cabin.groupId = id;
        }

        void remove(Cabin cabin) {
            if (cabin == INVALID_CABIN || !contains(cabin))
                return;

            count--;
            cabin.groupId = -1;
        }

        boolean contains(Cabin cabin) {
            if (!cabin.isGrouped())
                return false;

            Group cabinGroup = Group.getById(cabin.groupId).actualGroup();
            return actualGroup() == cabinGroup;
        }

        static Group getById(int groupId) {
            return map[groupId];
        }

        public String toString() {
            return "id " + id + ", count " + count;
        }
    }

    final static class Cabin {
        int cost, groupId = -1;
        int row, col;
        boolean visited = false;

        Cabin(int row, int col, int cost) {
            this.row = row;
            this.col = col;
            this.cost = cost;
        }

        boolean isGrouped() {
            return groupId != -1;
        }

        boolean passable() {
            return cost < 1;
        }

        public String toString() {
            return row + ", " + col + ", group " + groupId + ", cost " + cost;
        }
    }
}
