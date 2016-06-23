package com.ashok.leetcode.practice;


import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name: Find the Duplicate Number
 * Link: https://leetcode.com/problems/find-the-duplicate-number/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class FindDuplicate {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        FindDuplicate a = new FindDuplicate();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        while (true) {
            out.println(findDuplicate(in.readIntArray(in.readInt())));
            out.flush();
        }
    }

    public int findDuplicate(int[] nums) {
        int start = 1, end = nums.length - 1, mid = nums.length >>> 1;

        while (start < mid) {
            int count = 0;
            for (int i = 0; i < nums.length; i++)
                if (nums[i] >= start && nums[i] <= mid)
                    count++;

            if (count > mid - start + 1)
                end = mid;
            else
                start = mid;

            mid = (start + end) >>> 1;
        }

        int scount = 0;
        for (int i = 0; i < nums.length; i++)
            if (nums[i] == start)
                scount++;

        if (scount > 1)
            return start;

        return end;
    }
}
