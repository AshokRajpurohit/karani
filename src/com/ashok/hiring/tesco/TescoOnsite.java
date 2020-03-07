/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.tesco;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class TescoOnsite {
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
        while (true) {
            out.println(in.read());
            UserCompareStrategy strategy = UserComparisonStrataties.FIRST_NAME.andAlso(UserComparisonStrataties.ADDRESS);
            out.flush();
        }
    }

    private static List<User> mergeUsers(List<User> users1, List<User> users2, UserCompareStrategy strategy) {
        List<UserWrapper> userWrappers1 = users1.stream().map(u -> new UserWrapper(u, strategy)).collect(Collectors.toList());
        List<UserWrapper> userWrappers2 = users2.stream().map(u -> new UserWrapper(u, strategy)).collect(Collectors.toList());
        Map<UserWrapper, UserWrapper> userWrappersMap = new HashMap<>();
        userWrappers1.stream().forEach(u -> userWrappersMap.put(u, u));
        userWrappers2.stream().forEach(u -> {
            if (userWrappersMap.containsKey(u)) {
                try {
                    userWrappersMap.get(u).merge(u);
                } catch (UserNotCompatible userNotCompatible) {
                }
            } else
                userWrappersMap.put(u, u);
        });

        User[] users = users1.stream().toArray(t -> new User[t]);
        Function<User, UserWrapper> userToWrapper = u -> new UserWrapper(u, strategy);
        Function<UserWrapper, UserCompareStrategy> function = uw -> uw.strategy;

        Function<User, UserCompareStrategy> userStrategyFunction = userToWrapper.andThen(function);
        Function<User, User> identityFunction = Function.identity();

        int[] ar = new int[10];

        return userWrappersMap.values().stream().map(userWrapper -> userWrapper.user).collect(Collectors.toList());
    }
}
