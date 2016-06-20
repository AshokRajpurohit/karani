package com.ashok.codeforces.Common;
import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.util.Scanner;
//package Common;

public class Lame {
    public Lame() {
        super();
    }

    public static void main(String[] args) {
        Lame lame = new Lame();
        FileInputStream in;


        try {
            in = new FileInputStream("input.txt");
            Scanner s = new Scanner(in);
            int count = 0;
            try {
                while (true) {
                    count++;
                    String n = s.next();
                }
            } catch (Exception e) {
                // TODO: Add catch code
                e.printStackTrace();
            }
            System.out.println(count);
        } catch (FileNotFoundException e) {
        }
//        Scanner sc = new Scanner(System.in);
//        int n = sc.nextInt();
//        
//        for(int i=1; i<=n; i++)
//            System.out.print(i + ",");
//        System.out.println();
//        sc.close();
    }
}
