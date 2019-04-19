package com.ashok.hiring.flipkartad;

import java.util.Arrays;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public enum Gender {
    MALE('M'),
    FEMALE('F'),
    UNISEX('X');

    final char gender;

    Gender(char gender) {
        this.gender = gender;
    }

    static Gender getGender(char ch) {
        return Arrays.stream(values()).filter(g -> g.gender == ch).findAny().orElse(UNISEX);
    }
}
