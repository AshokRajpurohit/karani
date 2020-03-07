package com.ashok.learnings.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public interface GcdInterface extends Remote {
    int gcd(int a, int b) throws RemoteException;

    long gcd(long a, long b) throws RemoteException;
}
