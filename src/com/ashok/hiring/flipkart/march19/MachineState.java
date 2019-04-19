package com.ashok.hiring.flipkart.march19;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class MachineState {
    public final String name;

    MachineState(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public int hashCode() {
        return name.hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof MachineState))
            return false;

        return name.equals(((MachineState) o).name);
    }
}
