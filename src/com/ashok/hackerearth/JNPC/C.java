package com.ashok.hackerearth.JNPC;


import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class C {
    
    private static int[][] mat;
    private static HashMap<Integer,Long> hm = new HashMap<Integer, Long>(500);
    private static InputReader in;
    private static PrintWriter out;
    
    public C() {
        super();
    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream);
        C a = new C();
        a.solve();
        out.close();
    }
    
    public void solve() throws IOException {
        int t = in.nextInt();
        while(t>0) {
            t--;
            int n = in.nextInt();
            mat = new int[n][n];
            for(int i=0; i<n; i++) {
                for(int j=0; j<n; j++)
                    mat[i][j] = in.nextInt();
            }
            long res = process(0,0,n);
            out.println(res);
            hm.clear();
        }
    }
    
    private long process(int ti, int tj, int len) {
        if(len==1)
            return mat[ti][tj];
        
        if(hm.containsKey(hfun(ti,tj,len)))
            return hm.get(hfun(ti,tj,len));
        
        long t1 = mat[ti][tj];
        long a = process(ti+1,tj,len-1);
        long b = process(ti,tj+1,len-1);
        long c = process(ti+1,tj+1,len-1);
        a = a > b ? a : b;
        a = a > c ? a : c;
        t1 = a+t1;
        
        long t2 = mat[ti+len-1][tj];
        a = process(ti,tj,len-1);
        b = process(ti+1,tj+1,len-1);
        c = process(ti,tj+1,len-1);
        
        a = a > b ? a : b;
        a = a > c ? a : c;
        t2 = a+t2;
        
        long t3 = mat[ti+len-1][tj+len-1];
        a = process(ti+1,tj,len-1);
        b = process(ti,tj,len-1);
        c = process(ti,tj+1,len-1);
        
        a = a > b ? a : b;
        a = a > c ? a : c;
        t3 = t3+a;
        
        long t4 = mat[ti][tj+len-1];
        a = process(ti,tj,len-1);
        b = process(ti+1,tj+1,len-1);
        c = process(ti+1,tj,len-1);
        
        a = a > b ? a : b;
        a = a > c ? a : c;
        t4 = t4+a;
        
        t1 = t1 > t2 ? t1 : t2;
        t1 = t1 > t3 ? t1 : t3;
        t1 = t1 > t4 ? t1 : t4;
        
        hm.put(hfun(ti,tj,len), t1);
        return t1;
    }
    
    private int hfun(int i, int j, int k) {
        return (i<<14)+(j<<7)+k;
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
