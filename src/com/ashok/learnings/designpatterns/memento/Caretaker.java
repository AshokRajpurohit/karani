package com.ashok.learnings.designpatterns.memento;

import java.util.ArrayList;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Caretaker<T> {
    private ArrayList<T> objects = new ArrayList<>();
    private int currentIndex = 0, size = 0;

    T redo() {
        if (currentIndex >= size) {
            System.out.println("there are no operations to redo");
            throw new UnsupportedOperationException("there are no operations for redo");
        }

        return objects.get(currentIndex++);
    }

    T undo() {
        if (currentIndex <= 1)
            throw new UnsupportedOperationException("no operations for undo");

        currentIndex--;
        return objects.get(currentIndex - 1);
    }

    public void add(T t) {
        if (currentIndex < objects.size())
            objects.set(currentIndex, t);
        else
            objects.add(t);

        currentIndex++;
        size = currentIndex;
    }
}
