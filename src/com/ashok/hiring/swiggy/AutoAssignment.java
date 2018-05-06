/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.swiggy;

import com.ashok.lang.geometry.SphericalCoordinate;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * Problem Name: Swiggy Auto Assignment
 * Link: Onsite Machine Round
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AutoAssignment {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        AutoAssignment a = new AutoAssignment();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            String ordersString = in.readLine();
            String deliveryExecutivesString = in.readLine();
            OrderModel[] orderModels = orders(ordersString);
            Order[] orders = toOrders(orderModels);
            DeliveryExecutiveModel[] executiveModels = deliveryExecutives(deliveryExecutivesString);
            DeliveryExecutive[] executives = toDeliveryExecutives(executiveModels);

            List<Assignment> assignments = makeAssignments(orders, executives);
            assignments.forEach((e) -> out.println("orderId: " + e.order.id + ", executiveId: " + e.executive.id));

            Gson gson = new GsonBuilder().create();
            assignments.stream().map((e) -> new AssignmentModel(e)).toArray();
            out.println(gson.toJson(assignments.stream().map((e) -> new AssignmentModel(e)).toArray()));

            out.println(orderModels);
            out.flush();
        }
    }

    private static List<Assignment> makeAssignments(Order[] orders, DeliveryExecutive[] executives) {
        int on = orders.length, en = executives.length, index = 0;
        PriorityQueue<Assignment> queue = new PriorityQueue<>(ASSIGNMENT_COMPARATOR.reversed());
        for (Order order : orders)
            for (DeliveryExecutive executive : executives)
                queue.add(new Assignment(order, executive));

        Set<Order> mappedOrders = new HashSet<>();
        Set<DeliveryExecutive> mappedExecutives = new HashSet<>();
        List<Assignment> assignments = new LinkedList<>();
        for (Assignment assignment : queue) {
            if (mappedExecutives.contains(assignment.executive) || mappedOrders.contains(assignment.order))
                continue;

            mappedExecutives.add(assignment.executive);
            mappedOrders.add(assignment.order);
            assignments.add(assignment);
        }

        return assignments;
    }

    private static Order[] toOrders(OrderModel[] models) {
        int n = models.length;
        Order[] orders = new Order[n];
        for (int i = 0; i < n; i++)
            orders[i] = models[i].toOrder();

        return orders;
    }

    private static DeliveryExecutive[] toDeliveryExecutives(DeliveryExecutiveModel[] models) {
        int n = models.length;
        DeliveryExecutive[] executives = new DeliveryExecutive[n];
        for (int i = 0; i < n; i++)
            executives[i] = models[i].toDeliveryExecutive();

        return executives;
    }

    private static DeliveryExecutiveModel[] deliveryExecutives(String json) {
        Gson gson = new GsonBuilder().create();
        DeliveryExecutiveModel[] models = gson.fromJson(new StringReader(json), DeliveryExecutiveModel[].class);
        return models;
    }

    private static OrderModel[] orders(String json) {
        Gson gson = new GsonBuilder().create();
        OrderModel[] orders = gson.fromJson(new StringReader(json), OrderModel[].class);
        return orders;
    }

    static class OrderModel {
        long id;
        String ordered_time;
        String restaurant_location;

        public Order toOrder() {
            long time = Long.valueOf(ordered_time.substring(1));
            return new Order(id, time, getCoordinates(restaurant_location));
        }
    }

    private static Coordinates getCoordinates(String locationString) {
        String[] locationStrings = locationString.split(",");
        double lat = Double.valueOf(locationStrings[0].substring(3));
        double longitude = Double.valueOf(locationStrings[1].substring(4));
        Coordinates coordinates = new SphericalCoordinate(longitude, lat);
        return coordinates;
    }

    static class DeliveryExecutiveModel {
        long id;
        String current_location;
        String last_order_delivered_time;

        public DeliveryExecutive toDeliveryExecutive() {
            long time = Long.valueOf(last_order_delivered_time.substring(1));
            return new DeliveryExecutive(id, getCoordinates(current_location), time);
        }
    }

    static class AssignmentsModel {
        AssignmentModel[] assignments;
    }

    static class AssignmentModel {
        long order_id, de_id;

        AssignmentModel(Assignment assignment) {
            order_id = assignment.order.id;
            de_id = assignment.executive.id;
        }
    }

    private static final Comparator<Assignment> ASSIGNMENT_DISTANCE_COMPARATOR = (a, b) -> Double.compare(a.distance, b.distance);
    private static final Comparator<Assignment> ASSIGNMENT_WAITING_TIME_COMPARATOR = (a, b) -> Double.compare(a.executive.lastOrderDeliveredTime, b.executive.lastOrderDeliveredTime);
    private static final Comparator<Assignment> ASSIGNMENT_DELAY_TIME_COMPARATOR = (a, b) -> Double.compare(a.order.orderedTime, b.order.orderedTime);

    private static final Comparator<Assignment> ASSIGNMENT_COMPARATOR = (a, b) ->
            a.distance != b.distance ? Double.compare(b.distance, a.distance) :
                    a.executive.lastOrderDeliveredTime != b.executive.lastOrderDeliveredTime ?
                            Long.compare(a.executive.lastOrderDeliveredTime, b.executive.lastOrderDeliveredTime) :
                            Long.compare(a.order.orderedTime, b.order.orderedTime);

    static class Assignment {
        final Order order;
        final DeliveryExecutive executive;
        final double distance;

        Assignment(Order order, DeliveryExecutive executive) {
            this.order = order;
            this.executive = executive;
            distance = order.restaurantLocation.distance(executive.currentLocation);
        }
    }

    static class DeliveryExecutive {
        private static long sequence = 1;
        final long id;
        Coordinates currentLocation;
        long lastOrderDeliveredTime;

        private DeliveryExecutive() {
            id = sequence++;
        }

        private DeliveryExecutive(long id, Coordinates currentLocation, long lastOrderDeliveredTime) {
            this.id = id;
            this.currentLocation = currentLocation;
            this.lastOrderDeliveredTime = lastOrderDeliveredTime;
        }

        public static synchronized DeliveryExecutive newDeliveryExecutive() {
            return new DeliveryExecutive();
        }
    }

    static class Order {
        private static long sequence = 1;
        final long id;
        final long orderedTime;
        final Coordinates restaurantLocation;
        boolean priority = false;

        private Order(long orderedTime, Coordinates restaurantLocation) {
            this(sequence++, orderedTime, restaurantLocation);
        }

        private Order(long id, long orderedTime, Coordinates coordinates) {
            this.id = id;
            this.orderedTime = orderedTime;
            restaurantLocation = coordinates;
        }

        public static synchronized Order newOrder(long orderedTime, Coordinates restaurantLocation) {
            return new Order(orderedTime, restaurantLocation);
        }
    }

    static class Restaurant {
        final int id;
        final Coordinates location;

        Restaurant(int id, Coordinates location) {
            this.id = id;
            this.location = location;
        }
    }

    static class Strategy implements Comparator<Assignment> {
        final double firstMileWeightage, waitingTimeWeightage, delayTimeWeightage;

        Strategy(double firstMileWeightage, double waitingTimeWeightage, double delayTimeWeightage) {
            this.firstMileWeightage = firstMileWeightage;
            this.delayTimeWeightage = delayTimeWeightage;
            this.waitingTimeWeightage = waitingTimeWeightage;
        }

        @Override
        public int compare(Assignment a, Assignment b) {
            double score = ASSIGNMENT_DISTANCE_COMPARATOR.compare(a, b) * firstMileWeightage
                    + ASSIGNMENT_WAITING_TIME_COMPARATOR.compare(a, b) * waitingTimeWeightage
                    + ASSIGNMENT_DELAY_TIME_COMPARATOR.compare(a, b) * delayTimeWeightage;

            return a.order.priority == b.order.priority ? Double.compare(score, 0) : 0;
        }
    }
}
