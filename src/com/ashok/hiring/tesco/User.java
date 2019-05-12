package com.ashok.hiring.tesco;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class User {
    final String firstName, lastName;
    final String address;
    public int rewards = 0;

    public User(String firstName, String lastName, String address, int rewards) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.rewards = rewards;
    }

    public int hashCode() {
        return Long.hashCode(1L * firstName.hashCode() * lastName.hashCode());
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof User)) return false;
        if (o == this) return true;
        return firstName.equals(((User) o).firstName) && lastName.equals(((User) o).lastName);
    }
}
