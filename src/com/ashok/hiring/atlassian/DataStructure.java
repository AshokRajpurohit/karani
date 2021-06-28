/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.atlassian;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.*;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class DataStructure {
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
        testElectionWinner();
    }

    private static void testElectionWinner() {
        Election election = new Election(CANDIDATE_COMPARATOR);
        String[] vote1 = new String[] {"a", "c", "b"};
        String[] vote2 = new String[] {"b", "a", "c"};
        String[] vote3 = new String[] {"b", "a", "c"};

        List<List<String>> votes = new ArrayList<>();
        votes.add(Arrays.asList(vote1));
        votes.add(Arrays.asList(vote2));
        votes.add(Arrays.asList(vote3));

        if (!election.findWinner(votes).equals("b")) throw new RuntimeException("b is expected winner");
    }

    private static final Comparator<Election.Candidate> CANDIDATE_COMPARATOR = (a, b) -> {
        if (a.votes != b.votes) return a.votes - b.votes;
        for (int i = 0; i < 3; i++) {
            if (a.prefVotes[i] != b.prefVotes[i]) return a.prefVotes[i] - b.prefVotes[i];
        }

        return 0;
    };

    final static class Election {
        private final Comparator<DataStructure.Election.Candidate> comparator;

        Election(final Comparator<DataStructure.Election.Candidate> comparator) {
            this.comparator = comparator;
        }

        public String findWinner(List<List<String>> votes) {
            Candidate leader = Candidate.INVALID;
            if (votes.isEmpty()) return leader.name;
            Map<String, Candidate> voteMap = new HashMap<>();

            for (List<String> candidateVotes: votes) {
                int weight = 3, prefIndex = 0;
                for (String candidateName: candidateVotes) {
                    Candidate candidate = voteMap.computeIfAbsent(candidateName, key -> new Candidate(key));
                    candidate.prefVotes[prefIndex++] += weight;
                    candidate.votes += weight;

                    leader = breakTie(leader, candidate);
                    weight--;
                }
            }

            return leader.name;
        }

        private Candidate breakTie(Candidate a, Candidate b) {
            int comp = comparator.compare(a, b);
            if (comp >= 0) return a;
            return b;
        }


        final static class Candidate {
            private static final Candidate INVALID = new Candidate("");
            final String name;
            int votes = 0;
            int lastVoteId = -1;
            int[] prefVotes = new int[3];

            Candidate(final String name) {
                this.name = name;
            }

            public String toString() {
                return name + votes;
            }
        }
    }
}
