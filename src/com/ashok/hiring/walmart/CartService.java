package com.ashok.hiring.walmart;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class CartService {
    private Set<Cart> carts = ConcurrentHashMap.newKeySet();
    public static final Comparator<Cart> SORT_BY_COUNT = (a, b) -> a.getItemCount() - b.getItemCount();
    public static final Comparator<Cart> SORT_BY_TOTAL_PRICE = (a, b) -> Double.compare(a.getTotalPrice(), b.getTotalPrice());

    public Collection<Cart> cartsInStatus(CartStatus cartStatus, Comparator<Cart> cartComparator) {
        return getCarts(t -> t.getStatus() == cartStatus, cartComparator);
    }

    public void addCart(Cart cart) {
        carts.add(cart);
    }

    public Cart newCart() {
        Cart cart = new Cart();
        carts.add(cart);
        return cart;
    }

    public void putOrder(Cart cart) {
        cart.putOrder();
    }

    public void cancleOrder(Cart cart) {
        cart.cancleOrder();
    }

    public Collection<Cart> getCarts(Predicate<Cart> cartPredicate, Comparator<Cart> comparator) {
        return carts.stream().filter(cart -> cartPredicate.test(cart)).sorted(comparator).collect(Collectors.toList());
    }

    public Collection<Cart> getCarts(Date start, Date end) {
        return carts.stream().filter(c -> c.date.compareTo(start) > 0).filter(c -> c.date.compareTo(end) < 0).collect(Collectors.toList());
    }

    public Collection<Cart> getCarts(Category category) {
        return carts.stream().filter(c -> c.containsCategory(category)).collect(Collectors.toList());
    }

    public Map<CartStatus, List<Cart>> getCartsInGroup(Category category) {
        return getCarts(category).stream().collect(Collectors.groupingBy(Cart::getStatus));
    }
}
