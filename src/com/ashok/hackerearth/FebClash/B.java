package com.ashok.hackerearth.FebClash;


import java.io.*;
import java.util.StringTokenizer;

public class B {
    
    private static int[] ar = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997, 1009};
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
        B b = new B();
        b.solve();
        out.close();
    }
    
    public void solve() {
        int t = in.nextInt();
        int[] lar = new int[t];
        int[] rar = new int[t];
        
        while(t>0) {
            t--;
            int l = in.nextInt();
            int r = in.nextInt();
            r = r > 1000000 ? 1000000 : r;
            int res = 0;
            if(l<=1000000)
                for(int i=l; i<=r; i++) {
                    boolean tell = process(i);
                    if(tell)
                        res++;
                }
            out.println(res);
        }
    }
    
    private boolean process(int i) {
        if(i>1000000)
            return false;
        
        if(i==1 || i==2)
            return false;
        
        int count = 0;
        int prev = 1;
        int j=0;
        
        while(j < ar.length && i!=1) {
            if(i%ar[j]==0) {
                if(prev == ar[j])
                    return false;
                prev = ar[j];
                count++;
                i = i/ar[j];
                if((count==2) && i!=1)
                    return false;
            } else {
                j++;
            }
        }
        
        if(count==1 && i!=1)
            return true;
        
        if(count!=2)
            return false;
        return true;
    }
    
    private boolean search(int n, int i, int j) {
        if(i>j)
            return false;
        
        int m = (i+j)<<1;
        if(n==ar[m])
            return true;
        else if(n > ar[m])
            return search(n,m+1,j);
        else
            return search(n,i,m-1);        
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
