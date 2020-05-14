/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.amazon;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AmazonMarch20 {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        while (true) {
//            int a = in.readInt(), b = in.readInt();
//            List<String> features = Arrays.asList(in.readStringArray(a));
//            int c = in.readInt();
//            List<String> requests = Arrays.asList(in.readLineArray(c));
//            Features f = new Features();
//            out.println(f.popularNFeatures(a, b, features, c, requests));
//            out.flush();


            int r = in.readInt(), c = in.readInt();
            int[][] ar = in.readIntTable(r, c);
            List<List<Integer>> grid = Arrays.stream(ar)
                    .map(row -> Arrays.stream(row).mapToObj(ri -> Integer.valueOf(ri)).collect(Collectors.toList()))
                    .collect(Collectors.toList());

            out.println(new ServerUpdates().minimumDays(r, c, grid));
            out.flush();
        }
    }

    final static class ServerUpdates {
        private static final Server INVALID_SERVER = new Server(-1, -1, false);

        int minimumDays(int rows, int columns, List<List<Integer>> grid) {
            Grid serverGrid = new Grid(grid);
            List<Server> queue =
                    Arrays.stream(serverGrid.servers)
                            .flatMap(srs -> Arrays.stream(srs))
                            .filter(server -> server.updated)
                            .collect(Collectors.toList());

            int days = 0;
            while (!queue.isEmpty()) {
                days++;
                List<Server> dayServer = queue
                        .stream()
                        .flatMap(server -> {
                            List<Server> newServers = Arrays.asList(
                                    serverGrid.left(server)
                                    , serverGrid.right(server)
                                    , serverGrid.up(server)
                                    , serverGrid.down(server)
                            );
                            return newServers.stream();
                        }).filter(server -> server != INVALID_SERVER)
                        .filter(server -> !server.updated)
                        .distinct()
                        .collect(Collectors.toList());

                dayServer.forEach(server -> server.update());
                queue = dayServer;
            }

            return days - 1;
        }

        final static class Grid {
            final Server[][] servers;
            final int rows, cols, size;

            Grid(List<List<Integer>> grid) {
                int[][] matrix = grid.stream()
                        .map(row -> row.stream().mapToInt(rowItem -> rowItem).toArray())
                        .toArray(t -> new int[t][]);

                rows = matrix.length;
                cols = matrix[0].length;
                size = rows * cols;
                servers = new Server[rows][cols];
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        Server server = new Server(i, j, matrix[i][j] == 1);
                        servers[i][j] = server;

                    }
                }
            }

            Server get(int x, int y) {
                if (x < 0 || x >= rows || y < 0 || y >= cols) return INVALID_SERVER;
                return servers[x][y];
            }

            Server left(Server server) {
                return get(server.row, server.col - 1);
            }

            Server right(Server server) {
                return get(server.row, server.col + 1);
            }

            Server up(Server server) {
                return get(server.row - 1, server.col);
            }

            Server down(Server server) {
                return get(server.row + 1, server.col);
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                for (Server[] row : servers) {
                    sb.append(Arrays.asList(row)).append('\n');
                }

                return sb.toString();
            }
        }

        final static class Server {
            final int row, col;
            boolean updated;

            Server(final int row, final int col, boolean updated) {
                this.row = row;
                this.col = col;
                this.updated = updated;
            }

            private void update() {
                updated = true;
            }

            public int hashCode() {
                return row * row + col + col;
            }

            @Override
            public boolean equals(Object o) {
                Server server = (Server) o;
                return row == server.row && col == server.col;
            }

            public String toString() {
                return "[" + row + ", " + col + ": " + updated + "]";
            }
        }
    }

    final static class Features {
        public ArrayList<String> popularNFeatures(int numFeatures,
                                                  int topFeatures,
                                                  List<String> possibleFeatures,
                                                  int numFeatureRequests,
                                                  List<String> featureRequests) {
            ArrayList<String> topRequestedFeatures = new ArrayList<>();
            if (topFeatures >= numFeatures) {
                topRequestedFeatures.addAll(possibleFeatures);
                Collections.sort(topRequestedFeatures);
                return topRequestedFeatures;
            }

            if (topFeatures == 0) {
                return topRequestedFeatures;
            }

            List<String> requests = featureRequests.stream().map(request -> request.toLowerCase()).collect(Collectors.toList());
            possibleFeatures
                    .stream()
                    .map(feature -> {
                        long count = requests.stream().filter(request -> request.contains(feature.toLowerCase())).count();
                        return new Feature(feature, count);
                    })
                    .sorted()
                    .limit(topFeatures)
                    .map(feature -> feature.name)
                    .forEach(name -> topRequestedFeatures.add(name));

            return topRequestedFeatures;
        }

        final static class Feature implements Comparable<Feature> {
            final String name;
            final long count;

            Feature(final String name, final long count) {
                this.name = name;
                this.count = count;
            }

            @Override
            public int compareTo(Feature feature) {
                if (count != feature.count) {
                    return Long.compare(feature.count, this.count);
                }

                return name.compareToIgnoreCase(feature.name);
            }

            @Override
            public String toString() {
                return name + " " + count;
            }
        }
    }
}
