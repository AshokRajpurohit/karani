package com.ashok.leetcode.practice;


import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

import java.util.Arrays;

/**
 * Problem Name: Contains Duplicate
 * Link: https://leetcode.com/problems/contains-duplicate/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ContainsDuplicate {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        ContainsDuplicate a = new ContainsDuplicate();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        while (true) {
            out.println(containsDuplicate(in.readIntArray(in.readInt())));
            out.flush();
        }
    }

    public boolean containsDuplicate(int[] nums) {
        if (nums.length == 1)
            return false;

        if (nums.length == 2)
            return nums[0] == nums[1];

        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++)
            if (nums[i] == nums[i - 1])
                return true;

        return false;
    }
}
