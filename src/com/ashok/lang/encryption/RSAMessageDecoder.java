package com.ashok.lang.encryption;

import com.ashok.lang.math.PureMath;

import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class RSAMessageDecoder implements MessageDecoder<Long> {
    final int p, q, k;
    final int length; // lenth of digits, which forms the single character.

    public RSAMessageDecoder(final int p, final int q, final int k, final int length) {
        this.p = p;
        this.q = q;
        this.k = k;
        this.length = length;
    }

    public String decode1(List<Long> encodedNums) {
        StringBuilder sb = new StringBuilder();
        encodedNums.stream()
                .mapToLong(v -> PureMath.moduloRoot(k, v, p, q))
                .forEach(v -> sb.append(v));

        int charCounts = sb.length() / length;
        CharDecoder decoder = EncryptionUtil.getStandardDecoder();
        StringBuilder sb1 = new StringBuilder();
        IntStream.range(0, charCounts)
                .map(i -> i * length)
                .mapToObj(i -> sb.substring(i, i + length))
                .mapToInt(v -> Integer.valueOf(v))
                .forEach(v -> sb1.append(decoder.decode(v)));

        return sb1.toString();
    }

    @Override
    public Long decode(List<Long> messageKeys) {
        long message = messageKeys.get(0);
        return PureMath.moduloRoot(k, message, p, q);
    }
}
