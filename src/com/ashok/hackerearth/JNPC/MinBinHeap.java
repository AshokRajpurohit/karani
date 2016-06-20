package com.ashok.hackerearth.JNPC;


class MinHeap {
    int[] m;
    int count = 0;
    int sum = 0;

    public MinHeap(int k) {
        m = new int[k];
    }

    public void add(int i) {
        if (count < m.length) {
            m[count] = i;
            count++;
            perup();
        } else {
            if (i <= m[0])
                return;
            m[0] = i;
            perdown();
        }
    }

    public int top() {
        return m[0];
    }

    private void perup() {
        int i = count - 1;
        while (i > 0) {
            if (m[(i - 1) >> 1] < m[i]) {
                int temp = m[i];
                m[i] = m[(i - 1) >> 1];
                m[(i - 1) >> 1] = temp;
                i = (i - 1) >> 2;
            } else
                break;
        }
    }

    private void perdown() {
        int i = 1;
        int temp;
        while (i < count) {
            //Do percolate down based on current i(child index)
            if (i < count - 1)
                i = m[i] > m[i + 1] ? i : i + 1;
            if (m[i] <= m[(i - 1) >> 1])
                break;
            if (m[i] > m[(i - 1) >> 1]) {
                temp = m[i];
                m[i] = m[(i - 1) >> 1];
                m[(i - 1) >> 1] = temp;
            }
            System.out.println(" perdown: " + count + "," + i);
            i = (i << 1) + 1;
            System.out.println(" perdown: " + count + "," + i);
        }
    }
}
