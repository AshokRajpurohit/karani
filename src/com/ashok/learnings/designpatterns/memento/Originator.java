package com.ashok.learnings.designpatterns.memento;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Originator implements Memorable<Memento> {
    private String article;

    public void set(String newArticle) {
        System.out.println("From Originaltor: current version of article\n" + newArticle);
        article = newArticle;
    }

    @Override
    public Memento createMemento() {
        System.out.println("From Originator: creating memento");
        return new Memento(article);
    }

    @Override
    public void restore(Memento memento) {
        System.out.println("Saving from memento: " + memento.article);
        set(memento.article);
    }

    public String toString() {
        return article;
    }
}
