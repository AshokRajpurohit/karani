package com.ashok.hiring.tesco;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
@FunctionalInterface
public interface UserCompareStrategy {
    boolean checkEquals(User a, User b);

    default UserCompareStrategy andAlso(UserCompareStrategy strategy) {
        return (a, b) -> checkEquals(a, b) && strategy.checkEquals(a, b);
    }
}
