package com.ashok.test;/* IMPORTANT: Multiple classes and nested static classes are supported */

/*
 * uncomment this if you want to read input.
//imports for BufferedReader
import java.io.BufferedReader;
import java.io.InputStreamReader;

//import for Scanner and other utility classes
import java.util.*;
*/

// Warning: Printing unwanted or ill-formatted data to output will cause the test cases to fail
import java.util.Arrays;
public final class SetImplementation
{

    private int[] set;
    private int size;
    private int capacity;

    public SetImplementation(int c)
    {
        capacity = c;
        set = new int[capacity];
        size = 0;
    }

    public boolean contains(int x)
    {
        boolean contains = false;
        for (int i = 0; i < capacity; i++)
        {
            if (x == set[i])
                contains = true;
            else
                contains = false;
        }
        return contains;
    }

    public void add(int x)
    {

        for (int i = 0; i < capacity; i++)
        {
            if (!contains(x))
            {
                if (size == capacity)
                {
                    set = Arrays.copyOf(set, size * 2);
                }
                if (set[i] == 0)
                {
                    set[i++] = x;
                }

            }
        }
        size++;
    }

    public boolean remove(int x)
    {
        boolean remove = false;
        for (int i = 0; i < capacity; i++)
        {
            if (x == set[i])
            {
                set[i] = set[size - 1];
                size--;
                remove = true;
            }
            if (isEmpty())
            {
                remove = false;
            }
        }
        return remove;
    }

    public void clear()
    {
        set = null;
        size = 0;
    }

    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        if (size == 0)
            return true;
        else
            return false;
    }

    public int[] toArray()
    {
        return Arrays.copyOf(set, capacity);
    }

    public static void main(String[] args)
    {
        SetImplementation s1 = new SetImplementation(5);
        s1.add(2);
        s1.add(3);
        s1.add(4);
    }
}
