package com.ashok.learnings.designpatterns.memento;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
interface Memorable<T> {

    T createMemento();

    void restore(T memento);

}
