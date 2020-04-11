package com.ashok.friends.harsh;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * For Harshvardhan
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class AmazonApril5 {
    private static InputReader in = new InputReader();
    private static Output out = new Output();

    public static void main(String[] args) {

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
}
