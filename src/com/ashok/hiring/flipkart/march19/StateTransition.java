package com.ashok.hiring.flipkart.march19;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class StateTransition {
    public final MachineState from, to;
    public static final StateTransition ALL_TRANSITIONS = new StateTransition();

    StateTransition(MachineState from, MachineState to) {
        this.from = from;
        this.to = to;
    }

    private StateTransition() {
        from = null;
        to = null;
    }

    public boolean equals(Object o) {
        if (!(o instanceof StateTransition))
            return false;

        StateTransition transition = (StateTransition) o;
        return from.equals(transition.from) && to.equals(transition.to);
    }
}
