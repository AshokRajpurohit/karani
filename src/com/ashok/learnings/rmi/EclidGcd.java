package com.ashok.learnings.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class EclidGcd extends UnicastRemoteObject implements GcdInterface{
    protected EclidGcd() throws RemoteException {
//        super(10010);
    }

    public static void main(String[] args) {
        try {
            EclidGcd obj = new EclidGcd();
            GcdInterface stub = (GcdInterface) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("Hello", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public int gcd(int a, int b) {
        int value = euclid(a, b);
        System.out.println("lo bhai ab " + a + " and " + b + " k liye: " + value);
        return value;
    }

    @Override
    public long gcd(long a, long b) {
        long value = euclid(a, b);
        System.out.println("lo bhai ab " + a + " and " + b + " k liye: " + value);
        return value;
    }

    private static int euclid(int a, int b) {
        return a == 0 ? b : euclid(b % a, a);
    }

    private static long euclid(long a, long b) {
        return a == 0 ? b : euclid(b % a, a);
    }
}
