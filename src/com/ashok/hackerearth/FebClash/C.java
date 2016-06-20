package com.ashok.hackerearth.FebClash;


import java.io.*;
import java.util.StringTokenizer;

public class C {
    
    private static InputReader in;
    private static PrintWriter out;
    private static int mod = 1000000007;
    
    public C() {
        super();
    }

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream);
        C c = new C();
        c.solve();
        out.close();
    }
    
    public void solve() {
        int t = in.nextInt();
        while(t>0) {
            t--;
            long n = in.nextInt();
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            int d = in.nextInt();
            
            int e = a+b+c+d;
            n = n-e;
            if(n==0)
                out.println(1);
            else if(n<0)
                out.println(0);
            else {
                n = n>>1;
                n = n+3;
                long n1 = n-1;
                long n2 = n-2;
                
                if((n&1)==0)
                    n = n>>1;
                else
                    n1 = n1>>1;
                
                if(n%3==0)
                    n = n/3;
                else if(n1%3==0)
                    n1 = n1/3;
                else if(n2%3==0)
                    n2 = n2/3;
                
                n = n%mod;
                n1 = n1%mod;
                n2 = n2%mod;
                
                long res = 1;
                res = n*n1;
                res = res%mod;
                res = res*n2;
                res = res%mod;
                out.println(res);
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
