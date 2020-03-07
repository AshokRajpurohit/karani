package com.ashok.learnings.designpatterns.memento;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class TextManager {
    private final Originator originator = new Originator();
    private final ObjectHistoryManager<Memento, Originator> originatorObjectHistoryManager = new ObjectHistoryManager<>(originator);

    public void undo() {
        originatorObjectHistoryManager.undo();
        log("undo");
    }

    public void redo() {
        originatorObjectHistoryManager.redo();
        log("redo");
    }

    public void update(String newText) {
        originator.set(newText);
        originatorObjectHistoryManager.refresh();
    }

    @Override
    public String toString() {
        return originator.toString();
    }

    private void log(String operation) {
        System.out.println("performing " + operation + ", current value: " + originator);
    }

}
