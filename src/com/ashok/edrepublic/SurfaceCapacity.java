package com.ashok.edrepublic;


public class SurfaceCapacity {
    private SurfaceCapacity() {
        super();
    }
    
    public static int solve(int capacity, int count) {
        count = count << 1;
        count = count + capacity - 1;
        return 5 * (count / capacity);        
    }
}
