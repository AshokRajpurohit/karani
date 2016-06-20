package com.ashok.hackerearth.H06032015;


public class RubikCube {

    public static void main(String[] args) {
        long l = 40320*6561*Long.parseLong("1961990553600");
        System.out.println(l);
        long r = l;
        int count = 1;
        l = l/18;
        while(l>1) {
            l = l/17;
            count++;
        }
        System.out.println(count);
        System.out.println(Math.pow(17, 15));
        double res = Math.pow(17,15)/r;
        System.out.println(res);
    }
}
