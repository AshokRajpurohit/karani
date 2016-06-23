package com.ashok.lang.main;

/**
 * Programming Challenge Questions
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class ProChal {

    public static long[] transformArray(int[] ar) {
        long[] result = new long[ar.length];
        long mahaSum = 0;
        for (int i = 0; i < ar.length; i++)
            mahaSum += ar[i];

        for (int i = 0; i < ar.length; i++)
            result[i] = mahaSum - ar[i];

        return result;
    }

    public static char[] reverseOddWords(char[] input) {
        int count = 0;
        int i = 0;
        while (input[i] == ' ' || input[i] == '\t') {
            count++;
            i++;
        }

        int j = input.length - 2;
        while (input[j] == ' ' || input[j] == '\t') {
            count++;
            j--;
        }

        while (i < j) {
            while (i < j && input[i] != ' ' && input[i] != '\t')
                i++;

            i++;
            while (i < j && (input[i] == ' ' || input[i] == '\t')) {
                i++;
                count++;
            }
        }

        char[] output = new char[input.length - count];
        output[output.length - 1] = '.';
        i = 0;
        j = 0;
        while (i < output.length - 1) {
            while (input[j] == ' ' || input[j] == '\t')
                j++;

            while (j < input.length && input[j] != ' ' && input[j] != '\t') {
                output[i] = input[j];
                i++;
                j++;
            }

            if (i == output.length)
                return output;

            output[i] = ' ';
            i++;
            while (input[j] == ' ' || input[j] == '\t')
                j++;

            int k = j;
            while (k < (input.length - 1) && input[k] != ' ' &&
                   input[k] != '\t')
                k++;
            k--;

            while (i < output.length && j <= k) {
                output[i] = input[k];
                k--;
                i++;
            }

            while (j < (input.length - 1) && input[j] != ' ')
                j++;

            if (i >= output.length - 1)
                return output;

            output[i] = ' ';
            i++;
        }

        return output;
    }

}
