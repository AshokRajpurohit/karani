package com.ashok.hiring.flipkart;

/**
 * Menu item for restaurent. Restaurent chef prepare one item at a time,
 * so this would be the limiting factor for restaurent capacity.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class MenuItem {
    final long id;
    final String name;
    private int price;

    final Restaurent servingRestaurent;

    MenuItem(long id, String name, int price, Restaurent restaurent) {
        this.id = id;
        this.name = name;
        this.price = price;
        servingRestaurent = restaurent;
        restaurent.items.add(this);
    }

    public int getPrice() {
        return price;
    }

    public int hashCode() {
        return Long.hashCode(id);
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Restaurent))
            return false;

        return id == ((Restaurent) o).id;
    }
}
