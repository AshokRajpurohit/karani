package com.ashok.hackerearth.H06032015;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class A {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] prime;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        A a = new A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        StringBuilder sb = new StringBuilder();
        int t = in.readInt();

        while (t > 0) {
            t--;
            long l = in.readLong();
            long r = in.readLong();
            long count = 0;
            while(l<=r) {
                if(task(l))
                    count++;
                l++;
            }
            sb.append(count).append('\n');
        }
        out.print(sb);
    }
    
    private boolean task(long l) {
        if(l==1)
            return true;
        
        long fact = 1;
        int count = 1;
        int i = 0;
        
//        while((l&1)==0) {
//            count++;
//            l = (l>>1);
//        }
//        fact = fact*count;
        
        while(i<prime.length) {
            
            if((fact&1)==0) {
                return false;
            }
            
            if(l==1) {
                fact = fact*count;
                if((fact&1)==1)
                    return true;
                else
                    return false;
            }
            
            if(l%prime[i]==0) {
                l = l/prime[i];
                count++;
            } else {
                fact = fact*count;
                count = 1;
            }
            i++;
        }
        
        if((fact&1) == 1)
            return true;
        return false;
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

        public int readInt() throws IOException {
            int number = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            if (bufferSize == -1)
                throw new IOException("No new bytes");
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                number = (number << 3) + (number << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            return number * s;
        }

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }
    }
}
