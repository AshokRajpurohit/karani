package com.ashok.learnings.designpatterns.memento;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class TestMemento {

    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        TextManager textManager = new TextManager();

        while (true) {
            char operation = in.read().toLowerCase().charAt(0);
            switch (operation) {
                case 's':
                    requestForText();
                    textManager.update(in.readLine());
                    break;
                case 'u':
                    textManager.undo();
                    break;
                case 'r':
                    textManager.redo();
                    break;
            }
            out.println(textManager);
            out.flush();
        }
    }

    private static void requestForText() {
        out.println("Enter the text to be updated");
        out.flush();
    }
}
