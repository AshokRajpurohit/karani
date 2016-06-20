package com.ashok.projectEuler.problems;


public class P039 {
    public static int solve(int perimeter) {
        int[] hash = new int[perimeter + 1];
        int n = perimeter / 3;
        for (int i = 1; i < n; i++) {
            for (int j = i; j < perimeter; j++) {
                hash[process(i, j, perimeter)]++;
            }
        }

        int max = 0, p = 0;
        for (int i = 2; i <= perimeter; i++) {
            if (max < hash[i]) {
                max = hash[i];
                p = i;
            }
        }

        System.out.println(max);
        return p;
    }

    private static int process(int a, int b, int p) {
        int c2 = a * a + b * b;
        int c = squareRoot(c2);

        if (c == 0 || a + b + c > p)
            return 0;

        return a + b + c;
    }

    private static int squareRoot(int a) {
        int root = (int) Math.sqrt(a);
        if (root * root == a)
            return root;

        return 0;
    }
}
