package com.ashok.friends.harsh;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * For Harshvardhan
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class AmazonApril5 {
    private static InputReader in = new InputReader();
    private static Output out = new Output();

    public static void main(String[] args) throws IOException {
        int n = in.readInt();
        String[] lines = in.readLineArray(n);
        List<String> strings = Arrays.stream(lines).collect(Collectors.toList());
        out.print(new ReorderLogs().reorderLines(n, strings));
        out.flush();
    }

    final static class Toys {
        public ArrayList<String> popularNToys(int numToys,
                                              int topToys,
                                              List<String> toys,
                                              int numQuotes,
                                              List<String> quotes) {
            ArrayList<String> topRequestedToys = new ArrayList<>();
            if (topToys >= numToys) {
                topRequestedToys.addAll(toys);
                Collections.sort(topRequestedToys);
                return topRequestedToys;
            }

            if (topToys == 0) {
                return topRequestedToys;
            }

            List<String> requests = quotes.stream().map(request -> request.toLowerCase()).collect(Collectors.toList());
            toys
                    .stream()
                    .map(feature -> {
                        long count = requests.stream().filter(request -> request.contains(feature.toLowerCase())).count();
                        return new Toy(feature, count);
                    })
                    .sorted()
                    .limit(topToys)
                    .map(toy -> toy.name)
                    .forEach(name -> topRequestedToys.add(name));

            return topRequestedToys;
        }

        final static class Toy implements Comparable<Toy> {
            final String name;
            final long count;

            Toy(final String name, final long count) {
                this.name = name;
                this.count = count;
            }

            @Override
            public int compareTo(Toy toy) {
                if (this.count != toy.count) {
                    return Long.compare(toy.count, this.count);
                }

                return name.compareToIgnoreCase(toy.name);
            }
        }

    }

    final static class Features {
        public ArrayList<String> popularNFeatures(int numFeatures,
                                                  int topFeatures,
                                                  List<String> possibleFeatures,
                                                  int numFeatureRequests,
                                                  List<String> featureRequests) {
            ArrayList<String> tfr = new ArrayList<>(); // top feature requested.
            if (topFeatures >= numFeatures) {
                tfr.addAll(possibleFeatures);
                Collections.sort(tfr);
                return tfr;
            }

            if (topFeatures == 0) {
                return tfr;
            }

            List<String> requests = featureRequests.stream().map(request -> request.toLowerCase()).collect(Collectors.toList());
            possibleFeatures
                    .stream()
                    .map(feature -> {
                        int count = (int) requests.stream().filter(request -> request.contains(feature.toLowerCase())).count();
                        return new Feature(feature, count);
                    })
                    .sorted(FEATURE_COMPARATOR)
                    .limit(topFeatures)
                    .map(feature -> feature.name)
                    .forEach(name -> tfr.add(name));

            return tfr;
        }

        private static final Comparator<Feature> FEATURE_COMPARATOR = (a, b) ->
                a.count == b.count ? a.name.compareToIgnoreCase(b.name) : b.count - a.count;

        final static class Feature {
            final String name;
            final int count;

            Feature(final String name, final int count) {
                this.name = name;
                this.count = count;
            }

            @Override
            public String toString() {
                return name + " " + count;
            }
        }
    }

    final static class ReorderLogs {
        public List<String> reorderLines(int logFileSize, List<String> logLines) {
            List<LogLine<String>> stringLogLines = new ArrayList<>();
            List<String> intLogLines = new ArrayList<>();
            Predicate<String> isInteger = s -> {
                try {
                    Integer.parseInt(s);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            };

            for (String log : logLines) {
                String[] strings = log.split(" ");
                String id = strings[0];
                if (isInteger.test(strings[1])) {
                    intLogLines.add(log);
                    continue;
                }

                LogLine<String> logLine = new LogLine<>(id, Arrays.copyOfRange(strings, 1, strings.length), log);
                stringLogLines.add(logLine);
            }

            Collections.sort(stringLogLines, STR_LINE_COMPARATOR);
            List<String> lines = stringLogLines.stream()
                    .map(logLine -> logLine.toString())
                    .collect(Collectors.toList());

            lines.addAll(intLogLines);
            return lines;
        }

        private static final Comparator<LogLine<String>> STR_LINE_COMPARATOR = (a, b) -> {
            int cmp = 0;
            for (int i = 0; i < Math.min(a.logs.length, b.logs.length); i++) {
                cmp = a.logs[i].compareTo(b.logs[i]);
                if (cmp != 0) return cmp;
            }

            if (a.logs.length != b.logs.length) return a.logs.length - b.logs.length;

            return a.id.compareTo(b.id);
        };

        final static class LogLine<T extends Comparable> {
            final String id;
            final T[] logs;
            final String string;

            LogLine(final String id, final T[] logs, final String string) {
                this.id = id;
                this.logs = logs;
                this.string = string;
            }

            public String toString() {
                return string;
            }
        }
    }

}
