package com.ashok.hiring.tesco;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class UserWrapper {
    public final User user;
    public final UserCompareStrategy strategy;

    public UserWrapper(User user, UserCompareStrategy strategy) {
        this.user = user;
        this.strategy = strategy;
    }

    public int hashCode() {
        return user.hashCode();
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof UserWrapper)) return false;
        if (o == this) return true;
        return strategy.checkEquals(user, ((UserWrapper) o).user);
    }

    public void merge(UserWrapper userWrapper) throws UserNotCompatible {
        if (!strategy.checkEquals(userWrapper.user, user)) throw new UserNotCompatible();
        user.rewards += userWrapper.user.rewards;
        userWrapper.user.rewards = 0;
    }
}
