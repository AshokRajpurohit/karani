package com.ashok.leetcode.practice;


import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name: Maximum Subarray
 * Link: https://leetcode.com/problems/maximum-subarray/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MaxSubArray {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        MaxSubArray a = new MaxSubArray();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        while (true) {
            int n = in.readInt();
            out.println(maxSubArray(in.readIntArray(n)));
            out.flush();
        }
    }

    public int maxSubArray(int[] nums) {
        out.print(nums);
        if (nums.length == 1)
            return nums[0];

        int maxSoFar = nums[0], maxHere = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (maxHere < 0)
                maxHere = nums[i];
            else
                maxHere += nums[i];

            maxSoFar = Math.max(maxSoFar, maxHere);
        }

        return maxSoFar;
    }
}
