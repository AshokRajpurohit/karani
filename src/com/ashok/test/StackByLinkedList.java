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

class StackByLinkedList
{
    Node head = null;
    int MAX = 1000;
    int top = -1;

    class Node
    {
        int data;
        Node next;

        Node(int data)
        {
            this.data = data;
        }
    }

    public boolean push(int element)
    {
        if (top < MAX - 1)
        {
            top++;
            Node newNode = new Node(element);

            Node temp = head;
            if (temp == null)
            {
                head = newNode;
            } else
            {
                while (temp.next != null)
                {
                    temp = temp.next;
                }
                temp.next = newNode;
            }

            // System.out.println("Element pushed :" + element);
            return true;
        } else
        {
            // System.out.println("Stack overflow");
            return false;
        }
    }

    public int pop()
    {
        int poppedElement = -1;
        if (top > -1)
        {

            Node temp = head;
            Node prev = head;
            if (temp.next != null)
            {
                temp = temp.next;
                while (temp.next != null)
                {
                    prev = prev.next;
                    temp = temp.next;
                }

                poppedElement = temp.data;
                prev.next = null;

            } else
            {
                poppedElement = temp.data;
            }

            top--;

            if (top == -1)
            {
                head = null;
            }

        } /*
           * else { //System.out.println("Stack underflow"); }
           */
        return poppedElement;
    }

    public int peekOrTop()
    {
        int elementAtTop = -1;
        if (top > -1)
        {
            Node temp = head;
            while (temp.next != null)
            {
                temp = temp.next;
            }
            elementAtTop = temp.data;
        } /*
           * else { System.out.println("Stack underflow"); }
           */
        return elementAtTop;
    }

    public boolean isEmpty()
    {
        return head == null ? true : false;
    }

    public static void main(String args[]) throws Exception
    {
        /*
         * Sample code to perform I/O: Use either of these methods for input
         * 
         * //BufferedReader BufferedReader br = new BufferedReader(new
         * InputStreamReader(System.in)); String name = br.readLine(); //
         * Reading input from STDIN System.out.println("Hi, " + name + "."); //
         * Writing output to STDOUT
         * 
         * //Scanner Scanner s = new Scanner(System.in); String name =
         * s.nextLine(); // Reading input from STDIN System.out.println("Hi, " +
         * name + "."); // Writing output to STDOUT
         * 
         */

        // Write your code here

        StackByLinkedList stack = new StackByLinkedList();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.peekOrTop();

        stack.pop();
        stack.pop();
        stack.pop();

        stack.isEmpty();

    }
}
