package com.ashok.hackerearth.FebClash;


import java.io.*;
import java.util.StringTokenizer;

public class A {
    
    private static InputReader in;
    private static PrintWriter out;
    private static int mod = 1000000007;
    private static int[] ar;
    
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
            int n = in.nextInt();
            int p = in.nextInt();
            ar = new int[n];
            ar[n-1] = 2;
            if(p==1)
                out.println(1);
            else {
                for(int i=2; i<p; i++) {
                    ar[n-i] = ar[n+1-i]*2;
                    ar[n-i] = ar[n-i]%mod;
                }
                int res = process(p,1);
                out.println(res);
            }
        }
    }
    
    public int process(int p, int i) {
        if(i>=ar.length)
            return 1;
        
        if(ar[i]!=0)
            return ar[i];
        
        int res = process(p,i+p);
        for(int j=1; j < p; j++ ) {
            res = res + process(p,i+j);
        }
        res = res % mod;
        ar[i] = res;
        return res;
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
