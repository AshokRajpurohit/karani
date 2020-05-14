package com.ashok.lang.encryption;

import com.ashok.lang.math.Power;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class RSAMessageEncoder implements MessageEncoder<Long> {
    final int length;
    final CharEncoder charEncoder;
    final long pq, k;

    public RSAMessageEncoder(final int length, final CharEncoder charEncoder, final long pq, final long k) {
        this.length = length;
        this.charEncoder = charEncoder;
        this.pq = pq;
        this.k = k;
    }

    public List<Long> encode(String message) {
        StringBuilder sb = new StringBuilder();
        for (char ch : message.toCharArray()) {
            sb.append(charEncoder.encode(ch));
        }

        String charEncoded = sb.toString();
        int count = charEncoded.length();
        int numCount = (count + length - 1) / length;
        long[] nums = IntStream.range(0, numCount)
                .map(i -> i * length)
                .mapToObj(i -> charEncoded.substring(i, Math.min(count, i + length)))
                .mapToLong(s -> Long.valueOf(s))
                .toArray();

        return Arrays.stream(nums)
                .map(v -> Power.pow(v, k, pq))
                .mapToObj(v -> v)
                .collect(Collectors.toList());
    }

    public List<Long> encode(Long message) {
        return Arrays.asList(Power.pow(message, k, pq));
    }
}
