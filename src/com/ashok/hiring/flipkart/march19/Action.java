package com.ashok.hiring.flipkart.march19;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Action {
    public final String name;

    Action(String name) {
        this.name = name;
    }

    public int hashCode() {
        return name.hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof Action))
            return false;

        return name.equals(((Action) o).name);
    }
}
