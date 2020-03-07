package com.ashok.learnings.designpatterns.memento;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class ObjectHistoryManager<T, K extends Memorable<T>> {
    private final Caretaker<T> caretaker = new Caretaker<>();
    private final K originator;

    public ObjectHistoryManager(K originator) {
        this.originator = originator;
    }

    public void undo() {
        originator.restore(caretaker.undo());
        log("undo");
    }

    public void redo() {
        originator.restore(caretaker.redo());
        log("redo");
    }

    public void refresh() {
        caretaker.add(originator.createMemento());
    }

    @Override
    public String toString() {
        return originator.toString();
    }

    private void log(String operation) {
        System.out.println("performing " + operation + ", current value: " + originator);
    }
}
