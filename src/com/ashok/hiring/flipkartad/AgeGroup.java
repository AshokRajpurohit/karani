package com.ashok.hiring.flipkartad;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public enum AgeGroup {
    BABY(0, 3),
    KIDS(4, 10),
    TEEN(11, 21),
    ADULT(22, 45),
    OLD(46, 150);

    final int ageFrom, ageTo;

    AgeGroup(int ageFrom, int ageTo) {
        this.ageFrom = ageFrom;
        this.ageTo = ageTo;
    }
}
