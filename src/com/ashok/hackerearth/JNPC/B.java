package com.ashok.hackerearth.JNPC;


import java.io.*;
import java.util.StringTokenizer;

public class B {
    
    private static InputReader in;
    private static PrintWriter out;
    
    public B() {
        super();
    }

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream);
        B a = new B();
        a.solve();
        out.close();
    }
    
    public void solve() {
        int t = in.nextInt();
        int[] ars = new int[t];
        int[] ar = new int[t];
        
        ar[0] = in.nextInt();
        ars[0] = ar[0];
        
        for(int i=1; i<t; i++) {
            ar[i] = in.nextInt();
            ars[i] = ars[i-1]+ar[i];
        }
        
        int q = in.nextInt();
        while(q>0) {
            q--;
            int a = in.nextInt();
            if(a>ars[t-1])
                out.println(-1);
            else if(a==ars[t-1])
                out.println(t);
            else if(a<=ar[0])
                out.println(1);
            else {
                int i=0;
                int j=t-1;
                while(i!=j) {
                    int m = (i+j)>>1;
                    if(a>ars[m]) {
                        i = m+1;
                    } else if(a<ars[m]) {
                        j = m-1;
                    } else {
                        i=m;
                        j=m;
                    }
                }
                if(ars[i]==a)
                    out.println(i+1);
                else if(ars[i]<a) {
                    if(ars[i+1] > a)
                        out.println(i+2);
                    else
                        out.println(i+3);
                }
                else {
                    if(a>ars[i-1])
                        out.println(i+1);
                    else
                        out.println(i);
                }
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
