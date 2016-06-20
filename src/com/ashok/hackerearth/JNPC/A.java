package com.ashok.hackerearth.JNPC;


import java.io.*;
import java.util.StringTokenizer;

public class A {
    
    private static InputReader in;
    private static PrintWriter out;
    
    public A() {
        super();
    }

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream);
        A a = new A();
        a.solve();
        out.close();
    }
    
    public void solve() {
        int t = in.nextInt();
        while(t>0) {
            t--;
            int x = in.nextInt();
            int y = in.nextInt();
            int n = in.nextInt();
            if(n==0 || x==0 || y==0)
                out.println(0);
            else if(n<=x || x==1)
                out.println(y);
            else {
                int[] ar = new int[n];
                for(int i=0; i<x; i++) {
                    ar[i] = y;
                }
                int s = y*(x-1);
                int a = s+y;
                int p = 1;
                ar[x] = a;
                for(int i=x+1; i<n; i++) {
                    a = a+s;
                    ar[i] = a;
                    s = a-ar[p];
                    p++;
                }
                
                out.println(a);
            }
            
        }
    }
    
    final static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}
