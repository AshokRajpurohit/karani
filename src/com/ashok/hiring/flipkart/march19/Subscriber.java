package com.ashok.hiring.flipkart.march19;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Subscriber {
    public final String name;
    FiniteStateMachine producer;

    Subscriber(String name) {
        this.name = name;
    }

    public int hashCode() {
        return name.hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof Subscriber))
            return false;

        return name.equals(((Subscriber) o).name);
    }

    public void start() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + ": " + name + "\t" + producer.get(this));
        }
    }
}
