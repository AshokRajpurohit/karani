package com.ashok.lang.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Solution {

    static int counts[];
    static int months[][];
    static int sum;

    static {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            Date start;
            start = sdf.parse("2012.1.13");
            GregorianCalendar gcal = new GregorianCalendar();
            gcal.setTime(start);
            Date end = sdf.parse("2039.12.13");

            int prevYear = gcal.get(Calendar.YEAR);

            counts = new int[28];
            months = new int[28][3];
            int countI = 0;
            int monthI = 0;
            int monthJ = 0;
            int temp = 0;
            while (!gcal.getTime().after(end)) {
                if (gcal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                    temp++;
                    months[monthI][monthJ++] = gcal.get(Calendar.MONTH) + 1;
                }
                gcal.add(Calendar.MONTH, 1);
                int year = gcal.get(Calendar.YEAR);
                if (prevYear != year) {
                    prevYear = year;
                    counts[countI++] = temp;
                    temp = 0;
                    monthI++;
                    monthJ = 0;
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < counts.length; i++) {
            sum = sum + counts[i];
        }
    }

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        int tests = sc.nextInt();
        for (int i = 0; i < tests; ) {
            int d1 = sc.nextInt();
            int m1 = sc.nextInt();
            int y1 = sc.nextInt();
            int d2 = sc.nextInt();
            int m2 = sc.nextInt();
            int y2 = sc.nextInt();
            int res = findDaysOf13(d1, m1, y1, d2, m2, y2);
            System.out.println(res);
        }
        sc.close();
    }

    private static boolean arrayContains(int[] months, int find) {
        int i = 0;
        while (i < 3 && months[i] != 0) {
            if (months[i] == find)
                return true;
            i++;
        }
        return false;
    }

    private static int totalInAYear(int y1) {
        int index = (y1 - 1900) % 28;
        return counts[index];
    }

    private static int beforeADate(int d1, int m1, int y1) {
        if (m1 == 1 && d1 <= 13)
            return 0;
        int index = (y1 - 1900) % 28;
        int count = 0;
        if (d1 <= 13) {
            m1 = m1 - 1;
        }
        for (int i = 1; i <= m1; i++) {
            if (arrayContains(months[index], i)) {
                count++;
            }
        }
        return count;
    }

    private static int afterADate(int d1, int m1, int y1) {
        if (m1 == 12 && d1 >= 13)
            return 0;
        int index = (y1 - 1900) % 28;
        int count = 0;
        if (d1 >= 13) {
            m1 = m1 + 1;
        }
        for (; m1 <= 12; m1++) {
            if (arrayContains(months[index], m1)) {
                count++;
            }
        }
        return count;
    }

    private static int findDaysOf13(int d1, int m1, int y1, int d2, int m2,
                                    int y2) {
        //        System.out.println("inside");
        if (y1 == y2) {
            return totalInAYear(y1) - afterADate(d2, m2, y2) -
                beforeADate(d1, m1, y1);
            /* int index = (y1 - 1900) % 28;
            if (m1 == m2) {
                if (d1 == d2) {
                    if (d1 != 13) {
                        return 0;
                    } else if (arrayContains(months[index], m1)) {
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    if (d1 <= 13 && d2 >= 13) {
                        if (arrayContains(months[index], m1)) {
                            return 1;
                        }else{
                            return 0;
                        }
                    } else {
                        return 0;
                    }
                }
            } else {
                int count = 0;
                int[] temp = months[index];
                if (arrayContains(temp, m1)) {
                    if (d1 <= 13) {
                        count++;
                    }
                }
                if (arrayContains(temp, m2)) {
                    if (d2 <= 13) {
                        count++;
                    }
                }
                for (int i = m1 + 1; i < m2; i++) {
                    if (arrayContains(temp, i)) {
                        count++;
                    }
                }
                return count;
            }
        */
        } else {
            int count = 0;
            count += afterADate(d1, m1, y1);
            count += beforeADate(d2, m2, y2);
            for (int i = y1 + 1; i < y2; i++) {
                count += totalInAYear(i);
            }
            return count;

        }
    }
}
