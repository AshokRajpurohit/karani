package com.ashok.lang.encryption;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class EncryptionUtil {

    public static CharEncoder getStandardEncoder() {
        return StandardCharEncoder.INSTANCE;
    }

    public static CharDecoder getStandardDecoder() {
        return StandardCharEncoder.INSTANCE;
    }

    public static MessageEncoder getRSAEncoder(RSAPublicKey publicKey) {
        return new RSAMessageEncoder(rsaMessageLength(), getStandardEncoder(), publicKey.pq, publicKey.pow);
    }

    public static MessageDecoder getRSADecoder(RSAPrivateKey privateKey) {
        return new RSAMessageDecoder(privateKey.p, privateKey.q, privateKey.pow, rsaMessageLength());
    }

    /**
     * Converts the message into numbers and split them into certain length numbers.
     *
     * @param message
     * @return
     */
    public static List<Long> encodeMessage(String message) {
        CharEncoder encoder = getStandardEncoder();
        int length = rsaMessageLength();
        int charCounts = message.length() * 3;
        int mod = charCounts % length;
        StringBuilder sb = new StringBuilder();
        if (mod != 0) {
            int prefixLen = mod == 0 ? 0 : length - mod;
            char[] prefix = new char[prefixLen];
            Arrays.fill(prefix, '0');
            sb.append(prefix);
        }
        for (char ch : message.toCharArray()) {
            sb.append(encoder.encode(ch));
        }

        String charEncoded = sb.toString();
        int count = charEncoded.length();
        int numCount = (count + length - 1) / length;
        List<Long> result = IntStream.range(0, numCount)
                .map(i -> i * length)
                .mapToObj(i -> charEncoded.substring(i, i + length))
                .map(v -> Long.valueOf(v))
                .collect(Collectors.toList());

        return result;
    }

    public static String decodeMessage(String numString) {
        int length = 3;
        CharDecoder decoder = getStandardDecoder();
        int charCounts = numString.length() / length;
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, charCounts)
                .map(i -> i * length)
                .mapToObj(i -> numString.substring(i, i + length))
                .mapToInt(v -> Integer.valueOf(v))
                .filter(v -> v >= 100)
                .forEach(v -> sb.append(decoder.decode(v)));

        return sb.toString();
    }

    public static String decodeMessage(List<Long> numString) {
        String message = numString.stream()
                .map(v -> String.valueOf(v))
                .map(ns -> makeMessageLength(ns, rsaMessageLength()))
                .collect(Collectors.joining());

        return decodeMessage(makeMessageLength(message, 3));
    }

    private static String makeMessageLength(String s, int ref) {
        int len = s.length();
        int mod = len % ref;
        if (mod == 0) return s;
        char[] prefix = new char[ref - mod];
        Arrays.fill(prefix, '0');
        return new String(prefix) + s;
    }

    public static int rsaMessageLength() {
        return 7;
    }

    private static class StandardCharEncoder implements CharEncoder, CharDecoder {
        private static final StandardCharEncoder INSTANCE = new StandardCharEncoder();
        int base = 100;
        final int length = 3; // 3 char length.

        public int encode(char ch) {
            return base + ch;
        }

        public char decode(int n) {
            return (char) (n - base);
        }
    }

    public static final class RSAPublicKey {
        final long pq, pow;

        public RSAPublicKey(final long pq, final long pow) {
            this.pq = pq;
            this.pow = pow;
        }
    }

    public static final class RSAPrivateKey {
        final int p, q;
        final int pow;
        public final RSAPublicKey publicKey; // public key to be published.

        public RSAPrivateKey(final int p, final int q, final int pow) {
            this.p = p;
            this.q = q;
            this.pow = pow;
            publicKey = new RSAPublicKey(p * q, pow);
        }
    }

}
