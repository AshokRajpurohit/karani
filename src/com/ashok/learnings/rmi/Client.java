package com.ashok.learnings.rmi;

import com.ashok.codejam.template.InputReader;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Client {
    private Client() {
    }

    public static void main(String[] args) {
        InputReader in = new InputReader();
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            GcdInterface stub = (GcdInterface) registry.lookup("Hello");
            while (true) {
                System.out.println(stub.gcd(in.readInt(), in.readInt()));
                System.out.println(stub.gcd(in.readLong(), in.readLong()));
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
