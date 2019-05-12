package com.ashok.hiring.tesco;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public enum UserComparisonStrataties implements UserCompareStrategy {
    FIRST_NAME {
        @Override
        public boolean checkEquals(User a, User b) {
            return a.firstName.equalsIgnoreCase(b.firstName);
        }
    },
    LAST_NAME {
        @Override
        public boolean checkEquals(User a, User b) {
            return a.lastName.equalsIgnoreCase(b.lastName);
        }
    },
    ADDRESS {
        @Override
        public boolean checkEquals(User a, User b) {
            return a.address.equalsIgnoreCase(b.address);
        }
    };
}
