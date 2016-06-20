package com.ashok.codechef.Discussion;


import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.util.*;

public class Noor {
    static int num12 = 0;
    static int glob = 0;

    public static void main(String[] arrgs) throws java.lang.Exception {
        
        Anagram an = new Anagram();
        an.main(arrgs);
        //rec(8888);
        //System.out.println(num12);

        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t > 0) {
            System.out.println(sc.nextChar());
        }

        java.io.BufferedReader r =
            new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        int n = Integer.parseInt(r.readLine());
        int sum = 0;
        int i = 1;
        List<String> lst = new ArrayList<String>();
        while (i <= n) {
            lst.add(r.readLine());
            i++;
        }
        for (String str : lst) {
            String ch[] = new String[4];
            ch = str.split(" ");
            int a = Integer.parseInt(ch[0]);
            int d = Integer.parseInt(ch[1]);
            int l = Integer.parseInt(ch[2]);
            int r1 = Integer.parseInt(ch[3]);

            for (int i1 = l; i1 <= r1; i1++) {
                int temp = d * (i1 - 1) + a;
                rec(temp);
                glob += num12;
                num12 = 0;
            }
            System.out.println(glob);
            glob = 0;
        }
    }


    public static void rec(int n) {
        if (n >= 10) {
            num12 += n % 10;
            n = n / 10;
            rec(n);
        } else {
            num12 += n;
            if (num12 >= 10) {
                int n11 = num12;
                num12 = 0;
                rec(n11);
            }
        }
    }

    final static class Scanner {
        private final InputStream is;
        private final byte[] data = new byte[0x2000];
        private int next, size;

        public Scanner(InputStream is) {
            this.is = is;
        }

        private boolean read() throws IOException {
            size = is.read(data);
            if (size == 0) {
                int i = is.read();
                if (i < 0)
                    return false;
                data[0] = (byte)i;
                size = 1;
            } else if (size < 0)
                return false;
            next = 0;
            return true;
        }

        public int nextInt() throws IOException {
            int r;
            do {
                if (next >= size && !read())
                    throw new EOFException();
                r = data[next++];
            } while (r < '0' || r > '9');
            r -= '0';
            for (int d;
                 (next < size || read()) && '0' <= (d = data[next++]) && d <=
                 '9'; )
                r = r * 10 + d - '0';
            return r;
        }

        public char nextChar() throws IOException {
            int r;
            do {
                if (next >= size && !read())
                    throw new EOFException();
                r = data[next++];
            } while (r <= ' ');
            return (char)r;
        }
    }

    static class Anagram {

        public void main(String[] args) throws IOException {

            BufferedReader br =
                new BufferedReader(new InputStreamReader(System.in));
            int num = (int)(Long.parseLong(br.readLine()));

            String arr[] = new String[num];
            int i = 0;
            while (num-- > 0) {
                arr[i] = br.readLine();
                i++;
            }
            int base_count = 0;
            int spec_count = 0;

            for (int ii = 0; ii < i; ii++) {
                //                      System.out.println("i/p in "+ii+" position "+arr[ii] );

                spec_count = 0;

                if (arr[ii] == "-999")
                    continue;

                for (int jj = 0; jj < i; jj++) {
                    if (arr[jj] == "-999" || ii == jj)
                        continue;

                    if (arr[ii].length() == arr[jj].length()) {

                        int zz = 0;
                        for (zz = 0; zz < arr[ii].length(); zz++) {
                            if (!arr[jj].contains((arr[ii].charAt(zz)) + ""))
                                break;
                            if (!arr[ii].contains((arr[jj].charAt(zz)) + ""))
                                break;
                        }
                        if (zz == arr[ii].length()) {
                            //System.out.println("arr[ii] = "+arr[ii]+" arr[jj] "+arr[jj] );
                            arr[jj] = "-999";
                            spec_count++;
                        }
                    }
                }

                //System.out.println("Specific Count "+spec_count );
                if (base_count - 1 < spec_count) {
                    arr[ii] = "-999";
                    //System.out.println("Specific Count "+spec_count );
                    base_count = spec_count + 1;
                    //System.out.println("Base Count "+base_count );
                }
            }


            //while(i-- > 0)
            {
                System.out.println(base_count);
            }


        }

    }
}
